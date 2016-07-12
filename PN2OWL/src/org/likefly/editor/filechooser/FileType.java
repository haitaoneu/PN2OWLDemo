package org.likefly.editor.filechooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.likefly.petrinet.Document;
import org.likefly.util.PetriNetException;
import org.likefly.util.StringTools;


public abstract class FileType extends FileFilter {

	public static Set<FileType> getAllFileTypes() {
		Set<FileType> allFileTypes = new HashSet<FileType>();
		allFileTypes.add(new PNMLFileType());
		return allFileTypes;
	}

	public abstract String getExtension();
	public abstract String getName();
	public abstract String getExtensionPNML();
	public abstract String getNamePNML();
	
	public abstract void save(Document document, File file, int type) 
		throws FileNotFoundException, JAXBException, IOException, TransformerException, PetriNetException;


	public String getDescription() {
		return getName() + " (*." + getExtension() + ")";
	}
	
	public String getDescriptionPNML() {
		return getNamePNML() + " (*." + getExtensionPNML() + ")";
	}

	public boolean accept(File file) {
		if (file.isDirectory()) { 
			return true;
		}

		String extension = StringTools.getExtension(file);
		if (extension != null) {
			if (extension.equals(getExtension())) {
				return true;
			}
		}
		return false;
	}

	public static FileType getAcceptingFileType(File file, Collection<FileType> fileTypes) {
		for (FileType fileType : fileTypes) {
			if (fileType.accept(file)) {
				return fileType;
			}
		}
		return null;
	}
}
