package days;

import javax.swing.*;
import java.awt.*;

public class ShoelaceVisualization extends JFrame {

    private long[][] grid;

    public ShoelaceVisualization(long[][] grid) {
        this.grid = grid;
        setTitle("Shoelace Visualization");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int cellSize = 20;

        // Draw the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

        // Calculate and draw the polygon
        int[] xPoints = {1 * cellSize, 6 * cellSize, 6 * cellSize, 1 * cellSize};
        int[] yPoints = {1 * cellSize, 1 * cellSize, 9 * cellSize, 9 * cellSize};
        int nPoints = 4;

        g.setColor(Color.RED);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }
}
