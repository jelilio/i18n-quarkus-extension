package io.github.jelilio.i18nquarkusextension.deployment;

import io.github.jelilio.i18nquarkusextension.runtime.MessageSource;
import io.github.jelilio.i18nquarkusextension.runtime.MessageSourceProperties;
import io.github.jelilio.i18nresourcebundle.MessageSourceResolvable;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class I18nQuarkusExtensionTest {
  private static final String USER_NAME = "user.name";
  private static final String WELCOME = "welcome";
  private static final String NOT_EXIST = "notExist";
  private static final String HOTEL_NAME = "hotel.name";

  private static final String[] resourceFiles =
      {
          "another.properties", "another_en_US.properties", "another_zh_CN.properties",
          "message.properties", "message_de.properties", "message_de_AT.properties", "message_de_AT_oo.properties", "message_en_US.properties", "message_zh_CN.properties",
          "messages.properties", "messages_de.properties", "messages_de_AT.properties", "messages_de_AT_oo.properties",
          "messages_de_DE.xml", "more-messages.properties"
      };


  @RegisterExtension
  static final QuarkusUnitTest config = new QuarkusUnitTest()
      .withConfigurationResource("application.properties")
      .withEmptyApplication()
      .withApplicationRoot((jar) -> {
        Arrays.stream(resourceFiles)
            .forEach(jar::addAsResource);
      });

  @Inject
  BeanUsingMessageSourceProperties beanUsingMessageSourceProperties;

  @Inject
  MessageSourceProperties messageSourceProperties;

  @Inject
  MessageSource messageSource;

  @Test
  public void testMessageSourceProperties() {
    String basename = messageSourceProperties.basename();
    assertEquals("message,another", basename);
  }

  @Test
  public void testInjection() {
    beanUsingMessageSourceProperties.verify();
  }

  @Test
  public void getMessageWithOutArgs() throws Exception {
    String english = messageSource.getMessage(USER_NAME, null, Locale.US);
    String chinese = messageSource.getMessage(USER_NAME, null, Locale.SIMPLIFIED_CHINESE);

    assertEquals("username-us", english);
    assertEquals("用户名", chinese);
  }

  @Test
  public void getMessageWithOutArgsFallbackToDefaultMessage() throws Exception {
    String english = messageSource.getMessage(USER_NAME, null, Locale.UK);
    String chinese = messageSource.getMessage(USER_NAME, null, Locale.SIMPLIFIED_CHINESE);

    assertEquals("username", english);
    assertEquals("用户名", chinese);
  }

  @Test
  public void getMessageWithArgs() throws Exception {
    Object[] args = new Object[3];
    args[0] = "Ryan";
    args[1] = "EasyI18n";
    args[2] = "\uD83D\uDE1C";
    String english = messageSource.getMessage(WELCOME, args, Locale.US);
    String chinese = messageSource.getMessage(WELCOME, args, Locale.SIMPLIFIED_CHINESE);

    assertEquals("Welcome Ryan to EasyI18n, \uD83D\uDE1C", english);
    assertEquals("欢迎 Ryan 来到 EasyI18n, \uD83D\uDE1C", chinese);
  }

  @Test
  public void getMessageExistWithDefaultMessageShouldGetExistVal() throws Exception {
    String english = messageSource.getMessage(HOTEL_NAME, null,"default value", Locale.US);
    String chinese = messageSource.getMessage(HOTEL_NAME, null,"默认值", Locale.SIMPLIFIED_CHINESE);

    assertEquals("hotel name[us]", english);
    assertEquals("酒店名称", chinese);
  }

  @Test
  public void getMessageNotExistWithDefaultMessageShouldGetDefaultVal() throws Exception {
    String english = messageSource.getMessage(NOT_EXIST, null,"default value", Locale.US);
    String chinese = messageSource.getMessage(NOT_EXIST, null,"默认值", Locale.SIMPLIFIED_CHINESE);

    assertEquals("default value", english);
    assertEquals("默认值", chinese);
  }

  @Test
  public void getMessageWithResolvable(){
    String english = messageSource.getMessage(getResolvable(), Locale.US);
    String chinese = messageSource.getMessage(getResolvable(), Locale.SIMPLIFIED_CHINESE);

    assertEquals("username-us", english);
    assertEquals("用户名", chinese);
  }

  private MessageSourceResolvable getResolvable() {
    return new MessageSourceResolvable() {
      @Override
      public String[] getCodes() {
        return new String[]{"notExist","notExist","user.name"};
      }

      @Override
      public Object[] getArguments() {
        return new Object[0];
      }

      @Override
      public String getDefaultMessage() {
        return null;
      }
    };
  }

  @ApplicationScoped
  static class BeanUsingMessageSourceProperties {

    @Inject
    MessageSourceProperties messageSourceProperties;

    void verify() {
      String basename = messageSourceProperties.basename();
      assertEquals("message,another", basename);
    }
  }
}
