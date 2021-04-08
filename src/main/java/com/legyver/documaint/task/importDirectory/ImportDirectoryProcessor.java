package com.legyver.documaint.task.importDirectory;

import com.legyver.documaint.config.BindingFactory;
import com.legyver.core.exception.CoreException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class ImportDirectoryProcessor {
	private static final Logger logger = LogManager.getLogger(ImportDirectoryProcessor.class);

	private final BindingFactory bindingFactory;

	public ImportDirectoryProcessor(BindingFactory bindingFactory) {
		this.bindingFactory = bindingFactory;
	}

	public void onNewFileSelected(File mainDirectory) {
		ProjectGraph.GraphNode node = addRecursive(mainDirectory, 0);
		bindingFactory.addOrUpdateRoot(mainDirectory, node);
	}

	private ProjectGraph.GraphNode addRecursive(File file, int depth) {
		logger.trace("Scanning " + file.getName());
		final ProjectGraph.GraphNode node = ProjectGraph.newNode(file);
		if (file.isDirectory()) {
			List<File> subDirectories = new ArrayList<>();
			List<File> files = new ArrayList<>();

			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					if (!f.getName().startsWith(".")) {
						if (depth < 3 && f.getName().equals("build")) {
							logger.trace("Skipping directory: {}", f.getPath());
						} else {
							subDirectories.add(f);
						}
					}
				} else if (f.getName().endsWith(".java")) {
					files.add(f);
				}
			}

			for (File f : subDirectories) {
				logger.trace("Processing subdirectory: " + f.getAbsolutePath());
				ProjectGraph.GraphNode child = addRecursive(f, depth + 1);
				if (child.containsCode()) {
					node.link(child);
				}
			}
			Collections.sort(files);
			for (File f : files) {
				logger.trace("Processing file: " + f.getAbsolutePath());
				ProjectGraph.GraphNode child = addRecursive(f, depth + 1);
				node.link(child);
			}
		}
		return node;
	}
}
