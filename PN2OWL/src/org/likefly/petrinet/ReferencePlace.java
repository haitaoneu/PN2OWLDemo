package org.likefly.petrinet;

import java.awt.Graphics;

import org.likefly.util.GraphicsTools;


public class ReferencePlace extends PlaceNode {

	private PlaceNode connectedPlaceNode;
	
	public ReferencePlace(PlaceNode connectedPlaceNode) {
		this.connectedPlaceNode = connectedPlaceNode;
	}
	
	
	public ReferenceArc getReferenceArc() {
		for (Element element : getParentSubnet().getParentSubnet().getElements()) {
			if (element instanceof ReferenceArc) {
				ReferenceArc referenceArc = (ReferenceArc)element;
				if (referenceArc.getPlaceNode() == connectedPlaceNode &&
					referenceArc.getTransitionNode() == getParentSubnet()) {
					return referenceArc;
				}
			}
		}
		throw new RuntimeException("ReferencePlace: missing ReferencePlace");
	}
	
	public PlaceNode getConnectedPlaceNode() {
		return connectedPlaceNode;
	}
	
	public Place getConnectedPlace() {
		PlaceNode connPlaceNode = connectedPlaceNode;
		if (connPlaceNode == null) {
			return null;
		}
		while (connPlaceNode instanceof ReferencePlace && !(connPlaceNode instanceof Place)) {
			ReferencePlace connectedReferencePlace = (ReferencePlace)connPlaceNode;
			connPlaceNode = connectedReferencePlace.getConnectedPlaceNode();
		}
		return (Place)connPlaceNode;
	}
	
	public void setConnectedPlace(PlaceNode placeNode) {
		connectedPlaceNode = placeNode;
	}

	@Override
	public String getLabel() {
		if (connectedPlaceNode == null) {
			return "";
		}
		return connectedPlaceNode.getLabel();
	}

	/**
	 * This call is redirected to the connected PlaceNode.
	 */
	@Override
	public void setLabel(String label) {
		connectedPlaceNode.setLabel(label);
	}


//	@Override
//	public boolean isStatic() {
//		return false;
////		if (connectedPlaceNode == null) {
////			return false;
////		}
////		return connectedPlaceNode.isStatic();
//	}
	
	/**
	 * This call is redirected to the connected PlaceNode.
	 */
//	@Override
//	public void setStatic(boolean isStatic) {
//		connectedPlaceNode.setStatic(isStatic);
//	}
	
	@Override
	protected void drawPlaceBorder(Graphics g) {
		GraphicsTools.setDashedStroke(g);
		super.drawPlaceBorder(g);
		GraphicsTools.setDefaultStroke(g);
	}
	
}
