package com.srfairyox.Corvus;

import com.srfairyox.Corvus.Config.ConfigLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static Controller controller;
    public static Main instance;

    @Override
    public void start(Stage primaryStage) throws Exception{
        instance = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Corvus.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/corvus_img.png")));
        primaryStage.setTitle("Corvus - DarkOrbit Bot Helper");
        primaryStage.setScene(new Scene(root, 771, 520));
        primaryStage.setResizable(false);
        primaryStage.show();


        ConfigLoader configLoader = new ConfigLoader();

        primaryStage.setOnCloseRequest(event -> {
            try {
                configLoader.save(controller.chkEnableLog.isSelected(), controller.chkEnableDebugCMD.isSelected(), controller.cBoxGates.getSelectionModel().getSelectedIndex(), controller.chkBoxPlaceGateOnMap.isSelected(), controller.chkBoxSpinOnlyEE.isSelected(), Integer.parseInt(controller.txtMinUridium.getText()), Integer.parseInt(controller.txtMaxSpinCost.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}
