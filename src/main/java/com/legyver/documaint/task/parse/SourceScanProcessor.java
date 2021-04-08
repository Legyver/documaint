package com.legyver.documaint.task.parse;

import com.legyver.core.exception.CoreException;
import com.legyver.documaint.task.importDirectory.ProjectGraph;
import com.legyver.tuktukfx.adapter.AbortableTaskStatusAdapter;
import com.legyver.tuktukfx.processor.TaskProcessor;

public class SourceScanProcessor implements TaskProcessor<AbortableTaskStatusAdapter> {
	private final SourceScanContext context;

	public SourceScanProcessor(SourceScanContext context) {
		this.context = context;
	}

	@Override
	public void process(AbortableTaskStatusAdapter abortableTaskStatusAdapter) throws CoreException {
		ProjectGraph projectGraph = context.getProjectGraph();

	}
}
