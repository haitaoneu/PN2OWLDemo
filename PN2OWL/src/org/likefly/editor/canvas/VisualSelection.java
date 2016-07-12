package org.likefly.editor.canvas;

import java.awt.Graphics;

import org.likefly.petrinet.DrawingOptions;
import org.likefly.petrinet.Element;
import org.likefly.util.GraphicsTools;

/**
 *
 * @author must
 */
class VisualSelection extends Element {
	

	private static final long serialVersionUID = -1665379133788195880L;

	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {
		g.setColor(color);
		GraphicsTools.setDashedStroke(g);
		g.drawRect(Math.min(getStart().x, getEnd().x), Math.min(getStart().y, getEnd().y),getWidth(), getHeight());
		GraphicsTools.setDefaultStroke(g);
	}
}
