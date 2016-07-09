package org.likefly.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.likefly.editor.Root;
import org.likefly.editor.commands.SetLabelCommand;
import org.likefly.petrinet.Node;
import org.likefly.util.GraphicsTools;

/**
 *
 * @author like
 * must
 */
public class SetLabelAction extends AbstractAction {


	private static final long serialVersionUID = 8834757318413791765L;
	private Root root;
	
	public SetLabelAction(Root root) {
		this.root = root;
		String name = "Set label";
		putValue(NAME, name);
		putValue(SMALL_ICON, GraphicsTools.getIcon("Label.gif"));
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (root.getClickedElement() != null &&
			root.getClickedElement() instanceof Node
		) {
			Node clickedNode = (Node)root.getClickedElement();
			String newLabel = JOptionPane.showInputDialog(root.getParentFrame(), "Set Label:", clickedNode.getLabel());

			if (newLabel != null && !newLabel.equals(clickedNode.getLabel())) {
				root.excuteCommand(new SetLabelCommand(clickedNode, newLabel));
			}

		}
	}
}
