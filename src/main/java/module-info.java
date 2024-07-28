module com.example.appointment_calendar {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.appointment_calendar to javafx.fxml;
    exports com.example.appointment_calendar;
}