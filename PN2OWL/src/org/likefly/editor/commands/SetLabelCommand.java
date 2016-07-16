
package org.likefly.editor.commands;

import org.likefly.petrinet.Node;



public class SetLabelCommand implements Command {

	private Node node;
	private String newLabel;

	
	public SetLabelCommand(Node node, String newLabel) {
		this.node = node;
		this.newLabel = newLabel;
	}

	public void execute() {
		node.setLabel(newLabel);
	}

	
}
