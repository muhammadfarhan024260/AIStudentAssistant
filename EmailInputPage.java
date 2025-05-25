package bukc.project;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmailInputPage {

    private JPanel bgPanel;
    private JPanel innerPanel;
    private JPanel emailLabelPanel;
    private JPanel emailValidLabelPanel;

    private JLabel jLabel1;
    private JLabel emailLabel;
    private JLabel subLabel;
    private JLabel emailValidLabel;

    private JTextField emailField;

    private RoundedButton continueButton;

    private final String placeholder = "Enter your email";
    
    private Runnable SuccessfulProceed;
    
    private static boolean showLoginPage = true;

    public EmailInputPage(Runnable SuccessfulProceed) {
        
        this.SuccessfulProceed = SuccessfulProceed;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        bgPanel = new JPanel();
        bgPanel.setBounds(0, 0, width, height);
        bgPanel.setPreferredSize(new Dimension(width, height));
        bgPanel.setLayout(new GridBagLayout());
        bgPanel.setBackground(new Color(17, 24, 40));
        bgPanel.setFocusable(true);

        // Heading
        jLabel1 = new JLabel("Welcome");
        jLabel1.setFont(new Font("Roboto", Font.BOLD, 28));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        subLabel = new JLabel("Join our Student Community by entering your email");
        subLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        subLabel.setForeground(new Color(180, 180, 180));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inner panel
        innerPanel = new RoundedPanel(30);
        innerPanel.setBackground(new Color(32, 41, 56));
        innerPanel.setPreferredSize(new Dimension(400, 300));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setFocusable(true);

        innerPanel.add(Box.createVerticalStrut(40));
        innerPanel.add(jLabel1);
        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(subLabel);
        innerPanel.add(Box.createVerticalStrut(30));

        // Email Label
        emailLabelPanel = new JPanel(new BorderLayout());
        emailLabelPanel.setOpaque(false);
        emailLabelPanel.setMaximumSize(new Dimension(320, 20));
        emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        emailLabel.setForeground(Color.LIGHT_GRAY);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        emailLabelPanel.add(emailLabel, BorderLayout.WEST);
        innerPanel.add(emailLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Email Text Field
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(320, 40));
        emailField.setPreferredSize(new Dimension(320, 40));
        emailField.setFont(new Font("Roboto", Font.PLAIN, 14));
        emailField.setBackground(new Color(32, 41, 56));
        emailField.setForeground(Color.GRAY);
        emailField.setCaretColor(Color.GRAY);
        emailField.setText(placeholder);
        emailField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                emailField.setBorder(new RoundedBorder(new Color(100, 149, 237), 10));
                if (emailField.getText().equals(placeholder)) {
                    emailField.setText("");
                    emailField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                emailField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
                if (emailField.getText().isEmpty()) {
                    emailField.setText(placeholder);
                    emailField.setForeground(Color.GRAY);
                } else {
                    validateEmail();
                }
            }
        });

        innerPanel.add(emailField);
        innerPanel.add(Box.createVerticalStrut(5));

        // Email Validation Label
        emailValidLabelPanel = new JPanel(new BorderLayout());
        emailValidLabelPanel.setOpaque(false);
        emailValidLabelPanel.setMaximumSize(new Dimension(320, 12));
        emailValidLabel = new JLabel();
        emailValidLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        emailValidLabel.setForeground(new Color(151, 0, 0));
        emailValidLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        emailValidLabel.setVisible(false);
        emailValidLabelPanel.add(emailValidLabel, BorderLayout.WEST);
        innerPanel.add(emailValidLabelPanel);
        innerPanel.add(Box.createVerticalStrut(20));

        // Continue Button
        continueButton = new RoundedButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setMaximumSize(continueButton.getPreferredSize());

        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                continueButton.setBackground(new Color(79, 70, 229));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                continueButton.setBackground(new Color(99, 102, 241));
            }
            
            
// Email button ---------------------------------------------------------------------------
            @Override
            public void mouseClicked(MouseEvent e) {
                if (validateEmail()) {
                    // Proceed to next page or logic
                    System.out.println("Email entered: " + emailField.getText());
                    
                    if (SuccessfulProceed != null) {
                        SuccessfulProceed.run();
                    }
                    
                }
            }
        });

        innerPanel.add(continueButton);
        bgPanel.add(innerPanel, new GridBagConstraints());
    }

    public JPanel getMainPanel() {
        return bgPanel;
    }

    public RoundedButton getContinueButton() {
        return continueButton;
    }

    public String getEmail() {
        return emailField.getText();
    }

    private boolean validateEmail() {
        String email = emailField.getText();
        boolean valid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        if (!valid) {
            emailValidLabel.setText("Please enter a valid email address.");
            emailValidLabel.setForeground(new Color(200, 0, 0));
            emailValidLabel.setVisible(true);
            emailField.setBorder(new RoundedBorder(new Color(200, 0, 0), 10));
        } else {
            emailValidLabel.setVisible(false);
            emailField.setBorder(new RoundedBorder(new Color(0, 200, 0), 10));
        }
        return valid;
    }
    
    
    public static boolean getLoginPage(){
        return showLoginPage;
    }
}
