package bukc.project;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class RoundedButton extends JButton {

    private int cornerRadius = 20;
    private Dimension preferredSize = new Dimension(300, 45); // Default

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setBackground(new Color(99, 102, 241)); // Default indigo color
        setPreferredSize(new Dimension(300, 45));
    }

    public RoundedButton(String text, int height) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setBackground(new Color(99, 102, 241)); // Default indigo color
        setPreferredSize(new Dimension(300, height));
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw button background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        if (getBorder() != null) {
            if (getBorder() instanceof javax.swing.border.LineBorder lineBorder) {
                g2.setColor(lineBorder.getLineColor());
                g2.setStroke(new BasicStroke(lineBorder.getThickness()));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius);
            }
        }

        // Paint the text manually if disabled
        if (!isEnabled()) {
            g2.setColor(Color.WHITE); // Force white text
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(getText(), x, y);
        } else {
            // Let JButton handle enabled text
            super.paintComponent(g);
        }

        g2.dispose();
    }

    @Override
    public void updateUI() {
        // prevent default UI update from messing with custom painting
        setUI(new BasicButtonUI());
    }

    public void setPreferredSize(Dimension size) {
        this.preferredSize = size;
        super.setPreferredSize(size);
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }
}
