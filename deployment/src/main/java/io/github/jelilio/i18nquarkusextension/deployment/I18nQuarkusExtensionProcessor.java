package io.github.jelilio.i18nquarkusextension.deployment;

import io.github.jelilio.i18nquarkusextension.runtime.autoconfigure.MessageSourceAutoConfiguration;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class I18nQuarkusExtensionProcessor {

    private static final String FEATURE = "i18n-quarkus-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    public AdditionalBeanBuildItem producer() {
        return new AdditionalBeanBuildItem(MessageSourceAutoConfiguration.class);
    }
}
