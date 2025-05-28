package bukc.project;

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

public class LoginPage {

    private JPanel bgPanel;
    private JPanel innerPanel;
    private JPanel emailLabelPanel;
    private JPanel emailValidLabelPanel;
    private JPanel passLabelPanel;

    private JLabel jLabel1;
    private JLabel emailValidLabel;
    private JLabel passLabel;
    private JLabel subLabel;
    private JLabel emailLabel;

    private JTextField passField;
    private JTextField emailField;

    private Component emailSpacer;

    private RoundedButton signInButton;

    private String placeholder = "Enter your email or username";
    private String placeholder2 = "PaSsWoRd";

    private int length = 420;

    private boolean isEmailFieldSpaced = false;
    private boolean isPassFieldSpaced = false;
    private static boolean showForgetPasswordPage = false;
    private static boolean showDashboardPage = true;
    private boolean[] canProceed = new boolean[2];

    private Runnable forgetPassword;
    private Runnable dashboardPage;

    public LoginPage(Runnable forgetPassword, Runnable dashboardPage) {

        this.forgetPassword = forgetPassword;
        this.dashboardPage = dashboardPage;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        bgPanel = new JPanel();

        subLabel = new JLabel("Sign in to your account to continue");
        subLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        subLabel.setForeground(new Color(180, 180, 180)); // soft gray
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        jLabel1 = new JLabel();
        jLabel1.setText("Welcome back");
        jLabel1.setFont(new Font("Roboto", Font.BOLD, 30));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT); // very important for BoxLayout

        innerPanel = new RoundedPanel(30);
        innerPanel.setBackground(new Color(32, 41, 56));
        innerPanel.setPreferredSize(new Dimension(400, length));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.isFocusable();
        innerPanel.add(Box.createVerticalStrut(40)); // padding at the top
        innerPanel.add(jLabel1);
        innerPanel.add(subLabel);
        innerPanel.add(Box.createVerticalStrut(40)); // spacing after label

        // Email label aligned left
        emailLabelPanel = new JPanel();
        emailLabelPanel.setLayout(new BorderLayout());
        emailLabelPanel.setOpaque(false);
        emailLabelPanel.setMaximumSize(new Dimension(320, 20));
        emailLabelPanel.setBackground(Color.red);

        emailLabel = new JLabel("Email / Username");
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
        emailValidLabelPanel.setMaximumSize(new Dimension(320, 20));

        emailValidLabel = new JLabel("Please enter a valid email address");
        emailValidLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        emailValidLabel.setForeground(new Color(151, 0, 0));
        emailValidLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding
        emailValidLabel.setVisible(false);

        emailValidLabelPanel.add(emailValidLabel, BorderLayout.WEST);
        innerPanel.add(emailValidLabelPanel);

        emailSpacer = Box.createVerticalStrut(20);
        emailSpacer.setVisible(false);
        innerPanel.add(emailSpacer);

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

                passValidLabel.setVisible(false);
                passValidSpacer.setVisible(false);

