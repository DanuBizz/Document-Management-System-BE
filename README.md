<div id="readme-top"></div>
<div align="center">
  <a href="https://imgbb.com/"><img src="https://i.ibb.co/51J4TzV/logo.png" alt="logo" border="0"></a>
</div>

<br />
<div align="center">
  <h1 align="center">Kultur Betriebe Burgenland</h1>
  <h3 align="center">Backend für die Entwicklung der Web-Applikation Kultur Betriebe Burgenland</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary style="font-size: 24px;">Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#project-components">Project Components</a>
        <ul>
        <li><a href="#main-components">Main Components</a></li>
      </ul>
    </li>
    <li>
      <a href="#project-structure">Project Structure</a>
        <ul>
          <li><a href="#pom-file">POM File</a></li>
          <li><a href="#docker-compose-file">docker-compose File</a></li>
          <li><a href="#integration-with-frontend-ui">Integration with Frontend-UI</a></li>
        </ul>
    <li>
      <a href="#built-with">Built With</a>
        <ul>
          <li><a href="#swagger-ui-and-openapi-docs">Swagger UI and OpenAPI Docs</a></li>
        </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#screenshots">Screenshots</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

The project serves as a document management system, offering various functionalities such as creating user groups, categories, and documents. When creating a new document, all users are notified via email about the new document. Upon logging into the application via a login window, users are initially redirected to a page where they must manually confirm the document before accessing the main view.

Additionally, the system includes user management, allowing administrators to grant or revoke administrator rights to users. Documents can be opened and viewed in the web interface. Administrators have the ability to create new versions of a document and hide documents from users' view. All tables in the system feature sorting, pagination, and search functionalities to facilitate navigation and management.

## Project Components

### Main Components

- **Authentication and Authorization:**
    - Manages user authentication through Active Directory integration.
    - Provides secure access control for different user roles (administrators and regular users).

- **Document Management:**
    - Handles CRUD operations for documents and their versions.
    - Supports document viewing within the web interface.
    - Allows documents to be marked as "hidden" to manage visibility.

- **Category Management:**
    - Enables the creation and editing of document categories.
    - Associates categories with user groups to control access.

- **User and Group Management:**
    - Facilitates management of users and user groups.
    - Allows assignment and revocation of administrator rights.
    - Manages user group memberships.

- **Email Notifications:**
    - Sends email notifications for new document uploads.

<!-- PROJECT STRUCTURE -->
## Project Structure

The project consists of a parent POM and a single module for the document management service.

<!-- POM FILE -->
### POM File

The parent POM includes configuration for common properties and build plugins like Checkstyle and PMD to ensure code quality.

Module POM-File includes:

- Spring Boot Starter Data JPA
- Spring Boot Starter Mail
- Spring Boot Starter Web
- Spring Boot Dev-Tools
- MariaDB JDBC driver
- Lombok
- Spring Boot Starter Test
- Springdoc OpenAPI Starter Web MVC UI
- H2 Database (for testing)
- Spring Boot Starter Security
- Spring LDAP Core
- Spring Security LDAP
- UnboundID LDAP SDK

### docker-compose File:
The "docker-compose" file orchestrates the setup for the application environment. It includes configurations to connect to the MariaDB database and start the application. Additionally, it includes configurations to start MailHog, which is used for email testing.

```yml
version: '3.1'

services:
  mariadb:
    image: mariadb:10.5
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: documentDB
      MYSQL_USER: KBB-dev
      MYSQL_PASSWORD: documentDB-pw
    volumes:
      - ./data:/var/lib/mysql
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    ports:
      - 3306:3306
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 3

  mailhog:
    image: mailhog/mailhog
    restart: always
    ports:
      - 1025:1025
      - 8025:8025

  document-management-service:
    build:
      context: ./documentManagementService
      dockerfile: Dockerfile
    depends_on:
      mariadb:
        condition: service_healthy
      mailhog:
        condition: service_started
    environment:
      SPRING_DATASOURCE_URL: jdbc:mariadb://mariadb:3306/documentDB
      SPRING_DATASOURCE_USERNAME: KBB-dev
      SPRING_DATASOURCE_PASSWORD: documentDB-pw
      SPRING_MAIL_HOST: mailhog
      SPRING_MAIL_PORT: 1025
    ports:
      - 8080:8080
```
<!-- Integration with Frontend-UI -->
### Integration with Frontend-UI

In a parallel project, an Angular application was developed. This application manages the entire user interface, including functionalities like login, document management, and user management.

