package com.legyver.documaint.ui.widgets.files.tree;

import com.legyver.documaint.task.parse.JavaFileReader;
import com.legyver.fenxlib.controls.svg.SVGControl;
import com.legyver.fenxlib.icons.fa.FontAwesomeFreeSolidIcons;
import com.legyver.fenxlib.icons.fa.FontAwesomeIconFonts;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

import java.io.File;

public class TreeFile extends FileTreeItem {
	private final ObjectProperty<Paint> color = new SimpleObjectProperty<>();

	private final File file;

	public TreeFile(File file, Node icon) {
		super(file.getName(), icon);
		this.file = file;
	}

	public TreeFile(File file) {
		this(file, new SVGControl());
		SVGControl graphic = (SVGControl) getGraphic();
		graphic.setSvgIcon(FontAwesomeFreeSolidIcons.CODE);
		graphic.setSvgIconLibraryPrefix(FontAwesomeIconFonts.FONTAWESOME_FREE_SOLID);
		graphic.svgIconPaintProperty().bind(color);
		color.set(Paint.valueOf( "#4287f5"));
	}

	@Override
	protected void addChildren() {
		JavaFileReader javaFileReader = new JavaFileReader(file);

	}
}
