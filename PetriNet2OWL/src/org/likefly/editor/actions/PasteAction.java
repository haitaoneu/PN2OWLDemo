package org.likefly.editor.actions;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.editor.commands.PasteCommand;
import org.likefly.petrinet.Element;
import org.likefly.petrinet.PetriNet;


public class PasteAction extends AbstractAction {


	private static final long serialVersionUID = 6974562204696598377L;
	private Root root;

	public PasteAction(Root root) {
		this.root = root;
		String name = "Paste";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		PetriNet petriNet = root.getDocument().petriNet;
		Set<Element> pastedElements = root.getClipboard().getContents(petriNet);
		
		root.excuteCommand(new PasteCommand(pastedElements, root.getDocument().petriNet.getCurrentSubnet(), petriNet));
		
		root.setClickedElement(null);
		root.getSelection().clear();
		root.getSelection().addAll(pastedElements);
		root.refreshAll();
	}
}
