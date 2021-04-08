package com.legyver.documaint.ui;

import com.legyver.fenxlib.core.impl.uimodel.FileOptions;
import com.legyver.fenxlib.core.impl.uimodel.RecentFileAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ApplicationUIModel implements RecentFileAware {
	private final ObservableList<FileOptions> recentFiles = FXCollections.observableArrayList();
	@Override
	public List<FileOptions> getRecentFiles() {
		return recentFiles;
	}
}
