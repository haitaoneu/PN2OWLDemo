package org.likefly.editor.filechooser;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author must
 */
public class FileChooserDialog extends JFileChooser {


	private static final long serialVersionUID = 1L;

	
	@Override
	public File getSelectedFile() {
		File file = super.getSelectedFile();
		if (file == null) {
			return null;
		}
		else if (file.exists() && getFileFilter().getDescription().equals("All Files")) {
			return file;
		}
		else if (getFileFilter().accept(file)) {
			return file;
		}
		else {
			return new File(file.getAbsolutePath() + "." + ((FileType)getFileFilter()).getExtension());
		}
	}
	
	
	
	public File getSelectedFilePNML() {
		
		File file = super.getSelectedFile();
		if (file == null) {
			return null;
		}
		else if (file.exists() && getFileFilter().getDescription().equals("All Files")) {
			return file;
		}
		else if (getFileFilter().accept(file)) {
			return file;
		}
		else {
			return new File(file.getAbsolutePath() + "." + ((FileType)getFileFilter()).getExtensionPNML());
		}
	}

	@Override
	public void addChoosableFileFilter(FileFilter filter) {
		super.addChoosableFileFilter(filter);
		if (getChoosableFileFilters().length > 1) {
			setFileFilter(getChoosableFileFilters()[1]);
		}
	}
}
