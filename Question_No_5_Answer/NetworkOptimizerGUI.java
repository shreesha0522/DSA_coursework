package Question_No_5_Answer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkOptimizerGUI extends JFrame {
    private JPanel graphPanel;
    private JTextArea logArea;
    private JButton optimizeButton, calculatePathButton;

    /**
     * Constructor for NetworkOptimizerGUI. Sets up the window, panel, buttons, and event listeners.
     */
    public NetworkOptimizerGUI() {
        setTitle("Network Topology Optimizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to draw the network topology (nodes and edges)
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawNetwork(g);
            }
        };
        graphPanel.setPreferredSize(new Dimension(600, 600));
        add(graphPanel, BorderLayout.CENTER);

        // Control panel for buttons and log area
        JPanel controlPanel = new JPanel();
        optimizeButton = new JButton("Optimize Network");
        calculatePathButton = new JButton("Calculate Shortest Path");
        logArea = new JTextArea(5, 30);

        // Add buttons to control panel
        controlPanel.add(optimizeButton);
        controlPanel.add(calculatePathButton);
        add(controlPanel, BorderLayout.NORTH);

        // Add the log area at the bottom
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // Action listener for optimize button
        optimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logArea.append("Optimizing network topology...\n");
                // Implement network optimization logic here
            }
        });

        // Action listener for calculate path button
        calculatePathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logArea.append("Calculating shortest path...\n");
                // Implement shortest path calculation here
            }
        });
    }

    /**
     * Method to draw a sample network on the panel.
     * This includes nodes and edges with sample cost and bandwidth information.
     * @param g Graphics context used to draw the network
     */
    private void drawNetwork(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(100, 100, 30, 30); // Example node
        g.fillOval(200, 200, 30, 30); // Example node
        g.setColor(Color.BLACK);
        g.drawLine(115, 115, 215, 215); // Example edge
        g.drawString("Cost: 10, BW: 50", 150, 170); // Example edge details
    }

    /**
     * The entry point of the program, which initializes the GUI and displays it.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkOptimizerGUI().setVisible(true));
    }
}
