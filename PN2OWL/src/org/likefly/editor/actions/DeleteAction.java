package org.likefly.editor.actions;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.editor.commands.DeleteElementsCommand;
import org.likefly.petrinet.Element;


public class DeleteAction extends AbstractAction {

	private static final long serialVersionUID = 4280541460572867810L;
	private Root root;
	
	public DeleteAction(Root root) {
		this.root = root;
		String name = "Delete";
		putValue(NAME, name);
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) { 
		Set<Element> elementsToDelete = new HashSet<Element>();

		for (Element selectedElement : root.getSelection()) {
			elementsToDelete.add(selectedElement);
		}
		
		root.getSelection().clear();
		
		if (root.getClickedElement() != null) {
			elementsToDelete.add(root.getClickedElement());
			root.setClickedElement(null);
		}

		if ( !elementsToDelete.isEmpty()) {
			root.excuteCommand(new DeleteElementsCommand(elementsToDelete));
		}
	}
}
