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
				root.getParentFrame(), "��ѡ��OWL���﷨", null,
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
		//Ĭ��Ŀ¼���ó�����
		chooser.setCurrentDirectory(fsv.getHomeDirectory());
		
		if (chooser.showDialog(root.getParentFrame(), "����OWL") == JFileChooser.APPROVE_OPTION) {
			
			File file = chooser.getSelectedFile();
			FileType chosenFileType = (FileType)chooser.getFileFilter();
			
			/*
			 * parentComponent: ȷ����������ʾ�Ի���� Frame�����Ϊ null ���� parentComponent ������ Frame����ʹ��Ĭ�ϵ� Frame
			 * message: Ҫ��ʾ�� Object
			 * title: �Ի���ı����ַ���
			 * optionType: ָ�������ڶԻ����ѡ���������DEFAULT_OPTION��YES_NO_OPTION��YES_NO_CANCEL_OPTION �� OK_CANCEL_OPTION
			 * messageType: ָ����Ϣ�������������Ҫ����ȷ�����Կɲ�����۵�ͼ�꣺ERROR_MESSAGE��INFORMATION_MESSAGE��WARNING_MESSAGE��QUESTION_MESSAGE �� PLAIN_MESSAGE
			 * icon:��Ҫ�ڶԻ�������ʾ��ͼ��
			 * option: ָʾ�û�����ѡ��Ķ�����ɵ����飻���������������������ȷ���֣��� String ����ʹ���� toString �������֣�����˲���Ϊ null���������ȷ��ѡ��
			 * initialValue: ��ʾ�Ի����Ĭ��ѡ��Ķ���ֻ����ʹ�� options ʱ�������壻����Ϊ null
			 */
			if (!file.exists() || JOptionPane.showOptionDialog(
					root.getParentFrame(),
					"�ļ��Ѿ�����,�Ƿ񸲸�?",
					"����OWL��: " + file.getName(),
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
