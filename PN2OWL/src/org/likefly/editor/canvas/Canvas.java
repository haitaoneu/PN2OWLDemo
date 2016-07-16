package org.likefly.editor.canvas;

import java.awt.Color;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.likefly.editor.PNEditor;
import org.likefly.editor.Root;
import org.likefly.petrinet.Element;
import org.likefly.util.Point;



public class Canvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	

	private static final long serialVersionUID = 2996530901766838542L;
	List<Element> highlightedElements = new ArrayList<Element>();
	Cursor alternativeCursor;
	public Cursor activeCursor;
	public List<Feature> features = new ArrayList<Feature>();
	private Root root;

	
	public Canvas(Root root) {
		
        this.root = root;
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		features.add(new DraggingFeature(this));
		features.add(new SelectionDrawingFeature(this));
		features.add(new PlaceTransitionMakerFeature(this));
		features.add(new PopupMenuFeature(this));
		features.add(new ArcFeature(this));
		features.add(new PetriNetFeature(this));
	}

    public Root getRoot() {
        return root;
    }
    
	public int getTranslationX() {
		return PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getViewTranslation().x + getWidth() / 2;
	}
	
	public int getTranslationY() {
		return PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getViewTranslation().y + getHeight() / 2;
	}
    
    public Point getViewTranslation() {
        return new Point(
                PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getViewTranslation()
                );
    }
    
    public void setViewTranslation(Point newViewTranslation) {
        PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().setViewTranslation(newViewTranslation.getPoint());
    }
	
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
		g.translate(getTranslationX(), getTranslationY());
		
		for (Feature f : features) {
			f.drawBackground(g);
		}
		for (Feature f : features) {
			f.drawMainLayer(g);
		}
		for (Feature f : features) {
		//	f.drawForeground(g);
		}
	}

	
	public void mousePressed(MouseEvent event) {
		int x = event.getX() - getTranslationX();
		int y = event.getY() - getTranslationY();
		event = new MouseEvent(
			(Component)event.getSource(),
			event.getID(),
			event.getWhen(),
			event.getModifiers(),
			x,
			y,
			event.getXOnScreen(),
			event.getYOnScreen(),
			event.getClickCount(),
			event.isPopupTrigger(),
			event.getButton());
		
		//寻找鼠标点击位置的找法是按照x和y坐标比较
		PNEditor.getRoot().setClickedElement(PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y));
		
		for (Feature f : features) {
			f.mousePressed(event);
		}
		
		if (event.getButton() == MouseEvent.BUTTON3) {
			if (PNEditor.getRoot().getClickedElement() == null) { 
				PNEditor.getRoot().selectTool_Select();
			}
		}
		
		setCursor(x, y);
		setHoverEffects(x, y);
	}
	
	public void mouseDragged(MouseEvent event) {
		int x = event.getX() - getTranslationX();
		int y = event.getY() - getTranslationY();
		
		for (Feature f : features) {
			f.mouseDragged(x, y);
		}
		setHoverEffects(x, y);
	}

	public void mouseReleased(MouseEvent evt) {
		int x = evt.getX() - getTranslationX();
		int y = evt.getY() - getTranslationY();
		
		for (Feature f : features) {
			f.mouseReleased(x, y);
		}
		setHoverEffects(x, y);
		setCursor(x, y);
	}

	public void mouseMoved(MouseEvent evt) {
		int x = evt.getX() - getTranslationX();
		int y = evt.getY() - getTranslationY();
		
		for (Feature f : features) {
			f.mouseMoved(x, y);
		}

		setHoverEffects(x, y);
		setCursor(x, y);
	}
	
	void setHoverEffects(int x, int y) {
		if (!highlightedElements.isEmpty()) {
			for (Element element : highlightedElements) {
				element.highlightColor = null;
			}
			highlightedElements.clear();
			repaint();
		}
		for (Feature f : features) {
			f.setHoverEffects(x, y);
		}
		
	}
	
	//设置鼠标的焦点在x，y
	void setCursor(int x, int y) {
		alternativeCursor = null;
		
		for (Feature f : features) {
			f.setCursor(x, y);
		}
		
		Cursor cursor;
		if (alternativeCursor != null)
			cursor = alternativeCursor;
		else
			cursor = activeCursor;
		
		if (getCursor() != cursor) {
			setCursor(cursor);
		}
	}
	
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	public void mouseWheelMoved(MouseWheelEvent arg0) {	}

}
