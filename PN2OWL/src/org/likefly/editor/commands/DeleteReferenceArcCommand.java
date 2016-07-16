package org.likefly.editor.commands;

import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.ReferencePlace;


public class DeleteReferenceArcCommand implements Command {
	
	private ReferenceArc ReferenceArc;
	private Command deleteReferencePlace;
	
	public DeleteReferenceArcCommand(ReferenceArc referenceArc) {
		this.ReferenceArc = referenceArc;
		ReferencePlace referencePlace = referenceArc.getReferencePlace();
		deleteReferencePlace = new DeletePlaceNodeCommand(referencePlace);
	}

	public void execute() {
		ReferenceArc.getParentSubnet().removeElement(ReferenceArc);
		deleteReferencePlace.execute();
	}


}
