package com.karpenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));

        Scene scene = new Scene(root, 1920, 1080);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

    /*private static void showReferenceVector(List<boolean[]> referenceVectors) {
        for (boolean[] referenceVector : referenceVectors) {
            for (int i = 0; i < referenceVector.length; i++) {
                System.out.print((referenceVector[i] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }*/

    /*private static void showLearningMatrix(boolean[][] secondLearningMatrix) {
        for (int i = 0; i < secondLearningMatrix.length; i++) {
            for (int j = 0; j < secondLearningMatrix[0].length; j++) {
                System.out.print((secondLearningMatrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }*/
}
