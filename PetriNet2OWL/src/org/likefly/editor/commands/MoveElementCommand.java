package org.likefly.editor.commands;

import java.awt.Point;

import org.likefly.petrinet.Element;


public class MoveElementCommand implements Command {

	private Element element;
	private Point deltaPosition;

	public MoveElementCommand(Element element, Point deltaPosition) {
		this.element = element;
		this.deltaPosition = deltaPosition;
	}
	
	public void execute() {
		element.moveBy(deltaPosition.x, deltaPosition.y);
	}


}
