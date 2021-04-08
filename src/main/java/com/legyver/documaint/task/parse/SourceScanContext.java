package com.legyver.documaint.task.parse;

import com.legyver.documaint.task.importDirectory.ProjectGraph;
import com.legyver.tuktukfx.status.TaskTimingData;

public class SourceScanContext extends TaskTimingData {
	private final ProjectGraph projectGraph;

	public SourceScanContext(ProjectGraph projectGraph) {
		super(projectGraph.getSizeGuess());
		this.projectGraph = projectGraph;
	}

	public ProjectGraph getProjectGraph() {
		return projectGraph;
	}
}
