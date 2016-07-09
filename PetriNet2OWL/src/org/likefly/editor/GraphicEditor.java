package org.likefly.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.likefly.editor.actions.ArcSelectToolAction;
import org.likefly.editor.actions.CopyAction;
import org.likefly.editor.actions.CutAction;
import org.likefly.editor.actions.DeleteAction;
import org.likefly.editor.actions.ExportAction;
import org.likefly.editor.actions.ExportPNMLAction;
import org.likefly.editor.actions.PasteAction;
import org.likefly.editor.actions.PlaceSelectToolAction;
import org.likefly.editor.actions.SelectAllAction;
import org.likefly.editor.actions.SelectionSelectToolAction;
import org.likefly.editor.actions.SetLabelAction;
import org.likefly.editor.actions.TransitionSelectToolAction;
import org.likefly.editor.canvas.Canvas;
import org.likefly.editor.canvas.Selection;
import org.likefly.editor.commands.Command;
import org.likefly.petrinet.Document;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Marking;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.ReferencePlace;
import org.likefly.petrinet.TransitionNode;
import org.likefly.util.CollectionTools;
import org.likefly.util.GraphicsTools;



public class GraphicEditor implements Root, WindowListener, ListSelectionListener{
	
    private String name;
    protected MainFrame mainFrame = new MainFrame(getNewWindowTitle());
	protected Document document = new Document();
	protected Element clickedElement = null;
	protected Selection selection = new Selection();
	protected Canvas canvas = new Canvas(this);
    protected DrawingBoard drawingBoard = new DrawingBoard(canvas);
    //protected ResultArea resultArea = new ResultArea();
    protected JPanel resultArea=new JPanel();
    
	protected JPopupMenu placePopup;
	protected JPopupMenu transitionPopup;
	protected JPopupMenu arcEdgePopup;
	protected JPopupMenu canvasPopup;
	protected JToolBar toolBar = new JToolBar();
	protected JToolBar export=new JToolBar();
	protected JToggleButton select, place, transition, arc;
	protected Action setLabel, delete;
	protected Action cutAction, copyAction, pasteAction, selectAllAction;
	
