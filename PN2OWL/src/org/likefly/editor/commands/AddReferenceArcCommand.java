package org.likefly.editor.commands;

import org.likefly.petrinet.Net;
import org.likefly.petrinet.PetriNet;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.ReferencePlace;


public class AddReferenceArcCommand implements Command {
	
	private Net parentSubnet;
	private PlaceNode placeNode;
	private Net nestedSubnet;
	private PetriNet petriNet;

	public AddReferenceArcCommand(PlaceNode placeNode, Net nestedSubnet, PetriNet petriNet) {
		this.parentSubnet = placeNode.getParentSubnet();
		this.placeNode = placeNode;
		this.nestedSubnet = nestedSubnet;
		this.petriNet = petriNet;
	}
	
	public void execute() {
		ReferencePlace referencePlace = new ReferencePlace(placeNode);
		referencePlace.setCenter(
			placeNode.getCenter().x - nestedSubnet.getCenter().x,
			placeNode.getCenter().y - nestedSubnet.getCenter().y);
		petriNet.getNodeSimpleIdGenerator().setUniqueId(referencePlace);
		ReferenceArc createdReferenceArc = new ReferenceArc(placeNode, nestedSubnet);

		nestedSubnet.addElement(referencePlace);
		parentSubnet.addElement(createdReferenceArc);
	}

}
