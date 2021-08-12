
# Treasure


## Requirement
Java 11 + or GraalVM 11 +  
Maven 3.5 +

## Packaging and installation

Use maven for packaging executable jar :

```bash
mvn clean install
```

Use maven for packaging a native executable (only tested on linux):

```bash
mvn clean install -Pnative-image
```

## Running
running the jar file

```bash
java -jar ./target/treasure-0.0.1-SNAPSHOT-jar-with-dependencies.jar -f=src/test/resources/com/carbon/treasure/integrationTest/integrationTestResource
```
running the executable file

```bash
./target/treasure -f=src/test/resources/com/carbon/treasure/integrationTest/integrationTestResource
```

more help is available using :

```bash
java -jar ./target/treasure-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h
```
or

```bash
./target/treasure -h
```
