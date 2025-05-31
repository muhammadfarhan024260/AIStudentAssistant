package bukc.project;

import javax.swing.*;
import java.awt.*;

public class DeleteAccountDialog extends JDialog {

    public DeleteAccountDialog(JFrame parent, Runnable onDeleteConfirmed) {
        super(parent, "Delete Account", true);
        this.setUndecorated(true);
        this.setSize(480, 160);
        this.setLocationRelativeTo(parent);
        this.setBackground(new Color(0, 0, 0, 0)); // transparent

        // 🔲 Rounded background
        RoundedPanel bgPanel = new RoundedPanel(30);
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setBackground(new Color(32, 41, 56));
        bgPanel.setFocusable(true);

        // 🧠 Heading
        JLabel heading = new JLabel("Delete Account");
        heading.setFont(new Font("Roboto", Font.BOLD, 18));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        // 📣 Message
        JLabel message = new JLabel("<html><body style='width: 350px;'>Are you sure you want to permanently delete your account? This action cannot be undone.</body></html>");
        message.setFont(new Font("Roboto", Font.PLAIN, 14));
        message.setForeground(new Color(230, 230, 230));
        message.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // 📦 Content Panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(heading);
        content.add(message);

        // 🧾 Button Panel
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

        JButton deleteButton = new RoundedButton("Delete");
        deleteButton.setBackground(new Color(220, 53, 69)); // Red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Roboto", Font.BOLD, 13));
        deleteButton.setPreferredSize(new Dimension(90, 35));
        deleteButton.addActionListener(e -> {
            dispose();
            if (onDeleteConfirmed != null) {
                onDeleteConfirmed.run();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        bgPanel.add(content, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bgPanel, BorderLayout.CENTER);
    }
}
