package org.likefly.editor;

import java.awt.Frame;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.likefly.editor.canvas.Selection;
import org.likefly.editor.commands.Command;
import org.likefly.petrinet.Document;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Marking;


public interface Root {
    public DrawingBoard getDrawingBoard();
	public Frame getParentFrame();
	public Document getDocument();
	public void setDocument(Document document);
	public Element getClickedElement();
	public void setClickedElement(Element clickedElement);
	public Selection getSelection();
	public Set<Element> getSelectedElementsWithClickedElement();
	public void selectTool_Select();
	public boolean isSelectedTool_Select();
	public void selectTool_Place();
	public boolean isSelectedTool_Place();
	public void selectTool_Transition();
	public boolean isSelectedTool_Transition();
	public void selectTool_Arc();
	public boolean isSelectedTool_Arc();
	public JPopupMenu getPlacePopup();
	public JPopupMenu getTransitionPopup();
	public JPopupMenu getArcEdgePopup();
	public JPopupMenu getCanvasPopup();
	public JPanel getResultArea();
	public void refreshAll();
	public void repaintCanvas();
	public Marking getCurrentMarking();
	public Clipboard getClipboard();
	public boolean isModified();
	public void setModified(boolean isModified);
	public String getName();
	public void excuteCommand(Command command);
}
