package com.karpenko;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML
    private ScatterChart<?, ?> limits_graphic;

    @FXML
    private TextArea first_picture_matrix;

    @FXML
    private TextArea second_picture_matrix;

    @FXML
    private TextArea first_picture_binary_matrix;

    @FXML
    private TextArea second_picture_binary_matrix;

    @FXML
    private ImageView first_picture;

    @FXML
    private ImageView second_picture;

    @FXML
    private ImageView first_picture_binary;

    @FXML
    private ImageView second_picture_binary;

    @FXML
    private TextArea ev_first;

    @FXML
    private TextArea ev_second;

    @FXML
    private ImageView ev_first_picture;

    @FXML
    private ImageView ev_second_picture;

    @FXML
    private TextArea ownSKMatrixPctOne;

    @FXML
    private TextArea neighborSKMatrixPctOne;

    @FXML
    private TextArea ownSKMatrixPctTwo;

    @FXML
    private TextArea neighborSKMatrixPctTwo;

    @FXML
    private ImageView first_picture2;

    @FXML
    private ImageView second_picture2;

    @FXML
    private ImageView first_picture1;

    @FXML
    private ImageView second_picture1;

    @FXML
    private Label K1;

    @FXML
    private Label K2;

    @FXML
    private LineChart<String, Number> chartKulbacPictOne;

    @FXML
    private LineChart<String, Number> chartShenonPictOne;

    @FXML
    private LineChart<String, Number> chartShenonPictTwo;

    @FXML
    private LineChart<String, Number> chartKulbacPictTwo;

    @FXML
    void initialize() {
        try {
            ImageHandler firstImageHandler = new ImageHandler("1.bmp");
            ImageHandler secondImageHandler = new ImageHandler("2.bmp");
            int[][] firstImageMatrix = firstImageHandler.getImageMatrix();
            first_picture.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));
            first_picture1.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture1.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));
            first_picture2.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture2.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));

            StringBuilder builder1 = new StringBuilder();
            for (int i = 0; i < firstImageMatrix.length; i++) {
                for (int j = 0; j < firstImageMatrix[0].length; j++) {
                    builder1.append(firstImageMatrix[i][j]).append(" ");
                }
                builder1.append("\n");
            }
            first_picture_matrix.setText(builder1.toString());

            StringBuilder builder2 = new StringBuilder();
            int[][] secondImageMatrix = secondImageHandler.getImageMatrix();
            for (int i = 0; i < secondImageMatrix.length; i++) {
                for (int j = 0; j < secondImageMatrix[0].length; j++) {
                    builder2.append(secondImageMatrix[i][j]).append(" ");
                }
                builder2.append("\n");
                ;
            }
            second_picture_matrix.setText(builder2.toString());

            int[] firstVector = firstImageHandler.getVectors();

            for (int i = 0; i < firstVector.length; i++) {
                System.out.print(firstVector[i] + " ");
            }
            System.out.println();

            //30 30 // 40 40 лучше
            LearningMatrixHandler matrixHandler = new LearningMatrixHandler(firstVector, 30, 30);
            matrixHandler.setMatrixList(Arrays.asList(firstImageMatrix, secondImageMatrix));

            List<boolean[][]> matrixList = matrixHandler.getMatrixList();
            List<boolean[]> referensvectorslist = matrixHandler.getVectorsList();

            first_picture_binary_matrix.setText(showLearningMatrix(matrixList.get(0)));
            second_picture_binary_matrix.setText(showLearningMatrix(matrixList.get(1)));
            // showReferenceVector(matrixHandler.getVectorsList());

            first_picture_binary.setImage(SwingFXUtils.toFXImage(imageFromLearningMatrix(matrixList.get(0)), null));
            second_picture_binary.setImage(SwingFXUtils.toFXImage(imageFromLearningMatrix(matrixList.get(1)), null));

            ev_first.setText(showReferenceVector(referensvectorslist.get(0)));
            ev_second.setText(showReferenceVector(referensvectorslist.get(1)));

            ev_first_picture.setImage(SwingFXUtils.toFXImage(imageFromReferenceMatrix(referensvectorslist.get(0)),null));
            ev_second_picture.setImage(SwingFXUtils.toFXImage(imageFromReferenceMatrix(referensvectorslist.get(1)),null));

            List<int[]> SKDistances = matrixHandler.calculateDistance();

            /*ownSKMatrixPctOne.setText(showReferenceVector(SKDistances.get(0)));
            neighborSKMatrixPctOne.setText(showReferenceVector(SKDistances.get(1)));
            ownSKMatrixPctTwo.setText(showReferenceVector(SKDistances.get(3)));
            neighborSKMatrixPctTwo.setText(showReferenceVector(SKDistances.get(2)));*/

            List<int[]> elementsInRadiusPictOne = matrixHandler.elementsInRadius(SKDistances.subList(0, 2));
            List<int[]> elementsInRadiusPictTwo = matrixHandler.elementsInRadius(SKDistances.subList(2, 4));

           /* System.out.println(showReferenceVector(elementsInRadiusPictOne.get(0)));
            System.out.println(showReferenceVector(elementsInRadiusPictOne.get(1)));
            System.out.println(showReferenceVector(elementsInRadiusPictTwo.get(0)));
            System.out.println(showReferenceVector(elementsInRadiusPictTwo.get(1)));*/

            int ev1 = matrixHandler.getDifference();
            int ev2 = matrixHandler.getDifference();

            /*System.out.println(ev1);
            System.out.println(ev2);*/

            K1.setText(String.valueOf(ev1));
            K2.setText(String.valueOf(ev2));

            AlfaBetaD1D2 alfaBetaD1D2pict1 = matrixHandler.denis1(elementsInRadiusPictOne, 0);
            AlfaBetaD1D2 alfaBetaD1D2pict2 = matrixHandler.denis1(elementsInRadiusPictTwo, 1);

            System.out.println("==============Точнісні характеристики зображення 1================");
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getAlfaBeta().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getAlfaBeta().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getD1D2().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getD1D2().get(1)));

            System.out.println("==============Точнісні характеристики зображення 2================");

            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getAlfaBeta().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getAlfaBeta().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getD1D2().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getD1D2().get(0)));

            System.out.println("====================================================================================================");

            /*System.out.println(showReferenceVector(matrixHandler.calculateByKulbakFormula(alfaBetaD1D2pict2)));
            System.out.println("==============================");
            System.out.println(showReferenceVector((matrixHandler.calculateByShenonFormula(alfaBetaD1D2pict1))));*/

            List<double[]> alfaBetaPictureOne = alfaBetaD1D2pict1.getAlfaBeta();
            List<double[]> d1D2PictureOne = alfaBetaD1D2pict1.getD1D2();
            List<double[]> alfaBetaPictureTwo = alfaBetaD1D2pict2.getAlfaBeta();
            List<double[]> d1D2PictureTwo = alfaBetaD1D2pict2.getD1D2();
            double[] KulbakForPictureOne = matrixHandler.calculateByKulbakFormula(alfaBetaPictureOne.get(0),alfaBetaPictureOne.get(1), d1D2PictureOne.get(0), d1D2PictureOne.get(1));
            double[] ShenonForPictureOne = matrixHandler.calculateByShenonFormula(alfaBetaPictureOne.get(0),alfaBetaPictureOne.get(1), d1D2PictureOne.get(0), d1D2PictureOne.get(1));
            double[] KulbakForPictureTwo = matrixHandler.calculateByKulbakFormula(alfaBetaPictureTwo.get(1), alfaBetaPictureTwo.get(0),d1D2PictureTwo.get(1),d1D2PictureTwo.get(0));
            double[] ShenonForPictureTwo = matrixHandler.calculateByShenonFormula(alfaBetaPictureTwo.get(1), alfaBetaPictureTwo.get(0),d1D2PictureTwo.get(1),d1D2PictureTwo.get(0));

            System.out.print(showReferenceVector(KulbakForPictureOne) + "\n");
            System.out.print(showReferenceVector(ShenonForPictureOne) + "\n");
            System.out.print(showReferenceVector(KulbakForPictureTwo) + "\n");
            System.out.print(showReferenceVector(ShenonForPictureTwo) + "\n");

            chartsBuilder(chartKulbacPictOne,KulbakForPictureOne);
            chartsBuilder(chartShenonPictOne,ShenonForPictureOne);
            chartsBuilder(chartKulbacPictTwo, KulbakForPictureTwo);
            chartsBuilder(chartShenonPictTwo,ShenonForPictureTwo);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    private void chartsBuilder(LineChart<String,Number> chart, double[] kfe){

        XYChart.Series<String , Number> chart1 = new XYChart.Series<>();
        chart1.setName("");

        for (int i = 0; i < kfe.length; i++) {
            chart1.getData().add(new XYChart.Data<>(Integer.toString(i), kfe[i]));
        }
        chart.getData().add(chart1);
    }

    private String showReferenceVector(boolean[] referenceVectors) {
        StringBuilder builder4 = new StringBuilder();

        for (int i = 0; i < referenceVectors.length; i++) {
            builder4.append(referenceVectors[i] ? 1 : 0).append(" ");
        }
        builder4.append("\n");

        return builder4.toString();
    }

    private String showReferenceVector(int[] referenceVectors) {
        StringBuilder builder4 = new StringBuilder();

        for (int i = 0; i < referenceVectors.length; i++) {
            builder4.append(referenceVectors[i]).append(" ");
        }
        builder4.append("\n");

        return builder4.toString();
    }

    private String showReferenceVector(double[] referenceVectors) {
        StringBuilder builder4 = new StringBuilder();

        for (int i = 0; i < referenceVectors.length; i++) {
            builder4.append(referenceVectors[i]).append(" ");
        }
        builder4.append("\n");

        return builder4.toString();
    }

    private String showLearningMatrix(boolean[][] secondLearningMatrix) {
        StringBuilder builder3 = new StringBuilder();
        for (int i = 0; i < secondLearningMatrix.length; i++) {
            for (int j = 0; j < secondLearningMatrix[0].length; j++) {
                builder3.append(secondLearningMatrix[i][j] ? 1 : 0).append(" ");
            }
            builder3.append("\n");
        }
        return builder3.toString();
    }

    public BufferedImage imageFromLearningMatrix(boolean[][] matrix) {
        int width = matrix[0].length;
        int height = matrix.length;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = 0;

                if (!matrix[i][j]) {
                    color = 0xffffff;
                }

                bufferedImage.setRGB(i, j, color);
            }
        }

        return bufferedImage;
    }

    public BufferedImage imageFromReferenceMatrix(boolean[] matrix) {
        int width = matrix.length;
        int height = 40;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = 0;

                if (!matrix[i]) {
                    color = 0xffffff;
                }

                bufferedImage.setRGB(i, j, color);
            }
        }
        return bufferedImage;
    }




}
