module com.example.tierpark {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;

    opens com.example.tierpark to javafx.fxml;
    exports com.example.tierpark;
    exports com.example.tierpark.controllers;
    opens com.example.tierpark.controllers to javafx.fxml;
    exports com.example.tierpark.controllers.feed;
    opens com.example.tierpark.controllers.feed to javafx.fxml;
    exports com.example.tierpark.controllers.animalClass;
    opens com.example.tierpark.controllers.animalClass to javafx.fxml;
    exports com.example.tierpark.controllers.animalFamily;
    opens com.example.tierpark.controllers.animalFamily to javafx.fxml;
    exports com.example.tierpark.controllers.animalTypes;
    opens com.example.tierpark.controllers.animalTypes to javafx.fxml;
    exports com.example.tierpark.controllers.building;
    opens com.example.tierpark.controllers.building to javafx.fxml;
    exports com.example.tierpark.controllers.careType;
    opens com.example.tierpark.controllers.careType to javafx.fxml;
    exports com.example.tierpark.controllers.animal;
    opens com.example.tierpark.controllers.animal to javafx.fxml;
    exports com.example.tierpark.controllers.care;
    opens com.example.tierpark.controllers.care to javafx.fxml;
    exports com.example.tierpark.controllers.feedAnimal;
    opens com.example.tierpark.controllers.feedAnimal to javafx.fxml;
    exports com.example.tierpark.controllers.user;
    opens com.example.tierpark.controllers.user to javafx.fxml;
}