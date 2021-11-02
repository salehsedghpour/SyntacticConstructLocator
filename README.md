## `Syntactic Construct Locator`

This unit accepts an input containing a list of AST nodes, and filters AST elements based on input parameters.
It currently supports:

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

- ```
  mvn clean install
  alias scl='java -jar /abs/path/to/scl.jar'
  ```
- Options:
```
  -i OR --i: /path/to/parsed/ast
  -l OR --locate: loop (default) OR if OR assertions OR flow_break OR Switch OR Synchronized OR Try
  -f OR --format: table (default) OR csv <this specifies the output format>
  ```
- pipe-in input from [AST parser](https://github.com/khaes-kth/Simple-Parser)
    - `parser -s /path/to/source/dir -f csv | scl -l loop -f csv `
- load input from file
    - `scl -i /path/to/parsed/ast -l loop -f table`

