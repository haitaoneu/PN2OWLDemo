package org.likefly.editor.actions;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.likefly.editor.MainFrame;
import org.likefly.editor.ResultArea;
import org.likefly.editor.Root;
import org.likefly.editor.filechooser.FileChooserDialog;
import org.likefly.editor.filechooser.FileType;
import org.likefly.editor.filechooser.PNMLFileType;
import org.likefly.util.GraphicsTools;
import org.likefly.util.PetriNetException;


public class ExportAction extends AbstractAction {
	

	private static final long serialVersionUID = -7111701498431233246L;
	private Root root;

	
	public ExportAction(Root root) {
		this.root = root;
		String name = "ExportOWL";
		putValue(NAME, name);
		//putValue(SHORT_DESCRIPTION, name);
		//putValue(SMALL_ICON, GraphicsTools.getIcon("Export.png"));
		//putVaule()
	}
	
	
	/*
	 * parentComponent:　对话框的父 Component
	 * message: 要显示的 Object
	 * title: 要在对话框的标题栏中显示的 String
	 * messageType: 要显示的消息类型：ERROR_MESSAGE、INFORMATION_MESSAGE、WARNING_MESSAGE、QUESTION_MESSAGE 或 PLAIN_MESSAGE
	 * icon: 要显示的 Icon 图像
	 * selectionValues: 给出可能选择的 Object 数组
	 * initialSelectionValue: 用于初始化输入字段的值
	 */
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
	
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		
		/*ResultArea result=new ResultArea();
		TextArea textArea=result.getResult();
		System.out.println(root.getDocument());
		textArea.setText("11");
		textArea.repaint();*/
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
					TextArea midresult=(TextArea) root.getResultArea().getComponent(1);
					TextArea result=(TextArea) root.getResultArea().getComponent(3);	
					String encoding="GBK";
					
					File midresultfile=new File("D:\\export.txt");
					if(midresultfile.isFile() && midresultfile.exists()){ //判断文件是否存在
						
						midresult.setText(null);
						
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(midresultfile),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String mid = null;
	                    while((mid = bufferedReader.readLine()) != null){
	                        System.out.println(mid);
	                        //show+=lineTxt;
	                        midresult.append(mid+"\r\n");
	                        //result.
	                    }
	                    
	                 
	                    read.close();
	                   // result.setText(show);
					}	
					
					
					File resultfile=new File(file.toString());
					if(resultfile.isFile() && resultfile.exists()){ //判断文件是否存在
						
						result.setText(null);
						
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(resultfile),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        System.out.println(lineTxt);
	                        //show+=lineTxt;
	                        result.append(lineTxt+"\r\n");
	                        //result.
	                    }
	                    
	                    read.close();
	                   // result.setText(show);
					}	
					
					
					
					
					System.out.println(root.getResultArea().getComponents().length);
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
