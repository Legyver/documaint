package com.legyver.documaint.config;

import com.legyver.fenxlib.core.impl.config.options.ApplicationOptions;
import com.legyver.fenxlib.core.impl.config.options.init.SVGGlyphLoadingApplicationLifecycleHook;
import com.legyver.fenxlib.extensions.tuktukfx.config.TaskLifecycleMixin;

public class ApplicationOptionsBuilder extends ApplicationOptions.Builder<ApplicationOptionsBuilder> implements TaskLifecycleMixin {

	public ApplicationOptionsBuilder() {
		super();
		registerLifecycleHook(shutDownThreadPoolOnExit());
		registerLifecycleHook(new SVGGlyphLoadingApplicationLifecycleHook());
	}
}
