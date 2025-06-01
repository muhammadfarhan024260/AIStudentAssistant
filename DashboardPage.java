package bukc.project;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPage {

    private JPanel bgPanel;

    private JPanel chatPanel;

    private int leftPanelWidth = 200;

    private boolean proceedQuestions = false;

    private String placeholder = "What would you like to know? Ask a question...";
    private String question = null;

    private StringBuilder conversationContext = new StringBuilder();

    private Runnable runLoginPage;
    private Runnable runEmailInputPage;

    public DashboardPage(Runnable runLoginPage, Runnable runEmailInputPage) {

        this.runLoginPage = runLoginPage;
        this.runEmailInputPage = runEmailInputPage;

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
        leftPanel.setPreferredSize(new Dimension(leftPanelWidth, height));
        leftPanel.setBackground(new Color(32, 41, 56));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1, new Color(255, 255, 255, 50))
        );

//      Top title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setMaximumSize(new Dimension(leftPanelWidth, 60));
        titlePanel.setBackground(new Color(32, 41, 56));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20)); // left align + padding
        titlePanel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, new Color(255, 255, 255, 50))
        );

        JLabel appName1 = new JLabel("   AI");
//        JLabel appName1 = new JLabel("AI");
        appName1.setForeground(new Color(99, 102, 241));
        appName1.setFont(new Font("Roboto", Font.BOLD, 16));

        JLabel appName2 = new JLabel("Student");
        appName2.setForeground(Color.WHITE);
        appName2.setFont(new Font("Roboto", Font.BOLD, 16));

        JLabel appName3 = new JLabel("Assistant");
        appName3.setForeground(Color.WHITE);
        appName3.setFont(new Font("Roboto", Font.BOLD, 16));

        titlePanel.add(appName1, BorderLayout.WEST);
        titlePanel.add(appName2, BorderLayout.WEST);
        titlePanel.add(appName3, BorderLayout.WEST);
        leftPanel.add(titlePanel);

        JPanel leftInnerPanel = new JPanel();
        leftInnerPanel.setBackground(new Color(32, 41, 56));
        leftInnerPanel.setPreferredSize(new Dimension(leftPanelWidth - 50, 600));
        leftInnerPanel.setLayout(new BoxLayout(leftInnerPanel, BoxLayout.Y_AXIS));
        leftInnerPanel.setVisible(true);
        leftInnerPanel.setOpaque(true);
        leftPanel.add(leftInnerPanel, BorderLayout.CENTER);

        JPanel menuItem1 = constructMenuItem("Clear Chat");
        JPanel menuItem2 = constructMenuItem("Logout");
        JPanel menuItem3 = constructMenuItem("Delete Account");

        menuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(bgPanel);
                consClearChatDialog(topFrame, () -> {
                    chatPanel.removeAll();
                    chatPanel.revalidate();
                    chatPanel.repaint();
                    conversationContext.setLength(0);
                    question = null;
                }).setVisible(true);

            }
        });

        menuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (runLoginPage != null) {
                    runLoginPage.run();
                }
            }
        });

        menuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(bgPanel); // Make sure `bgPanel` is accessible here

                consDeleteAccountDialog(topFrame, () -> {
                    System.out.println("Deleting account for email: " + UserSession.loggedInEmail);
                    UserDataHandler.deleteUser(UserSession.loggedInEmail);
                    UserSession.invokedThroughDashboard = true;
                    if (runEmailInputPage != null) {
                        runEmailInputPage.run();
                    }
                }).setVisible(true);
            }
        });

        leftInnerPanel.add(Box.createVerticalStrut(10));
        leftInnerPanel.add(menuItem1);
        leftInnerPanel.add(Box.createVerticalStrut(10));
        leftInnerPanel.add(menuItem2);
        leftInnerPanel.add(Box.createVerticalStrut(10));
        leftInnerPanel.add(menuItem3);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension((width - leftPanelWidth), 200));
        rightPanel.setBackground(new Color(17, 24, 40));
        rightPanel.setLayout(new BorderLayout());

        // Top bar (Right upper)
        JPanel rightUpper = createGreetingBar();

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

                String current = question.trim();

                if (!current.isEmpty()) {
                    // Add the user's question to the context with labeling
                    conversationContext.append("User: ").append(current).append("\n");

                    // Build final prompt with all context
                    String finalPrompt = "You are an AI assistant. Consider the following conversation history and answer the latest question accordingly:\n\n";
                    finalPrompt += conversationContext.toString();
                    finalPrompt += "AI: ";

                    // Send to Gemini
                    String response = GeminiApi.getResponse(finalPrompt);

                    // Add bot response to context
                    conversationContext.append("AI: ").append(response).append("\n");

                    // Display
                    addBotMessage(response);
                    question = null;
                }

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

    private JPanel createGreetingBar() {
        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BorderLayout());
        greetingPanel.setPreferredSize(new Dimension(bgPanel.getWidth(), 60));
        greetingPanel.setBackground(new Color(32, 41, 56)); // Same as main background
        greetingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 50)), // bottom white border with transparency
                BorderFactory.createEmptyBorder(0, 20, 0, 20) // inner padding
        ));
        
        LocalTime now = LocalTime.now();
        String greetingText;

        if (now.isBefore(LocalTime.NOON)) {
            greetingText = "Good morning, ";
        } else if (now.isBefore(LocalTime.of(17, 0))) {
            greetingText = "Good afternoon, ";
        } else {
            greetingText = "Good evening, ";
        }

        JLabel greetingLabel = new JLabel(greetingText);
        greetingLabel.setForeground(new Color(255, 255, 255, 200));
        greetingLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        JLabel userNameLabel = new JLabel("Farhan");
        userNameLabel.setText(UserSession.loggedInUsername);
        userNameLabel.setForeground(new Color(99, 102, 241));
        userNameLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        leftPanel.setOpaque(false);
        leftPanel.add(greetingLabel);
        leftPanel.add(userNameLabel);

        // Right: Date
        JLabel dateLabel = new JLabel(new java.text.SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date()));
        dateLabel.setForeground(new Color(255, 255, 255, 180));
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 13));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 20));
        rightPanel.setOpaque(false);
        rightPanel.add(dateLabel);

        greetingPanel.add(leftPanel, BorderLayout.WEST);
        greetingPanel.add(rightPanel, BorderLayout.EAST);

        return greetingPanel;
    }

    public JPanel constructMenuItem(String label) {
        JPanel menuItemPanel = new RoundedPanel(10);
        menuItemPanel.setBackground(new Color(32, 41, 56));
        menuItemPanel.setMaximumSize(new Dimension(leftPanelWidth - 20, 40));
        menuItemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

        JLabel itemIcon = new JLabel();
        itemIcon.setMaximumSize(new Dimension(40, 40));
        itemIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel menuItem = new JLabel(label);
        menuItem.setOpaque(false);
        menuItem.setFont(new Font("Roboto", Font.BOLD, 14));
        menuItem.setForeground(Color.WHITE);
        menuItem.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));

        // Hover effect
        menuItemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItemPanel.setBackground(new Color(44, 55, 70));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItemPanel.setBackground(new Color(32, 41, 56));
            }
        });

        menuItemPanel.add(menuItem);

        return menuItemPanel;
    }

    public void clearChat() {
        chatPanel.removeAll();
        chatPanel.revalidate();
        chatPanel.repaint();
        messageCount = 0;
        conversationContext.setLength(0);
    }
    
    public JDialog consClearChatDialog(JFrame parent, Runnable onClearConfirmed) {
        JDialog clearChatDialog = new JDialog(parent, "Clear Chat", true);
        clearChatDialog.setUndecorated(true);
        clearChatDialog.setSize(450, 160);
        clearChatDialog.setLocationRelativeTo(parent);
        clearChatDialog.setBackground(new Color(0, 0, 0, 0)); // transparent

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
        cancelButton.addActionListener(e -> clearChatDialog.dispose());

        JButton clearButton = new RoundedButton("Clear");
        clearButton.setBackground(new Color(99, 102, 241)); // Subtle orange
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Roboto", Font.BOLD, 13));
        clearButton.setPreferredSize(new Dimension(90, 35));
        clearButton.addActionListener(e -> {
            clearChatDialog.dispose();
            if (onClearConfirmed != null) {
                onClearConfirmed.run();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(clearButton);

        bgPanel.add(content, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        clearChatDialog.add(bgPanel, BorderLayout.CENTER);
        
        return clearChatDialog;
    }
    
    public JDialog consDeleteAccountDialog(JFrame parent, Runnable onDeleteConfirmed) {
        JDialog deleteAccountDialog = new JDialog(parent, "Delete Account", true);
        deleteAccountDialog.setUndecorated(true);
        deleteAccountDialog.setSize(480, 160);
        deleteAccountDialog.setLocationRelativeTo(parent);
        deleteAccountDialog.setBackground(new Color(0, 0, 0, 0)); // transparent

        RoundedPanel bgPanel = new RoundedPanel(30);
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setBackground(new Color(32, 41, 56));
        bgPanel.setFocusable(true);

        JLabel heading = new JLabel("Delete Account");
        heading.setFont(new Font("Roboto", Font.BOLD, 18));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        JLabel message = new JLabel("<html><body style='width: 350px;'>Are you sure you want to permanently delete your account? This action cannot be undone.</body></html>");
        message.setFont(new Font("Roboto", Font.PLAIN, 14));
        message.setForeground(new Color(230, 230, 230));
        message.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.add(heading);
        content.add(message);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setBackground(new Color(64, 72, 90));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Roboto", Font.PLAIN, 13));
        cancelButton.setPreferredSize(new Dimension(90, 35));
        cancelButton.setBorder(new RoundedBorder(Color.WHITE, 20));
        cancelButton.addActionListener(e -> deleteAccountDialog.dispose());

        JButton deleteButton = new RoundedButton("Delete");
        deleteButton.setBackground(new Color(220, 53, 69)); // Red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Roboto", Font.BOLD, 13));
        deleteButton.setPreferredSize(new Dimension(90, 35));
        deleteButton.addActionListener(e -> {
            deleteAccountDialog.dispose();
            if (onDeleteConfirmed != null) {
                onDeleteConfirmed.run();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        bgPanel.add(content, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        deleteAccountDialog.add(bgPanel, BorderLayout.CENTER);
        
        return deleteAccountDialog;
    }
}
