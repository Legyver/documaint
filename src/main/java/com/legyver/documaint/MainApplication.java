package com.legyver.documaint;

import com.legyver.documaint.config.ApplicationOptionsBuilder;
import com.legyver.documaint.config.DocumaintConfig;
import com.legyver.documaint.config.DocumaintVersionInfo;
import com.legyver.documaint.config.BindingFactory;
import com.legyver.documaint.factory.FileExplorerFactory;
import com.legyver.documaint.factory.MenuRegionFactory;
import com.legyver.documaint.factory.util.OnFileOpen;
import com.legyver.documaint.task.importDirectory.ImportDirectoryProcessor;
import com.legyver.documaint.ui.ApplicationUIModel;
import com.legyver.core.exception.CoreException;
import com.legyver.fenxlib.core.impl.config.options.ApplicationOptions;
import com.legyver.fenxlib.core.impl.context.ApplicationContext;
import com.legyver.fenxlib.core.impl.factory.*;
import com.legyver.fenxlib.core.impl.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.core.impl.factory.options.RegionInitializationOptions;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApplication extends Application {
	private static Logger logger;
	private static ApplicationOptions applicationOptions;
	private BindingFactory bindingFactory;
	private DocumaintVersionInfo documaintVersionInfo;
	private ApplicationUIModel uiModel;

	public static void main(String[] args) throws CoreException {
		try {
			applicationOptions = new ApplicationOptionsBuilder()
					.appName("Documaint")
					.customAppConfigInstantiator(map -> new DocumaintConfig(map))
					.uiModel(new ApplicationUIModel())
					.build();//build() calls bootstrap() which inits logging
			logger = LogManager.getLogger(MainApplication.class);
			launch(args);
		} catch (Exception e) {
			if (logger != null) {
				logger.error("Crash in main: ", e);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			logger.info("Initializing application");
			applicationOptions.startup();
			bindingFactory = BindingFactory.INSTANCE;
			uiModel = (ApplicationUIModel) ApplicationContext.getUiModel();
			documaintVersionInfo = new DocumaintVersionInfo();

			SceneFactory sceneFactory = new SceneFactory(primaryStage, 1100, 750, MainApplication.class.getClassLoader().getResource("css/application.css"));
			ImportDirectoryProcessor importProcessor = new ImportDirectoryProcessor(bindingFactory);

			BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
					.left(new RegionInitializationOptions.SideBuilder("Controls")
							.displayContentByDefault()
							.factory(new FileExplorerFactory())
					)
					.center(new RegionInitializationOptions.Builder()
							//popup will display over this. See the centerContentReference Supplier above
							.factory(new StackPaneRegionFactory(true))
					)
					.top(new RegionInitializationOptions.Builder()
							.displayContentByDefault()
							.factory(new MenuRegionFactory(this, new OnFileOpen(uiModel, importProcessor)))
					)
					.bottom(new RegionInitializationOptions.SideAwareBuilder()
							.factory(BottomRegionFactory.INSTANCE)
					)
					.build();

			BorderPane root = new BorderPaneFactory(options).makeBorderPane();

			primaryStage.setScene(sceneFactory.makeScene(root));
			primaryStage.setTitle("Documaint");
			primaryStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("/legyvindocicon.png")));
			primaryStage.show();
		} catch (Exception ex) {
			logger.error("Error in MainApplication.start() " + ex.getMessage(), ex);
			System.exit(1);
		}

	}

	public BindingFactory getBindingFactory() {
		return bindingFactory;
	}

	public DocumaintVersionInfo getDocumaintVersionInfo() {
		return documaintVersionInfo;
	}

	public ApplicationUIModel getUiModel() {
		return uiModel;
	}
}
