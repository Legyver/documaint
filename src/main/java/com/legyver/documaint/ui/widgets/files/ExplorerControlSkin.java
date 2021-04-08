package com.legyver.documaint.ui.widgets.files;

import com.jfoenix.controls.JFXTreeView;
import com.legyver.fenxlib.controls.svg.SVGControl;
import com.legyver.fenxlib.icons.fa.FontAwesomeFreeSolidIcons;
import com.legyver.fenxlib.icons.fa.FontAwesomeFreeStandardIcons;
import com.legyver.fenxlib.icons.fa.FontAwesomeIconFonts;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

public class ExplorerControlSkin extends SkinBase<ExplorerControl> {
	private final HBox hbox;

	public ExplorerControlSkin(ExplorerControl explorerControl) {
		super(explorerControl);
		this.hbox = new HBox();
		hbox.setPadding(new Insets(10));
		addIcon(FontAwesomeFreeSolidIcons.SYNC_ALT, event -> {
			explorerControl.refreshFileExplorer();
		});

		getChildren().add(hbox);
	}

	private void addIcon(String icon, EventHandler<MouseEvent> onClick) {
		SVGControl svgControl = new SVGControl();
		svgControl.setSvgIcon(icon);
		svgControl.setSvgIconLibraryPrefix(FontAwesomeIconFonts.FONTAWESOME_FREE_SOLID);
		svgControl.setSvgIconPaint(Paint.valueOf("#68b1e3"));
		svgControl.setSvgIconSize(16);
		hbox.getChildren().add(svgControl);

		svgControl.setOnMouseClicked(onClick);
		Region spacer = new Region();
		hbox.getChildren().add(spacer);
		HBox.setHgrow(spacer, Priority.ALWAYS);
	}
}
