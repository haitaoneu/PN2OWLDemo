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
	 * parentComponent:���Ի���ĸ� Component
	 * message: Ҫ��ʾ�� Object
	 * title: Ҫ�ڶԻ���ı���������ʾ�� String
	 * messageType: Ҫ��ʾ����Ϣ���ͣ�ERROR_MESSAGE��INFORMATION_MESSAGE��WARNING_MESSAGE��QUESTION_MESSAGE �� PLAIN_MESSAGE
	 * icon: Ҫ��ʾ�� Icon ͼ��
	 * selectionValues: ��������ѡ��� Object ����
	 * initialSelectionValue: ���ڳ�ʼ�������ֶε�ֵ
	 */
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
					TextArea midresult=(TextArea) root.getResultArea().getComponent(1);
					TextArea result=(TextArea) root.getResultArea().getComponent(3);	
					String encoding="GBK";
					
					File midresultfile=new File("D:\\export.txt");
					if(midresultfile.isFile() && midresultfile.exists()){ //�ж��ļ��Ƿ����
						
						midresult.setText(null);
						
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(midresultfile),encoding);//���ǵ������ʽ
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
					if(resultfile.isFile() && resultfile.exists()){ //�ж��ļ��Ƿ����
						
						result.setText(null);
						
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(resultfile),encoding);//���ǵ������ʽ
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
