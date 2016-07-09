package org.likefly.editor.commands;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.likefly.petrinet.Element;


public class MoveElementsCommand implements Command {

	private Set<Command> moveElements = new HashSet<Command>();
	
	public MoveElementsCommand(Set<Element> elements, Point deltaPosition) {
		for (Element element : elements) {
			moveElements.add(new MoveElementCommand(element, deltaPosition));
		}
	}
	
	public void execute() {
		for (Command moveElement : moveElements) {
			moveElement.execute();
		}
	}


}
