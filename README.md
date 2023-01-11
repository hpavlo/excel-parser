<h1 align="center">
  Excel parser API 📑
</h1>

<p align="center">
  <a href="#-description">Description</a> •
  <a href="#-features">Features</a> •
  <a href="#-architecture">Architecture</a> •
  <a href="#-technologies">Technologies</a> •
  <a href="#-how-to-start-the-program">How to start the program</a> •
</p>

## 📃 Description
This is a simple API for working with excel files. The program reads data from the attached file and places it in the database.
Two types of files with the extension `.xlsx` and `.xls` are available for processing.
If the user uploads the same file with changed content, only the changed data will be stored in the database and access to the previous version of the file will also be available.
If the user wants to get data from the server, he can do it by generating a `.pdf` file and download it.

## 🚀 Features

## ⚙ Architecture
|         3-layer architecture        |
|:-----------------------------------:|
|   Controllers (Presentation layer)  |
|                  ↓↑                 |
|    Services (Application layer)     |
|                  ↓↑                 |
|    Repository (Data access layer)   |

## 🧑‍💻 Technologies
| Technology             | Version |
|:-----------------------|:--------|
| JDK                    | 17      |
| Maven                  | 4.0.0   |
| MySQL                  | 8.0.22  |
| Spring Boot            | 2.7.3   |
| Liquibase              | 4.9.1   |
| Jsonwebtoken           | 0.9.1   |
| Apache poi             | 5.2.0   |
| Itextpdf               | 5.5.10  |
| Swagger                | 2.9.2   |

## 📎 How to run this app
1. Clone the project from GitHub
2. Configure `/resources/application.properties` with your own URL, username and password
3. To run this app you need to have on your computer installed MySQL or you can use your own DB (than you need to configure `application.properties` with your own hibernate dialect and add a new dependency to the `pom.xml` file)
4. Run and enjoy the program 😉
