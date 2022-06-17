package com.assignment;

import com.assignment.basic.BasicAlgorithm;
import com.assignment.fox.FoxAlgorithm;
import com.assignment.striped.StripedAlgorithm;
import com.assignment.utils.Matrix;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
     threadNExperiment();
//     sizeMatrixExperiment();
  }

  public static void simpleRun(boolean printMatrices) {
    int sizeAxis0 = 2000;
    int sizeAxis1 = 2000;

    Matrix A = new Matrix(sizeAxis0, sizeAxis1);
    Matrix B = new Matrix(sizeAxis0, sizeAxis1);

    A.generateRandomMatrix();
    B.generateRandomMatrix();


    int nThread = Runtime.getRuntime().availableProcessors();

    BasicAlgorithm ba = new BasicAlgorithm(A, B);
    StripedAlgorithm sa = new StripedAlgorithm(A, B, nThread);
    FoxAlgorithm fa = new FoxAlgorithm(A, B, nThread);

    long currTime = System.nanoTime();
    Matrix C = ba.multiply();
    currTime = System.nanoTime() - currTime;

    if (printMatrices) C.print();
    System.out.println("Time:");

    System.out.println("Basic algorithm: " + currTime / 1_000_000);

    currTime = System.nanoTime();
    C = sa.multiply();
    currTime = System.nanoTime() - currTime;

    if (printMatrices) C.print();

    System.out.println("Striped algorithm: " + currTime / 1_000_000);

    currTime = System.nanoTime();
    C = fa.multiply();
    currTime = System.nanoTime() - currTime;

    if (printMatrices) C.print();

    System.out.println("Fox algorithm: " + currTime / 1_000_000);
    System.out.println("\n");
  }

  public static void threadNExperiment() {
    int sizeAxis0 = 500;
    int sizeAxis1 = 500;
    int nExperiments = 3;

    int[] threadsNStriped = new int[] {3, 5, 10, 50, 100};
    int[] threadsNFox = new int[] {3, 5, 10, 50, 100};
    Map<Integer, Long> timeResultStriped = new HashMap<>();
    Map<Integer, Long> timeResultFox = new HashMap<>();

    Matrix A = new Matrix(sizeAxis0, sizeAxis1);
    Matrix B = new Matrix(sizeAxis0, sizeAxis1);

    A.generateRandomMatrix();
    B.generateRandomMatrix();

    for (int nThread : threadsNStriped) {
      StripedAlgorithm sa = new StripedAlgorithm(A, B, nThread);

      long acc = 0;
      for (int i = 0; i < nExperiments; i++) {
        long currTime = System.nanoTime();
        Matrix C = sa.multiply();
        acc += System.nanoTime() - currTime;
      }
      acc /= nExperiments;

      timeResultStriped.put(nThread, acc / 1_000_000);
    }

    for (int nThread : threadsNFox) {
      FoxAlgorithm fa = new FoxAlgorithm(A, B, nThread);

      long acc = 0;
      for (int i = 0; i < nExperiments; i++) {
        long currTime = System.nanoTime();
        Matrix C = fa.multiply();
        acc += System.nanoTime() - currTime;
      }
      acc /= nExperiments;

      timeResultFox.put(nThread, acc / 1_000_000);
    }

    List<Integer> keysStriped =
        timeResultStriped.keySet().stream().sorted().collect(Collectors.toList());

    System.out.println("Experiment with different number of threads:");

    System.out.printf("%30s", "Number of thread:");
    for (int key : keysStriped) {
      System.out.printf("%10d", key);
      System.out.print(" ");
    }

    System.out.println();

    System.out.printf("%30s", "Time (Stripe algorithm):");
    for (int key : keysStriped) {
      System.out.printf("%10d", timeResultStriped.get(key));
      System.out.print(" ");
    }


    List<Integer> keysFox = timeResultFox.keySet().stream().sorted().collect(Collectors.toList());




    System.out.println();

    System.out.printf("%30s", "Time (Fox algorithm:");
    for (int key : keysFox) {
      System.out.printf("%10d", timeResultFox.get(key));
      System.out.print(" ");
    }
    System.out.println("\n");
  }

  public static void sizeMatrixExperiment() {
    int nThread = Runtime.getRuntime().availableProcessors();
    int nExperiments = 3;

    int[] sizesArray = new int[] {10, 50, 100, 500, 1000, 1500};
    Map<Integer, Long> timeResultStriped = new HashMap<>();
    Map<Integer, Long> timeResultFox = new HashMap<>();

    for (int size : sizesArray) {
      Matrix A = new Matrix(size, size);
      Matrix B = new Matrix(size, size);

      A.generateRandomMatrix();
      B.generateRandomMatrix();

      StripedAlgorithm sa = new StripedAlgorithm(A, B, nThread);

      long acc = 0;
      for (int i = 0; i < nExperiments; i++) {
        long currTime = System.nanoTime();
        Matrix C = sa.multiply();
        acc += System.nanoTime() - currTime;
      }
      acc /= nExperiments;

      timeResultStriped.put(size, acc / 1_000_000);

      FoxAlgorithm fa = new FoxAlgorithm(A, B, nThread);

      acc = 0;
      for (int i = 0; i < nExperiments; i++) {
        long currTime = System.nanoTime();
        Matrix C = fa.multiply();
        acc += System.nanoTime() - currTime;
      }
      acc /= nExperiments;

      timeResultFox.put(size, acc / 1_000_000);
    }

    List<Integer> keys = timeResultStriped.keySet().stream().sorted().collect(Collectors.toList());

    System.out.println("Experiment with the different size of matrix:");
    System.out.println();

    System.out.printf("%30s", "Matrix size:");
    for (int key : keys) {
      System.out.printf("%5d", key);
      System.out.print(" ");
    }

    System.out.println();

    System.out.printf("%30s", "Time (Striped algorithm):");
    for (int key : keys) {
      System.out.printf("%5d", timeResultStriped.get(key));
      System.out.print(" ");
    }

    System.out.println();

    System.out.printf("%30s", "Time (Fox algorithm):");
    for (int key : keys) {
      System.out.printf("%5d", timeResultFox.get(key));
      System.out.print(" ");
    }

    System.out.println("\n");
  }
}
