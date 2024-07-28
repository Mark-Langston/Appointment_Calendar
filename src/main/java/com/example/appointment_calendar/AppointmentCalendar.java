package com.example.appointment_calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentCalendar extends Application {
    private ListView<String> appointmentListView;
    private List<Appointment> appointments;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Appointment Calendar"); // Set the window title

        appointments = new ArrayList<>(); // Initialize the list of appointments
        appointmentListView = new ListView<>(); // Initialize the ListView to display appointments

        loadAppointments(); // Load saved appointments from the file

        // Button to add new appointments
        Button addButton = new Button("Add Appointment");
        addButton.setOnAction(e -> openAddAppointmentDialog());

        // Button to remove selected appointments
        Button removeButton = new Button("Remove Appointment");
        removeButton.setOnAction(e -> removeSelectedAppointment());

        // Layout for the main window
        VBox layout = new VBox(10);
        layout.getChildren().addAll(appointmentListView, addButton, removeButton);

        // Set the scene and show the primary stage
        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to open the dialog for adding a new appointment
    private void openAddAppointmentDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Appointment");

        VBox dialogLayout = new VBox(10);

        // Input fields for the appointment details
        TextField titleField = new TextField();
        titleField.setPromptText("Appointment Title");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Appointment Date");

        TextField startTimeField = new TextField();
        startTimeField.setPromptText("Start Time (HH:mm)");

        TextField endTimeField = new TextField();
        endTimeField.setPromptText("End Time (HH:mm)");

        // Label to show error messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Button to save the new appointment
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            LocalDate date = datePicker.getValue();
            String startTimeText = startTimeField.getText();
            String endTimeText = endTimeField.getText();

            // Check if all fields are filled
            if (title.isEmpty() || date == null || startTimeText.isEmpty() || endTimeText.isEmpty()) {
                errorLabel.setText("All fields must be filled.");
                return;
            }

            try {
                LocalTime startTime = LocalTime.parse(startTimeText);
                LocalTime endTime = LocalTime.parse(endTimeText);
                // Ensure start time is before end time
                if (startTime.isAfter(endTime)) {
                    errorLabel.setText("Start time must be before end time.");
                    return;
                }
                // Create a new appointment and add it to the list
                Appointment appointment = new Appointment(title, date, startTime, endTime);
                appointments.add(appointment);
                updateListView(); // Update the ListView
                saveAppointments(); // Save appointments to the file
                dialog.close(); // Close the dialog
            } catch (DateTimeParseException ex) {
                errorLabel.setText("Invalid time format. Please use HH:mm.");
            } catch (Exception ex) {
                errorLabel.setText("An error occurred: " + ex.getMessage());
            }
        });

        // Add all input fields and buttons to the dialog layout
        dialogLayout.getChildren().addAll(titleField, datePicker, startTimeField, endTimeField, saveButton, errorLabel);
        Scene dialogScene = new Scene(dialogLayout, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    // Method to remove the selected appointment from the list
    private void removeSelectedAppointment() {
        int selectedIndex = appointmentListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            appointments.remove(selectedIndex); // Remove the selected appointment
            updateListView(); // Update the ListView
            saveAppointments(); // Save the updated list to the file
        }
    }

    // Method to update the ListView with the current list of appointments
    private void updateListView() {
        appointmentListView.getItems().clear();
        for (Appointment appointment : appointments) {
            appointmentListView.getItems().add(appointment.toString());
        }
    }

    // Method to load saved appointments from the file
    private void loadAppointments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("saved_appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    LocalTime startTime = LocalTime.parse(parts[2]);
                    LocalTime endTime = LocalTime.parse(parts[3]);
                    appointments.add(new Appointment(title, date, startTime, endTime)); // Add appointment to the list
                } else {
                    System.out.println("Skipping invalid entry: " + line);
                }
            }
            updateListView(); // Update the ListView
        } catch (IOException e) {
            System.out.println("Error reading saved appointments.");
        }
    }

    // Method to save the current list of appointments to a file
    private void saveAppointments() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("saved_appointments.txt"))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.toCSV()); // Write each appointment to the file
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments.");
        }
    }

    // Class to represent an appointment
    public static class Appointment {
        private String title;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;

        public Appointment(String title, LocalDate date, LocalTime startTime, LocalTime endTime) {
            this.title = title;
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return title + " " + date + " " + startTime + " - " + endTime; // Format appointment as a string
        }

        public String toCSV() {
            return title + "," + date + "," + startTime + "," + endTime; // Format appointment for CSV
        }
    }
}
