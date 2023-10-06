package com.jverter.shared.model;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.context.FeatureContext;

public enum AppFeatures implements Feature {
	@EnabledByDefault
    FEATURE_ONE,
    FEATURE_TWO;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}