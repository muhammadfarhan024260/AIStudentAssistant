package bukc.project;

import bukc.project.RoundedBorder;
import bukc.project.SignupPage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EmailVerificationPage {

    private JPanel bgPanel;
    private JPanel innerPanel;
    private JPanel emailLabelPanel;
    private JPanel emailValidLabelPanel;
    private JPanel passLabelPanel;
    private JPanel resendLabelPanel;

    private JLabel jLabel1;
    private JLabel emailValidLabel;
    private JLabel passLabel;
    private JLabel subLabel;
    private JLabel subLabel2;
    private JLabel subLabel3;
    private JLabel emailLabel;
    private JLabel resendLabel;

    private JTextField passField;
    private JTextField emailField;

    private Component emailSpacer;

    private RoundedButton signInButton;

    private String placeholder = "Enter 6-digit code";
    private String placeholder2 = "Password";

    private Runnable onSuccessCallback;
    private boolean OtpMatched = false;
    private boolean userNotAdded = true;

    public EmailVerificationPage(Runnable onSuccessCallback) {

        this.onSuccessCallback = onSuccessCallback;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        bgPanel = new JPanel();

        subLabel = new JLabel("Verify your email address");
        subLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        subLabel.setForeground(new Color(180, 180, 180)); // soft gray
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subLabel2 = new JLabel("We've sent a verification code to");
        subLabel2.setFont(new Font("Roboto", Font.PLAIN, 14));
        subLabel2.setForeground(new Color(180, 180, 180)); // soft gray
        subLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        subLabel3 = new JLabel(SignupPage.getRecieverEmail());
        subLabel3.setFont(new Font("Roboto", Font.BOLD, 14));
        subLabel3.setForeground(Color.WHITE);
        subLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        jLabel1 = new JLabel();
        jLabel1.setText("Create an account");
        jLabel1.setFont(new Font("Roboto", Font.BOLD, 30));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT); // very important for BoxLayout

        innerPanel = new RoundedPanel(30);
        innerPanel.setBackground(new Color(32, 41, 56));
        innerPanel.setPreferredSize(new Dimension(400, 390));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.isFocusable();
        innerPanel.add(Box.createVerticalStrut(40)); // padding at the top
        innerPanel.add(jLabel1);
        innerPanel.add(subLabel);
        innerPanel.add(Box.createVerticalStrut(20)); // padding at the top
        innerPanel.add(subLabel2);
        innerPanel.add(subLabel3);
        innerPanel.add(Box.createVerticalStrut(30)); // spacing after label

        // Email label aligned left
        emailLabelPanel = new JPanel();
        emailLabelPanel.setLayout(new BorderLayout());
        emailLabelPanel.setOpaque(false);
        emailLabelPanel.setMaximumSize(new Dimension(320, 20));

        emailLabel = new JLabel("Verification code");
        emailLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        emailLabel.setForeground(Color.LIGHT_GRAY);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding

        emailLabelPanel.add(emailLabel, BorderLayout.WEST);
        innerPanel.add(emailLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Email text field
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(320, 40));
        emailField.setPreferredSize(new Dimension(320, 40));
        emailField.setFont(new Font("Roboto", Font.PLAIN, 14));
        emailField.setBackground(new Color(32, 41, 56));
        emailField.setForeground(Color.WHITE);
        emailField.setCaretColor(Color.GRAY);
        emailField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setText(placeholder);
        emailField.setForeground(Color.GRAY);
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
                }
            }
        });

        innerPanel.add(emailField);
        innerPanel.add(Box.createVerticalStrut(5)); // space before next component

        //Email validity Label
        emailValidLabelPanel = new JPanel();
        emailValidLabelPanel.setLayout(new BorderLayout());
        emailValidLabelPanel.setOpaque(false);
        emailValidLabelPanel.setMaximumSize(new Dimension(320, 12));

        emailValidLabel = new JLabel();
        emailValidLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        emailValidLabel.setForeground(new Color(151, 0, 0));
        emailValidLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding
        emailValidLabel.setVisible(false);

        emailValidLabelPanel.add(emailValidLabel, BorderLayout.WEST);
        innerPanel.add(emailValidLabelPanel);

        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = emailField.getText();
                if (SignupPage.getGeneratedOTP().equals(text)) {
                    emailField.setBorder(new RoundedBorder(new Color(0, 200, 0), 10));
                    emailValidLabel.setForeground(new Color(0, 200, 0));
                    emailValidLabel.setText("Correct OTP");
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);
                    OtpMatched = true;
                } else {
                    emailField.setBorder(new RoundedBorder(new Color(200, 0, 0), 10));
                    emailValidLabel.setForeground(new Color(200, 0, 0));
                    emailValidLabel.setText("Wrong OTP");
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);
                }
            }
        });

        emailField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                String text = emailField.getText();
                if (SignupPage.getGeneratedOTP().equals(text)) {
                    emailField.setBorder(new RoundedBorder(new Color(0, 200, 0), 10));
                    emailValidLabel.setForeground(new Color(0, 200, 0));
                    emailValidLabel.setText("Correct OTP");
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);
                } else {
                    emailField.setBorder(new RoundedBorder(new Color(200, 0, 0), 10));
                    emailValidLabel.setForeground(new Color(200, 0, 0));
                    emailValidLabel.setText("Wrong OTP");
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);
                }
            }
        });

        // Resend label aligned left
        resendLabelPanel = new JPanel();
        resendLabelPanel.setLayout(new BorderLayout());
        resendLabelPanel.setOpaque(false);
        resendLabelPanel.setMaximumSize(new Dimension(320, 20));

        resendLabel = new JLabel("Resend code");
        resendLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        resendLabel.setForeground(new Color(27, 90, 156));
        resendLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding

        resendLabelPanel.add(resendLabel, BorderLayout.WEST);
        innerPanel.add(resendLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        emailSpacer = Box.createVerticalStrut(20);
        emailSpacer.setVisible(false);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.revalidate();
        innerPanel.repaint();

        // Login Button
        signInButton = new RoundedButton("Verify and Create Account");
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setMaximumSize(signInButton.getPreferredSize());

        signInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signInButton.setBackground(new Color(79, 70, 229)); // darker indigo
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signInButton.setBackground(new Color(99, 102, 241));
            }
        });

        signInButton.addActionListener(e -> {
            if (OtpMatched) {
                if (userNotAdded){
                    UserDataHandler.addUser(SignupPage.getUser());
                    userNotAdded = false;
                }
                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }
            }
        });

        innerPanel.add(signInButton);

        bgPanel.setBounds(0, 0, width, height);
        bgPanel.setPreferredSize(new Dimension(width, height));
        bgPanel.setLayout(new GridBagLayout());
        bgPanel.add(innerPanel, new GridBagConstraints());
        bgPanel.setFocusable(true);
        bgPanel.setBackground(new Color(17, 24, 40));
        bgPanel.requestFocusInWindow();

        innerPanel.setFocusable(true);
        innerPanel.requestFocusInWindow();
    }

    public JPanel getMainPanel() {
        return bgPanel;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    public RoundedButton getSignInButton() {
        return signInButton;
    }

}
