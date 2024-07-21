package io.github.jelilio.i18nquarkusextension.deployment;

import io.github.jelilio.i18nquarkusextension.runtime.MessageSourceProperties;
import io.github.jelilio.i18nquarkusextension.runtime.autoconfigure.MessageSourceAutoConfiguration;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class I18nQuarkusExtensionProcessor {

    private static final String FEATURE = "i18n-quarkus-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

//    @BuildStep
//    public AdditionalBeanBuildItem producer() {
//        return new AdditionalBeanBuildItem(MessageSourceProperties.class);
//    }

    @BuildStep
    public AdditionalBeanBuildItem producer() {
        return new AdditionalBeanBuildItem(MessageSourceAutoConfiguration.class);
    }


//    @BuildStep
//    void registerBeans(BuildProducer<AdditionalBeanBuildItem> beans) {
//        beans.produce(AdditionalBeanBuildItem.builder().setUnremovable()
//            .addBeanClasses(MessageSourceProperties.class)
//            .build());
////        beans.produce(AdditionalBeanBuildItem.builder()
////            .addBeanClasses(MailTemplateProducer.class)
////            .build());
////        // add the @MailerName class otherwise it won't be registered as a qualifier
////        beans.produce(AdditionalBeanBuildItem.builder()
////            .addBeanClass(MailerName.class)
////            .build());
//    }
}
