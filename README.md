## Chess - Group 064

A chess program written in Java. 

## Requires 
* MongoDB 4.4.10

## Project Set-Up Instructions
Although the project uses MongoDB, nothing needs to be downloaded to set up the project, the the pom.xml file takes care of this. Because our project is built on Spring Boot framework (a Maven framework), there might be some potential issues when loading the project to your computer. Please follow instructions shown below if the project cannot successfully load:
1. If the project *cannot* be recognized as a Spring Boot project (the pom.xml file will be shown in orange), please right click on the pom.xml file and choose: “add a Maven to this project”. After this step, the pom.xml file will turn into blue.
2. If the above step does not work, then please hit Build -> run ->  filters to make the IntelliJ build up for us.
3. Please always enable auto-import Maven functionalities whenever a option about that pops up  and always click on the below icon in the pom.xml file whenever it pops up.

![Icon](https://i.pinimg.com/564x/ef/18/12/ef1812771605235a5c5f6b23e5fb4c05.jpg)

Next is the process of booting our project:
1.  Find the src/main/java/com.playchessgame.chessgame/ChessGameApplication.java class and run its main method. It is the entry point of our project.

![ChessGameApplication.java](https://i.pinimg.com/564x/29/44/64/2944643116ea7052b8f1f6eb5f71e8d5.jpg)

2. Wait for a while until no more lines come in the IntelliJ Console. That means that our project has been initialized and ready.
3. Open a web browser and go to the site/URL: localhost:8080. One will be able to see our index page and begin the journey of our project!

Last, for running tests, please right click on the test package and mark it as Test Sources Root. If the warning shown as below pops up, please ignore it because it does not impact the program running.

![Warning](https://i.pinimg.com/564x/92/fc/54/92fc5491e9e61bcb4de767530c1202ed.jpg)
