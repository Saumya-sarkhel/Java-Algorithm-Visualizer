package main;

import javax.swing.*;
import main.ui.MainFrame;
import java.awt.*;

/**
 * The entry point for the Java Algorithm Visualizer.
 * Responsible for setting the Look and Feel and initializing the UI.
 */
public class Application {

    public static void main(String[] args) {
        // Set the System Look and Feel for a native OS appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default if system theme fails
            e.printStackTrace();
        }

        // Standard practice: Start Swing apps on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame appFrame = new MainFrame("Java Algorithm Visualizer v1.0");

            // Setup basic window properties
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setPreferredSize(new Dimension(1200, 800));
            appFrame.pack();
            appFrame.setLocationRelativeTo(null); // Center on screen
            appFrame.setVisible(true);
        });
    }
}
