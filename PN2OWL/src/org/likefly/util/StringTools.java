package org.likefly.util;

import java.io.File;


public class StringTools {
	

	
	public static String getExtension(File file) {
		return getExtension(file.getName());
	}

	private static String getExtension(String filename) {
		String ext = null;
        String s = filename;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }

		if (ext != null) {
			return ext;
		}
		else {
			return "";
		}
	}

	public static String getExtensionCutOut(String filename) {
		String extension = getExtension(filename);
		String result = filename.substring(0, filename.length() - 1 - extension.length());
		return result;
	}
}
