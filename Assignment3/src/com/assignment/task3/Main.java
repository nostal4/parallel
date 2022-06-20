package com.assignment.task3;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Journal journal = new Journal();
    int nWeeks = 3;

    Runnable r =
        new Runnable() {
          @Override
          public void run() {
            (new Thread(
                    new Teacher(
                        "Lecturer 1", Arrays.asList("ІТ-92", "ІТ-93", "ІТ-94"), nWeeks, journal)))
                .start();
            (new Thread(
                    new Teacher(
                        "Assistant 1", Arrays.asList("ІТ-92", "ІТ-93", "ІТ-94"), nWeeks, journal)))
                .start();
            (new Thread(
                    new Teacher(
                        "Assistant 2", Arrays.asList("ІТ-92", "ІТ-93", "ІТ-94"), nWeeks, journal)))
                .start();
            (new Thread(
                    new Teacher(
                        "Assistant 3", Arrays.asList("ІТ-92", "ІТ-93", "ІТ-94"), nWeeks, journal)))
                .start();
          }
        };
    Thread t = new Thread(r);
    t.start();
    t.join();

    journal.show();
  }
}
