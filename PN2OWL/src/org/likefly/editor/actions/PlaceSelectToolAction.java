package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.util.GraphicsTools;


public class PlaceSelectToolAction extends AbstractAction {
	

	private static final long serialVersionUID = -1025802827180199148L;
	private Root root;
	
	public PlaceSelectToolAction(Root root) {
		this.root = root;
		String name = "Place";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("Place.png"));
	}

	public void actionPerformed(ActionEvent e) {
		root.selectTool_Place();
	}
}
	
