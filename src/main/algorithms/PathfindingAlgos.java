package main.algorithms;

import main.core.VisualizerCanvas;
import main.core.AlgorithmRunner;
import java.util.*;

public class PathfindingAlgos {

    public static void runBFS(int[][] grid, int startR, int startC, int endR, int endC, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startR, startC});

        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2];
        visited[startR][startC] = true;

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            if (runner.shouldStop()) return; // CRITICAL: Stop on reset

            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            if (r == endR && c == endC) {
                reconstructPath(grid, parent, startR, startC, endR, endC, canvas, delay, runner);
                return;
            }

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc] && grid[nr][nc] != 1) {
                    visited[nr][nc] = true;
                    parent[nr][nc] = new int[]{r, c};
                    queue.add(new int[]{nr, nc});
                    if (grid[nr][nc] == 0) {
                        grid[nr][nc] = 4; // Mark Visited
                        updateUI(canvas, delay);
                    }
                }
            }
        }
    }

    public static void runDFS(int[][] grid, int startR, int startC, int endR, int endC, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startR, startC});

        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2];

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!stack.isEmpty()) {
            if (runner.shouldStop()) return; // CRITICAL: Stop on reset

            int[] curr = stack.pop();
            int r = curr[0], c = curr[1];

            if (visited[r][c]) continue;
            visited[r][c] = true;

            if (r == endR && c == endC) {
                reconstructPath(grid, parent, startR, startC, endR, endC, canvas, delay, runner);
                return;
            }

            if (grid[r][c] == 0) {
                grid[r][c] = 4;
                updateUI(canvas, delay);
            }

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc] && grid[nr][nc] != 1) {
                    parent[nr][nc] = new int[]{r, c};
                    stack.push(new int[]{nr, nc});
                }
            }
        }
    }

    public static void runDijkstra(int[][] grid, int startR, int startC, int endR, int endC, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        // Since all edges are weight 1 in this grid, Dijkstra behaves like BFS
        runBFS(grid, startR, startC, endR, endC, canvas, delay, runner);
    }

    public static void runPrims(int[][] grid, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        // Simple Maze Generation Logic
        int rows = grid.length, cols = grid[0].length;
        // Fill with walls
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (runner.shouldStop()) return;
                grid[r][c] = 1;
            }
        }
        // Logic to carve out path would go here...
        updateUI(canvas, delay);
    }

    private static void reconstructPath(int[][] grid, int[][][] parent, int sR, int sC, int eR, int eC, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        int r = eR, c = eC;
        while (!(r == sR && c == sC)) {
            if (runner.shouldStop()) return;
            int[] p = parent[r][c];
            r = p[0]; c = p[1];
            if (r == sR && c == sC) break;
            grid[r][c] = 5; // Path color
            updateUI(canvas, delay * 2);
        }
    }

    private static void updateUI(VisualizerCanvas canvas, int delay) {
        canvas.repaint();
        try { Thread.sleep(delay); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
