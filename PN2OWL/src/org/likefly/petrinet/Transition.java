package org.likefly.petrinet;

import java.awt.Color;
import java.awt.Graphics;

import org.likefly.util.GraphicsTools;
import org.likefly.util.GraphicsTools.HorizontalAlignment;
import org.likefly.util.GraphicsTools.VerticalAlignment;


public class Transition extends TransitionNode implements Cloneable {


	private static final long serialVersionUID = -9021854008926254661L;

	public Transition() {
		super();
		setSize(16, 32);
	}
	
 	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {
		g.setColor(Color.white);
		g.fillRect(getStart().x, getStart().y, getWidth(), getHeight());
		g.setColor(color);
		g.drawRect(getStart().x, getStart().y, getWidth()-1, getHeight()-1);
		drawLabel(g);
	}

	@Override
	protected void drawLabel(Graphics g) {
		if (getLabel() != null && !getLabel().equals("")) {
			GraphicsTools.drawString(g, getLabel(), getCenter().x, getEnd().y, HorizontalAlignment.center, VerticalAlignment.top);
        }
	}
}
