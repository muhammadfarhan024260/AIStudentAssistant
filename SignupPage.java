package bukc.project;

import jakarta.mail.MessagingException;
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
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class SignupPage {

    private JPanel bgPanel;
    private JPanel innerPanel;
    private JPanel emailLabelPanel;
    private JPanel emailValidLabelPanel;
    private JPanel passLabelPanel;
    private JPanel confirmPassLabelPanel;
    private JPanel usernameLabelPanel;
    private JPanel passwordStrengthPanel;

    private JLabel jLabel1;
    private JLabel emailValidLabel;
    private JLabel passLabel;
    private JLabel subLabel;
    private JLabel emailLabel;
    private JLabel confirmPassLabel;
    private JLabel usernameLabel;
    private JLabel passwordStrengthLabel;
    private JLabel passwordStrengthResultLabel;
    private JLabel passwordDescription1;

    private JLabel rule1;
    private JLabel rule2;
    private JLabel rule3;
    private JLabel rule4;
    private JLabel rule5;

    private JTextField passField;
    private JTextField emailField;
    private JTextField confirmPassField;
    private JTextField usernameField;

    private Component emailSpacer;

    private RoundedButton signUpButton;

    private JProgressBar passwordStrengthBar;

    private String placeholder = "Enter your email";
    private String placeholder2 = "PaSsWoRd";
    private String placeholder3 = "Enter your name";
    private static String recieverEmail = "default@mail.com";

    private boolean isUsernameFieldSpaced = false;
    private boolean isEmailFieldSpaced = false;
    private boolean isPassFieldSpaced = false;
    private boolean isConfirmPassFieldSpaced = false;

    private int length = 550;

    private static String generatedOTP = null;

    private boolean[] canProceed = new boolean[4];

    public static boolean canDisposeSignupPage = false;
    public static boolean canProceedVar = false;

    private Runnable onSuccessCallback;
    
    private static User user;

    public SignupPage(Runnable onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        bgPanel = new JPanel();

        subLabel = new JLabel("Join our student community");
        subLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        subLabel.setForeground(new Color(180, 180, 180)); // soft gray
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        jLabel1 = new JLabel();
        jLabel1.setText("Create an account");
        jLabel1.setFont(new Font("Roboto", Font.BOLD, 30));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT); // very important for BoxLayout

        innerPanel = new RoundedPanel(30);
        innerPanel.setBackground(new Color(32, 41, 56));
        innerPanel.setPreferredSize(new Dimension(400, length));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.isFocusable();
        innerPanel.add(Box.createVerticalStrut(30)); // padding at the top
        innerPanel.add(jLabel1);
        innerPanel.add(subLabel);
        innerPanel.add(Box.createVerticalStrut(30)); // spacing after label

        // Username label aligned left
        usernameLabelPanel = new JPanel();
        usernameLabelPanel.setLayout(new BorderLayout());
        usernameLabelPanel.setOpaque(false);
        usernameLabelPanel.setMaximumSize(new Dimension(320, 20));

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding

        usernameLabelPanel.add(usernameLabel, BorderLayout.WEST);
        innerPanel.add(usernameLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Username text field
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(320, 40));
        usernameField.setPreferredSize(new Dimension(320, 40));
        usernameField.setFont(new Font("Roboto", Font.PLAIN, 14));
        usernameField.setBackground(new Color(32, 41, 56));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.GRAY);
        usernameField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setText(placeholder3);
        usernameField.setForeground(Color.GRAY);

        innerPanel.add(usernameField);
        innerPanel.add(Box.createVerticalStrut(5)); // space before next component

        //Username validity Label
        JPanel usernameValidLabelPanel = new JPanel();
        JLabel usernameValidLabel = new JLabel("This field is mendatory");
        Component usernameSpacer = Box.createVerticalStrut(20);
        validityLabel(usernameValidLabelPanel, usernameValidLabel, usernameSpacer);

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

                usernameValidLabel.setVisible(false);
                usernameSpacer.setVisible(false);
                if (isUsernameFieldSpaced) {
                    length -= 10;
                    innerPanel.setPreferredSize(new Dimension(400, length));
                    isUsernameFieldSpaced = false;
                }

                usernameField.setBorder(new RoundedBorder(new Color(100, 149, 237), 10));
                if (usernameField.getText().equals(placeholder3)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.WHITE);
                }

                if (!canProceed[0]) {
                    canProceed[0] = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(placeholder3);
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        innerPanel.add(usernameValidLabelPanel);
        innerPanel.add(usernameSpacer);

        // Email label aligned left
        emailLabelPanel = new JPanel();
        emailLabelPanel.setLayout(new BorderLayout());
        emailLabelPanel.setOpaque(false);
        emailLabelPanel.setMaximumSize(new Dimension(320, 20));

        emailLabel = new JLabel("Email");
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
                emailValidLabel.setVisible(false);
                emailSpacer.setVisible(false);
                if (isEmailFieldSpaced) {
                    length -= 10;
                    innerPanel.setPreferredSize(new Dimension(400, length));
                    isEmailFieldSpaced = false;
                }
                if (canProceed[1] == false) {
                    canProceed[1] = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                emailField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
                if (emailField.getText().isEmpty()) {
                    emailField.setText(placeholder);
                    emailField.setForeground(Color.GRAY);
                    emailValidLabel.setVisible(false);
                    emailSpacer.setVisible(false);
                }
            }
        });
