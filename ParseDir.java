package com.logicalpath.sandbox.utils;

import java.io.IOException;
import java.nio.*;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class ParseDir {
	
	private String suiteName;
	private static final String glob_CB1 = "C[0-9]*B1.script";
	private static final String glob_CAll = "C[0-9]*.script";

	public ParseDir(String sdir) {
				
	}
	
	public String getSuiteName() {
		return suiteName;
	}
	
	public <T> List<Path> getList(String sdir) {
		List<Path> scriptList = new ArrayList<>();
		Path dir = Paths.get(sdir);
        // assume the path is of the form .../suitename/scripts  
		int pthSize = dir.getNameCount();
		suiteName = dir.getName(pthSize -2).toString();
		
				
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob_CAll)) {
		    for (Path file: stream) {
		        scriptList.add(file);
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can only be thrown by newDirectoryStream.
		    System.err.println(x);
		}
				
		return scriptList;
		
		
	}
	
}
