package io.github.jelilio.i18nquarkusextension.runtime.autoconfigure;


import io.github.jelilio.i18nquarkusextension.runtime.MessageSource;
import io.github.jelilio.i18nquarkusextension.runtime.MessageSourceProperties;
import io.github.jelilio.i18nquarkusextension.runtime.support.ResourceBundleMessageSource;
import io.github.jelilio.i18nresourcebundle.util.StringUtils;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import java.time.Duration;

@Dependent
public class MessageSourceAutoConfiguration {
  @Inject
  MessageSourceProperties properties;

  @Produces @DefaultBean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    if (StringUtils.hasText(properties.basename())) {
      messageSource.setBasenames(StringUtils
          .commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.basename())));
    }
    if (properties.encoding() != null) {
      messageSource.setDefaultEncoding(properties.encoding().name());
    }
    messageSource.setFallbackToSystemLocale(properties.fallbackToSystemLocale());
    Duration cacheDuration = properties.cacheDuration();
    if (cacheDuration != null) {
      messageSource.setCacheMillis(cacheDuration.toMillis());
    }
    messageSource.setAlwaysUseMessageFormat(properties.alwaysUseMessageFormat());
    messageSource.setUseCodeAsDefaultMessage(properties.useCodeAsDefaultMessage());
    return messageSource;
  }
}
