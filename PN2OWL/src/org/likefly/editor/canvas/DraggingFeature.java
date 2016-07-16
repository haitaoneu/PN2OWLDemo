package org.likefly.editor.canvas;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import org.likefly.editor.PNEditor;
import org.likefly.editor.commands.MoveElementCommand;
import org.likefly.editor.commands.MoveElementsCommand;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Node;


class DraggingFeature implements Feature {
	
	private Canvas canvas;
	
	DraggingFeature(Canvas canvas) {
		this.canvas = canvas;
	}
	
	private Element draggedElement = null;
	private Point deltaPosition;
	private int prevDragX;  
	private int prevDragY;  
	
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		int mouseButton = event.getButton();
		boolean doubleclick = event.getClickCount() == 2;
		
		if (!doubleclick) {
			if (mouseButton == MouseEvent.BUTTON1 &&
				PNEditor.getRoot().getClickedElement() != null &&
				(
					PNEditor.getRoot().isSelectedTool_Select() ||
					PNEditor.getRoot().isSelectedTool_Place() ||
					PNEditor.getRoot().isSelectedTool_Transition()
				) &&
				PNEditor.getRoot().getClickedElement() instanceof Node
			) {
				if (!PNEditor.getRoot().getSelection().contains(PNEditor.getRoot().getClickedElement())) {
					PNEditor.getRoot().getSelection().clear();
				}

				draggedElement = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y);
				deltaPosition = new Point();
				prevDragX = x;
				prevDragY = y;
			}
		}
	}

	public void mouseDragged(int x, int y) {
		if (draggedElement != null) {
			doTheMoving(x, y);
			canvas.repaint();  // redraw canvas to show shape in new position
			deltaPosition.translate(x - prevDragX, y - prevDragY);
			prevDragX = x;
			prevDragY = y;
		}
	}

	public void mouseReleased(int x, int y) {
		if (draggedElement != null) {
			doTheMoving(x, y);
			deltaPosition.translate(x - prevDragX, y - prevDragY);
			saveTheMoving();
			canvas.repaint();
			draggedElement = null;  // Dragging is finished.
		}
	}

	private void doTheMoving(int mouseX, int mouseY) {
		if (!PNEditor.getRoot().getSelection().isEmpty()) {
			for (Element selectedElement : PNEditor.getRoot().getSelection()) {
				selectedElement.moveBy(mouseX - prevDragX, mouseY - prevDragY);
			}
		}
		else {
			draggedElement.moveBy(mouseX - prevDragX, mouseY - prevDragY);
		}
	}
	
	private void saveTheMoving() {
		if (!deltaPosition.equals(new Point(0, 0))) {
			if (!PNEditor.getRoot().getSelection().isEmpty()) {
				for (Element selectedElement : PNEditor.getRoot().getSelection()) {
					selectedElement.moveBy(-deltaPosition.x, -deltaPosition.y); //move back to original positions
				}
				PNEditor.getRoot().excuteCommand(new MoveElementsCommand(PNEditor.getRoot().getSelection().getElements(), deltaPosition));
			}
			else {
				draggedElement.moveBy(-deltaPosition.x, -deltaPosition.y);  //move back to original position
				PNEditor.getRoot().excuteCommand(new MoveElementCommand(draggedElement, deltaPosition));
			}
		}
	}

	public void setCursor(int x, int y) {
		Element element = PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElementByXY(x, y);

		if (PNEditor.getRoot().isSelectedTool_Select() ||
			PNEditor.getRoot().isSelectedTool_Place() ||
			PNEditor.getRoot().isSelectedTool_Transition()
		) {
			if (element != null && element instanceof Node) {
				canvas.alternativeCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
			}
		}
	}
	
	public void drawForeground(Graphics g) {}
	public void drawBackground(Graphics g) {}
	public void setHoverEffects(int x, int y) {}
	public void drawMainLayer(Graphics g) {}
	public void mouseMoved(int x, int y) {}
}
