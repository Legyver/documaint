package com.legyver.documaint.task.importDirectory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.stream.Collectors;

public class ProjectGraph {
	ProjectGraph parentNode;
	ObservableList<GraphNode> childNodes = FXCollections.observableArrayList();
	private Double sizeGuess;

	public synchronized void link(GraphNode graphNode) {
		childNodes.add(graphNode);
		graphNode.parentNode = this;
	}

	public static ProjectGraph.GraphNode newNode(File file) {
		CtxType type = file.isDirectory() ? CtxType.DIRECTORY : CtxType.FILE;
		return new GraphNode(file, type);
	}

	public ObservableList<GraphNode> getChildNodes() {
		return childNodes;
	}

	public synchronized Double getSizeGuess() {
		if (sizeGuess == null) {
			sizeGuess = calcSize().doubleValue();
		}
		return sizeGuess;
	}

	public synchronized void reset() {
		childNodes.clear();
		sizeGuess = null;
	}

	Long calcSize() {
		if (childNodes.isEmpty()) {
			return 1L;
		}
		return 1L + childNodes.parallelStream()
				.collect(Collectors.summingLong(e -> e.calcSize()));
	}


	public static class GraphNode extends ProjectGraph {
		private final File file;
		private final CtxType ctxType;
		private Boolean containsCode;

		private GraphNode(File file, CtxType ctxType) {
			this.file = file;
			this.ctxType = ctxType;
		}

		public CtxType getCtxType() {
			return ctxType;
		}

		public String getName() {
			if (file == null) {
				return "";
			}
			return file.getName();
		}

		public File getFile() {
			return file;
		}

		public boolean containsCode() {
			if (containsCode == null) {
				synchronized (this) {
					if (childNodes.isEmpty()) {
						containsCode = ctxType == CtxType.FILE;
					} else {
						containsCode = childNodes.parallelStream().anyMatch(GraphNode::containsCode);
					}
				}
			}
			return containsCode;
		}

	}

	public enum CtxType {
		DIRECTORY, FILE, PACKAGE, CLASS, INTERFACE, ENUM, METHOD, VARIABLE, IMPORT
	}
}
