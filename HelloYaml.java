package com.mfoundry.qe.sandbox.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.stream.Stream;

import org.yaml.snakeyaml.Yaml;

public class HelloYaml {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {
		Yaml yaml = new Yaml();
	
		System.out.println(yaml.dump(yaml.load(new FileInputStream(new File(
				"hello_world.yaml")))));

		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) yaml
				.load(new FileInputStream(new File("hello_world.yaml")));

		for (String key : values.keySet()) {
			Map<String, String> subValues = values.get(key);
			System.out.println(key);

			for (String subValueKey : subValues.keySet()) {
				System.out.println(String.format("\t%s = %s",
						subValueKey, subValues.get(subValueKey)));
			}
		}
	}
}