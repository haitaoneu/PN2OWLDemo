package org.likefly.editor.commands;

import java.util.HashSet;
import java.util.Set;

import org.likefly.petrinet.Element;


public class DeleteElementsCommand implements Command {

	private Set<Command> deleteAllElements = new HashSet<Command>();
	
	public DeleteElementsCommand(Set<? extends Element> elementsToDelete) {
		for (Element element : elementsToDelete) {
			deleteAllElements.add(new DeleteElementCommand(element));
		}
	}
	
	public void execute() {
		for (Command deleteElement : deleteAllElements) {
			deleteElement.execute();
		}
	}


}
