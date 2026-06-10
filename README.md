# Advanced Webtechnologies (SS26)
## How to run this application
1. Start Docker
2. Navigate into the project's repository folder (where compose.yml is located)
3. Run ```docker compose up --build -d```
   - ```--build``` rebuilds the frontend and backend for each start up, you can omit this if you have no local image of either or if you want to use the existing image
   - ```-d``` starts the containers in detached mode, meaning you can close the console after running the command, without shutting down the containers. You can omit this tag to simply shutdown the containers with the console after you're done using the application.

### IntelliJ
1. Start Docker
2. Open the compose.yml file and click on the run arrows next to the root "services" block.

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
1. Open the main project folder in IntelliJ, then open the services tab on the bottom left and expand the sections Docker, and select "Dev Containers".
5. There, click on the "Create Dev Container" button, then select "From Local Project" and in "Path to devcontainer.json", choose the file in "./Backend/.devcontainer". Click on "Ok".
6. IntelliJ will now ask how to open the project. Select "New Window".
7. In the new window, expand the "Backend/src/main/java" folder, open the "BackendApplication" file and click on the run button. Selecting Java SDK v21, a Maven project sync and executing the "install" task via the Maven menu on the top right might be required first.
8. The backend should start up now.
9. Switch to the main project IntelliJ window and select and select "Dev Containers" under "Docker" in the services tab again.
10. There, click on the "Create Dev Container" button, then select "From Local Project" and in "Path to devcontainer.json", choose the file in "./Frontend/.devcontainer". Click on "Ok".
11. IntelliJ will now ask how to open the project. Select "New Window".
12. In the new window, open the terminal on the bottom left and run "npm run dev". "npm install" might be required first.
13. The frontend should start up now.

### Regular Docker containers
#### IntelliJ
1. Open the main project folder in IntelliJ, then open the compose.dev.yml file.
3. Click on the run arrows next to the root "services" block.
4. Afterwards, open the services tab on the bottom left and expand the sections Docker, the project itself and the container "dev-backend".
5. There, click on the "Open Project" button, then expand the folder "workspace" in the window that pops up and select the "Backend" folder. Click on "Ok".
6. IntelliJ will now ask how to open the project. Select "New Window".
7. In the new window, expand the "Backend/src/main/java" folder, open the "BackendApplication" file and click on the run button. A Maven project sync and executing the "install" task via the Maven menu on the top right might be required first.
8. The backend should start up now.
9. Switch to the main project IntelliJ window and select the container "dev-frontend" in the services tab.
10. There, click on the "Open Project" button, then expand the folder "workspace" in the window that pops up and select the "Frontend" folder. Click on "Ok".
11. IntelliJ will now ask how to open the project. Select "New Window".
12. In the new window, open the terminal on the bottom left and run "npm run dev". "npm install" might be required first.
13. The frontend should start up now.

### Frontend URL
* You can reach the frontend via http://localhost:4173 .

### Backend URLs
* You can reach the backend HTML API docs via http://localhost:8080/swagger-ui/index.html and the JSON API docs via http://localhost:8080/v3/api-docs .
* The in-memory H2 database for development can be reached via http://localhost:8080/h2-console . Use the credentials from the application-dev.properties file.

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