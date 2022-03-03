package com.clark.data.support;

import java.io.File;

public class Utils {

	public static String removeSpecialCharacters(String campo) {
		if (campo == null) {
			return "";
		}
		return campo.replaceAll("[\\[\\]\"]", "");
	}
	
	public static String getAbsolutePath(String path) {
    	File file = new File(path);
    	String absolutePath = file.getAbsolutePath();
		return absolutePath;
	}

}
