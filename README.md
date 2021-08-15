
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
java -jar ./target/treasure-0.0.1-SNAPSHOT-jar-with-dependencies.jar -i=src/test/resources/com/carbon/treasure/integrationTest/integrationTestResource
```
running the executable file

```bash
./target/treasure -i=src/test/resources/com/carbon/treasure/integrationTest/integrationTestResource
```

more help is available using :

```bash
java -jar ./target/treasure-0.0.1-SNAPSHOT-jar-with-dependencies.jar -h
```
or

```bash
./target/treasure -h
```

## Specification choices :
Few thing in specification have been precised when hitting a player the player don't skip its instruction.
if a player1 can't move because blocked by a player2 that can't move any more the player1 consumed all it instruction and will no more move. 
