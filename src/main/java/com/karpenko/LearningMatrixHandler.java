package com.karpenko;

import java.util.ArrayList;
import java.util.List;

public class LearningMatrixHandler {
    private int[] upEdge;
    private int[] downEdge;
    private int upLimit;
    private int downLimit;
    private List<boolean[][]> matrixList = new ArrayList<>();
    private List<boolean[]> vectorsList = new ArrayList<>();
    private List<Integer> neighbors = new ArrayList<>();

    public LearningMatrixHandler(int[] mainVector, int upLimit, int downLimit) {
        this.upLimit = upLimit;
        this.downLimit = downLimit;
        this.upEdge = getUpLimit(mainVector);
        this.downEdge = getDownLimit(mainVector);
    }

    public void setMatrixList(List<int[][]> matrixList) {
        for (int i = 0; i < matrixList.size(); i++) {
            this.matrixList.add(getByteMatrix(matrixList.get(i)));
        }

        setVectorList();
    }

    private void setVectorList() {
        for (int i = 0; i < matrixList.size(); i++) {
            this.vectorsList.add(getReferenceVector(matrixList.get(i)));
        }


    }

    public boolean[][] getByteMatrix(int[][] imageMatrix) {
        boolean[][] matrix = new boolean[imageMatrix.length][imageMatrix[0].length];
        //System.out.println("======================GETBYTEMATRIX=========================");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = (imageMatrix[i][j] < upEdge[i] && imageMatrix[i][j] > downEdge[i]);
                // System.out.print((matrix[i][j] ? 1 : 0) + " ");
            }
            //System.out.println();
        }
        //System.out.println("=====================================================");
        return matrix;
    }

    public int getDifference() {
        int countDifference = 0;
        boolean[] vector = vectorsList.get(0);

        for (int j = 0; j < vector.length; j++) {
            boolean value = vector[j];
            if (!compareValueWithList(j, value)) {
                countDifference++;
            }
        }

        return countDifference;
    }

    private boolean[] getReferenceVector(boolean[][] learningMatrix) {
        int countZeros = 0;
        int countOnes = 0;
        int k = 0;
        boolean[] vector = new boolean[learningMatrix.length];

        for (int j = 0; j < learningMatrix[0].length; j++) {

            for (int i = 0; i < learningMatrix.length; i++) {
                if (learningMatrix[j][i]) {
                    countOnes++;
                } else {
                    countZeros++;
                }
            }

            vector[k] = countOnes > countZeros;

            k++;

        }

        return vector;
    }

    private int[] getUpLimit(int[] vector) {
        int[] upLimits = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            upLimits[i] = vector[i] + upLimit;
            //System.out.print(upLimits[i] + " ");
        }
       // System.out.println();
        return upLimits;
    }


    private int[] getDownLimit(int[] vector) {
        int[] downLimits = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            downLimits[i] = vector[i] - downLimit;
            //System.out.print(downLimits[i] + " ");
        }
      //  System.out.println();
        return downLimits;
    }

    private boolean compareValueWithList(int index, boolean value) {
        for (int i = 1; i < vectorsList.size(); i++) {
            boolean[] vector = vectorsList.get(i);

            if (vector[index] != value) {
                return false;
            }
        }

        return true;
    }

    public List<Different> calculateDistance() {
        findNeighbor();
        List<Different> list = new ArrayList<>();
        for (int i=0; i<matrixList.size(); i++) {
            Different different = new Different();
            List<int[]> distances = new ArrayList<>();
            List<boolean[]> neighborVectors = new ArrayList<>();
            boolean[][] learningMatrix = matrixList.get(i);
            boolean[] vector = vectorsList.get(i);
            boolean[] neighborVector = vectorsList.get(neighbors.get(i));
            neighborVectors.add(vector);
            neighborVectors.add(neighborVector);
            for (boolean[] v : neighborVectors){
                int[] distance = calculateDistance(learningMatrix, v);
                distances.add(distance);
            }
            different.setSK(distances);
            list.add(different);
        }
        return list;
    }

    public int[] calculateDistance(boolean[][] matrix, boolean[] vector) {
        int[] distance = new int[matrix[0].length];
        int countDifferences;

        for (int i = 0; i < matrix.length; i++) {
            countDifferences = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != vector[j]) {
                    countDifferences++;
                }
            }
            distance[i] = countDifferences;
        }

        return distance;
    }

    private void findNeighbor() {
        for (int i = 0; i < vectorsList.size(); i++) {
            boolean[] vector = vectorsList.get(i);
            int minDifference = 0;
            int indexNeighbor = -1;

            for (int j = 0; j < vectorsList.size(); j++) {
                int difference = compareDifferences(vector, vectorsList.get(j));

                if (minDifference < difference) {
                    minDifference = difference;
                    indexNeighbor = j;
                }
            }

            if (indexNeighbor == -1) {
                for (int j = 0; j < vectorsList.size(); j++) {
                    if (j != i) {
                        indexNeighbor = j;
                        break;
                    }
                }
            }


            neighbors.add(indexNeighbor);
        }
    }

    private int compareDifferences(boolean[] vectorOne, boolean[] vectorTwo) {
        int countDifferences = 0;

        for (int i = 0; i < vectorOne.length; i++) {
            if (vectorOne[i] != vectorTwo[i]) {
                countDifferences++;
            }
        }

        return countDifferences;
    }

    public List<int[]> elementsInRadius(List<int[]> list) {
        List<int[]> elements = new ArrayList<>();
        for (int[] distance : list) {
            int[] elementsInRadius = new int[distance.length];

            for (int i = 0; i < distance.length; i++) {
                int count = 0;
                for (int j = 0; j < distance.length; j++) {
                    if (distance[j] <= i) {
                        count++;
                    }
                }
                elementsInRadius[i] = count;
            }
            elements.add(elementsInRadius);
        }
        return elements;
    }

     public AlfaBetaD1D2 denis1(List<int[]> kkk) {

        List<double[]> AlfaBeta = new ArrayList<>();
        List<double[]> D1D2 = new ArrayList<>();

        for (int j = 0; j < kkk.size(); j++) {
            int[] elems = kkk.get(j);
            double[] d = new double[elems.length];
            double[] ab = new double[elems.length]; // alpha and beta

            for (int k = 0; k < elems.length; k++) {
                if ( j == 0) {
                    d[k] = roundAvoid((double) elems[k] / (double) elems.length);
                    ab[k] = roundAvoid(1 - ((double) elems[k] / (double) elems.length));
                } else {
                    d[k] = roundAvoid(1 - ((double) elems[k] / (double) elems.length));
                    ab[k] = roundAvoid((double) elems[k] / (double) elems.length);
                }
            }
            AlfaBeta.add(ab);
            D1D2.add(d);
        }
        return new AlfaBetaD1D2(AlfaBeta,D1D2);
    }

    private double roundAvoid(double value) {
        double scale = Math.pow(10, 3);
        return Math.round(value * scale) / scale;
    }

    public double[] calculateByKulbakFormula(double[] alpha, double[] betta, double[] d1, double[] d2) {
        double[] e = new double[alpha.length];

        for (int i = 0; i < alpha.length; i++) {
            e[i] = 0.5 * (log2((d1[i] + d2[i] + 0.1) / (alpha[i] + betta[i] + 0.1)) * ((d1[i] + d2[i]) - (alpha[i] + betta[i])));
        }

        return e;
    }

    public double[] calculateByShenonFormula(double[] alpha, double[] betta, double[] d1, double[] d2) {
        double[] e = new double[alpha.length];

        for (int i = 0; i < alpha.length; i++) {
            double alphaResult = alpha[i] == 0 ? 0 : (alpha[i] / (alpha[i] + d2[i])) * log2(alpha[i] / (alpha[i] + d2[i]));
            double bettaResult = betta[i] == 0 ? 0 : (betta[i] / (betta[i] + d1[i])) * log2(betta[i] / (betta[i] + d1[i]));
            double d1Result = d1[i] == 0 ? 0 : (d1[i] / (d1[i] + betta[i])) * log2(d1[i] / (d1[i] + betta[i]));
            double d2Result = d2[i] == 0 ? 0 : (d2[i] / (d2[i] + alpha[i])) * log2(d2[i] / (d2[i] + alpha[i]));

            e[i] = 1 + 0.5 * (alphaResult + bettaResult + d1Result + d2Result);
        }

        return e;
    }

    private double log2(double n) {
        return n == 0 ? 0 : Math.log(n) / Math.log(2);
    }


    public int getIntersectionRadius(List<int[]> list) {

        int[] vector = list.get(0);
        int i;

        for (i = 0; i < vector.length; i++) {
            if (compareValueWithList(i, vector.length, list)) {
                break;
            }
        }

        return i;
    }

    private boolean compareValueWithList(int index, int value, List<int[]> list) {
        for (int[] vector : list) {
            if (vector[index] != value) {
                return false;
            }
        }

        return true;
    }

    public List<boolean[]> getVectorsList() {
        return vectorsList;
    }

    public List<boolean[][]> getMatrixList() {
        return matrixList;
    }
}
