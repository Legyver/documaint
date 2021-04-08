package com.legyver.documaint.ui.widgets.files.tree;

import com.legyver.documaint.task.importDirectory.ProjectGraph;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TreeRoot extends TreeFolder {
	private static final Logger logger = LogManager.getLogger(TreeRoot.class);

	public TreeRoot() {
		super("", null);
	}

	public void refresh(ProjectGraph.GraphNode node) {
		ObservableList<TreeItem> children = getChildren();
		if (children.size() == 1) {
			//remove the placeholder
			TreeItem item = children.iterator().next();
			if (PLEASE_WAIT.equals(item.getValue())) {
				children.clear();
			}
		}

		//figure out if we need to update it or insert it (in-order) by binary search
		TreeItem existing = null;
		int upperBound = children.size() - 1;
		int lowerBound = 0;
		int index = children.size();

		while (lowerBound <= upperBound) {
			index = (lowerBound + upperBound) / 2;
			TreeItem item = children.get(index);
			String name = (String) item.getValue();
			int compareTo = name.compareTo(node.getName());
			if (compareTo == 0) {
				logger.trace("Found match: {}", name);
				existing = item;
				break;
			}
			if (compareTo > 0) {
				logger.trace("{} is greater than {}", name, node.getName());
				upperBound = index - 1;
			}
			if (compareTo < 0) {
				logger.trace("{} is less than {}", name, node.getName());
				lowerBound = index + 1;
			}
		}

		if (existing == null) {
			logger.trace("Adding new node: {}", node.getName());
			existing = new TreeFolder(node.getName());
			((TreeFolder) existing).setProjectGraph(node);
			children.add(index, existing);
		} else {
			logger.trace("Replacing node: {}", node.getName());
			existing.getChildren().clear();
		}

		if (existing instanceof TreeFolder) {
			((TreeFolder) existing).addChildren();
		}
	}
}
