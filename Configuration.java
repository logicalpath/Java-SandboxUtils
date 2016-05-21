package com.mfoundry.qe.sandbox.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unused")

public final class Configuration {
	
	ArrayList<String> suiteName = new ArrayList<String>();
    ArrayList<String> suiteDir = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public Configuration(String fname) {
		Yaml yaml = new Yaml();
		
		try {
			
			 InputStream is = new FileInputStream(new File(fname));
			 Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) 
					 yaml.load(is);

			 for (String key : values.keySet()) {
					Map<String, String> subValues = values.get(key);

					for (String subValueKey : subValues.keySet()) {
						suiteName.add(subValueKey);
						suiteDir.add(subValues.get(subValueKey));
					}
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ArrayList getSuites() {
		return suiteDir;
	}
		
		
	
}
