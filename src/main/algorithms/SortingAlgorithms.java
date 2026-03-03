package main.algorithms;

import main.core.VisualizerCanvas;
import main.core.AlgorithmRunner;

public class SortingAlgorithms {

    /**
     * Selection Sort: Min element is YELLOW, current scan is RED.
     */
    public static void selectionSort(int[] array, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        int n = array.length;
        canvas.resetBarStates();

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // Mark the starting minimum
            canvas.setBarState(i, 2);

            for (int j = i + 1; j < n; j++) {
                if (runner.shouldStop()) return;

                canvas.setBarState(j, 1); // Current bar being compared
                updateUI(canvas, delay);

                if (array[j] < array[minIdx]) {
                    canvas.setBarState(minIdx, 0); // Reset previous min color
                    minIdx = j;
                    canvas.setBarState(minIdx, 2); // Highlight new min
                } else {
                    canvas.setBarState(j, 0); // Reset scan color
                }
            }

            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;

            canvas.setBarState(minIdx, 0);
            canvas.setBarState(i, 3); // Mark current position as permanently sorted
        }
        canvas.setBarState(n - 1, 3); // Last element is sorted
    }

    /**
     * Insertion Sort: Moving element is YELLOW, comparisons are RED.
     */
    public static void insertionSort(int[] array, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        int n = array.length;
        canvas.resetBarStates();

        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            // Mark the key we are currently placing
            canvas.setBarState(i, 2);

            while (j >= 0 && array[j] > key) {
                if (runner.shouldStop())
                    return;

                canvas.setBarState(j, 1); // Comparing with this bar
                updateUI(canvas, delay);

                array[j + 1] = array[j];
                canvas.setBarState(j + 1, 0); // Reset color after shift
                j--;
            }
            array[j + 1] = key;
            canvas.setBarState(j + 1, 0); // Reset the key's color once placed
            updateUI(canvas, delay);
        }
        // Once done, flash green for completion
        for (int i = 0; i < n; i++)
            canvas.setBarState(i, 3);
    }

    /**
     * Quick Sort: Pivot is YELLOW, pointers are RED.
     */
    public static void quickSort(int[] array, int low, int high, VisualizerCanvas canvas, int delay,
            AlgorithmRunner runner) {
        if (runner.shouldStop())
            return;

        if (low <= high) {
            // Partition the array and get the pivot index
            int pi = partition(array, low, high, canvas, delay, runner);

            // If reset was pressed during partition, exit recursion
            if (pi == -1)
                return;

            // Recursively sort elements before and after partition
            quickSort(array, low, pi - 1, canvas, delay, runner);
            quickSort(array, pi + 1, high, canvas, delay, runner);
        }
    }

    private static int partition(int[] array, int low, int high, VisualizerCanvas canvas, int delay,
            AlgorithmRunner runner) {
        // 1. Choose the rightmost element as pivot
        int pivot = array[high];
        canvas.setBarState(high, 2); // Highlight Pivot in YELLOW

        int i = (low - 1); // Pointer for greater element

        for (int j = low; j < high; j++) {
            if (runner.shouldStop())
                return -1;

            // 2. Highlight current element being compared in RED
            canvas.setBarState(j, 1);
            updateUI(canvas, delay);

            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                // Briefly show the swap
                updateUI(canvas, delay);
            }

            // 3. Reset the color of the scanned bar immediately
            canvas.setBarState(j, 0);
        }

        // 4. Swap the pivot element with the greater element specified by i
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        // 5. Pivot is now in its FINAL sorted position
        canvas.setBarState(high, 0); // Clear old pivot color
        canvas.setBarState(i + 1, 3); // Mark NEW pivot position as GREEN (Sorted)
        updateUI(canvas, delay);

        return i + 1;
    }

    // Bubble Sort
    public static void bubbleSort(int[] array, VisualizerCanvas canvas, int delay, AlgorithmRunner runner) {
        canvas.resetBarStates();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (runner.shouldStop())
                    return;

                canvas.setBarState(j, 1);
                canvas.setBarState(j + 1, 1);
                updateUI(canvas, delay);

                if (array[j] > array[j + 1]) {
                    canvas.setBarState(j, 2);
                    canvas.setBarState(j + 1, 2);
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    updateUI(canvas, delay);
                }
                canvas.setBarState(j, 0);
                canvas.setBarState(j + 1, 0);
            }
            canvas.setBarState(array.length - 1 - i, 3);
        }
        canvas.setBarState(0, 3);
    }

    private static void updateUI(VisualizerCanvas canvas, int delay) {
        canvas.repaint();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
