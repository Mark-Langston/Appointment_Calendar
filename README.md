# Appointment Calendar

## Overview
AppointmentCalendar is a JavaFX-based desktop application for managing appointments. Users can add, remove, and view appointments through an intuitive graphical user interface. The application persists appointments in a text file, ensuring that appointments are saved between sessions.

## Features
- **Add Appointment**: Add a new appointment with a title, date, start time, and end time.
- **Remove Appointment**: Remove an existing appointment from the list.
- **View Appointments**: Display all saved appointments in a list.
- **Persistent Storage**: Appointments are saved to and loaded from a text file.

## Installation
1. **Clone the repository:**
    ```sh
    git clone https://github.com/Mark-Langston/Appointment_Calendar.git
    ```
2. **Navigate to the project directory:**
    ```sh
    cd AppointmentCalendar
    ```
3. **Ensure you have Java and JavaFX installed.**
   - Java: [Install Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
   - JavaFX: [Install JavaFX](https://openjfx.io/)

## Running the Application
1. **Compile the project:**
    ```sh
    javac -d out --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls src/com/example/appointment_calendar/AppointmentCalendar.java
    ```
2. **Run the application:**
    ```sh
    java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out com.example.appointment_calendar.AppointmentCalendar
    ```

## Usage
1. **Add Appointment**: Click the "Add Appointment" button and fill in the details (title, date, start time, end time). Click "Save" to add the appointment.
2. **Remove Appointment**: Select an appointment from the list and click the "Remove Appointment" button to delete it.
3. **View Appointments**: All saved appointments are displayed in the main window when the application starts.

## Project Structure
```plaintext
AppointmentCalendar/
├── src/
│   └── com/
│       └── example/
│           └── appointment_calendar/
│               ├── AppointmentCalendar.java
│               └── saved_appointments.txt
├── README.md
└── saved_appointments.txt
