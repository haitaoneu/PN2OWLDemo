package org.likefly.editor;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.likefly.editor.canvas.Canvas;
import org.likefly.editor.filechooser.FileChooserDialog;
import org.likefly.editor.filechooser.FileType;
import org.likefly.editor.filechooser.PNMLFileType;
import org.likefly.util.PetriNetException;

public class ResultArea extends JPanel{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Root root;
	private JScrollBar verticalScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 10000, 0, 10000);
	private JScrollBar horizontalScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10000, 0, 10000);
	private TextArea result;
	private JButton button;
	public ResultArea() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.setButtton();
		this.setResult(400,800);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=40;
		constraints.ipady=20;
		//constraints.gridwidth=1;
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.anchor=GridBagConstraints.CENTER;
		this.add(this.getButton(), constraints);
		constraints.fill = GridBagConstraints.REMAINDER;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 10;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx=70;
		constraints.ipady=380;
		constraints.insets = new Insets(0, 0, 0,0);
		this.add(this.getResult(), constraints);

		verticalScrollBar.setUnitIncrement(30);
		horizontalScrollBar.setUnitIncrement(30);
		
	}
	
	public void setResult(int width,int height){
		this.result=new TextArea();
		this.result.setSize(width,height);
		//this.result.setText("ceshi");
	}
	public TextArea getResult(){
		return this.result;
	}
	
	@SuppressWarnings("deprecation")
	public void setButtton(){
		this.button=new JButton();
		//button.setSize(80, 40);
		button.setLabel("Export");
	}
	
	public JButton getButton(){
		return this.button;
	}
	
	public JScrollBar getVerticalScrollBar() {
		return verticalScrollBar;
	}

	public JScrollBar getHorizontalScrollBar() {
		return horizontalScrollBar;
	}
    
	
	private int chooseOWLSyntax() {
		String[] possibleValues = { "RDF/XML", "Turtle", "Manchester Syntax", "OWL/XML"};
		String owlSyntax = (String)JOptionPane.showInputDialog(
				root.getParentFrame(), "请选择OWL的语法", null,
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
		if (owlSyntax == null) {
			return 0;
		}
		else if ("RDF/XML".equals(owlSyntax)) {
			return 1;
		}
		else if ("OWL/XML".equals(owlSyntax)) {
			return 2;
		}
		else if ("Turtle".equals(owlSyntax)) {
			return 3;
		}
		return 4;
	}
	
	public void actionPerformed(ActionEvent e) {
		int owlType = chooseOWLSyntax();
		if (owlType == 0) {
			return;
		}
		
		FileChooserDialog chooser = new FileChooserDialog();
		chooser.addChoosableFileFilter(new PNMLFileType());
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileSystemView fsv = FileSystemView.getFileSystemView(); 
//		System.out.println(fsv.getHomeDirectory());
		//默认目录设置成桌面
		chooser.setCurrentDirectory(fsv.getHomeDirectory());
		
		if (chooser.showDialog(root.getParentFrame(), "导出OWL") == JFileChooser.APPROVE_OPTION) {
			
			File file = chooser.getSelectedFile();
			FileType chosenFileType = (FileType)chooser.getFileFilter();
			
			/*
			 * parentComponent: 确定在其中显示对话框的 Frame；如果为 null 或者 parentComponent 不具有 Frame，则使用默认的 Frame
			 * message: 要显示的 Object
			 * title: 对话框的标题字符串
			 * optionType: 指定可用于对话框的选项的整数：DEFAULT_OPTION、YES_NO_OPTION、YES_NO_CANCEL_OPTION 或 OK_CANCEL_OPTION
			 * messageType: 指定消息种类的整数，主要用于确定来自可插入外观的图标：ERROR_MESSAGE、INFORMATION_MESSAGE、WARNING_MESSAGE、QUESTION_MESSAGE 或 PLAIN_MESSAGE
			 * icon:　要在对话框中显示的图标
			 * option: 指示用户可能选择的对象组成的数组；如果对象是组件，则可以正确呈现；非 String 对象使用其 toString 方法呈现；如果此参数为 null，则由外观确定选项
			 * initialValue: 表示对话框的默认选择的对象；只有在使用 options 时才有意义；可以为 null
			 */
			if (!file.exists() || JOptionPane.showOptionDialog(
					root.getParentFrame(),
					"文件已经存在,是否覆盖?",
					"导出OWL到: " + file.getName(),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, null, null) == JOptionPane.YES_OPTION) {
				
				try {
					//System.out.println("Hello");
					chosenFileType.save(root.getDocument(), file, owlType);
				} catch (FileNotFoundException e4) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e4.getMessage());
				} catch (JAXBException e1) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e1.getMessage());
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e2.getMessage());
				} catch (TransformerException e3) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e3.getMessage());
				} catch (PetriNetException e5) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(root.getParentFrame(), e5.getMessage());
				}
			}
		}
	}
}
