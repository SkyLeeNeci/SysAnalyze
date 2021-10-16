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
        System.out.println("=============UPLIMIT==================");
        for (int i = 0; i < vector.length; i++) {
            upLimits[i] = vector[i] + upLimit;
            System.out.print(upLimits[i] + " ");
        }
        System.out.println();
        return upLimits;
    }


    private int[] getDownLimit(int[] vector) {
        int[] downLimits = new int[vector.length];
        System.out.println("===============DOWNLIMIT================");
        for (int i = 0; i < vector.length; i++) {
            downLimits[i] = vector[i] - downLimit;
            System.out.print(downLimits[i] + " ");
        }
        System.out.println();
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

    public List<int[]> calculateDistance() {
        List<int[]> list = new ArrayList<>();
        for (boolean[][] matrix : matrixList) {
            for (boolean[] vector : vectorsList) {
                int[] distance = calculateDistance(matrix, vector);
                list.add(distance);
            }
        }
        return list;
    }

    private int[] calculateDistance(boolean[][] matrix, boolean[] vector) {
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
