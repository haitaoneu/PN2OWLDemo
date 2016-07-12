package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.util.GraphicsTools;


public class TransitionSelectToolAction extends AbstractAction {
	

	private static final long serialVersionUID = -5943097337037254320L;
	private Root root;
	
	public TransitionSelectToolAction(Root root) {
		this.root = root;
		String name = "Transition";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("Transition.png"));
	}

	public void actionPerformed(ActionEvent e) {
		root.selectTool_Transition();
	}
}