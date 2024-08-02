# i18n-quarkus-extension

This Quarkus extension provides an extensible functionalities for implementing internalization (i18n) in Quarkus' applications.

## Usage
### Maven:

````xml
 <dependency>
    <groupId>io.github.jelilio</groupId>
    <artifactId>i18n-quarkus-extension</artifactId>
    <version>0.0.1</version>
</dependency>
````

### Gradle:

````    
implementation 'io.github.jelilio:i18n-quarkus-extension:0.0.1'
````  
### Define locale source file in the "application.properties"

````properties 
quarkus.messages.basename=messages
````
#### Other "application.properties" configurations
````properties    
quarkus.messages.encoding=UTF-8
#in seconds
quarkus.messages.cache-duration=10
quarkus.messages.fallback-to-system-locale=true
quarkus.messages.always-use-message-format=false
quarkus.messages.use-code-as-default-message=false
````

### Define "messages.properties" file in classpath

````properties 
welcome.text=Welcome {0} to a new world
````

### Localizing a message

````java
@Inject
MessageSource messageSource;

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/greet/{username}")
public String greet(@PathParam("username") String username, @HeaderParam(value = "Accept-Language") Locale locale) {
  String greetingMessage = messageSource.getMessage("welcome.text", new Object[] { username }, locale);
  return String.format("Locale: %s, Message: %s", locale, greetingMessage);
}
````

### To override the default MessageSource with ReloadableResourceBundleMessageSource

````java
@Dependent
public class MessageSourceConfig {
  @Produces
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(10);
    return messageSource;
  }
}
````

### Contributor
* [@jelilio](https://jelilio.github.io)

### License

This library is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).