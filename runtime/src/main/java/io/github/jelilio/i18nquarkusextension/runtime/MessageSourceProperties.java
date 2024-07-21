package io.github.jelilio.i18nquarkusextension.runtime;

import io.github.jelilio.i18nquarkusextension.runtime.annotation.DurationUnit;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Configuration properties for Message Source.
 *
 */
@ConfigMapping(prefix = "quarkus.messages")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface MessageSourceProperties {

  /**
   * Comma-separated list of basenames (essentially a fully-qualified classpath
   * location), each following the ResourceBundle convention with relaxed support for
   * slash based locations. If it doesn't contain a package qualifier (such as
   * "org.mypackage"), it will be resolved from the classpath root.
   */
  @WithDefault("messages")
  String basename();

  /**
   * Message bundles encoding.
   */
  @WithDefault("UTF-8")
  Charset encoding();

  /**
   * Loaded resource bundle files cache duration. When not set, bundles are cached
   * forever. If a duration suffix is not specified, seconds will be used.
   */
  @WithDefault("-1")
  @DurationUnit(ChronoUnit.SECONDS)
  Duration cacheDuration();

  /**
   * Whether to fall back to the system Locale if no files for a specific Locale have
   * been found. if this is turned off, the only fallback will be the default file (e.g.
   * "messages.properties" for basename "messages").
   */
  @WithDefault("true")
  boolean fallbackToSystemLocale();

  /**
   * Whether to always apply the MessageFormat rules, parsing even messages without
   * arguments.
   */
  @WithDefault("false")
  boolean alwaysUseMessageFormat();

  /**
   * Whether to use the message code as the default message instead of throwing a
   * "NoSuchMessageException". Recommended during development only.
   */
  @WithDefault("false")
  boolean useCodeAsDefaultMessage();
}
