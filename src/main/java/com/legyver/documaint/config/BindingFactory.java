package com.legyver.documaint.config;


import com.legyver.documaint.factory.FileExplorerFactory;
import com.legyver.documaint.task.importDirectory.ProjectGraph;
import com.legyver.documaint.ui.widgets.files.FileExplorer;
import com.legyver.documaint.ui.widgets.files.tree.TreeFolder;
import com.legyver.fenxlib.core.api.locator.query.ComponentQuery;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.extensions.tuktukfx.bindings.TaskAbortBindingFactory;

import java.io.File;
import java.util.Optional;

public enum BindingFactory implements TaskAbortBindingFactory {
	INSTANCE;

	public void addOrUpdateRoot(File mainDirectory, ProjectGraph.GraphNode node) {
		String path = mainDirectory.getPath();
		Optional<FileExplorer> fileExplorerOptional = new ComponentQuery.QueryBuilder()
				.inRegion(BorderPaneInitializationOptions.REGION_LEFT)
				.named(FileExplorerFactory.FILE_EXPLORER).execute();
		FileExplorer fileExplorer = fileExplorerOptional.get();
		fileExplorer.addRootFolder(path, node);
	}
}
