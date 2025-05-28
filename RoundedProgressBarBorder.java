package bukc.project;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedProgressBarBorder extends AbstractBorder {
    private int radius;

    public RoundedProgressBarBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GREEN); // border color
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 4, 4, 4); // small padding
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 4, 4, 4);
        return insets;
    }
}
