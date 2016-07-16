package org.likefly.petrinet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;


public abstract class PlaceNode extends Node/* implements Cloneable*/{
	

	private static final long serialVersionUID = -3935659702619458132L;

	public Arc getConnectedArc(Transition transition, boolean placeToTransition) {
		for (Arc arc : getConnectedArcs()) { 
			if (arc.getTransition() == transition && arc.isPlaceToTransition() == placeToTransition) {
				return arc;
			}
		}
		return null;
	}
	
	public Set<ArcEdge> getConnectedArcEdges(TransitionNode transitionNode) {
		Set<ArcEdge> connectedArcEdgesToTransitionNode = new HashSet<ArcEdge>();
		for (ArcEdge arc : getConnectedArcEdges()) {
			if (arc.getTransitionNode() == transitionNode) {
				connectedArcEdgesToTransitionNode.add(arc);
			}
		}
		return connectedArcEdgesToTransitionNode;
	}

	public Set<Arc> getConnectedArcs(Transition transition) {
		Set<Arc> connectedArcsToTransition = new HashSet<Arc>();
		for (Arc arc : getConnectedArcs()) {
			if (arc.getTransitionNode() == transition) {
				connectedArcsToTransition.add(arc);
			}
		}
		return connectedArcsToTransition;
	}
	

	@Override
	public void draw(Graphics g, DrawingOptions drawingOptions) {

		drawPlaceBackground(g);
		drawPlaceBorder(g);
		drawLabel(g);
	}
	
	
	protected void drawPlaceBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(getStart().x, getStart().y, getWidth(), getHeight());
	}
	
	protected void drawPlaceBorder(Graphics g) {
		g.setColor(color);
		g.drawOval(getStart().x, getStart().y, getWidth()-1, getHeight()-1);
	}

	public Set<TransitionNode> getConnectedTransitionNodes() {
		Set<TransitionNode> connectedTransitionNodes = new HashSet<TransitionNode>();
		for (ArcEdge arcEdge : getConnectedArcEdges()) {
			connectedTransitionNodes.add(arcEdge.getTransitionNode());
		}
		return connectedTransitionNodes;
	}


	
	public Place getPlace() {
		Place place;
		if (this instanceof ReferencePlace) {
			ReferencePlace referencePlace = (ReferencePlace)this;
			place = referencePlace.getConnectedPlace();
		}
		else if (this instanceof Place) {
			place = (Place)this;
		}
		else {
			throw new RuntimeException("PlaceNode which is not ReferencePlace neither Place");
		}
		return place;
	}

	public Set<Transition> getConnectedTransitionsRecursively() {
		Set<Transition> connectedTransitions = new HashSet<Transition>();
		
		for (Arc arc : getConnectedArcs()) {
			connectedTransitions.add(arc.getTransition());
		}

		return connectedTransitions;
	}
}