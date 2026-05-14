# Advanced Webtechnologies (SS26)
## Developer Notes
### Dev Containers
This Project is set up to use **Dev Containers** for a better experience between developers on Mac and Windows. The Frontend and Backend use separate Containers to each fit its environment natively.
#### How to use?
1. Make sure **Docker** is running on your machine.
2. Make sure the extension **Dev Containers** (from Microsoft) is installed

#### VS Code
1. Open the folder "Frontend" or "Backend" explicitly
2. On the bottom right a Pop-up should appear, that says "Reopen in Container". Click on that.
   1. If the Pop-up does not show, use the shortcut `Ctrl + Shift + P` and select `Dev Containers: Reopen in Container`from the selection.
3. Wait until Docker initiated the Container and installed all required tools
4. Now you should be able to develop as if you would normally, but all is done inside the Linux-Container
#### IntelliJ
*since i am not using this, i will simply paste the response from copilot here*
1. Starten Sie Ihre JetBrains-IDE und schließen Sie eventuell geöffnete Projekte, bis Sie den **Welcome Screen** (Willkommensbildschirm) sehen.
2. Wählen Sie links im Menü **Remote Development** (Remote-Entwicklung) und dann **Dev Containers**.
3. Klicken Sie auf **New Dev Container** (Neuer Dev-Container).
4. Unter "**Dev Container from source**" (Dev-Container aus Quelle) wählen Sie den lokalen Pfad zu Ihrem Backend- oder Frontend-Ordner aus, in dem die `devcontainer.json` liegt.
5. Die IDE baut nun den Container, richtet den integrierten JetBrains Client ein und verbindet sich.

## Project - Hotel Booking Interface
### Context and Background
    Dear Team,
    I am excited to share that our small software company has been commissioned to
    develop a booking app for the Boutique Hotel Technikum. This app will be available on
    both smartphones and desktops and will be able to handle up to 100 visitors per day. Our
    goal is to create a modern and sustainable app that can be extended with additional
    features in the years to come.
    The booking app will provide guests of the Boutique Hotel Technikum with a user-friendly
    way to book hotel rooms for a specific period. Guests will also be able to register in order
    to conveniently view their bookings and personal details. The project will be carried out in
    teams of three, with each team responsible for both the backend and the frontend.
    We know this project presents a challenge — but we are confident that with our
    dedication and creativity, we will rise to meet it. We also recognize that we are not only
    building a great app, but gaining valuable experience and skills as developers.
    I look forward to working with you and to sharing our skills and ideas to achieve the best
    possible result. We will support the project through feedback rounds and code reviews
    and will assist you whenever needed. Let us work together on this project and create a
    great booking app for the Boutique Hotel Technikum!
    Best regards,
    your project lead

### Technical Requirements
- The booking app will be developed using Vue.js (Version 3) and implemented
efficiently using the Ionic framework.
- The Axios library is used for API calls, and state management is handled using Pinia.
- The application is primarily developed for mobile devices but must also be usable on
desktops.
- For the backend we will use Spring Boot (Version >= 3).
- A MySQL database is used as the data store.
### Best Practices
The following engineering standards and best practices are mandatory throughout the
project:
- Clean Code
- Clean Architecture as defined by R. Martin and discussed in class
- Richardson Maturity Model meet Level 2
- Atomic Design: the frontend must be structured accordingly
- Pinia must be used as discussed in class
- All further best practices discussed and introduced during the course are equally
binding and must be applied consistently.

### Technology Stack
#### Backend: Java with Spring Boot
- Follow [Clean Architecture](https://github.com/GunterMueller/Books-3/blob/master/Clean%20Architecture%20A%20Craftsman%20Guide%20to%20Software%20Structure%20and%20Design.pdf) (as shown in the lectures etc.)
#### Frontend: [Ionic with Vue 3](https://ionicframework.com/)
- Use modular design (e.g. Atomic Design or similar)
- Maintain state with [Pinia](https://pinia.vuejs.org/)
- Use Axios Library or JS fetch API for Ajax Calls
#### Tooling:
- [GitHub Classroom](https://github.com/Lammer-FH/semester-project-2026-mse26_team_teamarbeit)
- [GitHub Projects (Kanban)](https://github.com/orgs/Lammer-FH/projects/36)
- Figma (optional) for digital prototypes
- Markdown for documentation