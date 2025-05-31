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
//        pageWrapper = new JPanel(new BorderLayout());
        pageWrapper = new JPanel(null);
        pageWrapper.setBackground(new Color(17, 24, 40));

        mainWrapper.add(pageWrapper, BorderLayout.CENTER);

        this.setContentPane(mainWrapper);

        showEmailInputPage();
//        showDashboardPage();
//        showSignupPage();
//        showLoginPage();
//        showEmailPage();
//        showForgetPasswordPage();
        this.setVisible(true);

        UserDataHandler.displayUsers();
//        UserDataHandler.deleteAllUsers();
        System.out.println("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>                        

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

        DashboardPage dashboard = new DashboardPage(
                () -> SwingUtilities.invokeLater(() -> showLoginPage()),
                () -> SwingUtilities.invokeLater(() -> showEmailInputPage())
        );

//        pageWrapper.removeAll();
//        pageWrapper.add(dashboard.getMainPanel(), BorderLayout.CENTER);
//        pageWrapper.revalidate();
//        pageWrapper.repaint();
        animatePanelTransition(dashboard.getMainPanel());

        this.revalidate();
        this.repaint();
    }

    private void showLoginPage() {
        LoginPage loginPage = new LoginPage(
                () -> SwingUtilities.invokeLater(() -> showForgetPasswordPage()),
                () -> SwingUtilities.invokeLater(() -> showDashboardPage())
        );
//        pageWrapper.removeAll();
//        pageWrapper.add(loginPage.getMainPanel(), BorderLayout.CENTER);
//        pageWrapper.revalidate();
//        pageWrapper.repaint();
        animatePanelTransition(loginPage.getMainPanel());
        this.revalidate();
        this.repaint();
    }

    private void showSignupPage() {
        SignupPage signupPage = new SignupPage(() -> {
            SwingUtilities.invokeLater(() -> showEmailPage());
        });

//        pageWrapper.removeAll();
//        pageWrapper.add(signupPage.getMainPanel(), BorderLayout.CENTER);
//        pageWrapper.revalidate();
//        pageWrapper.repaint();
        animatePanelTransition(signupPage.getMainPanel());

        this.revalidate();
        this.repaint();
    }

    private void showEmailPage() {
        EmailVerificationPage emailverification = new EmailVerificationPage(() -> {
            SwingUtilities.invokeLater(() -> showLoginPage());
        });
//        pageWrapper.removeAll();
//        pageWrapper.add(emailverification.getMainPanel(), BorderLayout.CENTER);
//        pageWrapper.revalidate();
//        pageWrapper.repaint();
        animatePanelTransition(emailverification.getMainPanel());
        this.revalidate();
        this.repaint();
    }

    private void showForgetPasswordPage() {
        ForgetPasswordPage forgetPassword = new ForgetPasswordPage(() -> {
            SwingUtilities.invokeLater(() -> showLoginPage());
        });
//        pageWrapper.removeAll();
//        pageWrapper.add(forgetPassword.getMainPanel(), BorderLayout.CENTER);
//        pageWrapper.revalidate();
//        pageWrapper.repaint();
        animatePanelTransition(forgetPassword.getMainPanel());
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

        if (UserSession.invokedThroughDashboard) {
            animatePanelTransition(emailPage[0].getMainPanel());
        } else {
            pageWrapper.removeAll();
            pageWrapper.add(emailPage[0].getMainPanel(), BorderLayout.CENTER);
            pageWrapper.revalidate();
            pageWrapper.repaint();
        }

        this.revalidate();
        this.repaint();

    }

    private void animatePanelTransition(JPanel newPanel) {
        int width = pageWrapper.getWidth();
        int height = pageWrapper.getHeight();

        // Current visible panel (if any)
        JPanel current = (pageWrapper.getComponentCount() > 0) ? (JPanel) pageWrapper.getComponent(0) : null;

        // Position the new panel off-screen to the right
        newPanel.setBounds(width, 0, width, height);
        pageWrapper.add(newPanel);
        pageWrapper.setComponentZOrder(newPanel, 0); // Bring to front

        Timer timer = new Timer(2, null);
        timer.addActionListener(e -> {
            Point pNew = newPanel.getLocation();
            Point pOld = (current != null) ? current.getLocation() : null;

            // Stop when the new panel reaches x = 0
            if (pNew.x <= 0) {
                newPanel.setLocation(0, 0);
                if (current != null) {
                    pageWrapper.remove(current);
                }
                pageWrapper.revalidate();
                pageWrapper.repaint();
                timer.stop();
            } else {
                // Move new panel left
                newPanel.setLocation(pNew.x - 25, 0);

                // Move old panel left too
                if (current != null) {
                    current.setLocation(pNew.x - width - 25, 0);
                }
            }
        });

        timer.start();
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
