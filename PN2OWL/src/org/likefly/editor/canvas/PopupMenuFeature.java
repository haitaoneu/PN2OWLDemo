package org.likefly.editor.canvas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import org.likefly.editor.PNEditor;
import org.likefly.petrinet.ArcEdge;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.Transition;


public class PopupMenuFeature implements Feature {

	private Canvas canvas;

	public PopupMenuFeature(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		int mouseButton = event.getButton();
		
		int realX = x + canvas.getTranslationX();
		int realY = y + canvas.getTranslationY();
		
		if (mouseButton == MouseEvent.BUTTON3) {
			if (PNEditor.getRoot().getClickedElement() != null &&
				(PNEditor.getRoot().isSelectedTool_Select() ||
				PNEditor.getRoot().isSelectedTool_Place() ||
				PNEditor.getRoot().isSelectedTool_Transition() ||
				PNEditor.getRoot().isSelectedTool_Arc())) {

				if (PNEditor.getRoot().getClickedElement() instanceof PlaceNode) {
					showPopup(PNEditor.getRoot().getPlacePopup(), realX, realY);
					if (!PNEditor.getRoot().getSelection().contains(PNEditor.getRoot().getClickedElement())) {
						PNEditor.getRoot().getSelection().clear();
					}
				}
				else if (PNEditor.getRoot().getClickedElement() instanceof Transition) {
					showPopup(PNEditor.getRoot().getTransitionPopup(), realX, realY);
					if (!PNEditor.getRoot().getSelection().contains(PNEditor.getRoot().getClickedElement())) {
						PNEditor.getRoot().getSelection().clear();
					}
				}
				else if (PNEditor.getRoot().getClickedElement() instanceof ArcEdge) {
					showPopup(PNEditor.getRoot().getArcEdgePopup(), realX, realY);
					if (!PNEditor.getRoot().getSelection().contains(PNEditor.getRoot().getClickedElement())) {
						PNEditor.getRoot().getSelection().clear();
					}
				}
			}
			
			if (PNEditor.getRoot().getClickedElement() == null &&
				PNEditor.getRoot().isSelectedTool_Select()
			) {
				showPopup(PNEditor.getRoot().getCanvasPopup(), realX, realY);
			}
		}
	}
	
	private void showPopup(JPopupMenu popupMenu, int clickedX, int clickedY) {
		popupMenu.show(canvas, clickedX - 10, clickedY - 2);
	}

	
	public void drawForeground(Graphics g) {}
	public void drawBackground(Graphics g) {}
	public void mouseDragged(int x, int y) {}
	public void mouseReleased(int x, int y) {}
	public void setHoverEffects(int x, int y) {}
	public void setCursor(int x, int y) {}
	public void drawMainLayer(Graphics g) {}
	public void mouseMoved(int x, int y) {}
	
}
