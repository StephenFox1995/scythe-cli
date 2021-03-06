# scythe-cli
[![Build Status](https://travis-ci.org/StephenFox1995/scythe-cli.svg?branch=master)](https://travis-ci.org/StephenFox1995/scythe-cli)

Scythe is a command line and environment parser for Java.

### Documentation
- Commands
    - __TODO__
- Options
    - Aliases
    - Supported types
    - Single value options
    - Multi value options
    - Multiple options
    - Flags
    - Required options
    - Default option values __TODO DOCS__
    - Option Ordering
- Environment variables
    - __TODO__

The basic building block of the Scythe parser are options. To create an option use the `Option` annotation, the `Option` annotation can be declared at fields and methods. Depending on declaration site of the `Option` the option values parsed from the cli will be passed to your application in different ways. If the annotations are declared at a field then a call to Scythe will return a mapping of option names to the corresponding values. 

For example:

```java
class Main {

  @Option(name="--name")
  @Option(name="--age", type=Integer.class)
  Object options;
  
  // args = ["--name", "Stephen", "--age", "100"]
  public static void main(String[] args) {
    final Map<String, Object> values = Scythe.cli(args, Main.class).parse();        
    values.get("--name"); // Stephen
    values.get("--age"); // 100
  }
}
```

Alternatively the options can be declared at a method level, if options are declared at 
method level then the method they are declared at will be invoked with the options.

For example:

```java
class Main {
  
  // args = ["--name", "Stephen", "--age", "100"]
  public static void main(String[] args) {
    Scythe.cli(args, Main.class).parse();
  }
  
  @Option(name="--name")
  @Option(name="--age", type=Integer.class)
  public static void main(String name, Integer age) {
    System.out.println(name); // Stephen
    System.out.println(age); // 100
  }
}
```

### Supported types
Scythe supports `String` and all `Number` subtypes i.e. `Integer`, `Float`, `Double` etc.
```java
@Option(name="--int", type=Integer.class)
@Option(name="--float", type=Float.class)
@Option(name="--string") // String is default type so not required explicitly.
```

Scythe also supports custom types, with the requirement that the type has a default constructor.

```java
class CustomClass {
  // Required constructor.
  public CustomClass(String arg) {}
}
```

```java
@Option(name="--customOption", type=CustomClass.class)
```


### Single Value Options
Single value options are options that take a single value.

```java
@Option(name="--host")
@Option(name="--port", type=Integer.class)
```

```bash
$ opts --host 127.0.0.1 --port 8080
```

### Multi Value Options
Multi value options allow for multiple values to be assign to one option.
By using `nargs=2` we expect the `--dimensions` option to have two values.
```java
@Options(name="--dimensions", nargs=2, type=Integer.class)
```

```bash
$ nargs --dimensions 2 4
```

### Multiple Options
Multiple options allow for the same option to be declared multiple times with different values. Scythe will pass values of these to your application inside a `java.util.List` class.
```java
@Option(name="--env", multiple=true)
```

```bash
$ multi --env DOCKER_HOST=127.0.0.1 --env DOCKER_PORT=2375
```

### Flags
Flags are determined either by the presence or absence of the value.

```java
@Option(name="--reset", isFlag=true)
```

```bash
$ flag --reset  # true

$ flag # false
```

### Required Options
Options can be required or optional. By default options are required. If options are not required and no value is given for them then they will be passed as null.

```java
@Option(name="--gender", required=false)
@Option(name="--age") // Implicitly required.
```

### Default Options

