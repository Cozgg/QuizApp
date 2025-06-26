module com.nhc.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.nhc.quizapp to javafx.fxml;
    exports com.nhc.quizapp;
}
