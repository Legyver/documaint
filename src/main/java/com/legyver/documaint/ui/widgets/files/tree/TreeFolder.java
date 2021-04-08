package com.legyver.documaint.ui.widgets.files.tree;

import com.legyver.documaint.task.importDirectory.ProjectGraph;
import com.legyver.fenxlib.controls.svg.SVGControl;
import com.legyver.fenxlib.icons.fa.FontAwesomeFreeSolidIcons;
import com.legyver.fenxlib.icons.fa.FontAwesomeIconFonts;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TreeFolder extends FileTreeItem {
	private static final Logger logger = LogManager.getLogger(TreeFolder.class);

	private final ObjectProperty<Paint> color = new SimpleObjectProperty<>();
	private ProjectGraph projectGraph;

	public TreeFolder(Object value, SVGControl graphic) {
		super(value, graphic);
		if (graphic != null) {
			graphic.setSvgIcon(FontAwesomeFreeSolidIcons.FOLDER);
			graphic.setSvgIconLibraryPrefix(FontAwesomeIconFonts.FONTAWESOME_FREE_SOLID);
			graphic.svgIconPaintProperty().bind(color);
			color.set(Paint.valueOf("#e7c9a9"));
		}
	}

	public TreeFolder(Object value) {
		this(value, new SVGControl());
	}

	@Override
	protected void addChildren() {
		if (projectGraph != null) {
			addFolderChildren(projectGraph.getChildNodes());
		} else {
			logger.trace("Project graph null [{}]", getValue());
		}
	}

	void addFolderChildren(List<ProjectGraph.GraphNode> childNodes) {
		for (ProjectGraph.GraphNode graphNode : childNodes) {
			if (graphNode.getCtxType() == ProjectGraph.CtxType.DIRECTORY) {
				logger.trace("Adding TreeFolder for directory: {}", graphNode.getName());
				TreeFolder treeFolder = new TreeFolder(graphNode.getName());
				treeFolder.setProjectGraph(graphNode);
				getChildren().add(treeFolder);
				treeFolder.addChildren();
			} else if (graphNode.getCtxType() == ProjectGraph.CtxType.FILE) {
				logger.trace("Adding TreeFile for file: {}", graphNode.getName());
				TreeFile treeFile = new TreeFile(graphNode.getFile());
				getChildren().add(treeFile);
				treeFile.addChildren();
			} else {
				logger.trace("Unknown type of node: {} ", graphNode.getCtxType());
			}
		}
	}

	public Paint getColor() {
		return color.get();
	}

	public ObjectProperty<Paint> colorProperty() {
		return color;
	}

	public void setColor(Paint color) {
		this.color.set(color);
	}

	public void setProjectGraph(ProjectGraph projectGraph) {
		this.projectGraph = projectGraph;
	}

	public ProjectGraph getProjectGraph() {
		return projectGraph;
	}
}
