package main.core;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class VisualizerCanvas extends JPanel {
    private int[] data;
    private int[][] grid;
    private boolean isGridMode = false;

    // --- COLOR STATES FOR SORTING ---
    // 0: Default, 1: Comparing (Red), 2: Swapping (Yellow), 3: Sorted (Green)
    private int[] barStates;

    private final Color DEFAULT_BAR_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color COMPARING_COLOR = new Color(231, 76, 60);    // Soft Red
    private final Color SWAPPING_COLOR = new Color(241, 196, 15);     // Sun Yellow
    private final Color SORTED_COLOR = new Color(46, 204, 113);      // Emerald Green

    public VisualizerCanvas(int[] data) {
        this.data = data;
        this.barStates = new int[data.length];
        this.isGridMode = false;
        setBackground(Color.BLACK);
    }

    // --- STATE MANAGEMENT METHODS ---

    public void updateData(int[] newData) {
        this.data = newData;
        this.barStates = new int[newData.length]; // Reset state array size
        this.isGridMode = false;
        repaint();
    }

    /**
     * Set the visual state of a specific bar (e.g., mark as comparing)
     */
    public void setBarState(int index, int state) {
        if (index >= 0 && index < barStates.length) {
            barStates[index] = state;
        }
    }

    /**
     * Reset all bars to the default color
     */
    public void resetBarStates() {
        if (barStates != null) {
            Arrays.fill(barStates, 0);
        }
    }

    public void updateGrid(int[][] newGrid) {
        this.grid = newGrid;
        this.isGridMode = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isGridMode && grid != null) {
            drawGrid(g2d);
        } else if (!isGridMode && data != null) {
            drawBars(g2d);
        }
    }

    private void drawBars(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        double barWidth = (double) width / data.length;

        for (int i = 0; i < data.length; i++) {
            // Pick color based on the state set by the algorithm
            switch (barStates[i]) {
                case 1 -> g2d.setColor(COMPARING_COLOR);
                case 2 -> g2d.setColor(SWAPPING_COLOR);
                case 3 -> g2d.setColor(SORTED_COLOR);
                default -> g2d.setColor(DEFAULT_BAR_COLOR);
            }

            int barHeight = data[i];
            int x = (int) (i * barWidth);
            int y = height - barHeight;

            int w = Math.max(1, (int) barWidth) ;
            g2d.fillRect(x, y, w, barHeight);
        }
    }

    private void drawGrid(Graphics2D g2d) {
        int rows = grid.length;
        int cols = grid[0].length;
        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
        int offsetX = (getWidth() - (cols * cellSize)) / 2;
        int offsetY = (getHeight() - (rows * cellSize)) / 2;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                switch (grid[r][c]) {
                    case 1 -> g2d.setColor(Color.DARK_GRAY);
                    case 2 -> g2d.setColor(Color.GREEN);
                    case 3 -> g2d.setColor(Color.RED);
                    case 4 -> g2d.setColor(new Color(100, 200, 255));
                    case 5 -> g2d.setColor(Color.YELLOW);
                    default -> g2d.setColor(Color.WHITE);
                }
                g2d.fillRect(offsetX + (c * cellSize), offsetY + (r * cellSize), cellSize - 1, cellSize - 1);
            }
        }
    }

    public int[][] getGrid() { return grid; }
    public boolean isGridMode() { return isGridMode; }
}
