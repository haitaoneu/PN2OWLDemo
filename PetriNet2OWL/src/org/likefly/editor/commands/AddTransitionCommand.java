package org.likefly.editor.commands;

import org.likefly.petrinet.Net;
import org.likefly.petrinet.PetriNet;
import org.likefly.petrinet.Transition;

public class AddTransitionCommand implements Command {
	
	private Net subnet;
	private int x, y;
	private PetriNet petriNet;

	public AddTransitionCommand(Net subnet, int x, int y, PetriNet petriNet) {
		this.subnet = subnet;
		this.x = x;
		this.y = y;
		this.petriNet = petriNet;
	}
	
	public void execute() {
		Transition createdTransition = new Transition();
		createdTransition.setCenter(x, y);
		petriNet.getNodeSimpleIdGenerator().setUniqueId(createdTransition);
		petriNet.getNodeLabelGenerator().setLabelToNewlyCreatedNode(createdTransition);
		subnet.addElement(createdTransition);
	}


}
