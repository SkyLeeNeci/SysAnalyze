package com.karpenko;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
    private Label K3;

    @FXML
    private Label K4;

    @FXML
    private Label K5;

    @FXML
    private Label K6;

    @FXML
    private LineChart<String, Number> chartShenonPictOne;

    @FXML
    private LineChart<String, Number> chartKulbacPictOne;

    @FXML
    private LineChart<String, Number> chartShenonPictTwo;

    @FXML
    private LineChart<String, Number> chartKulbacPictTwo;

    @FXML
    private LineChart<String, Number> chartKulbacPictThr;

    @FXML
    private LineChart<String, Number> chartShenonPictThr;

    @FXML
    private ImageView third_picture;

    @FXML
    private TextArea third_picture_matrix;

    @FXML
    private TextArea third_picture_binary_matrix;

    @FXML
    private TextArea ev_third;

    @FXML
    private ImageView third_picture_binary;

    @FXML
    private TextArea ownSKMatrixPctThree;

    @FXML
    private TextArea neighborSKMatrixPctThree;

    @FXML
    private ImageView third_picture1;

    @FXML
    private ImageView third_picture2;

    @FXML
    private ImageView ev_third_picture;

    @FXML
    void initialize() {

        try {

            List<Double> optimization = someMEthod();

            ImageHandler firstImageHandler = new ImageHandler("1.bmp");
            ImageHandler secondImageHandler = new ImageHandler("2.bmp");
            ImageHandler thirdImageHandler = new ImageHandler("11.bmp");
            ImageHandler foursImageHandler = new ImageHandler("22.bmp");
            int[][] firstImageMatrix = firstImageHandler.getImageMatrix();
            first_picture.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));
            third_picture.setImage(new Image(new File("11.bmp").toURI().toURL().toString()));

            first_picture1.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture1.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));
            third_picture1.setImage(new Image(new File("11.bmp").toURI().toURL().toString()));

            first_picture2.setImage(new Image(new File("1.bmp").toURI().toURL().toString()));
            second_picture2.setImage(new Image(new File("2.bmp").toURI().toURL().toString()));
            third_picture2.setImage(new Image(new File("11.bmp").toURI().toURL().toString()));


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
            }
            second_picture_matrix.setText(builder2.toString());

            StringBuilder builder3 = new StringBuilder();
            int[][] thirdImageMatrix = thirdImageHandler.getImageMatrix();
            for (int i = 0; i < thirdImageMatrix.length; i++) {
                for (int j = 0; j < thirdImageMatrix[0].length; j++) {
                    builder3.append(thirdImageMatrix[i][j]).append(" ");
                }
                builder3.append("\n");
            }
            third_picture_matrix.setText(builder3.toString());

            /*StringBuilder builder4 = new StringBuilder();
            int[][] fourImageMatrix = foursImageHandler.getImageMatrix();
            for (int i = 0; i < fourImageMatrix.length; i++) {
                for (int j = 0; j < fourImageMatrix[0].length; j++) {
                    builder4.append(fourImageMatrix[i][j]).append(" ");
                }
                builder4.append("\n");
            }
            System.out.println(builder4);*/

            int[] firstVector = firstImageHandler.getVectors();

            for (int i = 0; i < firstVector.length; i++) {
                System.out.print(firstVector[i] + " ");
            }
            System.out.println();

            //30 30 // 40 40 лучше
            LearningMatrixHandler matrixHandler = new LearningMatrixHandler(firstVector, optimization.get(1).intValue(), optimization.get(1).intValue());
            matrixHandler.setMatrixList(Arrays.asList(firstImageMatrix, secondImageMatrix, thirdImageMatrix));

            List<boolean[][]> matrixList = matrixHandler.getMatrixList();
            List<boolean[]> referensvectorslist = matrixHandler.getVectorsList();

            first_picture_binary_matrix.setText(showLearningMatrix(matrixList.get(0)));
            second_picture_binary_matrix.setText(showLearningMatrix(matrixList.get(1)));
            third_picture_binary_matrix.setText(showLearningMatrix(matrixList.get(2)));
            // showReferenceVector(matrixHandler.getVectorsList());

            first_picture_binary.setImage(SwingFXUtils.toFXImage(imageFromLearningMatrix(matrixList.get(0)), null));
            second_picture_binary.setImage(SwingFXUtils.toFXImage(imageFromLearningMatrix(matrixList.get(1)), null));
            third_picture_binary.setImage(SwingFXUtils.toFXImage(imageFromLearningMatrix(matrixList.get(2)), null));

            ev_first.setText(showReferenceVector(referensvectorslist.get(0)));
            ev_second.setText(showReferenceVector(referensvectorslist.get(1)));
            ev_third.setText(showReferenceVector(referensvectorslist.get(2)));

            ev_first_picture.setImage(SwingFXUtils.toFXImage(imageFromReferenceMatrix(referensvectorslist.get(0)), null));
            ev_second_picture.setImage(SwingFXUtils.toFXImage(imageFromReferenceMatrix(referensvectorslist.get(1)), null));
            ev_third_picture.setImage(SwingFXUtils.toFXImage(imageFromReferenceMatrix(referensvectorslist.get(2)), null));

            List<Different> SKDistances = matrixHandler.calculateDistance();

            ownSKMatrixPctOne.setText(showReferenceVector(SKDistances.get(0).getSK().get(0)));
            neighborSKMatrixPctOne.setText(showReferenceVector(SKDistances.get(0).getSK().get(1)));
            ownSKMatrixPctTwo.setText(showReferenceVector(SKDistances.get(1).getSK().get(0)));
            neighborSKMatrixPctTwo.setText(showReferenceVector(SKDistances.get(1).getSK().get(1)));
            ownSKMatrixPctThree.setText(showReferenceVector(SKDistances.get(2).getSK().get(0)));
            neighborSKMatrixPctThree.setText(showReferenceVector(SKDistances.get(2).getSK().get(1)));

            List<int[]> elementsInRadiusPictOne = matrixHandler.elementsInRadius(SKDistances.get(0).getSK());
            List<int[]> elementsInRadiusPictTwo = matrixHandler.elementsInRadius(SKDistances.get(1).getSK());
            List<int[]> elementsInRadiusPictThree = matrixHandler.elementsInRadius(SKDistances.get(2).getSK());

           /* System.out.println(showReferenceVector(elementsInRadiusPictOne.get(0)));
            System.out.println(showReferenceVector(elementsInRadiusPictOne.get(1)));
            System.out.println(showReferenceVector(elementsInRadiusPictTwo.get(0)));
            System.out.println(showReferenceVector(elementsInRadiusPictTwo.get(1)));*/

            int ev1 = matrixHandler.compareDifferences(referensvectorslist.get(0),referensvectorslist.get(1));
            int ev2 = matrixHandler.compareDifferences(referensvectorslist.get(0),referensvectorslist.get(2));
            int ev3 = matrixHandler.compareDifferences(referensvectorslist.get(1),referensvectorslist.get(0));
            int ev4 = matrixHandler.compareDifferences(referensvectorslist.get(1),referensvectorslist.get(2));
            int ev5 = matrixHandler.compareDifferences(referensvectorslist.get(2),referensvectorslist.get(0));
            int ev6 = matrixHandler.compareDifferences(referensvectorslist.get(2),referensvectorslist.get(1));

            /*System.out.println(ev1);
            System.out.println(ev2);*/

            K1.setText(String.valueOf(ev1));
            K2.setText(String.valueOf(ev2));
            K3.setText(String.valueOf(ev3));
            K4.setText(String.valueOf(ev4));
            K5.setText(String.valueOf(ev5));
            K6.setText(String.valueOf(ev6));


            AlfaBetaD1D2 alfaBetaD1D2pict1 = matrixHandler.denis1(elementsInRadiusPictOne);
            AlfaBetaD1D2 alfaBetaD1D2pict2 = matrixHandler.denis1(elementsInRadiusPictTwo);
            AlfaBetaD1D2 alfaBetaD1D2pict3 = matrixHandler.denis1(elementsInRadiusPictThree);

            /*System.out.println("==============Точнісні характеристики зображення 1================");
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getAlfaBeta().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getAlfaBeta().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getD1D2().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict1.getD1D2().get(1)));

            System.out.println("==============Точнісні характеристики зображення 2================");

            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getAlfaBeta().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getAlfaBeta().get(0)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getD1D2().get(1)));
            System.out.println(showReferenceVector(alfaBetaD1D2pict2.getD1D2().get(0)));

            System.out.println("====================================================================================================");*/

            /*System.out.println(showReferenceVector(matrixHandler.calculateByKulbakFormula(alfaBetaD1D2pict2)));
            System.out.println("==============================");
            System.out.println(showReferenceVector((matrixHandler.calculateByShenonFormula(alfaBetaD1D2pict1))));*/

            List<double[]> alfaBetaPictureOne = alfaBetaD1D2pict1.getAlfaBeta();
            List<double[]> d1D2PictureOne = alfaBetaD1D2pict1.getD1D2();
            List<double[]> alfaBetaPictureTwo = alfaBetaD1D2pict2.getAlfaBeta();
            List<double[]> d1D2PictureTwo = alfaBetaD1D2pict2.getD1D2();
            List<double[]> alfaBetaPictureThree = alfaBetaD1D2pict3.getAlfaBeta();
            List<double[]> d1D2PictureThree = alfaBetaD1D2pict3.getD1D2();
            double[] KulbakForPictureOne = matrixHandler.calculateByKulbakFormula(alfaBetaPictureOne.get(0), alfaBetaPictureOne.get(1), d1D2PictureOne.get(0), d1D2PictureOne.get(1));
            double[] ShenonForPictureOne = matrixHandler.calculateByShenonFormula(alfaBetaPictureOne.get(0), alfaBetaPictureOne.get(1), d1D2PictureOne.get(0), d1D2PictureOne.get(1));
            double[] KulbakForPictureTwo = matrixHandler.calculateByKulbakFormula(alfaBetaPictureTwo.get(0), alfaBetaPictureTwo.get(1), d1D2PictureTwo.get(0), d1D2PictureTwo.get(1));
            double[] ShenonForPictureTwo = matrixHandler.calculateByShenonFormula(alfaBetaPictureTwo.get(0), alfaBetaPictureTwo.get(1), d1D2PictureTwo.get(0), d1D2PictureTwo.get(1));
            double[] KulbakForPictureThree = matrixHandler.calculateByKulbakFormula(alfaBetaPictureThree.get(0), alfaBetaPictureThree.get(1),d1D2PictureThree.get(0),d1D2PictureThree.get(1));
            double[] ShenonForPictureThree = matrixHandler.calculateByShenonFormula(alfaBetaPictureThree.get(0), alfaBetaPictureThree.get(1),d1D2PictureThree.get(0),d1D2PictureThree.get(1));


           /* System.out.print(showReferenceVector(KulbakForPictureOne) + "\n");
            System.out.print(showReferenceVector(ShenonForPictureOne) + "\n");
            System.out.print(showReferenceVector(KulbakForPictureTwo) + "\n");
            System.out.print(showReferenceVector(ShenonForPictureTwo) + "\n");*/

            chartsBuilder(chartKulbacPictOne, KulbakForPictureOne);
            chartsBuilder(chartShenonPictOne, ShenonForPictureOne);
            chartsBuilder(chartKulbacPictTwo, KulbakForPictureTwo);
            chartsBuilder(chartShenonPictTwo, ShenonForPictureTwo);
            chartsBuilder(chartKulbacPictThr, KulbakForPictureThree);
            chartsBuilder(chartShenonPictThr,ShenonForPictureThree);

            boolean[][] byteMatrix = matrixHandler.getByteMatrix(foursImageHandler.getImageMatrix());

            examAlgorithm(byteMatrix,referensvectorslist,matrixHandler, Arrays.asList(KulbakForPictureOne,KulbakForPictureTwo, KulbakForPictureThree));

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    private void chartsBuilder(LineChart<String, Number> chart, double[] kfe) {

        XYChart.Series<String, Number> chart1 = new XYChart.Series<>();
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

                bufferedImage.setRGB(j, i, color);
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

    public double optimized(double[] betta, double[] d1, double[] kfe){
        double maxKfe = 0;
        boolean wasFind = false;

        for (int d = 0; d < kfe.length; d++) {
            if (d1[d] > 0.5 && betta[d] < 0.5) {
                if (kfe[d] > maxKfe) {
                    maxKfe = kfe[d];
                }

                wasFind = true;
            }
        }

        if (!wasFind) {
            for (int d = 0; d < kfe.length; d++) {
                if (kfe[d] > maxKfe) {
                    maxKfe = kfe[d];
                }
            }
        }
        return maxKfe;
    }


    private List<Double> someMEthod() {

        double maxKfeAverage = 0;
        int delta = 0;

        for (int i = 0; i < 100; i++) {

            try {
                ImageHandler firstImageHandler = new ImageHandler("1.bmp");
                ImageHandler secondImageHandler = new ImageHandler("2.bmp");
                ImageHandler thirdImageHandler = new ImageHandler("11.bmp");
                int[][] firstImageMatrix = firstImageHandler.getImageMatrix();
                int[][] secondImageMatrix = secondImageHandler.getImageMatrix();
                int[][] thirdImageMatrix = thirdImageHandler.getImageMatrix();
                int[] firstVector = firstImageHandler.getVectors();

                // 40 40
                LearningMatrixHandler matrixHandler = new LearningMatrixHandler(firstVector, i, i);
                matrixHandler.setMatrixList(Arrays.asList(firstImageMatrix, secondImageMatrix, thirdImageMatrix));

                List<boolean[][]> matrixList = matrixHandler.getMatrixList();
                List<boolean[]> referensvectorslist = matrixHandler.getVectorsList();
                List<Different> SKDistances = matrixHandler.calculateDistance();
                List<int[]> elementsInRadiusPictOne = matrixHandler.elementsInRadius(SKDistances.get(0).getSK());
                List<int[]> elementsInRadiusPictTwo = matrixHandler.elementsInRadius(SKDistances.get(1).getSK());
                List<int[]> elementsInRadiusPictThree = matrixHandler.elementsInRadius(SKDistances.get(2).getSK());


                AlfaBetaD1D2 alfaBetaD1D2pict1 = matrixHandler.denis1(elementsInRadiusPictOne);
                AlfaBetaD1D2 alfaBetaD1D2pict2 = matrixHandler.denis1(elementsInRadiusPictTwo);
                AlfaBetaD1D2 alfaBetaD1D2pict3 = matrixHandler.denis1(elementsInRadiusPictThree);

                List<double[]> alfaBetaPictureOne = alfaBetaD1D2pict1.getAlfaBeta();
                List<double[]> d1D2PictureOne = alfaBetaD1D2pict1.getD1D2();
                List<double[]> alfaBetaPictureTwo = alfaBetaD1D2pict2.getAlfaBeta();
                List<double[]> d1D2PictureTwo = alfaBetaD1D2pict2.getD1D2();
                List<double[]> alfaBetaPictureThree = alfaBetaD1D2pict3.getAlfaBeta();
                List<double[]> d1D2PictureThree = alfaBetaD1D2pict3.getD1D2();


                double[] KulbakForPictureOne = matrixHandler.calculateByKulbakFormula(alfaBetaPictureOne.get(0), alfaBetaPictureOne.get(1), d1D2PictureOne.get(0), d1D2PictureOne.get(1));
                double[] KulbakForPictureTwo = matrixHandler.calculateByKulbakFormula(alfaBetaPictureTwo.get(0), alfaBetaPictureTwo.get(1), d1D2PictureTwo.get(0), d1D2PictureTwo.get(1));
                double[] KulbakForPictureThree = matrixHandler.calculateByKulbakFormula(alfaBetaPictureThree.get(0), alfaBetaPictureThree.get(1),d1D2PictureThree.get(0),d1D2PictureThree.get(1));

                double averageKfe = 0;

               averageKfe += optimized(alfaBetaPictureOne.get(0),d1D2PictureOne.get(1),KulbakForPictureOne);
               averageKfe += optimized(alfaBetaPictureTwo.get(0),d1D2PictureTwo.get(1),KulbakForPictureTwo);
                averageKfe += optimized(alfaBetaPictureThree.get(0),d1D2PictureThree.get(1),KulbakForPictureThree);


                averageKfe /= 3;

                if (averageKfe > maxKfeAverage) {
                    maxKfeAverage = averageKfe;
                    delta = i;
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());

            }

        }
        System.out.println( " Оптимальное кфе : " + maxKfeAverage + "  оптимальная дельта :  " + delta);
        return Arrays.asList(maxKfeAverage, (double) delta);
    }

    public void examAlgorithm(boolean[][] learningMatrix, List<boolean[]> vectors, LearningMatrixHandler handler, List<double[]> kfe) {
        double maxMu = 0;
        int index = -1;

        for (int i = 0; i < vectors.size(); i++) {
            boolean[] referenceVector = vectors.get(i);
            int[] ints = handler.calculateDistance(learningMatrix, referenceVector);
            double[] mu = new double[ints.length];
            double averageMu = 0;
            int optRadius = getOptRadius(kfe.get(i));

            for (int j = 0; j < ints.length; j++) {
                mu[j] = 1 - (double) ints[j] / optRadius;
                averageMu += mu[j];
            }

            averageMu /= ints.length;

            System.out.println("index: " + i + " = " + averageMu);

            if (averageMu > maxMu) {
                maxMu = averageMu;
                index = i;
            }
        }

        if (maxMu > 0) {
            System.out.println("Это изображение принадлежит " + (index + 1) + "-му  класу");
        } else {
            System.out.println("Это изображение не принадлежит ни одному из классов");
        }
    }

    private int getOptRadius(double[] kfe) {
        double max = 0;
        int index = -1;

        for (int i = 0; i < kfe.length; i++) {
            if (kfe[i] > max) {
                max = kfe[i];
                index = i;
            }
        }

        return index;
    }

}
