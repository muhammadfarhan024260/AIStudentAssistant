package bukc.project;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPage {

    private JPanel bgPanel;

    private JPanel chatPanel;

    private int leftPanelWidth = 150;

    public DashboardPage() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        bgPanel = new JPanel();
        bgPanel.setBounds(0, 0, width, height);
        bgPanel.setPreferredSize(new Dimension(width, height));
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setFocusable(true);
        bgPanel.setBackground(new Color(17, 24, 40));
        bgPanel.requestFocusInWindow();

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(leftPanelWidth, 200));
        leftPanel.setBackground(new Color(32, 41, 56));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension((width - leftPanelWidth), 200));
        rightPanel.setBackground(new Color(17, 24, 40));
        rightPanel.setLayout(new BorderLayout());

        // Top bar (Right upper)
        JPanel rightUpper = new JPanel();
        rightUpper.setPreferredSize(new Dimension(width - 250, 60));
        rightUpper.setBackground(new Color(32, 41, 56));
        rightUpper.setLayout(new BorderLayout());

        // Chat Input Panel (Bottom)
        JPanel chatInputPanel = new JPanel();
        chatInputPanel.setLayout(new BorderLayout());
        chatInputPanel.setPreferredSize(new Dimension(width, 80));
        chatInputPanel.setBackground(new Color(17, 24, 40));
        chatInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Input field
        JTextField chatInputField = new JTextField("What would you like to know? Ask a question...");
        chatInputField.setFont(new Font("Roboto", Font.PLAIN, 14));
        chatInputField.setForeground(Color.GRAY);
        chatInputField.setBackground(new Color(32, 41, 56));
        chatInputField.setCaretColor(Color.WHITE);
        chatInputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(99, 102, 241), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        chatInputField.setPreferredSize(new Dimension(width - 150, 40));

        // Clear placeholder on focus
        chatInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (chatInputField.getText().equals("What would you like to know? Ask a question...")) {
                    chatInputField.setText("");
                    chatInputField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (chatInputField.getText().isEmpty()) {
                    chatInputField.setText("What would you like to know? Ask a question...");
                    chatInputField.setForeground(Color.GRAY);
                }
            }
        });

        // Send Button
        JButton sendButton = new JButton("Ask");
        sendButton.setBackground(new Color(99, 102, 241));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Roboto", Font.BOLD, 14));
        sendButton.setPreferredSize(new Dimension(80, 40));

        sendButton.addActionListener(e -> {
            String question = chatInputField.getText().trim();

            if (chatInputField.getText().isEmpty() || chatInputField.getText().equals("What would you like to know? Ask a question...")) {
                addUserMessage("Empty");
                addBotMessage("I'm great, thank you! How can I help you today?");
                chatInputField.setText("What would you like to know? Ask a question...");
                chatInputField.setForeground(Color.GRAY);
            } else {

                String responce = GeminiApi.getResponse(question);

                addUserMessage(chatInputField.getText());
                addBotMessage(responce);
                chatInputField.setText("What would you like to know? Ask a question...");
                chatInputField.setForeground(Color.GRAY);
            }

        });

        chatInputPanel.add(chatInputField, BorderLayout.CENTER);
        chatInputPanel.add(sendButton, BorderLayout.EAST);

        rightPanel.add(rightUpper, BorderLayout.NORTH);

        setupChatArea(rightPanel);

//        addUserMessage("user, how are you?");
//        addBotMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ac suscipit elit. Aenean dolor sapien, posuere id dictum id, ultricies a mi. Vivamus vehicula posuere sodales. Aenean cursus tincidunt neque at sollicitudin. Suspendisse venenatis nec neque ac volutpat. Integer vel purus cursus, gravida eros id, bibendum diam. Aliquam auctor, urna in scelerisque gravida, purus leo consequat neque, finibus dictum enim orci vel neque. Nunc eu lorem tortor. Fusce eget commodo orci, dictum luctus nisi. Cras tellus arcu, tincidunt in tristique et, sagittis vel enim. Aenean sollicitudin ligula eu lectus blandit, non luctus lacus semper. Lorem ipsum dolor sit amet, consectetur.");
//        addUserMessage("Hello, how are you?");
//        addBotMessage("I'm great, thank you! How can I help you today?");
//        addUserMessage("Hello, how are you?");
//        addBotMessage("I'm great, thank you! How can I help you today?");
//        addUserMessage("Hello, how are you?");
//        addBotMessage("I'm great, thank you! How can I help you today?");
        bgPanel.add(leftPanel, BorderLayout.WEST);
        bgPanel.add(rightPanel, BorderLayout.EAST);
        rightPanel.add(chatInputPanel, BorderLayout.SOUTH);

        chatPanel.revalidate();
        chatPanel.repaint();

    }

    public JPanel getMainPanel() {
        return bgPanel;
    }

    private void setupChatArea(JPanel rightPanel) {
        chatPanel = new JPanel(new GridBagLayout());
        chatPanel.setBackground(new Color(17, 24, 40));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(125, 24, 40));

        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.setBorder(null);

        rightPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private int messageCount = 0;

    public void addUserMessage(String message) {
        addMessageBubble(message, new Color(56, 86, 160), FlowLayout.RIGHT);
    }

    public void addBotMessage(String message) {
        addMessageBubble(message, new Color(50, 60, 80), FlowLayout.LEFT);
    }

    private void addMessageBubble(String text, Color bgColor, int alignment) {
        JPanel bubblePanel = new JPanel(new FlowLayout(alignment, 0, 0));
        bubblePanel.setOpaque(false);
        bubblePanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        RoundedTextArea messageArea = new RoundedTextArea(text, 20, bgColor);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setForeground(Color.WHITE);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        messageArea.setColumns(1);
        messageArea.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        int bubbleWidth = 500;
        messageArea.setColumns(1);
        messageArea.setSize(new Dimension(bubbleWidth, Short.MAX_VALUE));
        messageArea.setPreferredSize(new Dimension(bubbleWidth, messageArea.getPreferredSize().height));
        messageArea.setMaximumSize(new Dimension(bubbleWidth, Integer.MAX_VALUE));
        messageArea.setMinimumSize(new Dimension(bubbleWidth, 30));

        bubblePanel.add(messageArea);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = messageCount++;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        chatPanel.add(bubblePanel, gbc);

        chatPanel.revalidate();
        chatPanel.repaint();
        scrollToBottom();
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = ((JScrollPane) chatPanel.getParent().getParent()).getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

}
