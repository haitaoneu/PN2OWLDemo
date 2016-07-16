package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.util.GraphicsTools;


public class SelectionSelectToolAction extends AbstractAction {
	

	private static final long serialVersionUID = 496325171404644447L;
	private Root root;
	
	public SelectionSelectToolAction(Root root) {
		this.root = root;
		String name = "Select";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("Select.png"));
	}

	public void actionPerformed(ActionEvent e) {
		root.selectTool_Select();
	}
}