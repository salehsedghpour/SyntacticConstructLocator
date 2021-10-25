## `Syntactic Construct Locator`

This unit uses [Spoon](https://spoon.gforge.inria.fr/) to locate syntactic constructs from a Java source file.
Currently it supports:

- Loops
- If
- Assertions
- Flow breaks
- Switch
- Syncrhonized
- Try

### Dependencies
- JDK 11+
- [Maven](https://maven.apache.org/)

### Building and running

- `mvn clean install`
- ```
  java -jar target/<name-version-jar-with-dependencies>.jar /path/to/a/java/SourceFile.java
  --locate <loop (default) OR if OR assertions OR flow_break OR Switch OR Synchronized OR Try>
  ```