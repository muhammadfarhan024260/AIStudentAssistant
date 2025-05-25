package bukc.project;

import javax.swing.*;
import java.awt.*;

public class Login extends javax.swing.JFrame {

    private JPanel mainWrapper;
    private JPanel pageWrapper;

    public Login() {
        this.setUndecorated(true);
        initComponents();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        this.setSize(width, height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setTitle("AI assistant");
        this.isFocusable();
        this.requestFocusInWindow();

        // Setup main wrapper
        mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(new Color(17, 24, 40));

        // Add custom title bar
        mainWrapper.add(new CustomTitleBar(this), BorderLayout.NORTH);

        // Setup page wrapper (for dynamic pages like LoginPage, SignupPage etc.)
        pageWrapper = new JPanel(new BorderLayout());
        pageWrapper.setBackground(new Color(17, 24, 40));

        mainWrapper.add(pageWrapper, BorderLayout.CENTER);

        this.setContentPane(mainWrapper);

        showEmailInputPage();
//        showDashboardPage();
//        showSignupPage();
//        showLoginPage();
//        showEmailPage();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private void showDashboardPage() {

        pageWrapper.removeAll();
        pageWrapper.add((new DashboardPage()).getMainPanel(), BorderLayout.CENTER);
        pageWrapper.revalidate();
        pageWrapper.repaint();
//        this.setContentPane(new DashboardPage().getMainPanel());
        
        this.revalidate();
        this.repaint();
    }

    private void showLoginPage() {
        LoginPage loginPage = new LoginPage();

        pageWrapper.removeAll();
        pageWrapper.add(loginPage.getMainPanel(), BorderLayout.CENTER);
        pageWrapper.revalidate();
        pageWrapper.repaint();

        this.revalidate();
        this.repaint();
    }

    private void showSignupPage() {
        SignupPage signupPage = new SignupPage(() -> {
            SwingUtilities.invokeLater(() -> showEmailPage());
        });

        pageWrapper.removeAll();
        pageWrapper.add(signupPage.getMainPanel(), BorderLayout.CENTER);
        pageWrapper.revalidate();
        pageWrapper.repaint();

        this.revalidate();
        this.repaint();
    }

    private void showEmailPage() {
        EmailVerificationPage emailPage = new EmailVerificationPage();
        pageWrapper.removeAll();
        pageWrapper.add(emailPage.getMainPanel(), BorderLayout.CENTER);
        pageWrapper.revalidate();
        pageWrapper.repaint();
        this.revalidate();
        this.repaint();
    }

    private void showEmailInputPage() {
        final EmailInputPage[] emailPage = new EmailInputPage[1];

        emailPage[0] = new EmailInputPage(() -> {
            if (UserDataHandler.isValidEmail(emailPage[0].getEmail())) {
                SwingUtilities.invokeLater(() -> showLoginPage());
            } else {
                SwingUtilities.invokeLater(() -> showSignupPage());
            }
        });

        pageWrapper.removeAll();
        pageWrapper.add(emailPage[0].getMainPanel(), BorderLayout.CENTER);
        pageWrapper.revalidate();
        pageWrapper.repaint();

        this.revalidate();
        this.repaint();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
