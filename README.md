# harperdb-sdk-java

### Authorization
```Java
Authorization auth = Authorization.basic()
    .withUserName("admin")
    .withPassword("test_pwd_123");
```

### Configuration
HarperDB SDK can be configured using a `.yaml` or a `.properties` file. If the file is available under `src/main/resources` 
with the name `harperdb` it will be automatically loaded with the Config initialization.
When user provides multiple configurations, they will be loaded according to the specified order.
So the properties in the first configuration file can be overridden using the second configuration file. 

Configuration initialization can be done in two ways. If you have configuration files 
in the default location with the default name; you can use `create()` to initialize 
the configuration;
```Java
HarperDBConfig config = HarperDBConfig.create();
```

or a configuration files can be specified when Config initialization happens.

```Java
HarperDBConfig config = HarperDBConfig.builder()
                .sources(
                        classpath("harperdb_custom.properties")
                        classpath("harperdb_custom.yaml")
                ).build();
```
