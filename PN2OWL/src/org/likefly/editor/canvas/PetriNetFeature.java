

package org.likefly.editor.canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import org.likefly.editor.PNEditor;
import org.likefly.petrinet.DrawingOptions;
import org.likefly.petrinet.Element;


public class PetriNetFeature implements Feature {


	private DrawingOptions drawingOptions = new DrawingOptions();

	public PetriNetFeature(Canvas canvas) {
	}

	public void drawMainLayer(Graphics g) {
		

		for (Element element : PNEditor.getRoot().getDocument().petriNet.getCurrentSubnet().getElements()) {
			if (element.highlightColor != null) {
				Color previousColor = element.getColor();
				element.setColor(element.highlightColor);
				drawingOptions.setMarking(PNEditor.getRoot().getCurrentMarking());
				element.draw(g, drawingOptions);
				element.setColor(previousColor);
			}
			else {
				drawingOptions.setMarking(PNEditor.getRoot().getCurrentMarking());
				element.draw(g, drawingOptions);
			}
		}
	}
	
	public void drawForeground(Graphics g) {}
	public void drawBackground(Graphics g) {}
	public void mousePressed(MouseEvent event) {}
	public void mouseDragged(int x, int y) {}
	public void mouseReleased(int x, int y) {}
	public void setHoverEffects(int x, int y) {}
	public void setCursor(int x, int y) {}
	public void mouseMoved(int x, int y) {}
	
}
