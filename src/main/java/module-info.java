module com.example.java_l_quiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.java_l_quiz to javafx.fxml;
    exports com.example.java_l_quiz;
}