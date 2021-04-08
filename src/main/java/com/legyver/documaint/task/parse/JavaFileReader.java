package com.legyver.documaint.task.parse;

import com.legyver.core.exception.CoreException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class JavaFileReader {
	private final File file;

	public JavaFileReader(File file) {
		this.file = file;
	}

	public ParsedFile parse() throws CoreException {
		ParsedFile result = new ParsedFile();
		ParseData section = new ParseData();
		result.sections.add(section);

		String tabs = "(\\t)*";
		String spaces = "( )*";
		String or = "|";
		String whitespace = new StringJoiner(or)
				.add(tabs).add(spaces)
				.add(tabs).add(spaces)//enough repetitions of any number tabs/spaces to catch mixed values
				.add(tabs).add(spaces)
				.add(tabs).add(spaces)
				.add(tabs).add(spaces)
				.toString();

		String startPublicStaticOption = "^(\\t)*( )*(public|protected|static){0,1}( )*(public|static){0,1}( )*";
		//group 7 -> 15?
		Pattern stringConstant = Pattern.compile("^(\\t)*( )*(public|protected|static){0,1}( )*(public|protected|static){0,1}( )*String ([A-Z0-9_])*( )*=( )*\\\"(([a-zA-Z0-9, \\-])*)\\\"");
		Pattern klassPattern = Pattern.compile("^(\\t)*( )*(public|static){0,1}( )*(public|static){0,1}( )*class (([a-zA-Z0-9])*)");
		Pattern enumPattern = Pattern.compile("^(\\t)*( )*(public|static){0,1}( )*(public|static){0,1}( )*enum (([a-zA-Z0-9])*)");

		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			int bracketBalance = 0;
			String line;
			while ((line = in.readLine()) != null) {

			}
		} catch (IOException ex) {
			throw new CoreException("Error parsing file: " + file.getAbsolutePath(), ex);
		}
		return result;
	}

	public static class ParsedFile {
		List<ParseData> sections = new ArrayList<>();
		public List<ParseData> getSections() {
			return sections;
		}
	}

	public static class ParseData {
		private String name;
		private Type type;
		List<ParseData> sections = new ArrayList<>();
		private List<String> lines = new ArrayList<>();

	}

	public enum Type {
		CLASS,INTERFACE,ENUM,METHOD,CONSTANT
	}
}
