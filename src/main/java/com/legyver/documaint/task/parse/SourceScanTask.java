package com.legyver.documaint.task.parse;

import com.legyver.tuktukfx.processor.TaskProcessor;
import com.legyver.tuktukfx.task.AbstractAbortableTask;

public class SourceScanTask extends AbstractAbortableTask<Void, SourceScanContext> {

	public SourceScanTask(SourceScanContext timingData) {
		super(timingData);
	}

	@Override
	public TaskProcessor getTaskProcessor() {
		return new SourceScanProcessor(context);
	}
}
