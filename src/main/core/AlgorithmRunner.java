package main.core;

import javax.swing.*;
import main.algorithms.SortingAlgorithms;
import main.algorithms.PathfindingAlgos;

public class AlgorithmRunner {
    private final VisualizerCanvas canvas;
    private volatile boolean isRunning = false;

    public AlgorithmRunner(VisualizerCanvas canvas) {
        this.canvas = canvas;
    }

    public synchronized void stopExecution() {
        isRunning = false;
    }

    public boolean shouldStop() {
        return !isRunning;
    }

    public void runSortingAlgorithm(String algorithmName, int[] data, int delay) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            switch (algorithmName) {
                case "Bubble Sort" -> SortingAlgorithms.bubbleSort(data, canvas, delay, this);
                case "Selection Sort" -> SortingAlgorithms.selectionSort(data, canvas, delay, this);
                case "Insertion Sort" -> SortingAlgorithms.insertionSort(data, canvas, delay, this);
                case "Quick Sort" -> SortingAlgorithms.quickSort(data, 0, data.length - 1, canvas, delay, this);
            }
            isRunning = false;
            canvas.repaint();
        }).start();
    }

    // THIS IS THE METHOD THAT WAS MISSING
    public void runPathfindingAlgorithm(String algorithmName, int[][] grid, int delay) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            int startR = 1;
            int startC = 1;
            int endR = grid.length - 2;
            int endC = grid[0].length - 2;

            switch (algorithmName) {
                case "Dijkstra" -> PathfindingAlgos.runDijkstra(grid, startR, startC, endR, endC, canvas, delay, this);
                case "BFS" -> PathfindingAlgos.runBFS(grid, startR, startC, endR, endC, canvas, delay, this);
                case "DFS" -> PathfindingAlgos.runDFS(grid, startR, startC, endR, endC, canvas, delay, this);
                default -> JOptionPane.showMessageDialog(null, algorithmName + " not implemented!");
            }
            isRunning = false;
            canvas.repaint();
        }).start();
    }
}
