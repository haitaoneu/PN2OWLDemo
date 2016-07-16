package org.likefly.petrinet;

import java.awt.Graphics;
import java.awt.Point;

import org.likefly.util.GraphicsTools;
import org.likefly.util.GraphicsTools.HorizontalAlignment;
import org.likefly.util.GraphicsTools.VerticalAlignment;


public abstract class ArcEdge extends Edge implements Cloneable{


	private static final long serialVersionUID = -78527008588767372L;

	public ArcEdge() {
	}
	
	public ArcEdge(PlaceNode placeNode, TransitionNode transitionNode, boolean placeToTransition) {
		if (placeToTransition) {
			setSource(placeNode);
			setDestination(transitionNode);
		}
		else {
			setSource(transitionNode);
			setDestination(placeNode);
		}
	}
	
	public PlaceNode getPlaceNode() {
		return isPlaceToTransition() ? (PlaceNode)getSource() : (PlaceNode)getDestination();
	}
	
	public void setPlaceNode(PlaceNode placeNode) {
		if (isPlaceToTransition()) {
			setSource(placeNode);
		}
		else {
			setDestination(placeNode);
		}
	}
	
	public TransitionNode getTransitionNode() {
		return isPlaceToTransition() ? (TransitionNode)getDestination() : (TransitionNode)getSource();
	}
	
	public void setTransitionNode(TransitionNode transition) {
		if (isPlaceToTransition()) {
			setDestination(transition);
		}
		else {
			setSource(transition);
		}
	}
	
	public boolean isPlaceToTransition() {
		return (getSource() instanceof PlaceNode);
	}
	
	public void setPlaceToTransition(boolean placeToTransition) {
		if (isPlaceToTransition() != placeToTransition) {
			reverseBreakPoints();
		}
		if (placeToTransition && getSource() instanceof TransitionNode && getDestination() instanceof PlaceNode) {
			TransitionNode transitionNode = (TransitionNode)getSource();
			PlaceNode placeNode = (PlaceNode)getDestination();
			setSource(placeNode);
			setDestination(transitionNode);
		}
		if (!placeToTransition && getSource() instanceof PlaceNode && getDestination() instanceof TransitionNode) {
			PlaceNode placeNode = (PlaceNode)getSource();
			TransitionNode transitionNode = (TransitionNode)getDestination();
			setSource(transitionNode);
			setDestination(placeNode);
		}
	}
	
	protected void drawArrow(Graphics g, Point arrowTip) {
		Point lastBreakPoint = getLastBreakPoint();
		GraphicsTools.drawArrow(g, lastBreakPoint.x, lastBreakPoint.y, arrowTip.x, arrowTip.y);
	}
	
	protected void drawMultiplicityLabel(Graphics g, Point arrowTip, int multiplicity) {
		Point labelPoint = getLabelPoint(arrowTip);
		GraphicsTools.drawString(g, Integer.toString(multiplicity), labelPoint.x, labelPoint.y, HorizontalAlignment.center, VerticalAlignment.bottom);
	}
    
    @Override
	public ArcEdge getClone() {
		ArcEdge arcEdge = (ArcEdge)super.getClone();
		arcEdge.setSource(this.getSource());
		arcEdge.setDestination(this.getDestination());
		arcEdge.setBreakPoints(this.getBreakPointsCopy());
		return arcEdge;
    }
}