                if (isPassFieldSpaced) {
                    length -= 10;
                    innerPanel.setPreferredSize(new Dimension(400, length));
                    isPassFieldSpaced = false;
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
        innerPanel.add(passValidSpacer);
        innerPanel.add(passValidPanel);
//        innerPanel.add(Box.createVerticalStrut(5)); // space before next component
//        innerPanel.add(Box.createVerticalStrut(5)); // space before next component

        // Resend label aligned left
        JPanel resendLabelPanel = new JPanel();
        resendLabelPanel.setLayout(new BorderLayout());
        resendLabelPanel.setOpaque(false);
        resendLabelPanel.setMaximumSize(new Dimension(320, 20));
//        resendLabelPanel.setFocusable(true);

        JLabel resendLabel = new JLabel("Forgot password");
        resendLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        resendLabel.setForeground(new Color(27, 90, 156));
        resendLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0)); // small left padding
        resendLabel.setFocusable(true);

        resendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resendLabel.setForeground(new Color(79, 70, 229)); // darker indigo
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resendLabel.setForeground(new Color(27, 90, 156));
            }
        });

        resendLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (!showForgetPasswordPage) {
                    showForgetPasswordPage = true;
                }
                if (forgetPassword != null) {
                    forgetPassword.run();
                }
            }
        });

        resendLabelPanel.add(resendLabel, BorderLayout.WEST);
        innerPanel.add(resendLabelPanel);
        innerPanel.add(Box.createVerticalStrut(5));

        // Login Button
        signInButton = new RoundedButton("Sign in");
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
            if (emailField.getText().isEmpty() || emailField.getText().equals(placeholder)) {
                emailValidLabel.setText("This field is mendatory");
                emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                emailValidLabel.setVisible(true);
                emailSpacer.setVisible(true);

                if (!isEmailFieldSpaced) {
                    length += 10;
                    isEmailFieldSpaced = true;
                }

                if (canProceed[0]) {
                    canProceed[0] = false;
                }

            } else if (isValidEmail(emailField.getText())) {
                if (!UserDataHandler.isValidEmail(emailField.getText())) {
                    emailValidLabel.setText("This email is not registered");
                    emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                    emailValidLabel.setVisible(true);
                    emailSpacer.setVisible(true);

                    if (!isEmailFieldSpaced) {
                        length += 10;
                        isEmailFieldSpaced = true;
                    }

                    if (canProceed[0]) {
                        canProceed[0] = false;
                    }

                } else {
                    if (!canProceed[0]) {
                        canProceed[0] = true;
                    }
                }
            } else if (!UserDataHandler.isValidUsername(emailField.getText())) {
                emailValidLabel.setText("This username is not registered");
                emailField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                emailValidLabel.setVisible(true);
                emailSpacer.setVisible(true);

                if (!isEmailFieldSpaced) {
                    length += 10;
                    isEmailFieldSpaced = true;
                }

                if (canProceed[0]) {
                    canProceed[0] = false;
                }
            } else {
                if (!canProceed[0]) {
                    canProceed[0] = true;
                }
            }

            if (passField.getText().isEmpty() || passField.getText().equals(placeholder2)) {
                passValidLabel.setText("This field is mendatory");
                passField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                passValidLabel.setVisible(true);
                passValidSpacer.setVisible(true);

                if (!isPassFieldSpaced) {
                    length += 10;
                    isPassFieldSpaced = true;
                }

                if (canProceed[1]) {
                    canProceed[1] = false;
                }
            } else if (isValidEmail(emailField.getText())) {
                if (!(UserDataHandler.isPassCorrectByEmail(emailField.getText(), passField.getText()))) {
                    passValidLabel.setText("The password is incorrect");
                    passField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                    passValidLabel.setVisible(true);
                    passValidSpacer.setVisible(true);

                    if (!isPassFieldSpaced) {
                        length += 10;
                        isPassFieldSpaced = true;
                    }

                    if (canProceed[1]) {
                        canProceed[1] = false;
                    }
                } else {
                    if (!canProceed[1]) {
                        canProceed[1] = true;
                    }
                }
            } else if (!(UserDataHandler.isPassCorrectByUser(emailField.getText(), passField.getText()))) {
                passValidLabel.setText("The password is incorrect");
                passField.setBorder(new RoundedBorder(new Color(150, 0, 0), 10));
                passValidLabel.setVisible(true);
                passValidSpacer.setVisible(true);

                if (!isPassFieldSpaced) {
                    length += 10;
                    isPassFieldSpaced = true;
                }

                if (canProceed[1]) {
                    canProceed[1] = false;
                }
            } else {
                if (!canProceed[1]) {
                    canProceed[1] = true;
                }
            }

            innerPanel.setPreferredSize(new Dimension(400, length));

            for (boolean proceed : canProceed) {
                if (!proceed) {
                    showDashboardPage = false;
                } else {
                    showDashboardPage = true;
                }
            }

            if (showDashboardPage) {
                if (dashboardPage != null) {
                    dashboardPage.run();
                }
            }

        });

        innerPanel.add(Box.createVerticalStrut(20));
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

    private void validityLabel(JPanel panel, JLabel label, Component spacer) {
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(320, 12));

        label.setFont(new Font("Roboto", Font.BOLD, 10));
        label.setForeground(new Color(151, 0, 0));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // small left padding
        label.setVisible(false);

        spacer.setVisible(false);

        panel.add(label, BorderLayout.WEST);
    }

    public static boolean ShowForgetPasswordPage() {
        return showForgetPasswordPage;
    }

    public static boolean ShowDashboardPage() {
        return showDashboardPage;
    }

}
