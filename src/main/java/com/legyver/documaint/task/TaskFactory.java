package com.legyver.documaint.task;

import com.legyver.documaint.task.parse.SourceScanContext;
import com.legyver.documaint.task.parse.SourceScanTask;
import com.legyver.fenxlib.extensions.tuktukfx.bindings.TaskAbortBindingFactory;
import com.legyver.fenxlib.extensions.tuktukfx.task.adapter.JavaFxAdapter;

import javax.swing.*;

public enum TaskFactory implements TaskAbortBindingFactory {
	INSTANCE;

	public JavaFxAdapter createScanTask(SourceScanContext sourceScanContext) {
		SourceScanTask sourceScanTask = new SourceScanTask(sourceScanContext);
		JavaFxAdapter adapter = new JavaFxAdapter(sourceScanTask);
		taskAbortObservesShutdown(adapter);
		return adapter;
	}
}
