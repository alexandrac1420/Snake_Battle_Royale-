# Snake Game Application

An interactive Snake game developed with React and Spring Boot that allows multiple players to play in real-time using WebSockets and MongoDB for data storage and session management.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools and configure their dependencies:

1. **Java** (versions 7 or 8)
    ```sh
    java -version
    ```
    Should return something like:
    ```sh
    java version "1.8.0"
    Java(TM) SE Runtime Environment (build 1.8.0-b132)
    Java HotSpot(TM) 64-Bit Server VM (build 25.0-b70, mixed mode)
    ```

2. **Maven**
    - Download Maven from [here](http://maven.apache.org/download.html)
    - Follow the installation instructions [here](http://maven.apache.org/download.html#Installation)

    Verify the installation:
    ```sh
    mvn -version
    ```
    Should return something like:
    ```sh
    Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T12:29:23-05:00)
    Maven home: /Users/dnielben/Applications/apache-maven-3.2.5
    Java version: 1.8.0, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/jre
    Default locale: es_ES, platform encoding: UTF-8
    OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"
    ```

3. **Git**
    - Install Git by following the instructions [here](http://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

    Verify the installation:
    ```sh
    git --version
    ```
    Should return something like:
    ```sh
    git version 2.2.1
    ```

### Installing

1. Clone the repository and navigate into the project directory:
    ```sh
    git clone https://github.com/alexandrac1420/Snake_Battle_Royale-.git
    cd Snake_Battle_Royale-
    ```

2. Build the Spring Boot backend:
    ```sh
    mvn package
    ```

    Should display output similar to:
    ```sh
    [INFO] Building jar: C:\Users\your-user\Downloads\SnakeGame\target\SnakeGame-0.0.1-SNAPSHOT.jar       
    [INFO] The original artifact has been renamed to C:\Users\your-user\Downloads\SnakeGame\target\SnakeGame-0.0.1-SNAPSHOT.jar.original
    [INFO] BUILD SUCCESS
    ```

### Running the Application

To run the backend and frontend, follow these steps:

2. **Run the Spring Boot backend:**
    ```sh
    mvn spring-boot:run
    ```

    The backend will start on `http://localhost:8080`, and it will store the game data and session information in MongoDB.

## Architectural Design

### Class Diagram

![Class Diagram](https://github.com/alexandrac1420/Snake_Battle_Royale-/blob/main/Pictures/DiagramaClases.png)

The class diagram provides an overview of the structure of the Snake Game application. Below is a general description of each class and their relationships:

#### Classes and Their Descriptions

1. **SnakeGame**
    - Manages the overall state and logic of the game, including the game board dimensions, the active and eliminated snakes, the positions of apples and power food, and the game status.

2. **Snake**
    - Represents the snake in the game, including its body segments, direction of movement, and status (alive or dead). Manages the snake's movement, growth, and collision detection.

3. **Apple**
    - Represents the apple on the game board, which the snake can eat to grow.

4. **PowerFood**
    - Represents special food items that give the snake temporary powers, such as increased speed or immunity.

5. **Point**
    - Represents a coordinate on the game board, used by the Snake, Apple, and PowerFood classes.

6. **User**
    - Represents a player in the game, including their username, password, and high score.

7. **GameRoom**
    - Manages the game session, including the users in the room and the SnakeGame instance. Facilitates user joining and leaving.

8. **MoveRequest**
    - Encapsulates a request to move the snake in a specified direction.

9. **ReadyRequest**
    - Represents a request indicating that a user is ready to start the game.

10. **GameRoomController**
    - Handles HTTP requests related to game rooms, such as starting a game or joining a room.

11. **SnakeGameController**
    - Handles HTTP requests related to the snake game, such as moving the snake or handling game events.

12. **UserController**
    - Manages user-related HTTP requests, including user registration and login.

13. **GameRoomService**
    - Contains business logic for creating and managing game rooms.

14. **SnakeGameService**
    - Contains business logic for updating and managing the state of the snake game.

15. **UserService**
    - Handles user authentication and registration logic.

16. **GameRoomRepository**
    - Interfaces with the database to perform CRUD operations on game room data.

17. **UserRepository**
    - Interfaces with the database to perform CRUD operations on user data.

#### Relationships
- **SnakeGame** is composed of **Snake**, **Apple**, and **PowerFood**.
- **Apple** and **PowerFood** use **Point** for their positions.
- **Snake** is composed of **Point**.
- **GameRoom** is composed of **User** and **SnakeGame**.
- **Controllers** interact with **Services**.
- **Services** interact with **Repositories**.

This class diagram provides a detailed view of the structure and relationships within the Snake Game application, illustrating how different components interact and work together to create the full functionality of the game.

### Deployment Diagram

![Deployment Diagram](https://github.com/alexandrac1420/Snake_Battle_Royale-/blob/main/Pictures/Despliegue.png)

The deployment diagram shows how the application is organized across different nodes:
- **Client (Browser)**: Contains the React application.
- **Server (Spring Boot)**: Contains the API server.
- **Database (MongoDB)**: Stores user data and game state.
- The connections show the communication between these nodes.

### Component Diagram

![Component Diagram](https://github.com/alexandrac1420/Snake_Battle_Royale-/blob/main/Pictures/Componentes.png)

The component diagram shows the internal structure of the application:
- **Frontend (React)**: Includes the UI components and WebSocket client.
- **Backend (Spring Boot)**: Includes controllers, services, repositories, and security configuration.
- The relationships show how these components interact with each other.

### Explanation of Components

#### UI Components (React)
Manages the user interface of the game.

#### WebSocket Client (React)
Handles real-time communication with the server using WebSockets.

#### Controllers (Spring Boot)
Handles HTTP and WebSocket requests:
- **GameRoomController**: Manages game rooms.
- **SnakeGameController**: Manages the snake game.
- **UserController**: Manages users.

#### Services (Spring Boot)
Implements the business logic:
- **GameRoomService**: Manages game room logic.
- **SnakeGameService**: Manages snake game logic.
- **UserService**: Manages user authentication and registration.

#### Repositories (Spring Boot)
Accesses the database:
- **GameRoomRepository**: Accesses game room data.
- **UserRepository**: Accesses user data.

#### Security Config (Spring Boot)
Configures the application security.


### Design Patterns Employed

1. **Singleton Pattern**
   - **Usage**: Ensures a single instance of configuration classes, such as MongoDB configuration.
   - **Where**: Configuration classes in the backend.

2. **Factory Pattern**
   - **Usage**: Handles the creation of complex objects, like new instances of `SnakeGame`.
   - **Where**: Methods in services that create game instances.

3. **Observer Pattern**
   - **Usage**: Manages real-time updates between the server and multiple clients.
   - **Where**: WebSocket communication.

4. **Dependency Injection (DI) Pattern**
   - **Usage**: Injects dependencies to improve modularity and facilitate testing.
   - **Where**: Spring Boot's use of `@Autowired` in controllers and services.

### Architectural Styles Employed

1. **Client-Server Architecture**
   - **Description**: Separates the client (React) and server (Spring Boot), with the client making requests to the server.
   - **Where**: The overall interaction between the frontend and backend.

2. **Layered Architecture**
   - **Description**: Divides the application into layers, each with a specific responsibility (presentation, business logic, data access).
   - **Where**: The structure of the backend (controllers, services, repositories).

3. **Real-Time Communication with WebSockets**
   - **Description**: Enables real-time bidirectional communication between the client and server.
   - **Where**: WebSocket connections for updating game state in real-time.


## Testing

The project uses JaCoCo for code coverage and SonarQube for continuous inspection. Code coverage is maintained above 85%.

### Test Cases

1. **SnakeGameControllerTest**
   - **Purpose**: Tests the SnakeGameController class.
   - **Tests**:
     - `testStartNewGame()`: Verifies that a new game can be started.
     - `testTestEndpoint()`: Checks that the test endpoint returns the expected message.

2. **ViewControllerTest**
   - **Purpose**: Tests the ViewController class.
   - **Tests**:
     - `testLogin()`: Verifies the login view is returned.
     - `testRegister()`: Verifies the register view is returned.
     - `testHome()`: Verifies the home view is returned.
     - `testCreateGame()`: Verifies the create-game view is returned.
     - `testJoinGame()`: Verifies the join-game view is returned.
     - `testLobby()`: Verifies the lobby view is returned.

3. **GameRoomRepositoryTest**
   - **Purpose**: Tests the GameRoomRepository class.
   - **Tests**:
     - `testFindByNotStarted()`: Verifies that game rooms which are not started can be found.

4. **UserRepositoryTest**
   - **Purpose**: Tests the UserRepository class.
   - **Tests**:
     - `testFindByUsername()`: Verifies that a user can be found by username.

5. **SnakeGameServiceTest**
   - **Purpose**: Tests the SnakeGameService class.
   - **Tests**:
     - `testCreateNewGame()`: Verifies that a new game can be created.
     - `testAddSnakeToGame()`: Verifies that a snake can be added to the game.
     - `testRemoveSnakeFromGame()`: Verifies that a snake can be removed from the game.
     - `testMoveSnake()`: Verifies that a snake can be moved in the game.

6. **UserServiceTest**
   - **Purpose**: Tests the UserService class.
   - **Tests**:
     - `testSave()`: Verifies that a user can be saved.
     - `testFindByUsername_UserFound()`: Verifies that a user can be found by username.
     - `testFindByUsername_UserNotFound()`: Verifies that the appropriate response is returned when a user is not found.

### SonarQube Integration

To ensure code quality and coverage, SonarQube is integrated into the project:

1. **Setup SonarQube**:
   - Pull the SonarQube Docker image and run it:
     ```sh
     docker pull sonarqube
     docker run -d -p 9000:9000 --name sonarqube sonarqube
     ```

   - Access SonarQube at `http://localhost:9000` and log in with the default credentials (`admin`/`admin`).

2. **Configure Maven**:
   - Add the SonarQube plugin to your `pom.xml`:
     ```xml
     <build>
       <plugins>
         <plugin>
           <groupId>org.sonarsource.scanner.maven</groupId>
           <artifactId>sonar-maven-plugin</artifactId>
           <version>3.9.1.2184</version>
         </plugin>
       </plugins>
     </build>
     ```

3. **Run SonarQube Analysis**:
   - Use the following Maven command to perform SonarQube analysis:
     ```sh
     mvn verify sonar:sonar -D sonar.token=<your-sonar-token>
     ```

   Replace `<your-sonar-token>` with the token generated from the SonarQube interface.

### JaCoCo Integration

JaCoCo is used for code coverage reporting:

1. **Add JaCoCo Plugin**:
   - Add the JaCoCo plugin to your `pom.xml`:
     ```xml
     <build>
       <plugins>
         <plugin>
           <groupId>org.jacoco</groupId>
           <artifactId>jacoco-maven-plugin</artifactId>
           <version>0.8.12</version>
           <executions>
             <execution>
               <goals>
                 <goal>prepare-agent</goal>
               </goals>
             </execution>
             <execution>
               <id>report</id>
               <phase>test</phase>
               <goals>
                 <goal>report</goal>
               </goals>
               <configuration>
                 <excludes>
                   <exclude>**/clases</exclude>
                 </excludes>
               </configuration>
             </execution>
             <execution>
               <id>jacoco-check</id>
               <goals>
                 <goal>check</goal>
               </goals>
               <configuration>
                 <rules>
                   <rule>
                     <element>PACKAGE</element>
                     <excludes>
                       <exclude>**/clases</exclude>
                     </excludes>
                     <limits>
                       <limit>
                         <counter>CLASS</counter>
                         <value>COVEREDRATIO</value>
                         <minimum>0.85</minimum>
                       </limit>
                     </limits>
                   </rule>
                 </rules>
               </configuration>
             </execution>
           </executions>
         </plugin>
       </plugins>
     </build>
     ```

2. **Generate Coverage Report**:
   - Run Maven to generate the coverage report:
     ```sh
     mvn clean verify
     ```

   - View the generated coverage report at `target/site/jacoco/index.html`.
  
     ![image](https://github.com/user-attachments/assets/63cd0127-e912-4ad2-9ac1-dc742eb4ac47)


## Deployment on AWS

Follow these steps to deploy the application on AWS:

1. **Start the virtual machine**

    Launch an EC2 instance with your preferred configuration.

2. **Transfer dependencies and the JAR file**

    Upload the dependencies.zip (containing necessary dependencies) and the built JAR file to the created virtual machine.

3. **Execute the following command**

    Navigate to the directory where you uploaded the files and run:
    ```sh
    java -jar demo-0.0.1-SNAPSHOT.jar
    ```
    This will start the Spring service.

4. **Start the Spring service**

    Ensure the Spring Boot application starts without errors.

5. **Verify the deployment**

    Check the application's availability using the public DNS of the EC2 instance on port 8080.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management for backend
* [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
* [React](https://reactjs.org/) - Frontend framework
* [MongoDB](https://www.mongodb.com/) - Data storage
* [Git](http://git-scm.com/) - Version Control System

## Versioning

I use [GitHub](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/alexandrac1420/Snake_Battle_Royale-.git).

## Authors

* **Alexandra Cortes Tovar** - [alexandrac1420](https://github.com/alexandrac1420)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
