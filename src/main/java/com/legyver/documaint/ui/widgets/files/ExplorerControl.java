package com.legyver.documaint.ui.widgets.files;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ExplorerControl extends Control {
	private final FileExplorer fileExplorer;

	public ExplorerControl(FileExplorer fileExplorer) {
		this.fileExplorer = fileExplorer;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new ExplorerControlSkin(this);
	}

	public void refreshFileExplorer() {
		fileExplorer.refresh();
	}
}
