package com.legyver.documaint.config;

import com.legyver.fenxlib.widgets.about.AboutPageOptions;

import java.util.Properties;

public class DocumaintVersionInfo {
	private final AboutPageOptions aboutPageOptions;

	public DocumaintVersionInfo() {
		aboutPageOptions = new AboutPageOptions.Builder(getClass())
				.dependenciesFile("licenses/license.properties")
				.buildPropertiesFile("buildlabel.properties")
				.copyrightPropertiesFile("licenses/copyright.properties")
				.title("Autodoc")
				.intro("A desktop client to generate boilerplate javadocs for your constants")
				.build();
	}

	public AboutPageOptions getAboutPageOptions() {
		return aboutPageOptions;
	}

	public Properties getBuildProperties() {
		return aboutPageOptions.getBuildProperties();
	}
}
