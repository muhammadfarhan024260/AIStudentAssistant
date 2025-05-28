package bukc.project;

import javax.swing.*;
import java.awt.*;

public class RoundedTextArea extends JTextArea {

    private int cornerRadius = 20;
    private Color backgroundColor = new Color(56, 86, 160); // Default

    public RoundedTextArea(String text, int radius, Color bgColor) {
        super(text);
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false); // Prevent default background fill
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill rounded rectangle as background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }
}
