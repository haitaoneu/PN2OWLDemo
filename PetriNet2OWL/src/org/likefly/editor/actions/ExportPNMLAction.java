package org.likefly.editor.actions;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.likefly.editor.GraphicEditor;
import org.likefly.editor.Root;
import org.likefly.editor.filechooser.FileChooserDialog;
import org.likefly.editor.filechooser.FileType;
import org.likefly.editor.filechooser.PNMLFileType;
import org.likefly.util.PetriNetException;

public class ExportPNMLAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private Root root;
	
	public ExportPNMLAction(Root root) {
		// TODO Auto-generated constructor stub
		this.root = root;
		String name = "ExportPNML";
		putValue(NAME, name);
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		
		FileChooserDialog chooser = new FileChooserDialog();
		chooser.addChoosableFileFilter(new PNMLFileType());
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileSystemView fsv = FileSystemView.getFileSystemView(); 
		chooser.setCurrentDirectory(fsv.getHomeDirectory());
		
		if (chooser.showDialog(root.getParentFrame(), "导出PNML") == JFileChooser.APPROVE_OPTION) {
			
			
			File file = chooser.getSelectedFilePNML();
					
					
			FileType chosenFileType = (FileType)chooser.getFileFilter();
					
					
			
			
			
		
			if (!file.exists() || JOptionPane.showOptionDialog(
					root.getParentFrame(),
					"文件已经存在,是否覆盖?",
					"导出PNML到: " + file.getName(),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, null, null) == JOptionPane.YES_OPTION) {
				try {
					//System.out.println("Hello");
					chosenFileType.save(root.getDocument(), file, 2);
					TextArea midresult=(TextArea) root.getResultArea().getComponent(1);
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
				
					System.out.println(root.getResultArea().getComponents().length);
				} catch (FileNotFoundException e4) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e4.getMessage());
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(root.getParentFrame(), e2.getMessage());
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PetriNetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

		
	
}

