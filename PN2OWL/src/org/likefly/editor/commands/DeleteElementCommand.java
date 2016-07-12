package org.likefly.editor.commands;

import org.likefly.petrinet.Arc;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.Place;
import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.TransitionNode;


public class DeleteElementCommand implements Command {
	
	private Element element;
	private Command deleteElement;

	public DeleteElementCommand(Element elementToDelete) {
		this.element = elementToDelete;
		if (element instanceof Place) {
			Place place = (Place)element;
			deleteElement = new DeletePlaceNodeCommand(place);
		}
		else if (element instanceof TransitionNode) {
			TransitionNode transition = (TransitionNode)element;
			deleteElement = new DeleteTransitionNodeCommand(transition);
		}
		else if (element instanceof ReferenceArc) {
			ReferenceArc referenceArc = (ReferenceArc)element;
			deleteElement = new DeleteReferenceArcCommand(referenceArc);
		}
		else if (element instanceof Arc) {
			Arc arc = (Arc)element;
			deleteElement = new DeleteArcCommand(arc);
		}
	}
	
	public void execute() {
		if (deleteElement != null) {
			deleteElement.execute();
		}
	}

}
