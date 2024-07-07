package com.Controller;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.setTitle("Checkers");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String resourcePath = "/com/" + fxml + ".fxml";
        URL resource = App.class.getResource(resourcePath);
        if (resource == null) {
            throw new IllegalStateException("Could not find resource: " + resourcePath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
