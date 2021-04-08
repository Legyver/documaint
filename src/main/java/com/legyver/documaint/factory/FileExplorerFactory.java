package com.legyver.documaint.factory;

import com.legyver.core.exception.CoreException;
import com.legyver.documaint.ui.widgets.files.FileExplorer;
import com.legyver.fenxlib.core.api.locator.LocationContext;
import com.legyver.fenxlib.core.api.locator.LocationContextDecorator;
import com.legyver.fenxlib.core.impl.context.ApplicationContext;
import com.legyver.fenxlib.core.impl.factory.RegionFactory;
import com.legyver.fenxlib.core.impl.factory.options.RegionInitializationOptions;
import javafx.scene.layout.BorderPane;

public class FileExplorerFactory implements RegionFactory<FileExplorer, RegionInitializationOptions> {

	public static final String FILE_EXPLORER = "FileExplorer";

	@Override
	public FileExplorer makeRegion(BorderPane borderPane, RegionInitializationOptions regionInitializationOptions) throws CoreException {
		FileExplorer fileExplorer = new FileExplorer();
		LocationContext locationContext = new LocationContextDecorator(regionInitializationOptions.getLocationContext());
		locationContext.setName(FILE_EXPLORER);
		ApplicationContext.getComponentRegistry().register(locationContext, fileExplorer);
		return fileExplorer;
	}
}
