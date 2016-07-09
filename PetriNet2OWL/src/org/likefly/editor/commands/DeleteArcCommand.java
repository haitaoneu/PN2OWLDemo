package org.likefly.editor.commands;

import org.likefly.petrinet.Arc;

public class DeleteArcCommand implements Command {

	private Arc arc;

	
	public DeleteArcCommand(Arc arc) {
		this.arc = arc;
	}
	
	public void execute() {
		boolean isAlreadyDeleted = !arc.getParentSubnet().getElements().contains(arc);
		if ( !isAlreadyDeleted) {
			arc.getParentSubnet().removeElement(arc);
		}
	}

}