//        emailField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                String text = emailField.getText();
//                if (isValidEmail(text)) {
//                    emailValidLabel.setText("Not verified");
//                    emailValidLabel.setVisible(true);
//                    emailSpacer.setVisible(true);
//                }
//            }
//        });

        JPanel verifyButtonPanel = new JPanel();
        verifyButtonPanel.setMaximumSize(new Dimension(320, 20));
        verifyButtonPanel.setPreferredSize(new Dimension(320, 20));
        verifyButtonPanel.setOpaque(false);
        verifyButtonPanel.setLayout(new BorderLayout());
        verifyButtonPanel.setBackground(Color.YELLOW);

        RoundedButton verifyButton = new RoundedButton("Send OTP");
        verifyButton.setMaximumSize(new Dimension(100, 20));
        verifyButton.setPreferredSize(new Dimension(100, 20));
        verifyButton.setBackground(new Color(32, 41, 56));
        verifyButton.setFont(new Font("Roboto", Font.PLAIN, 10));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        verifyButton.setCornerRadius(10);

        innerPanel.add(emailField);
        innerPanel.add(Box.createVerticalStrut(5)); // space before next component

        //Email validity Label
        emailValidLabelPanel = new JPanel();
        emailValidLabel = new JLabel("Please enter a valid email address");
        emailSpacer = Box.createVerticalStrut(20);

        validityLabel(emailValidLabelPanel, emailValidLabel, emailSpacer);
        innerPanel.add(emailValidLabelPanel);
        innerPanel.add(emailSpacer);
//        innerPanel.add(Box.createVerticalStrut(5));
//        verifyButtonPanel.add(verifyButton, BorderLayout.WEST);
//        innerPanel.add(verifyButtonPanel);

