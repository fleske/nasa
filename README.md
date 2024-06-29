# Desription

## Plenigo Coding Task

Using Spring Shell, create a command that takes all images of a given day from the NASA EPIC API and puts them into a
given folder.

The command to accept at least two parameters:

- date (optional), last available day if empty
- target folder

The command should create a subfolder (of target folder) per date to store images to.

## Running

Requirements:

- Java 17
- Maven

To run the application execute following commands from the root project folder:

Build:

```shell
$ ./mvnw clean install
```

You can also run the build without tests to avoid API timeouts etc.

```shell
$ ./mvnw clean install -DskipTests
```

Run:

```shell
$ java -jar target/nasa-0.0.1-SNAPSHOT.jar
```

You will be presented with a shell.

To view the description of the download command:

```shell
$ help download
```

Examples:

```shell
$ download --date 2015-10-31 --targetDirectory output
```

```shell
$ download --targetDirectory output
```

Check the targetDirectory you defined for the images.

Enjoy!