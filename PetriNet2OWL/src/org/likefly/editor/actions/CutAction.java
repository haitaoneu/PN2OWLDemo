package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.editor.commands.CutCommand;


public class CutAction extends AbstractAction {


	private static final long serialVersionUID = -1797370954079277014L;
	private Root root;

	public CutAction(Root root) {
		this.root = root;
		String name = "Cut";
		putValue(NAME, name);
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		root.getClipboard().setContents(root.getSelectedElementsWithClickedElement(), root.getDocument().petriNet);
		root.excuteCommand(new CutCommand(root.getSelectedElementsWithClickedElement()));
		root.getSelection().clear();
		root.setClickedElement(null);
		root.refreshAll();
	}
}
