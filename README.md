# mtfc - MultiThread FileCopier
Tool to copying multiple files at the same time.  
Writen by
[Maciej Nowak](https://www.linkedin.com/in/maciek-nowak/)
and
[Jakub Skiba](https://www.linkedin.com/in/jakub-skiba-krk/)
during [Codecool](https://codecool.pl/) course.

## Technologies
* Java 8
* Multithreading
* Spring context
* Maven

## Features
* Thread pool
* File I/O Streams
* Thread priority
* Locking files during copying
* application.properties configuration

## Configuration
To configure application modify src/main/resources/application.properties file.  
There are two variables:
* mtfc.copierThreads - (int) means amount of threads used to copying files
* mtfc.enableDelay - (boolean) toogles delay during copying of file. useful to test features on smaller files

## Start
If you are unix user:
simply clone repo and execute run.sh script.

In other case:
run maven package and then execute jar file.
