package com.legyver.documaint.factory.util;

import com.legyver.documaint.task.importDirectory.ImportDirectoryProcessor;
import com.legyver.documaint.ui.ApplicationUIModel;
import com.legyver.core.exception.CoreException;
import com.legyver.core.function.ThrowingConsumer;

import java.io.File;

public class OnFileOpen implements ThrowingConsumer<File> {
	private final ApplicationUIModel uiModel;
	private final ImportDirectoryProcessor importProcessor;

	public OnFileOpen(ApplicationUIModel uiModel, ImportDirectoryProcessor importProcessor) {
		this.uiModel = uiModel;
		this.importProcessor = importProcessor;
	}

	@Override
	public void accept(File file) throws CoreException {
		//process it
		importProcessor.onNewFileSelected(file);
	}
}
