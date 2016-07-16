package org.likefly.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.likefly.editor.canvas.Canvas;

public class DrawingBoard extends JPanel {
	

	private static final long serialVersionUID = 3592970603295140986L;
	private JScrollBar verticalScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10000, 0, 10000);
	private JScrollBar horizontalScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10000, 0, 10000);
	
	public DrawingBoard(Canvas canvas) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.add(canvas, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		this.add(verticalScrollBar, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		this.add(horizontalScrollBar, constraints);

		verticalScrollBar.setUnitIncrement(30);
		horizontalScrollBar.setUnitIncrement(30);
	}

	public JScrollBar getVerticalScrollBar() {
		return verticalScrollBar;
	}

	public JScrollBar getHorizontalScrollBar() {
		return horizontalScrollBar;
	}
}
