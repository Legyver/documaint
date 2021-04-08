package com.legyver.documaint.ui.widgets.files;

import com.legyver.documaint.task.TaskFactory;
import com.legyver.documaint.task.importDirectory.ProjectGraph;
import com.legyver.documaint.task.parse.SourceScanContext;
import com.legyver.documaint.ui.widgets.files.tree.TreeFolder;
import com.legyver.documaint.ui.widgets.files.tree.TreeRoot;
import com.legyver.fenxlib.extensions.tuktukfx.task.adapter.JavaFxAdapter;
import com.legyver.fenxlib.extensions.tuktukfx.task.exec.TaskExecutor;
import com.legyver.utils.nippe.Base;
import com.legyver.utils.nippe.Step;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TreeItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileExplorer extends Control {
	private static final Logger logger = LogManager.getLogger(FileExplorer.class);
	private final TreeRoot pseudoRoot;
	private final ProjectGraph projectGraph;

	private final ObservableList<String> roots = FXCollections.observableArrayList();
	private final Map<String, TreeFolder> rootFoldersByPath = new HashMap<>();
	private final Map<String, WeakReference<JavaFxAdapter>> currentTaskByRoot = new HashMap<>();
	private final BooleanProperty refreshPulse = new SimpleBooleanProperty(false);

	public FileExplorer() {
		pseudoRoot = new TreeRoot();
		projectGraph = new ProjectGraph();
		pseudoRoot.setProjectGraph(projectGraph);
		projectGraph.getChildNodes().addListener((ListChangeListener<ProjectGraph.GraphNode>) c -> {
			logger.trace("Refreshing explorer nodes");
			c.next();
			if (c.wasAdded()) {
				for (ProjectGraph.GraphNode node : c.getAddedSubList()) {
					logger.trace("Refreshing node: {}", node.getName());
					pseudoRoot.refresh(node);
				}
			}
			Platform.runLater(() -> {
				if (logger.isTraceEnabled()) {
					ObservableList<TreeItem> children = pseudoRoot.getChildren();
					String topLevel = children.stream()
							.map(treeItem -> treeItem.getValue())
							.map(Object::toString)
							.collect(Collectors.joining("\n"));
					logger.trace("Root children: {}", topLevel);
				}
				pseudoRoot.setExpanded(true);
				refreshPulse.set(!refreshPulse.get());
			});
		});
	}

	public void refresh() {
		for (String root : roots) {
			refreshRoot(root);
		}
	}

	private void refreshRoot(String root) {
		ProjectGraph projectGraph = new Step<>(new Base<>(rootFoldersByPath.get(root)),
				treeFolder -> treeFolder.getProjectGraph()).execute();
		if (projectGraph != null) {
			JavaFxAdapter adapter = TaskFactory.INSTANCE.createScanTask(new SourceScanContext(projectGraph));
			adapter.setOnSucceeded(e -> {
					currentTaskByRoot.remove(root);

			});
			JavaFxAdapter oldTask = new Step<>(new Base<>(currentTaskByRoot.remove(root)),
					ref -> ref.get()).execute();
			if (oldTask != null) {
				oldTask.abort();
			}
			currentTaskByRoot.put(root, new WeakReference<>(adapter));
			TaskExecutor.INSTANCE.submitTask(adapter);
		}
	}


	public TreeFolder getPseudoRoot() {
		return pseudoRoot;
	}

	public void addRootFolder(String path, ProjectGraph.GraphNode node) {
		projectGraph.link(node);
		refreshRoot(path);
	}

	public boolean isRefreshPulse() {
		return refreshPulse.get();
	}

	public BooleanProperty refreshPulseProperty() {
		return refreshPulse;
	}

	public void setRefreshPulse(boolean refreshPulse) {
		this.refreshPulse.set(refreshPulse);
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new FileExplorerSkin(this);
	}

}
