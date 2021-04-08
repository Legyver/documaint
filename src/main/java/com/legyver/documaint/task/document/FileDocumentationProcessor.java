package com.legyver.documaint.task.document;

import com.legyver.core.exception.CoreException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileDocumentationProcessor {
	private static final Logger logger = LogManager.getLogger(FileDocumentationProcessor.class);
	public void onNewFileSelected(File file) throws CoreException {

		List<String> lines = new ArrayList<>();
		Pattern constant = Pattern.compile("^^\\tString ([A-Z0-9_])*( ){0,1}=( ){0,1}\"(([a-zA-Z0-9, \\-])*)\";");
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = in.readLine()) != null) {
				Matcher m = constant.matcher(line);

				if (m.find()) {
					String previousLine = lines.get(lines.size() - 1);
					if (previousLine.contains("*/")) {
						lines.add(line);
						continue;//there's already a comment
					}
					String value = m.group(4);//what's between the quotes
					StringJoiner comment = new StringJoiner("\r\n\t");
					comment
							.add("\t/**")
							.add(" * " + value + " icon")
							.add(" */");
					lines.add("");
					lines.add(comment.toString());
				}
				lines.add(line);
			}
		} catch (IOException e) {
			logger.error("Errors reading file", e);
		}

		String newContent = lines.stream().collect(Collectors.joining("\r\n"));
		try {
			FileUtils.write(file, newContent, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			logger.error("Error writing javadoc code: " + ex.getMessage(), ex);
		}
	}
}
