package org.likefly.petrinet;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import org.likefly.editor.PNEditor;
import org.likefly.util.GraphicsTools;
import org.likefly.util.GraphicsTools.HorizontalAlignment;
import org.likefly.util.GraphicsTools.VerticalAlignment;


public abstract class Node extends Element implements Comparable<Node> {
	

	private static final long serialVersionUID = -1463481037735563369L;
	private String id;
	private String label;

	public Node() {
		setSize(32, 32);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Set<ArcEdge> getConnectedArcEdges() {
		Set<ArcEdge> connectedArcEdges = new HashSet<ArcEdge>();
		for (ArcEdge arcEdge : getParentSubnet().getArcEdges()) {
			if (arcEdge.getSource() == this || arcEdge.getDestination() == this) {
				connectedArcEdges.add(arcEdge);
			}
		}
		return connectedArcEdges;
	}

	public Set<Arc> getConnectedArcs() {
		Set<Arc> connectedArcs = new HashSet<Arc>();
		for (Arc arc : getParentSubnet().getArcs()) {
			if (arc.getSource() == this || arc.getDestination() == this) {
				connectedArcs.add(arc);
			}
		}
		return connectedArcs;
	}

	public Set<Arc> getConnectedArcs(boolean placeToTransition) {
		Set<Arc> connectedArcs = new HashSet<Arc>();
		for (Arc arc : getParentSubnet().getArcs()) {
			if ((arc.getSource() == this || arc.getDestination() == this) && arc.isPlaceToTransition() == placeToTransition) {
				connectedArcs.add(arc);
			}
		}
		return connectedArcs;
	}

	public Arc getConnectedArcToNode(Node node) {
		for (Arc arc : getParentSubnet().getArcs()) {
			if (arc.getSource() == this && arc.getDestination() == node) {
				return arc;
			}
		}
		return null;
	}

	public Set<Arc> getConnectedArcsToAndFromNode(Node node) {
		Set<Arc> connectedArcs = new HashSet<Arc>();
		for (Arc arc : getParentSubnet().getArcs()) {
			if (arc.getSource() == this && arc.getDestination() == node ||
				arc.getSource() == node && arc.getDestination() == this) {
				connectedArcs.add(arc);
			}
		}
		return connectedArcs;
	}

	public Set<ReferenceArc> getConnectedReferenceArcs() {
		Set<ReferenceArc> connectedReferenceArcs = new HashSet<ReferenceArc>();
		for (ReferenceArc referenceArc : getParentSubnet().getReferenceArcs()) {
			if (referenceArc.getSource() == this || referenceArc.getDestination() == this) {
				connectedReferenceArcs.add(referenceArc);
			}
		}
		return connectedReferenceArcs;
	}


	public String getLabel() {
		return label;
	}

	
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public Set<Node> getInputNodes() {
		Set<Node> inputNodes = new HashSet<Node>();
		for (Arc arc : this.getConnectedArcs()) {
			if (arc.getDestination() == this) {
				inputNodes.add(arc.getSource());
			}
		}
		return inputNodes;
	}

	public Set<Node> getOutputNodes() {
		Set<Node> outputNodes = new HashSet<Node>();
		for (Arc arc : this.getConnectedArcs()) {
			if (arc.getSource() == this) {
				outputNodes.add(arc.getDestination());
			}
		}
		return outputNodes;
	}

	public int compareTo(Node node) {
		if (this.getLabel() != null && node.getLabel() != null && !(this.getLabel().equals("") && node.getLabel().equals("")) ) {
			return this.getLabel().compareTo(node.getLabel());
		}
		else {
			return this.getId().compareTo(node.getId());
		}
	}

	protected void drawLabel(Graphics g) {
		if (getLabel() != null && !getLabel().equals("")) {
			GraphicsTools.drawString(g, getLabel(), getCenter().x, getEnd().y, HorizontalAlignment.center, VerticalAlignment.top);
		}
	}

	@Override
	public Node getClone() {
		Node node = (Node)super.getClone();
		node.label = this.label;
        PNEditor.getRoot().getDocument().getPetriNet().getNodeSimpleIdGenerator().setUniqueId(node);
		return node;
    }
}
