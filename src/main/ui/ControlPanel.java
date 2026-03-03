package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private JComboBox<String> algorithmSelector;
    private JButton startButton;
    private JButton resetButton;
    private JSlider speedSlider;

    public ControlPanel(ActionListener startListener, ActionListener resetListener) {
        // Dark theme for a modern "Visualizer" look
        setBackground(new Color(45, 45, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // 1. Algorithm Selection
        JLabel algoLabel = createLabel("Algorithm:");
        // FIXED: Added "Dijkstra" to the list so MainFrame can detect it
        String[] algos = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort",
                  "Dijkstra", "BFS", "DFS"};
        algorithmSelector = new JComboBox<>(algos);

        // 2. Speed Slider
        JLabel speedLabel = createLabel("Speed:");
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        speedSlider.setBackground(new Color(45, 45, 45));
        speedSlider.setPreferredSize(new Dimension(150, 20));

        // 3. Buttons
        startButton = createButton("Start Visualizer", new Color(46, 204, 113));
        startButton.addActionListener(startListener);

        resetButton = createButton("Reset / Shuffle", new Color(231, 76, 60));
        resetButton.addActionListener(resetListener);

        // Assemble the UI
        add(algoLabel);
        add(algorithmSelector);
        add(speedLabel);
        add(speedSlider);
        add(startButton);
        add(resetButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        return label;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        // Important for Cross-Platform background colors
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        return btn;
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmSelector.getSelectedItem();
    }

    public int getSpeed() {
        // High slider = small delay (fast)
        // Low slider = large delay (slow)
        return 101 - speedSlider.getValue();
    }
}
