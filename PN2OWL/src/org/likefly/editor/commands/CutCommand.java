package org.likefly.editor.commands;

import java.util.Set;

import org.likefly.petrinet.Element;


public class CutCommand implements Command {
	
	private Command deleteElements;

	public CutCommand(Set<Element> elementsToDelete) {
		deleteElements = new DeleteElementsCommand(elementsToDelete);
	}

	public void execute() {
		deleteElements.execute();
	}

	
}
