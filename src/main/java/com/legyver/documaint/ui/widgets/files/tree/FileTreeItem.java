package com.legyver.documaint.ui.widgets.files.tree;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public abstract class FileTreeItem extends TreeItem {

	public static final String PLEASE_WAIT = "Please wait...";

	public FileTreeItem(Object value, Node graphic) {
		super(value, graphic);
		initChildRefresher();
		addFauxChild();
	}

	private void initChildRefresher() {
		expandedProperty().addListener(((observable, oldValue, newValue) -> {
			if (newValue) {
				refresh();
			}
		}));
	}

	private void addFauxChild() {
		TreeItem fauxChild = new TreeItem(PLEASE_WAIT);
		getChildren().add(fauxChild);
	}

	public void refresh() {
		setExpanded(true);
		Platform.runLater(() -> {
			getChildren().clear();
			addChildren();
		});
	}

	abstract protected void addChildren();

}
