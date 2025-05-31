package bukc.project;

import javax.swing.*;
import java.awt.*;

public class ClearChatDialog extends JDialog {

    public ClearChatDialog(JFrame parent, Runnable onClearConfirmed) {
        super(parent, "Clear Chat", true);
        this.setUndecorated(true);
        this.setSize(450, 160);
        this.setLocationRelativeTo(parent);
        this.setBackground(new Color(0, 0, 0, 0)); // transparent

        // Rounded background panel
        RoundedPanel bgPanel = new RoundedPanel(30);
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setBackground(new Color(32, 41, 56));
        bgPanel.setFocusable(true);

        // Title
        JLabel heading = new JLabel("Clear Chat");
        heading.setFont(new Font("Roboto", Font.BOLD, 18));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        // Message
        JLabel message = new JLabel("<html><body style='width: 350px;'>Are you sure you want to clear all chat messages? This cannot be undone.</body></html>");
        message.setFont(new Font("Roboto", Font.PLAIN, 14));
        message.setForeground(new Color(230, 230, 230));
        message.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // Content wrapper
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(heading);
        content.add(message);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setBackground(new Color(64, 72, 90));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Roboto", Font.PLAIN, 13));
        cancelButton.setPreferredSize(new Dimension(90, 35));
        cancelButton.setBorder(new RoundedBorder(Color.WHITE, 20));
        cancelButton.addActionListener(e -> dispose());

        JButton clearButton = new RoundedButton("Clear");
        clearButton.setBackground(new Color(99, 102, 241)); // Subtle orange
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Roboto", Font.BOLD, 13));
        clearButton.setPreferredSize(new Dimension(90, 35));
        clearButton.addActionListener(e -> {
            dispose();
            if (onClearConfirmed != null) {
                onClearConfirmed.run();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(clearButton);

        bgPanel.add(content, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bgPanel, BorderLayout.CENTER);
    }
}
