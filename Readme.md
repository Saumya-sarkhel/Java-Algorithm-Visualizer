# 📊 Java Algorithm Visualizer

A high-performance, multi-threaded desktop application built with **Java Swing** and **AWT** to visualize the inner workings of popular sorting and pathfinding algorithms in real-time.

---

## 🚀 Features

### 🧩 Sorting Visualization
Watch how data organizes itself through various algorithmic lenses. The UI uses a color-coded system to represent the internal state of the array:.

**Algorithms Included:**
* **Bubble Sort**: The classic neighbor-swap comparison.
* **Selection Sort**: Finding the minimum and placing it in order.
* **Insertion Sort**: Building a sorted section one item at a time.
* **Quick Sort**: Recursive divide-and-conquer using pivots.



### 🗺️ Pathfinding Visualization
Navigate through a 2D grid filled with random obstacles to find the path from Start (Green) to End (Red).
* **BFS (Breadth-First Search)**: Guarantees the shortest path on unweighted grids.
* **DFS (Depth-First Search)**: Explores as far as possible before backtracking.
* **Dijkstra’s Algorithm**: Classic weighted pathfinding logic.



---

## 🛠️ Tech Stack
* **Language**: Java 17+
* **GUI Framework**: Swing & AWT
* **Concurrency**: Multi-threaded execution using `volatile` flags for safe thread termination.

---

## 📂 Project Structure
```text
src/
└── main/
    ├── Application.java          # Main entry point and Look-and-Feel setup
    ├── algorithms/
    │   ├── SortingAlgorithms.java # Logic for all sorting routines
    │   └── PathfindingAlgos.java  # Logic for grid search and path reconstruction
    ├── core/
    │   ├── AlgorithmRunner.java   # Manages background threads and stop signals
    │   └── VisualizerCanvas.java  # Custom JPanel responsible for all GFX rendering
    └── ui/
        ├── MainFrame.java         # Primary window and layout coordination
        └── ControlPanel.java      # User input components (buttons, sliders, etc.)

```
---
### 📥 Getting Started

**1. Installation**


```bash

git clone https://github.com/Saumya-sarkhel/Java-Algorithm-Visualizer.git
cd Java-Algorithm-Visualizer

```
<br>

**2. Compile the Source Code and run**

```bash
# Windows
javac -d bin src/main/*.java src/main/ui/*.java src/main/core/*.java src/main/algorithms/*.java
java -cp bin main.Application


# Linux/Mac
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
rm sources.txt
java -cp bin main.Application
```
