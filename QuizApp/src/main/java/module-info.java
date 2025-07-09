module com.nhc.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.logging;

    opens com.nhc.quizapp to javafx.fxml;
    exports com.nhc.quizapp;
    exports com.nhc.utils; // nếu bạn cần truy cập MyConnector từ nơi khác
}