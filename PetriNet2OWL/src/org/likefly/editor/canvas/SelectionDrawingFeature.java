package org.likefly.editor.canvas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import org.likefly.editor.PNEditor;
import org.likefly.petrinet.Element;


class SelectionDrawingFeature implements Feature {
	
	private Canvas canvas;
	
	SelectionDrawingFeature(Canvas canvas) {
		this.canvas = canvas;
	}
	
	private boolean selecting = false;
	private VisualSelection visualSelection = new VisualSelection();
	private Set<Element> previousSelection = new HashSet<Element>();
	
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		int mouseButton = event.getButton();
		
		if (mouseButton == MouseEvent.BUTTON1 &&
			PNEditor.getRoot().getClickedElement() == null &&
			PNEditor.getRoot().isSelectedTool_Select()) {
	
			selecting = true;
			visualSelection.setStart(x, y);
			visualSelection.setEnd(x, y);
			canvas.repaint();
			
			//把元素添加到“选择”中去。
			
			if (event.isShiftDown()) {
				previousSelection.addAll(PNEditor.getRoot().getSelection().getElements());
			}
			else {
				PNEditor.getRoot().getSelection().clear();
				previousSelection.clear();
			}
		}
	}

	public void mouseDragged(int x, int y) {
		if (selecting) {
			visualSelection.setEnd(x, y);
			canvas.repaint();
		}
		//System.out.println("sss");
	}

	public void mouseReleased(int x, int y) {
		if (selecting) {
			selecting = false;
			canvas.repaint();
		}
	}

	public void setHoverEffects(int x, int y) {
	}
	public void drawForeground(Graphics g) {
		if (selecting) {
			visualSelection.draw(g, null);
		}
	}

	public void drawBackground(Graphics g) {}
	public void setCursor(int x, int y) {}
	public void drawMainLayer(Graphics g) {}
	public void mouseMoved(int x, int y) {}
}