# SENTINEL - Community-Based Crime Reporting System

## Introduction

This project aims to develop a Community-Based Crime Reporting System (CBCRS) using JavaFX. The system will provide a platform for citizens to report crime incidents, share information, and collaborate with law enforcement agencies to improve community safety.

## Features

- **User Registration and Login:** Secure user accounts for citizens and law enforcement officers.
- **Incident Reporting:** Citizens can report crime incidents, providing details, location, and evidence (photos, videos).
- **Anonymous Reporting:** Option for citizens to report incidents anonymously.
- **Incident Tracking:** Citizens can track the status and progress of their reported incidents.
- **Data Analysis:** Tools for analyzing crime trends and patterns to inform crime prevention strategies.

## Technologies Used

- **JavaFX:** For creating the user interface.
- **MySQL:** For data storage.
- **JDBC:** For connecting to the MySQL database.
- **jBCrypt:** For password hashing.
- **Maven (or Gradle):** For project management and dependency management.

## Project Structure

- **`src/main/java`:**
    - **`buisness`:** Package containing business logic and service classes.
        - **`models`:** Domain models representing entities (e.g., `CrimeReport`, `Case`, `User`).
        - **`services`:** Service classes implementing business logic (e.g., `CrimeReportService`, `UserService`).
    - **`datalayer`:** Package containing data access components.
        - **`repositories`:** Repository interfaces defining data access methods.
        - **`jdbc`:** JDBC implementations of the repository interfaces.
    - **`cbcrs.presentation`:** Package containing the JavaFX presentation layer.
        - **`controllers`:** Controllers for handling user interactions and UI logic.
        - **`viewmodels`:** View models for data binding and UI presentation.
        - **`views`:** FXML files defining the UI layouts.

## Getting Started

1. **Clone the repository:** `git clone https://github.com/M-BilalMehmood/SDA_Project.git`
2. **Import into IntelliJ IDEA:** Import the project as a Maven project.
3. **Set up MySQL Database:** Create a MySQL database and configure the connection details in the `DatabaseConnection` class.
4. **Run the application:** Run the `Login` class to start the application.

## Future Enhancements

- **Mobile App:** Develop a mobile app version of the system for increased accessibility.
- **Real-time Notifications:** Implement real-time notifications to alert citizens of incidents in their area.
- **Integration with External Systems:** Integrate with external systems like crime mapping services or law enforcement databases.
- **Advanced Data Analytics:** Implement more sophisticated data analysis tools to identify crime patterns and predict future trends.

## Contributing

Contributions to the project are welcome! Please follow the standard GitHub workflow for submitting pull requests.

## Note

This is not a complete project. There are a lot of issues that are not fixed and I didn't got the time to do so. If you are going to clone this repo, you may have to do a lot of things yourself but you will get a grey structure and a starting point.

## License

This project is licensed under the [MIT License](LICENSE).
