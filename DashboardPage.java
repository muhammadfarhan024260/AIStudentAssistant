package bukc.project;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPage {

    private JPanel bgPanel;

    private JPanel chatPanel;

    private int leftPanelWidth = 150;

    private boolean proceedQuestions = false;

    private String placeholder = "What would you like to know? Ask a question...";
    private String question = null;

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
        JTextField chatInputField = new JTextField(placeholder);
        chatInputField.setFont(new Font("Roboto", Font.PLAIN, 14));
        chatInputField.setForeground(Color.GRAY);
        chatInputField.setBackground(new Color(32, 41, 56));
        chatInputField.setCaretColor(Color.WHITE);
        chatInputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(99, 102, 241), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        chatInputField.setPreferredSize(new Dimension(width - 150, 40));

        // Send Button
        JButton sendButton = new JButton("Proceed");
        sendButton.setBackground(new Color(99, 102, 241));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorderPainted(false);
        sendButton.setFont(new Font("Roboto", Font.BOLD, 14));
        sendButton.setPreferredSize(new Dimension(100, 40));

        // Clear placeholder on focus
        chatInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (chatInputField.getText().equals(placeholder)) {
                    chatInputField.setText("");
                    chatInputField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (chatInputField.getText().isEmpty()) {
                    chatInputField.setText(placeholder);
                    chatInputField.setForeground(Color.GRAY);
                }
            }
        });

        chatInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (chatInputField.getText().isEmpty() || chatInputField.getText().equals(placeholder)) {

                } else {
                    sendButton.setText("Ask");
                    if (proceedQuestions) {
                        proceedQuestions = false;
                    }
                }
            }
        });

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setBackground(new Color(79, 70, 229)); // darker indigo
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setBackground(new Color(99, 102, 241));
            }
        });

        sendButton.addActionListener(e -> {

            if (!proceedQuestions && !(chatInputField.getText().equals(placeholder))) {

                java.util.List<String> questions = new ArrayList<>();
                questions.add(chatInputField.getText().trim());

                for (String q : questions) {
                    question = question + "\n" + q;
                }
//                System.out.println("121");
                addUserMessage(chatInputField.getText());
                chatInputField.setText(placeholder);
                chatInputField.setForeground(Color.GRAY);
                sendButton.setText("Proceed");
                proceedQuestions = true;

            } else if (question != null) {
                String response = GeminiApi.getResponse(question);
//                System.out.println("Response = " + response);
//                System.out.println("Question = " + question);
                addBotMessage(response);
                question = null;
//                addUserMessage(question);
            }

        });

        chatInputPanel.add(chatInputField, BorderLayout.CENTER);
        chatInputPanel.add(sendButton, BorderLayout.EAST);

        rightPanel.add(rightUpper, BorderLayout.NORTH);

        setupChatArea(rightPanel);
        
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