	public GraphicEditor(String name) {
        PNEditor.setRoot(this);
        this.name = name;
		setupMainFrame();
		setModified(false);
		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	@Override
	public Frame getParentFrame() {
		return mainFrame;
	}
	
	@Override
	public Document getDocument() {
		return document;
	}
	
    @Override
	public void setDocument(Document document) {
		this.document = document;
		getDocument().petriNet.resetView();
		refreshAll();
	}
		
    @Override
	public Element getClickedElement() {
		return clickedElement;
	}
    
    @Override
	public void setClickedElement(Element clickedElement) {
		this.clickedElement = clickedElement;
		enableOnlyPossibleActions();
	}
	
    @Override
	public Selection getSelection() {
		return selection;
	}

	
    @Override
	public Set<Element> getSelectedElementsWithClickedElement() {
		Set<Element> selectedElements = new HashSet<Element>();
		selectedElements.addAll(getSelection().getElements());
		selectedElements.add(getClickedElement());
		return selectedElements;
	}
	
    
    @Override
	public void valueChanged(ListSelectionEvent e) {
		enableOnlyPossibleActions();
		repaintCanvas();
	}

    @Override
	public void selectTool_Select() {
		select.setSelected(true);
		//如果是选择按钮则光标的形状是默认的。库所和变迁的光标是图片。
		canvas.activeCursor = Cursor.getDefaultCursor();
		canvas.setCursor(canvas.activeCursor);
		repaintCanvas();
	}
    
    @Override
	public boolean isSelectedTool_Select() {
		return select.isSelected();
	}
	
    @Override
	public void selectTool_Place() {
		place.setSelected(true);
		//游标用一个图标表示
		canvas.activeCursor = GraphicsTools.getCursor("place.gif", new Point(16, 16));
		canvas.setCursor(canvas.activeCursor);
		repaintCanvas();
	}
    
    @Override
	public boolean isSelectedTool_Place() {
		return place.isSelected();
	}
    
    @Override
	public void selectTool_Transition() {
		transition.setSelected(true);
		canvas.activeCursor = GraphicsTools.getCursor("Transition.png", new Point(16, 16));
		canvas.setCursor(canvas.activeCursor);
		repaintCanvas();
	}
    
    @Override
	public boolean isSelectedTool_Transition() {
		return transition.isSelected();
	}
	
    @Override
	public void selectTool_Arc() {
		arc.setSelected(true);
		canvas.activeCursor = GraphicsTools.getCursor("arc.gif", new Point(0, 0));
		canvas.setCursor(canvas.activeCursor);
		repaintCanvas();
	}
    
    @Override
	public boolean isSelectedTool_Arc() {
		return arc.isSelected();
	}
	

    @Override
	public JPopupMenu getPlacePopup() {
		return placePopup;
	}

    @Override
	public JPopupMenu getTransitionPopup() {
		return transitionPopup;
	}

    @Override
	public JPopupMenu getArcEdgePopup() {
		return arcEdgePopup;
	}


    @Override
	public JPopupMenu getCanvasPopup() {
		return canvasPopup;
	}
	
    @Override
	public JPanel getResultArea() {
		// TODO Auto-generated method stub
		return resultArea;
	}
	
    @Override
	public void refreshAll() {
		canvas.repaint();
		enableOnlyPossibleActions();
	}
	
    @Override
	public void repaintCanvas() {
		canvas.repaint();
	}
	
	/*
	 * 把所有冲突的按钮禁用
	 */
	protected void enableOnlyPossibleActions() {
		boolean isDeletable = clickedElement != null &&
			!(clickedElement instanceof ReferencePlace) ||
			!selection.isEmpty() &&
			!CollectionTools.containsOnlyInstancesOf(selection.getElements(), ReferencePlace.class);
		boolean isCutable = isDeletable;
		boolean isCopyable = isCutable;
		boolean isPastable = !clipboard.isEmpty();
		boolean isPlaceNode = clickedElement instanceof PlaceNode;
		boolean isTransitionNode = clickedElement instanceof TransitionNode;

		cutAction.setEnabled(isCutable);
		copyAction.setEnabled(isCopyable);
		pasteAction.setEnabled(isPastable);
        selectAllAction.setEnabled(true);
		delete.setEnabled(isDeletable);
		setLabel.setEnabled(isPlaceNode || isTransitionNode);
	}
	
    @Override
	public void windowClosing(WindowEvent e) {
    	System.exit(0);
	}

	
	protected void setupFrameIcons() {}

	protected void setupMainFrame() {
		
		setLabel = new SetLabelAction(this);
		delete = new DeleteAction(this);
		cutAction = new CutAction(this);
		copyAction = new CopyAction(this);
		pasteAction = new PasteAction(this);
        selectAllAction = new SelectAllAction();
        /*TextArea resultArea=new TextArea();
        resultArea.setEditable(false);
        resultArea.setSize(300, 300);*/
		Action selectTool_SelectionAction = new SelectionSelectToolAction(this);
		Action selectTool_PlaceAction = new PlaceSelectToolAction(this);
		Action selectTool_TransitionAction = new TransitionSelectToolAction(this);
		Action selectTool_ArcAction = new ArcSelectToolAction(this);
		
		
		select = new JToggleButton(selectTool_SelectionAction);
		select.setSelected(true);
		place = new JToggleButton(selectTool_PlaceAction);
		transition = new JToggleButton(selectTool_TransitionAction);
		arc = new JToggleButton(selectTool_ArcAction);
		
		select.setText("");
		place.setText("");
		transition.setText("");
		arc.setText("");
		
		/*
		 * 加入到一个按钮组里面使得每次只能按下一个按钮
		 */
		ButtonGroup drawGroup = new ButtonGroup();
		drawGroup.add(select);
		drawGroup.add(place);
		drawGroup.add(transition);
		drawGroup.add(arc);
		
		toolBar.setFloatable(false);
		
		toolBar.add(select);
		toolBar.add(place);
		toolBar.add(transition);
		toolBar.add(arc);
		toolBar.addSeparator();
		export.add(new ExportAction(this));
		//toolBar.add(new ExportAction(this));
	
		placePopup = new JPopupMenu();
		placePopup.add(setLabel);
		placePopup.addSeparator();
		placePopup.add(cutAction);
		placePopup.add(copyAction);
		placePopup.add(delete);
		
		transitionPopup = new JPopupMenu();
		transitionPopup.add(setLabel);
		transitionPopup.addSeparator();
		transitionPopup.add(cutAction);
		transitionPopup.add(copyAction);
		transitionPopup.add(delete);
		
		canvasPopup = new JPopupMenu();
		canvasPopup.add(pasteAction);
		
		arcEdgePopup = new JPopupMenu();
		arcEdgePopup.add(delete);
		
		
		resultArea.setLayout(new GridBagLayout());
		
		//JScrollBar verticalScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10000, 0, 10000);
		//JScrollBar horizontalScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10000, 0, 10000);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=40;
		constraints.ipady=20;
		//constraints.gridwidth=1;
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.anchor=GridBagConstraints.CENTER;
		resultArea.add(export.add(new ExportPNMLAction(this)), constraints);
		constraints.fill = GridBagConstraints.REMAINDER;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 10;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=60;
		constraints.ipady=180;
		constraints.insets = new Insets(0, 0, 0,0);
		TextArea midResult=new TextArea();
		//midResult.add(verticalScrollBar);
		resultArea.add(new TextArea(), constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=40;
		constraints.ipady=20;
		//constraints.gridwidth=1;
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.anchor=GridBagConstraints.CENTER;
		resultArea.add(export.add(new ExportAction(this)), constraints);
		constraints.fill = GridBagConstraints.REMAINDER;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 10;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=60;
		constraints.ipady=180;
		constraints.insets = new Insets(10, 0, 0,0);
		resultArea.add(new TextArea(), constraints);
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		splitPane.setLeftComponent(drawingBoard);
		splitPane.setRightComponent(resultArea);
		splitPane.setDividerLocation(500);
		mainFrame.add(splitPane, BorderLayout.CENTER);
		mainFrame.add(toolBar, BorderLayout.NORTH);
		mainFrame.addWindowListener(this);
		mainFrame.setLocation(20, 20);
		mainFrame.setSize(800,690);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(screenSize.width * 80 / 100, screenSize.height * 80 / 100);
		mainFrame.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public Marking getCurrentMarking() {
		return getDocument().petriNet.getInitialMarking();
	}
	
	protected Clipboard clipboard = new Clipboard();

	public Clipboard getClipboard() {
		return clipboard;
	}

	private boolean isModified = false;

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
		mainFrame.setTitle(getNewWindowTitle());
	}

	private String getNewWindowTitle() {
		return name;
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
    @Override
    public DrawingBoard getDrawingBoard() {
        return drawingBoard;
    }

	@Override
	public void excuteCommand(Command command) {
		command.execute();
		refreshAll();
		setModified(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
