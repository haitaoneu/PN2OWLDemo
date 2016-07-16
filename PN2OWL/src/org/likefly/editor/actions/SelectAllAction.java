package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.PNEditor;
import org.likefly.editor.canvas.Selection;
import org.likefly.petrinet.PetriNet;

/**
 *
 * @author must
 */
public class SelectAllAction extends AbstractAction {


	private static final long serialVersionUID = -6777442353734974294L;

	public SelectAllAction() {
		String name = "Select All";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		setEnabled(false);
	}

    @Override
	public void actionPerformed(ActionEvent e) {
		PetriNet petriNet = PNEditor.getRoot().getDocument().getPetriNet();
        
        Selection selection = PNEditor.getRoot().getSelection();
        selection.clear();
        selection.addAll(petriNet.getCurrentSubnet().getElements());
        PNEditor.getRoot().refreshAll();
	}

}
