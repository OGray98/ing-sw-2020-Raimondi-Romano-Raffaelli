# Prova Finale di Ingegneria del Software - a.a. 2019-2020

The project is the implementation of the game [Santorini](http://www.craniocreations.it/prodotto/santorini/) through 
the MVC pattern

## Students
- [__Daniele Raimondi__](https://github.com/OGray98)
- [__Francesco Romanò__](https://github.com/romano-francesco)
- [__Davide Raffaelli__](https://github.com/daxus4)

## Functionalities implemented:
- Complete rules
- GUI
- CLI
- Socket
- Advanced Gods (as advanced functionality)

# Documentation
In this section there are all the documents used to realize the design of the game

### UML
The follow uml describes the structure of the project
- [UML](https://github.com/OGray98/ing-sw-2020-Raimondi-Romano-Raffaelli/tree/master/deliveries/uml)


### JavaDoc
The follow documentation describe all method and class of project with java style, it is possible to see it at this link : [Javadoc](https://github.com/OGray98/ing-sw-2020-Raimondi-Romano-Raffaelli/tree/master/deliveries/javadoc)

### Libraries e Plugins
|Library/Plugin|Description|
|---------------|-----------|
|__maven__|management tool for Java's software and build automation|
|__junit__|Java's framework for unit testing|
|__Swing__|Java graphic library|


### Jars
The follow jars are used to launch the software, how to launch this jar are described in section called __Jar execution__, it is possible to see them at this link: [Jars](https://github.com/OGray98/ing-sw-2020-Raimondi-Romano-Raffaelli/tree/master/deliveries/jar).


## Test coverage

|Package|Coverage|
|---------------|-----------|
|__model__| 97% lines coverage|
|__controller__|94% lines coverage|
|__message and exception__|100% lines coverage|

To see a full report click on this link [Report](https://github.com/OGray98/ing-sw-2020-Raimondi-Romano-Raffaelli/tree/master/deliveries/report)

## Jar execution

### Server
To launch the game is necessary at first to launch the server with the follow command

```
java -jar server.jar
```

### Client
The client is executed choosing the interface to play with, it is possible to choose GUI or CLI.
The follow sections describe how launch GUI or CLI.

#### CLI
To launch the CLI you must launch the client with a terminal that supports UTF-8 encoding and ANSI escapes.
This is the command to digit:
```
java -jar client.jar cli
```
#### GUI
To launch the GUI you must write:
```
java -jar client.jar 
```
