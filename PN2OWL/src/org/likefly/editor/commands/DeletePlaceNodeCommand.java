package org.likefly.editor.commands;

import java.util.HashSet;
import java.util.Set;

import org.likefly.petrinet.Arc;
import org.likefly.petrinet.ArcEdge;
import org.likefly.petrinet.PlaceNode;
import org.likefly.petrinet.ReferenceArc;


public class DeletePlaceNodeCommand implements Command {
	
	private PlaceNode placeNode;
	private Set<Command> deleteAllArcEdges = new HashSet<Command>();
	
	public DeletePlaceNodeCommand(PlaceNode placeNode) {
		this.placeNode = placeNode;
		Set<ArcEdge> connectedArcEdges = new HashSet<ArcEdge>(placeNode.getConnectedArcEdges());
		for (ArcEdge arcEdge : connectedArcEdges) {
			if (arcEdge instanceof Arc) {
				deleteAllArcEdges.add(new DeleteArcCommand((Arc)arcEdge));
			}
			else if (arcEdge instanceof ReferenceArc) {
				deleteAllArcEdges.add(new DeleteReferenceArcCommand((ReferenceArc)arcEdge));
			}
			else {
				throw new RuntimeException("arcEdge not instanceof Arc neither ReferenceArc");
			}
		}
	}
	
	public void execute() {
		for (Command deleteArc : deleteAllArcEdges) {
			deleteArc.execute();
		}
		placeNode.getParentSubnet().removeElement(placeNode);
	}


}
