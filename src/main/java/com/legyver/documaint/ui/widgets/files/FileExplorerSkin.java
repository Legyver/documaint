package com.legyver.documaint.ui.widgets.files;

import com.jfoenix.controls.JFXTreeView;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

public class FileExplorerSkin extends SkinBase<FileExplorer> {
	private final BorderPane borderPane;
	private final ExplorerControl explorerControl;
	private final JFXTreeView jfxTreeView;

	public FileExplorerSkin(FileExplorer fileExplorer) {
		super(fileExplorer);
		borderPane = new BorderPane();
		explorerControl = new ExplorerControl(fileExplorer);
		jfxTreeView = new JFXTreeView(fileExplorer.getPseudoRoot());
		jfxTreeView.setShowRoot(false);
		fileExplorer.refreshPulseProperty().addListener((observable, oldValue, newValue) -> jfxTreeView.refresh());
		borderPane.setTop(explorerControl);
		borderPane.setCenter(jfxTreeView);

		getChildren().add(borderPane);
	}
}
