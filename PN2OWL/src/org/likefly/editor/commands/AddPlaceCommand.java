package org.likefly.editor.commands;

import org.likefly.petrinet.Net;
import org.likefly.petrinet.PetriNet;
import org.likefly.petrinet.Place;


public class AddPlaceCommand implements Command {

	private Net subnet;
	private int x, y;

	private PetriNet petriNet;

	public AddPlaceCommand(Net subnet, int x, int y, PetriNet petriNet) {
		this.subnet = subnet;
		this.x = x;
		this.y = y;
		this.petriNet = petriNet;
	}
	
	public void execute() {
		Place createdPlace = new Place();
		createdPlace.setCenter(x, y);
		petriNet.getNodeSimpleIdGenerator().setUniqueId(createdPlace);
		petriNet.getNodeLabelGenerator().setLabelToNewlyCreatedNode(createdPlace);
		subnet.addElement(createdPlace);
	}


}
