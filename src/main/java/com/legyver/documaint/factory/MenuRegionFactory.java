package com.legyver.documaint.factory;


import com.legyver.documaint.MainApplication;
import com.legyver.documaint.factory.util.OnFileOpen;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.core.api.locator.query.ComponentQuery;
import com.legyver.fenxlib.core.impl.factory.TextFieldFactory;
import com.legyver.fenxlib.core.impl.factory.TopRegionFactory;
import com.legyver.fenxlib.core.impl.factory.menu.*;
import com.legyver.fenxlib.core.impl.factory.menu.file.ImportDirectoryDecorator;
import com.legyver.fenxlib.core.impl.factory.menu.file.RecentlyOpenedFileFactory;
import com.legyver.fenxlib.core.impl.factory.menu.file.SelectDirectoryMenuFactory;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.widgets.about.AboutMenuItemFactory;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class MenuRegionFactory extends TopRegionFactory {

	public MenuRegionFactory(MainApplication mainApplication, OnFileOpen onFileOpen) throws CoreException {
		super(new LeftMenuOptions(
						new MenuFactory("File",
								new ImportDirectoryDecorator("Open", "Select root source directory", new SelectDirectoryMenuFactory(), fileOptions -> {
									onFileOpen.accept(fileOptions.getFile());
								}),
								new RecentlyOpenedFileFactory("Recent", onFileOpen),
								new ExitMenuItemFactory("Exit")
						)
				),
				new CenterOptions(new TextFieldFactory(false)),
				new RightMenuOptions(
						new MenuFactory("Help", new AboutMenuItemFactory("About", MenuRegionFactory::centerContentReference, mainApplication.getDocumaintVersionInfo().getAboutPageOptions()))
				));
	}

	public static StackPane centerContentReference() {
		Optional<StackPane> center = new ComponentQuery.QueryBuilder()
				.inRegion(BorderPaneInitializationOptions.REGION_CENTER)
				.type(StackPane.class).execute();
		return center.get();
	}
}