//        innerPanel.add(VerifyButtonSpacer);
        // Password label aligned left
        passLabelPanel = new JPanel();
        passLabelPanel.setLayout(new BorderLayout());
        passLabelPanel.setOpaque(false);
        passLabelPanel.setMaximumSize(new Dimension(320, 20));

        passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        passLabel.setForeground(Color.LIGHT_GRAY);
        passLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding

        passLabelPanel.add(passLabel, BorderLayout.WEST);
        innerPanel.add(passLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Password text field
        passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(320, 40));
        passField.setPreferredSize(new Dimension(320, 40));
        passField.setFont(new Font("Roboto", Font.PLAIN, 14));
        passField.setBackground(new Color(32, 41, 56));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.GRAY);
        passField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passField.setText(placeholder2);
        passField.setForeground(Color.GRAY);

        JPanel passValidPanel = new JPanel();
        JLabel passValidLabel = new JLabel("This field is mendatory");
        Component passValidSpacer = Box.createVerticalStrut(10);
        validityLabel(passValidPanel, passValidLabel, passValidSpacer);

        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passField.setBorder(new RoundedBorder(new Color(100, 149, 237), 10));

                if (passField.getText().equals(placeholder2)) {
                    passField.setText("");
                    passField.setForeground(Color.WHITE);
                }

                if (isPassFieldSpaced) {
                    length -= 10;
                    innerPanel.setPreferredSize(new Dimension(400, length));
                    isPassFieldSpaced = false;
                }

                if (canProceed[2] == false) {
                    canProceed[2] = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                passField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
                if (passField.getText().isEmpty()) {
                    passField.setText(placeholder2);
                    passField.setForeground(Color.GRAY);
                }
            }
        });

        innerPanel.add(passField);
        innerPanel.add(Box.createVerticalStrut(5)); // space before next component

        innerPanel.add(passValidPanel);
        innerPanel.add(passValidSpacer);

        passwordStrengthPanel = new JPanel();
        passwordStrengthPanel.setLayout(new BorderLayout());
        passwordStrengthPanel.setOpaque(false);
        passwordStrengthPanel.setMaximumSize(new Dimension(320, 20));

        passwordStrengthLabel = new JLabel("Strength: ");
        passwordStrengthLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        passwordStrengthLabel.setForeground(Color.GRAY);
        passwordStrengthPanel.setVisible(false);

        passwordStrengthResultLabel = new JLabel("");
        passwordStrengthResultLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        passwordStrengthResultLabel.setForeground(Color.WHITE);

        passwordStrengthPanel.add(passwordStrengthLabel, BorderLayout.WEST);
        passwordStrengthPanel.add(passwordStrengthResultLabel, BorderLayout.EAST);
        innerPanel.add(passwordStrengthPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Password Strength Bar
        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setMaximumSize(new Dimension(320, 5));
        passwordStrengthBar.setBorderPainted(false);
        passwordStrengthBar.setBorder(null);
        passwordStrengthBar.setForeground(new Color(200, 0, 0)); // Weak = red
        passwordStrengthBar.setBackground(new Color(50, 60, 80));
        passwordStrengthBar.setValue(0);
        passwordStrengthBar.setStringPainted(false); // optional
        passwordStrengthBar.setVisible(false);

        innerPanel.add(passwordStrengthBar);
//        innerPanel.add(Box.createVerticalStrut(10)); // space before confirm pass

        JPanel description1 = new JPanel();
        JPanel description2 = new JPanel();
        JPanel description3 = new JPanel();
        JPanel description4 = new JPanel();
        JPanel description5 = new JPanel();

        for (JPanel panel : Arrays.asList(description1, description2, description3, description4, description5)) {
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);
            panel.setVisible(false);
            panel.setMaximumSize(new Dimension(320, 10));
            panel.setPreferredSize(new Dimension(320, 12));
        }

        rule1 = new JLabel("• At least 8 characters");
        rule2 = new JLabel("• At least one uppercase letter");
        rule3 = new JLabel("• At least one lowercase letter");
        rule4 = new JLabel("• At least one number");
        rule5 = new JLabel("• At least one symbol");

        for (JLabel label : Arrays.asList(rule1, rule2, rule3, rule4, rule5)) {
            label.setForeground(Color.GRAY);
            label.setFont(new Font("Roboto", Font.PLAIN, 10));
            label.setVisible(true);
        }

        Component passSpacer = Box.createVerticalStrut(10);
        passSpacer.setVisible(false);

        innerPanel.add(Box.createVerticalStrut(5));
        description1.add(rule1, BorderLayout.WEST);
        innerPanel.add(description1);
        description2.add(rule2, BorderLayout.WEST);
        innerPanel.add(description2);
        description3.add(rule3, BorderLayout.WEST);
        innerPanel.add(description3);
        description4.add(rule4, BorderLayout.WEST);
        innerPanel.add(description4);
        description5.add(rule5, BorderLayout.WEST);
        innerPanel.add(description5);
        innerPanel.add(passSpacer); // space before confirm pass

        passField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                boolean isVisible = true;

                passValidPanel.setVisible(!isVisible);
                passValidSpacer.setVisible(!isVisible);

                passwordStrengthPanel.setVisible(isVisible);
                passwordStrengthBar.setVisible(isVisible);
                description1.setVisible(isVisible);
                description2.setVisible(isVisible);
                description3.setVisible(isVisible);
                description4.setVisible(isVisible);
                description5.setVisible(isVisible);
                passSpacer.setVisible(isVisible);

                length += 70;
                innerPanel.setPreferredSize(new Dimension(400, length));
            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean isVisible = false;

                passValidPanel.setVisible(isVisible);
                passValidSpacer.setVisible(!isVisible);

                passwordStrengthPanel.setVisible(isVisible);
                passwordStrengthBar.setVisible(isVisible);
                description1.setVisible(isVisible);
                description2.setVisible(isVisible);
                description3.setVisible(isVisible);
                description4.setVisible(isVisible);
                description5.setVisible(isVisible);
                passSpacer.setVisible(isVisible);

                length -= 70;
                innerPanel.setPreferredSize(new Dimension(400, length));
            }
        });

        passField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updatePasswordStrength();
            }
        });

        // Confirm Password label aligned left
        confirmPassLabelPanel = new JPanel();
        confirmPassLabelPanel.setLayout(new BorderLayout());
        confirmPassLabelPanel.setOpaque(false);
        confirmPassLabelPanel.setMaximumSize(new Dimension(320, 20));

        confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        confirmPassLabel.setForeground(Color.LIGHT_GRAY);
        confirmPassLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding

        confirmPassLabelPanel.add(confirmPassLabel, BorderLayout.WEST);
        innerPanel.add(confirmPassLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Confirm Password text field
        confirmPassField = new JPasswordField();
        confirmPassField.setMaximumSize(new Dimension(320, 40));
        confirmPassField.setPreferredSize(new Dimension(320, 40));
        confirmPassField.setFont(new Font("Roboto", Font.PLAIN, 14));
        confirmPassField.setBackground(new Color(32, 41, 56));
        confirmPassField.setForeground(Color.WHITE);
        confirmPassField.setCaretColor(Color.GRAY);
        confirmPassField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
        confirmPassField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassField.setText(placeholder2);
        confirmPassField.setForeground(Color.GRAY);

        //Username validity Label
        JPanel confirmPassValidPanel = new JPanel();
        JLabel confirmPassValidLabel = new JLabel("This field is mendatory");
        Component confirmPassSpacer = Box.createVerticalStrut(30);
        validityLabel(confirmPassValidPanel, confirmPassValidLabel, confirmPassSpacer);

        Component signupSpacer = Box.createVerticalStrut(20);
        signupSpacer.setVisible(true);

        confirmPassField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                confirmPassField.setBorder(new RoundedBorder(new Color(100, 149, 237), 10));
                confirmPassValidLabel.setVisible(false);
                confirmPassSpacer.setVisible(false);
                if (confirmPassField.getText().equals(placeholder2)) {
                    confirmPassField.setText("");
                    confirmPassField.setForeground(Color.WHITE);
                }
                signupSpacer.setVisible(true);
                if (isConfirmPassFieldSpaced) {
                    length -= 10;
                    innerPanel.setPreferredSize(new Dimension(400, length));
                    isConfirmPassFieldSpaced = false;
                }
                if (canProceed[3] == false) {
                    canProceed[3] = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                confirmPassField.setBorder(new RoundedBorder(new Color(50, 60, 80), 10));
                confirmPassValidLabel.setVisible(false);
                confirmPassSpacer.setVisible(false);
                if (confirmPassField.getText().isEmpty()) {
                    confirmPassField.setText(placeholder2);
                    confirmPassField.setForeground(Color.GRAY);
                }
            }
        });

        innerPanel.add(confirmPassField);
        innerPanel.add(Box.createVerticalStrut(5)); // space before next component
        innerPanel.add(confirmPassValidPanel);
        innerPanel.add(confirmPassSpacer);

        // SignUp Button
        signUpButton = new RoundedButton("Create an account");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setMaximumSize(signUpButton.getPreferredSize());

        innerPanel.add(signupSpacer);
        innerPanel.add(signUpButton);

        signUpButton.addActionListener(e -> {

            if (usernameField.getText().isEmpty() || usernameField.getText().equals(placeholder3)) {
                usernameField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                usernameValidLabel.setVisible(true);
                usernameSpacer.setVisible(true);

                if (!isUsernameFieldSpaced) {
                    length += 10;
                    isUsernameFieldSpaced = true;
                }

                if (canProceed[0]) {
                    canProceed[0] = false;
                }
            } else if (UserDataHandler.isValidUsername(usernameField.getText())) {
                usernameField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                usernameValidLabel.setText("This username is already registered");
                usernameValidLabel.setVisible(true);
                usernameSpacer.setVisible(true);

                if (!isUsernameFieldSpaced) {
                    length += 10;
                    isUsernameFieldSpaced = true;
                }

                if (canProceed[0]) {
                    canProceed[0] = false;
                }
            }

            if (emailField.getText().isEmpty() || emailField.getText().equals(placeholder)) {
                emailValidLabel.setText("This field is mendatory");
                emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                emailValidLabel.setVisible(true);
                emailSpacer.setVisible(true);

                if (!isEmailFieldSpaced) {
                    length += 10;
                    isEmailFieldSpaced = true;
                }

                if (canProceed[1]) {
                    canProceed[1] = false;
                }

            } else if (isValidEmail(emailField.getText()) == false) {
                emailValidLabel.setText("Please enter a valid email address");
                emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                emailValidLabel.setVisible(true);
                emailSpacer.setVisible(true);

                if (!isEmailFieldSpaced) {
                    length += 10;
                    isEmailFieldSpaced = true;
                }

                if (canProceed[1]) {
                    canProceed[1] = false;
                }
            } else if ((UserDataHandler.isValidEmail(emailField.getText()))) {
                emailValidLabel.setText("This email address is already registered");
                emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                emailValidLabel.setVisible(true);
                emailSpacer.setVisible(true);

                if (!isEmailFieldSpaced) {
                    length += 10;
                    isEmailFieldSpaced = true;
                }

                if (canProceed[1]) {
                    canProceed[1] = false;
                }
            }

            if (passField.getText().isEmpty() || passField.getText().equals(placeholder2)) {
                passValidLabel.setText("This field is mendatory");
                passField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                passValidLabel.setVisible(true);
                passValidPanel.setVisible(true);
                passValidSpacer.setVisible(true);

                if (!isPassFieldSpaced) {
                    length += 10;
                    isPassFieldSpaced = true;
                }

                if (canProceed[2] == true) {
                    canProceed[2] = false;
                }

            } else if ("Weakest".equals(passwordStrengthResultLabel.getText()) || "Weak".equals(passwordStrengthResultLabel.getText())) {
                passValidLabel.setText("The password is not strong");
                passField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                passValidLabel.setVisible(true);
                passValidPanel.setVisible(true);
                passValidSpacer.setVisible(true);

                if (!isPassFieldSpaced) {
                    length += 10;
                    isPassFieldSpaced = true;
                }

                if (canProceed[2] == true) {
                    canProceed[2] = false;
                }
            }

            if (confirmPassField.getText().isEmpty() || confirmPassField.getText().equals(placeholder2)) {
                confirmPassValidLabel.setText("This field is mendatory");
                confirmPassField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                confirmPassValidLabel.setVisible(true);
                confirmPassSpacer.setVisible(true);
                signupSpacer.setVisible(false);

                if (!isConfirmPassFieldSpaced) {
                    length += 10;
                    isConfirmPassFieldSpaced = true;
                }

                if (canProceed[3] == true) {
                    canProceed[3] = false;
                }

            } else if (confirmPassField.getText().equals(passField.getText()) == false) {
                confirmPassValidLabel.setText("The password does not matches");
                confirmPassField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                confirmPassValidLabel.setVisible(true);
                confirmPassSpacer.setVisible(true);
                signupSpacer.setVisible(false);

                if (!isConfirmPassFieldSpaced) {
                    length += 10;
                    isConfirmPassFieldSpaced = true;
                }

                if (canProceed[3] == true) {
                    canProceed[3] = false;
                }
            }

            canProceedVar = true;

            for (boolean proceed : canProceed) {
                if (proceed == false) {
                    canProceedVar = false;
                }
            }

            if (canProceedVar) {

                recieverEmail = emailField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passField.getText();

                generatedOTP = EmailSender.generateOTP();
                String subject = "Your Verification Code";
                String body = "Your OTP is: " + generatedOTP;

                try {
                    EmailSender.sendEmail(username, email, subject, body);
                    user = new User(username, email, password);
                    if (onSuccessCallback != null) {
                        onSuccessCallback.run();
                    }
                } catch (MessagingException ex) {
                    emailValidLabel.setText("Failed to send OTP. Check your network or email setup.");
                    emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);

                    if (!isEmailFieldSpaced) {
                        length += 10;
                        isEmailFieldSpaced = true;
                    }
                } catch (UnsupportedEncodingException ex) {
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                canDisposeSignupPage = true;

            } else {
                
            }

            innerPanel.setPreferredSize(new Dimension(400, length));
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signUpButton.setBackground(new Color(79, 70, 229)); // darker indigo
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signUpButton.setBackground(new Color(99, 102, 241));
            }
        });

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

    private void updatePasswordStrength() {
        String password = passField.getText().trim();

        if (password.isEmpty() || password.equals(placeholder2)) {
            passwordStrengthBar.setValue(0);
            passwordStrengthResultLabel.setText("");
            rule1.setForeground(Color.GRAY);
            rule2.setForeground(Color.GRAY);
            rule3.setForeground(Color.GRAY);
            rule4.setForeground(Color.GRAY);
            rule5.setForeground(Color.GRAY);
            return;
        }

        // Reset all rule colors to gray
        rule1.setForeground(Color.GRAY);
        rule2.setForeground(Color.GRAY);
        rule3.setForeground(Color.GRAY);
        rule4.setForeground(Color.GRAY);
        rule5.setForeground(Color.GRAY);

        int strength = 0;

        if (password.length() >= 8) {
            rule1.setForeground(Color.WHITE);
            strength += 20;
        }

        if (password.matches(".*[A-Z].*")) {
            rule2.setForeground(Color.WHITE);
            strength += 20;
        }

        if (password.matches(".*[a-z].*")) {
            rule3.setForeground(Color.WHITE);
            strength += 20;
        }

        if (password.matches(".*\\d.*")) {
            rule4.setForeground(Color.WHITE);
            strength += 20;
        }

        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            rule5.setForeground(Color.WHITE);
            strength += 20;
        }

        passwordStrengthBar.setValue(strength);

        switch (strength) {
            case 20 -> {
                passwordStrengthResultLabel.setText("Weakest");
                passwordStrengthBar.setForeground(new Color(200, 0, 0));
            }
            case 40 -> {
                passwordStrengthResultLabel.setText("Weak");
                passwordStrengthBar.setForeground(new Color(200, 0, 0));
            }
            case 60 -> {
                passwordStrengthResultLabel.setText("Moderate");
                passwordStrengthBar.setForeground(new Color(255, 165, 0));
            }
            case 80 -> {
                passwordStrengthResultLabel.setText("Strong");
                passwordStrengthBar.setForeground(new Color(0, 200, 0));
            }
            case 100 -> {
                passwordStrengthResultLabel.setText("Strongest");
                passwordStrengthBar.setForeground(new Color(0, 200, 0));
            }
        }
    }

    private void validityLabel(JPanel panel, JLabel label, Component spacer) {
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(320, 10));

        label.setFont(new Font("Roboto", Font.BOLD, 10));
        label.setForeground(new Color(151, 0, 0));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // small left padding
        label.setVisible(false);

        spacer.setVisible(false);

        panel.add(label, BorderLayout.WEST);
    }

    public static String getRecieverEmail() {
        return recieverEmail;
    }

    public RoundedButton getSignUpButton() {
        return signUpButton;
    }

    public static String getGeneratedOTP() {
        return generatedOTP;
    }

    public static User getUser(){
        return user;
    }
}
