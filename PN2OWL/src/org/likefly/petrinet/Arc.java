/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.likefly.petrinet;

import java.awt.Graphics;
import java.awt.Point;


@SuppressWarnings("serial")
public class Arc extends ArcEdge implements Cloneable{


	public Arc(Node sourceNode) {
		setSource(sourceNode);
		setStart(sourceNode.getCenter().x, sourceNode.getCenter().y);
		setEnd(sourceNode.getCenter().x, sourceNode.getCenter().y);
	}

	public Arc(Node source, Node destination) {
		setSource(source);
		setDestination(destination);
	}

	public Arc(PlaceNode placeNode, Transition transition, boolean placeToTransition) {
		super(placeNode, transition, placeToTransition);
	}


	
	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {
		g.setColor(color);
		drawSegmentedLine(g);
		Point arrowTip = computeArrowTipPoint();
		drawArrow(g, arrowTip);
	}
	
	public Transition getTransition() {
		return (Transition)getTransitionNode();
	}

	@Override
	public Arc getClone() {
		Arc arc = (Arc)super.getClone();
		return arc;
    }
}
