package org.likefly.editor.commands;

import java.util.HashSet;
import java.util.Set;

import org.likefly.petrinet.Arc;
import org.likefly.petrinet.ArcEdge;
import org.likefly.petrinet.ReferenceArc;
import org.likefly.petrinet.TransitionNode;


public class DeleteTransitionNodeCommand implements Command {
	
	private TransitionNode transitionNode;
	private Set<Command> deleteAllArcEdges = new HashSet<Command>();
	
	public DeleteTransitionNodeCommand(TransitionNode transitionNode) {
		this.transitionNode = transitionNode;
		Set<ArcEdge> connectedArcs = new HashSet<ArcEdge>(transitionNode.getConnectedArcEdges());
		for (ArcEdge arcEdge : connectedArcs) { //TODO: create new class DeleteArcEdgeCommand (use also DeletePlaceNodeCommand)
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
		transitionNode.getParentSubnet().removeElement(transitionNode);
	}


}
