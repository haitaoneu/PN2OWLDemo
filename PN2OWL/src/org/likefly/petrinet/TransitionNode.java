package org.likefly.petrinet;

import java.util.HashSet;
import java.util.Set;


public abstract class TransitionNode extends Node implements Cloneable{
	

	private static final long serialVersionUID = 8661902566519918996L;

	public Set<PlaceNode> getConnectedPlaceNodes() {
		Set<PlaceNode> connectedPlaceNodes = new HashSet<PlaceNode>();
		for (ArcEdge arc : getConnectedArcEdges()) {
			connectedPlaceNodes.add(arc.getPlaceNode());
		}
		return connectedPlaceNodes;
	}
}
