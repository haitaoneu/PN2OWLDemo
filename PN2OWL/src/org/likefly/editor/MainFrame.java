package org.likefly.editor;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class MainFrame extends JFrame {


	private static final long serialVersionUID = 2437451694097122679L;

	public MainFrame(String title) {
		
		super(title);
		
		UIManager.getDefaults().put("ToolTip.hideAccelerator", Boolean.TRUE);
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception ex) {}
	}
}