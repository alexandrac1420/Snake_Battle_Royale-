# Snake Battle Royale

A multiplayer snake battle game developed with React and Spring Boot that allows multiple users to compete in real-time.

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

4. **Node.js** and **npm**
    - Download Node.js from [here](https://nodejs.org/)
    - npm is included with Node.js

    Verify the installation:
    ```sh
    node -v
    ```
    Should return something like:
    ```sh
    v14.17.0
    ```

    ```sh
    npm -v
    ```
    Should return something like:
    ```sh
    6.14.13
    ```

### Installing

1. Clone the repository and navigate into the project directory:
    ```sh
    git clone https://github.com/alexandrac1420/Snake_Battle_Royale-
    cd Snake_Battle_Royale-
    ```
2. Install the React frontend dependencies and build the project:
    ```sh
    cd ../front
    npm install
    npm run build
    ```

### Running the Application

To run the backend and frontend, follow these steps:

1. **Run the React frontend:**
    ```sh
    cd front
    npm start
    ```

    The frontend will start on `http://localhost:3000` and communicate with the backend to retrieve and update the game actions.

    ![alt text](https://github.com/alexandrac1420/Snake_Battle_Royale-/blob/main/Pictures/image.png)

## Game Controls

Use the arrow keys on your keyboard to control the snake:
- **Up Arrow:** Move up
- **Down Arrow:** Move down
- **Left Arrow:** Move left
- **Right Arrow:** Move right

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management for backend
* [npm](https://www.npmjs.com/) - Dependency Management for frontend
* [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
* [React](https://reactjs.org/) - Frontend framework
* [Git](http://git-scm.com/) - Version Control System

## Versioning

I use [GitHub](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/alexandrac1420/Snake_Battle_Royale-/tags).

## Authors

* **Alexandra Cortes Tovar** - [alexandrac1420](https://github.com/alexandrac1420)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
