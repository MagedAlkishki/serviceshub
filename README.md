# ServiceHub

A small subscription platform for hosting your own tools as services - sign up, subscribe, and get access to gated tools without ever seeing their source code. Built as the first step toward a larger "host many of my projects in one place" platform.

Right now it ships with one service: a **QR Code Generator**.

## Stack

- Java 21, Spring Boot 3.3.4
- Spring Data JPA + H2 (in-memory database, resets on every restart)
- Plain HTML/CSS/JS frontend, no framework
- Session-based login (no Spring Security starter - just BCrypt for password hashing and the servlet's own HttpSession)
- ZXing for QR code generation

## Running it

Open the project in IntelliJ and run `ServiceHubApplication`, then visit `http://localhost:8080`.

Two demo accounts are seeded on startup:

| Email | Password | Subscribed |
|---|---|---|
| subscribed@example.com | password123 | Yes |
| free@example.com | password123 | No |

Log in as `free@example.com` to see the locked state, or `subscribed@example.com` to use the QR generator right away. You can also sign up a brand new account and subscribe yourself from the dashboard.

## How the gating works

Every service's controller checks two things before doing any real work: is someone logged in (`AuthController.currentUserId`), and is their account subscribed (`UserService.findById(...).isSubscribed()`). `QrCodeController` is the template for every service added later - the QR logic itself is only a few lines, wrapped in that same check.

## Adding a new service later

1. Add a `*Service` class with the actual logic.
2. Add a controller under `/api/services/<name>` that does the login + subscription check, then calls it.
3. Add a page to `static/` and a card to `dashboard.html`.

No changes needed to auth, subscriptions, or the database - those are shared across every service.
