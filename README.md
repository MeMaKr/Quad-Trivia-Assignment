# Quad Trivia Assignment

A Spring Boot application that fetches trivia questions from the Open Trivia Database API, presents them to users via a frontend interface, and evaluates their answers to provide a score.

## Features

Dynamic Question Fetching: Retrieves trivia questions from the Open Trivia Database API.

User Interaction: Displays questions with multiple-choice options.

Answer Evaluation: Compares user responses with correct answers and calculates the score.

Frontend & Backend Integration: Seamless interaction between the frontend and Spring Boot backend.

## Technologies Used

Backend: Java 17, Spring Boot

Frontend: HTML, JavaScript

API: Open Trivia Database API

Build Tool: Maven

Deployment: Docker, Render.com

## Access on Render.com

Access the application simply via this url: https://quad-trivia-assignment.onrender.com

This only works if the application is still up and running in the cloud. It might also have a delay and be relatively slow.

## Setup & Installation
### Prerequisites
Java 21

Maven

### Local Development

Clone the repository:
~~~
git clone https://github.com/MeMaKr/Quad-Trivia-Assignment.git
cd Quad-Trivia-Assignment
~~~

Build the application:
~~~
./mvnw clean install 
~~~

Run the application:
~~~
./mvnw spring-boot:run
~~~

The application will be accessible at http://localhost:8080.

## Usage

Navigate to the provided URL to interact with the trivia application. Answer the questions and submit your responses to receive a score.

## License

This project is licensed under the MIT License.
