package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.likefly.editor.Root;
import org.likefly.util.GraphicsTools;

/**
 * Arc icon
 * @author like
 *
 */
public class ArcSelectToolAction extends AbstractAction {
	

	private static final long serialVersionUID = 6954010796596389149L;
	private Root root;
	
	public ArcSelectToolAction(Root root) {
		this.root = root;
		String name = "Arc";
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("Arc.png"));	
	}

	public void actionPerformed(ActionEvent e) {
		root.selectTool_Arc();
	}
}
	