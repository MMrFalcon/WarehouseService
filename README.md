# Warehouse Service

## FIRST RUN

### Set up empty database

With **docker** go to your app root directory and type

`docker-compose -f src/main/docker/mysql.yml up -d`

If you already have database server edit `application.properties` file located in `src/main/resources` by change `DB connection properties` section to your custom data.

____

### Application Start

**Application will generate tables and sample data.**

You can run application with Maven. To do this go to your app root directory and type
`./mvnw spring-boot:run`

You can run .jar file directly. To generate .jar file go to your application root directory and type
`./mvnw clean`
`./mvnw install`

Your jar file will be located in `./target` directory. You can simply run him by typing
`java -jar .\target\JarNameHere.jar`

___

IDE Preparation

Application using Lombok plugin. To install it in IntelliJ press ctrl + alt + s go to plugin section search for `lombok` 
install it and restart your IDE