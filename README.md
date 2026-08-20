# Secure Time Booking

**Secure Time Booking** is a Java-based web application designed for managing time-booking functionality with a focus on **secure access, user and administrator interaction, real-time communication, and email notifications**.

The project combines a Java backend with a lightweight HTML, CSS, and JavaScript frontend.

---

## About

The application was built to explore and implement several concepts in Java web development, including:

* User and administrator functionality
* Secure session and cookie handling
* IP-based validation for administrative access
* Real-time communication using WebSockets
* Email notifications using SendGrid
* Frontend development using HTML, CSS, and JavaScript
* Integration between frontend and backend components

---

## Technologies Used

### Backend

* Java
* Java Web Technologies
* WebSockets
* Cookies
* HTTP Sessions
* SendGrid Email API

### Frontend

* HTML
* CSS
* JavaScript

Frontend resources are maintained under:

```text
src/main/resources/static/
```

---

## Key Features

### User & Admin Roles

The application supports different functionality for **users and administrators**.

Users can interact with the time-booking system, while administrators have additional privileges for managing and monitoring the application.

---

### Real-Time Admin/User Chat

The application includes a **live chat system between administrators and users** using WebSockets.

Unlike traditional request/response communication, WebSockets allow the application to maintain a persistent connection and exchange messages in real time.

```text
User
  │
  │ WebSocket Connection
  ▼
Server
  │
  │ WebSocket Connection
  ▼
Admin
```

This allows messages to be delivered without repeatedly refreshing or polling the server.

---

### Email Notifications

Email functionality is implemented using **SendGrid**.

The application can send emails for relevant user/application events without requiring the user to manually send them.

```text
Application
     │
     ▼
SendGrid
     │
     ▼
User Email
```

> API credentials and other sensitive configuration should be stored securely and should not be committed to the repository.

---

### IP-Based Admin Validation

Administrative access includes **IP-based validation** as an additional security layer.

The application validates the client's IP address when handling protected administrative functionality.

This was implemented as an exercise in understanding how server-side request information can be used as part of an access-control mechanism.

> IP-based validation should not be considered a replacement for proper authentication and authorization in a production system.

---

### Cookies & Sessions

The project uses Java web concepts such as:

* HTTP Cookies
* Sessions
* Session-based user state
* Server-side request handling

These mechanisms are used to maintain user state and support authenticated interactions between the client and server.

---

## Frontend

The frontend is implemented using:

* HTML
* CSS
* JavaScript

Static frontend resources are located under:

```text
src/
└── main/
    └── resources/
        └── static/
            ├── html/
            ├── css/
            └── js/
```

> The exact directory structure may vary depending on the current project organization.

---

## Project Structure

```text
Secure-Time-Booking/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── ...
│       │
│       └── resources/
│           └── static/
│               ├── html/
│               ├── css/
│               └── js/
│
├── pom.xml
└── README.md
```

---

## Application Flow

A simplified view of the application:

```text
                 ┌─────────────────┐
                 │     Browser     │
                 │ HTML/CSS/JS     │
                 └────────┬────────┘
                          │
                          ▼
                 ┌─────────────────┐
                 │   Java Backend  │
                 └────────┬────────┘
                          │
          ┌───────────────┼────────────────┐
          │               │                │
          ▼               ▼                ▼
      Sessions         WebSocket        Email
      & Cookies        Chat             Service
          │               │                │
          │               │                ▼
          │               │             SendGrid
          │               │
          ▼               ▼
        Users           Admin
```

---

## 🚀 Getting Started

### Prerequisites

* Java JDK
* Maven
* A SendGrid account/API key 

### Clone the Repository

```bash
git clone <your-repository-url>
```

### Build the Application

```bash
mvn clean package
```

### Run the Application

```bash
mvn spring-boot:run
```

---

## Configuration

Sensitive configuration such as API keys should **not** be hardcoded in the source code.

For example:

```text
SENDGRID_API_KEY=<your-api-key>
```

Use environment variables or an appropriate application configuration mechanism to provide secrets at runtime.

---

## What I Learned

This project provided hands-on experience with:

* Java backend development
* Building web applications
* HTTP cookies and sessions
* WebSocket-based real-time communication
* User/admin role handling
* IP-based access validation
* Email API integration
* HTML, CSS, and JavaScript integration
* Connecting frontend functionality with backend services
* Handling application configuration and sensitive credentials

---

## Future Improvements

Potential improvements include:

* Implementing stronger authentication and authorization
* Adding database persistence
* Password hashing and account management
* Improving role-based access control
* Adding automated tests
* Adding proper input validation
* Containerizing the application with Docker
* Adding CI/CD using Jenkins
* Improving WebSocket authentication and security
* Adding logging and monitoring

---

## 📌 Project Status

**Completed — Personal/learning project.**

The project was primarily developed to gain practical experience with Java web development, security concepts, WebSockets, email integration, and frontend-backend communication.
