module com.example.kursova_train_003 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;



    opens com.example.kursova_train_003 to javafx.fxml;
    exports com.example.kursova_train_003;
}