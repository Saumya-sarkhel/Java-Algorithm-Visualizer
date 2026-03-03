package main.ui;

import javax.swing.*;
import main.core.VisualizerCanvas;
import main.core.AlgorithmRunner; // Added Import
import java.awt.*;
import java.util.Random;

public class MainFrame extends JFrame {
    private final VisualizerCanvas canvas;
    private final ControlPanel controlPanel;
    private final AlgorithmRunner runner; // Added Runner field
    private int[] data;

    public MainFrame(String title) {
        super(title);
        setLayout(new BorderLayout());

        // 1. Initialize data
        this.data = generateRandomArray(100);

        // 2. Initialize Canvas (Center)
        canvas = new VisualizerCanvas(data);
        add(canvas, BorderLayout.CENTER);

        // 3. Initialize the Runner
        this.runner = new AlgorithmRunner(canvas);

        // 4. Initialize ControlPanel (North)
        controlPanel = new ControlPanel(
                e -> handleStartAction(), // Updated method name
                e -> resetState() // Updated to handle both modes
        );
        add(controlPanel, BorderLayout.NORTH);
    }

    /**
     * Determines whether to start sorting or pathfinding based on selection.
     */
    private void handleStartAction() {
        String selectedAlgo = controlPanel.getSelectedAlgorithm();
        int sleepDelay = controlPanel.getSpeed();

        // Check if it's a Pathfinding algorithm
        if (isPathfindingAlgo(selectedAlgo)) {
            int[][] currentGrid = canvas.getGrid();

            // FORCE a grid if one doesn't exist or we were just in sorting mode
            if (currentGrid == null || !canvas.isGridMode()) {
                currentGrid = generateDefaultGrid(20, 30);
                canvas.updateGrid(currentGrid);
            }

            runner.runPathfindingAlgorithm(selectedAlgo, currentGrid, sleepDelay);
        } else {
            // SORTING MODE
            // Ensure we update canvas back to bars if we were in grid mode
            canvas.updateData(data);
            runner.runSortingAlgorithm(selectedAlgo, data, sleepDelay);
        }
    }

    private void resetState() {
        // 1. Force stop the thread
        runner.stopExecution();

        // 2. Small sleep ensures the background thread has exited the loop
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        // 3. Clear all visuals
        canvas.resetBarStates();

        String selectedAlgo = controlPanel.getSelectedAlgorithm();
        if (isPathfindingAlgo(selectedAlgo)) {
            int[][] grid = generateDefaultGrid(20, 30);
            canvas.updateGrid(grid);
        } else {
            // Generate NEW data for sorting
            this.data = generateRandomArray(100);
            canvas.updateData(data);
        }
        canvas.repaint();
    }

    // Helper to keep code clean
    private boolean isPathfindingAlgo(String algo) {
        return algo.equals("Dijkstra") || algo.equals("BFS") ||
                algo.equals("DFS") || algo.equals("Prim's (Maze)");
    }

    private int[][] generateDefaultGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        // 2 = Start, 3 = End
        grid[1][1] = 2;
        grid[rows - 2][cols - 2] = 3;

        // Add a few random walls (1)
        Random rand = new Random();
        for (int i = 0; i < (rows * cols) / 4; i++) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (grid[r][c] == 0)
                grid[r][c] = 1;
        }
        return grid;
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(500) + 10;
        }
        return arr;
    }
}
