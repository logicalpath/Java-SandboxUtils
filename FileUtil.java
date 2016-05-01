package com.mfoundry.qe.sandbox.utils;

import java.io.IOException;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.yaml.snakeyaml.Yaml;

import com.mfoundry.qe.sandbox.*;

@SuppressWarnings("unused")
public class FileUtil {
	
	private static String REGRESSIONS = "/Users/eddie.dickey/Documents/4.0Regressions.suite/Scripts";
	private static String GP405 = "/Users/eddie.dickey/Documents/4xTrunk/4.0.5GoldenPath.suite/Scripts";
	private static String GP400 = "/Users/eddie.dickey/Documents/4xTrunk/4.0GoldenPath.suite/Scripts";

	
	private static List<Path> fileList = new ArrayList<>();
	private static List<String> csvValues = new ArrayList<>();
	
	private String suiteName = null;
	private int numCols = 4;
	

	public static void main( final String[] args ) throws IOException
    {
        
        for (String s: args) {
            System.out.println(s);
        }
        
        Yaml yaml = new Yaml();
        
        String dir = GP405;

        FileUtil futil = new FileUtil();
        futil.listYaml(args[1]);
        
        futil.listDir(dir);
        
        futil.processFiles();
        futil.writeCSVFile();
//        futil.ik();
                
        return;
                
    }
	
	void listYaml (String sdir) {
		
	}
	
	void listDir (String sdir)
	{
		ParseDir pd = new ParseDir(sdir);
		fileList = pd.getList(sdir);
		suiteName = pd.getSuiteName();
				
	}
	
	void processFiles ()
	{		
		String substr = null;
		String scriptName = null;
		StringJoiner sj = new StringJoiner(",");
		// Add the Header 
		sj.add("Suite Name").add("Eggplant Script Name").add("Testrail Test Case ID").add("Test Case Description");
		csvValues.add(sj.toString());
		
						
		for(Path file : fileList){
			scriptName = file.getFileName().toString();
			Map<String, String> map = new HashMap<>();
								
			try {
				List<String> lines = Files.readAllLines(file);
							
				lines.forEach((line) -> {				
					int start = 0, end = 0;
					
					if (line.contains("Set global TestCaseID")) {
						// Each ID matches to a Name - the Name is on a different line
						start = line.indexOf('"');
						end = line.lastIndexOf('"');	
						map.put("Suite", suiteName);						
						map.put("TestCaseID",line.substring(start+1, end));
						map.put("ScriptName",file.getFileName().toString());
										
					}
					if (line.contains("Set global TestCaseName")) {
						// Matches to Name, ID (see above)
						// find surrounding quotes
						start = line.indexOf('"');
						end = line.lastIndexOf('"');
						// replace quotes in middle of string
						String str = line.substring(start+1, end);
						str = str.replaceAll("\"", "'");
						// preserve the " surronding the string due to ',' in the string
						// which can be misinterpreted as delimiters
						String str2 = "\"";
						str2 = str2.concat(str);
						StringBuffer buf = new StringBuffer();
						buf.append(str2);
						buf.append("\"");
												
						map.put("TestCaseDescription",buf.toString());
					}
										
					int j = map.size();
					if (j == numCols){						
						// create the string that will be written to file
						StringJoiner sj2 = new StringJoiner(",");
						sj2.add(map.get("Suite")).
						add(map.get("ScriptName")).
						add(map.get("TestCaseID")).						
						add(map.get("TestCaseDescription"));
						// empty the list, so to speak
						map.clear();
						
						csvValues.add(sj2.toString());

					}
					
				});
				
			} catch (IOException e) {
				e.printStackTrace();
				}
//			System.out.println(sj.toString());
			
			}					
	}
	
	void writeCSVFile() {
		String fname = "./" + suiteName + ".csv";
		Path csvFile = Paths.get(fname);
		System.out.println("suite is " + suiteName);
		
		csvValues.forEach(v -> System.out.println(v));
		System.out.println(csvValues.size());
		System.out.println();
		
		try{
			Files.write(csvFile, csvValues, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		
	}
		
	void ik() {
		StringJoiner sj = new StringJoiner(",");
		sj.add("George").add("Sally").add("Fred");
		System.out.println(sj.toString());
		
		
	}
						
}
