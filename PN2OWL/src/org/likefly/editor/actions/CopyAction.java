package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;


public class CopyAction extends AbstractAction {
	

	private static final long serialVersionUID = 8142511822844888992L;
	private Root root;
	
	public CopyAction(Root root) {
		this.root = root;
		String name = "Copy";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		root.getClipboard().setContents(root.getSelectedElementsWithClickedElement(), root.getDocument().petriNet);
		root.refreshAll();
	}
}