- Link to GitHub Repo: [GitHub Repo](https://github.com/DanuBizz/KulturBetriebeBurgenland-FE)

## Built With

This project leverages a powerful technology stack to ensure robustness, scalability, and ease of development.

| Backend Technologies     | DevOps Technologies   | Testing Technologies  | Linter Technologies  |
|--------------------------|-----------------------|-----------------------|----------------------|
| [<img src="https://1000logos.net/wp-content/uploads/2020/09/Java-Logo.png" alt="Java Logo" width="150">][Java-url] | [<img src="https://upload.wikimedia.org/wikipedia/commons/7/70/Docker_logo.png" alt="Docker Logo" width="200">][Docker-url] | [<img src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="JUnit Logo" width="150">][JUnit-url] | [<img src="https://upload.wikimedia.org/wikipedia/commons/a/a9/Checkstyle_Logo.png" alt="Checkstyle Logo" width="150">][Checkstyle-url] |
| [<img src="https://e4developer.com/wp-content/uploads/2018/01/spring-boot.png" width="150">][Spring-Boot-url] | [<img src="https://raw.githubusercontent.com/swagger-api/swagger.io/wordpress/images/assets/SWE-logo-clr.png" alt="SwaggerUI Logo" width="200">][Swagger-url] | [<img src="https://miro.medium.com/v2/resize:fit:800/1*SU4u-Fj6FJRh_LcdMXoQSQ@2x.png" alt="Mockito Logo" width="200">][Mockito-url] | [<img src="https://raw.githubusercontent.com/pmd/pmd/pmd/7.0.x/docs/images/logo/pmd-logo-300px.png" alt="PMD Logo" width="150">][PMD-url] |
| [<img src="https://www.cleo.com/sites/default/files/2023-12/ldap-integration.png" alt="LDAP Logo" width="200">][LDAP-url] | [<img src="https://miro.medium.com/v2/resize:fit:1400/1*SQn7hF3iATJd0Xbz3QnBYg.png" alt="OpenAPI Logo" width="200">][OpenAPI-url] | | |
| [<img src="https://static.wixstatic.com/media/6a27b5_af6321a8828d4d13b7083c94254d6797~mv2.png/v1/fill/w_640,h_302,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/6a27b5_af6321a8828d4d13b7083c94254d6797~mv2.png" alt="Active-Directory Logo" width="200">][Active-Directory-url] | | | 
| [<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Apache_Maven_logo.svg/640px-Apache_Maven_logo.svg.png" alt="Maven Logo" width="150">][Maven-url]|

### Swagger UI and OpenAPI Docs
The API endpoints are documented using Swagger UI and OpenAPI. You can access the documentation through the following URLs once the application is running:

* Swagger UI: http://localhost:8080/swagger-ui/index.html

* OpenAPI Docs: http://localhost:8080/v3/api-docs/public

<!-- GETTING STARTED -->

## Getting Started

Follow these steps to set up and run the application in your local environment.

### Prerequisites

Make sure you have the following installed:

1. [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
2. [Docker](https://www.docker.com/get-started)

Make sure your Docker engine is running. You can start Docker Desktop or check with the following command:

```bash
docker info
```

Also, ensure you have the correct version of the Java Development Kit installed. You can check the installed Java Development Kit version with:

```bash
java --version
```
Make sure the version is JDK 17 or later.

### Installation

#### Clone the repository

```bash
- git clone https://github.com/DanuBizz/KulturBetriebeBurgenland-BE.git
```
#### Run Application

##### Option 1

- Open the application in your prefered development environment or Editor (We used Intellij)
- Run the application by executing the Docker Compose file

##### Option 2

- Open the command prompt.
- Navigate to the project folder.
- Run the Docker Compose Up command with the following command:

```bash
docker-compose up
```
##### Option 3

- Open the command prompt.
- Navigate to the project folder.
- Run mvn clean package to build the project.
```bash
mvn -B clean package
```
- Run the built .jar file with the following command:
```bash
java -jar target/documentManagementService-0.0.1-SNAPSHOT.jar
```
- Run the mariaDB container with the following command:
```bash
docker-compose up mariadb
```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Screenshots

### SwaggerUI

<a href="https://ibb.co/Kj3SsWp"><img src="https://i.ibb.co/TBFd0tx/Screenshot-2024-06-02-235052.png" alt="Screenshot-2024-06-02-235052" border="0"></a>

### OpenAPI Doc

<a href="https://ibb.co/xXbK4rY"><img src="https://i.ibb.co/bQVjfGF/Screenshot-2024-06-02-235118.png" alt="Screenshot-2024-06-02-235118" border="0"></a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

[![contributors-shield]][contributors-url]  [![issues-shield]][issues-url]  [![forks-shield]][forks-url]

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the Apache 2.0 License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

- MikeSirb - 2210859010@fh-burgenland.at
- DanuBizz - 2210859021@fh-burgenland.at
- Hasnat-Sajid - 2210859008@fh-burgenland.at

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- SOURCES -->

## Sources

We leveraged various resources and platforms during the development of the Sensor Measurements API. Here are some of the key sources:

- ![YouTube][YouTube-Badge] - YouTube tutorials and channels offering insights into microservices architecture and Spring Boot.
- ![Baeldung][Baeldung-Badge] - Baeldung's tutorials and guides were instrumental in understanding Spring Boot best practices.
- ![Spring-Boot-Documentation][Spring-Boot-Documentation-Badge] - Official documentation for Spring Boot, serving as a comprehensive reference.
- ![OpenAPI-Specification][OpenAPI-Specification-Badge] - OpenAPI Specification documentation guided us in implementing standardized API documentation.
- ![ChatGPT][ChatGPT-Badge] - OpenAI's GPT models, including ChatGPT, were used for generating code snippets and assisting in problem-solving.
- ![Stack-Overflow][Stack-Overflow-Badge] - The Stack Overflow community provided solutions to specific programming challenges and debugging assistance.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/DanuBizz/KulturBetriebeBurgenland-BE/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/DanuBizz/KulturBetriebeBurgenland-BE/fork
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/DanuBizz/KulturBetriebeBurgenland-BE/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/blob/master/LICENSE.txt
[Java-logo]: https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com
[Spring-Boot-logo]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-Boot-url]: https://spring.io/projects/spring-boot
[Docker-logo]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com
[Swagger-logo]: https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black
[Swagger-url]: https://swagger.io
[OpenAPI-logo]: https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=black
[OpenAPI-url]: https://www.openapis.org
[LDAP-logo]: https://img.shields.io/badge/LDAP-0076C0?style=for-the-badge&logo=ldap&logoColor=white
[LDAP-url]: https://ldap.com
[Active-Directory-logo]: https://seeklogo.com/images/A/active-directory-logo-73332B0200-seeklogo.com.png
[Active-Directory-url]: https://learn.microsoft.com/de-de/windows-server/identity/ad-ds/get-started/virtual-dc/active-directory-domain-services-overview
[Exchange-logo]: https://img.shields.io/badge/Exchange-0078D4?style=for-the-badge&logo=microsoft%20exchange&logoColor=white
[Exchange-url]: #your-exchange-url-here
[MariaDB-logo]: https://mariadb.com/wp-content/uploads/2019/11/mariadb-horizontal-blue.svg
[MariaDB-url]: https://mariadb.org
[Maven-logo]: https://maven.apache.org/images/maven-logo-black-on-white.png
[Maven-url]: https://maven.apache.org
[JUnit-logo]: https://junit.org/junit5/assets/img/junit5-logo.png
[JUnit-url]: https://junit.org
[Mockito-logo]: https://github.com/mockito/mockito/blob/release/3.x/doc/logo-letters.png?raw=true
[Mockito-url]: https://site.mockito.org
[Checkstyle-logo]: https://checkstyle.org/img/logo-sm.png
[Checkstyle-url]: https://checkstyle.org
[PMD-url]: https://pmd.github.io
[PMD-logo]: https://github.com/pmd/pmd/blob/master/pmd-site/src/main/resources/images/pmd-logo.png
[YouTube]: https://www.youtube.com 
[YouTube-Badge]: https://img.shields.io/badge/-YouTube-red
[Baeldung]: https://www.baeldung.com
[Baeldung-Badge]: https://img.shields.io/badge/-Baeldung-brightgreen
[Spring-Boot-Documentation]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle 
[Spring-Boot-Documentation-Badge]: https://img.shields.io/badge/-Spring%20Boot%20Documentation-blue
[OpenAPI-Specification]: https://swagger.io/specification 
[OpenAPI-Specification-Badge]: https://img.shields.io/badge/-OpenAPI%20Specification-lightgrey
[ChatGPT]: https://www.openai.com/gpt 
[ChatGPT-Badge]: https://img.shields.io/badge/-ChatGPT-yellow
[Stack-Overflow]: https://stackoverflow.com 
[Stack-Overflow-Badge]: https://img.shields.io/badge/-Stack%20Overflow-orange

