package org.likefly.editor.commands;

import org.likefly.petrinet.Arc;
import org.likefly.petrinet.Net;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.Transition;



public class AddArcCommand implements Command {

	private Net parentNet;
	private PlaceNode placeNode;
	private Transition transition;
	private boolean placeToTransition;
	
	public AddArcCommand(PlaceNode placeNode, Transition transition, boolean placeToTransition) {
		this.parentNet = placeNode.getParentSubnet();
		this.placeNode = placeNode;
		this.transition = transition;
		this.placeToTransition = placeToTransition;
	}
	
	public void execute() {
		Arc createdArc = new Arc(placeNode, transition, placeToTransition);
		parentNet.addElement(createdArc);
	}

}
