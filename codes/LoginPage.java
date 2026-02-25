import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {
    JTextField emailField;
    JPasswordField passField;

    public LoginPage() {
        setTitle("Travel Expense Tracker");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(130, 204, 221);
                Color color2 = new Color(160, 224, 231);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("TRAVEL EXPENSE TRACKER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBounds(0, 80, 600, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);

        // Login label
        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(new Color(44, 62, 80));
        loginLabel.setBounds(0, 200, 600, 30);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(loginLabel);

        // White card panel for login form
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(null);
        cardPanel.setBounds(100, 260, 400, 280);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Email field
        emailField = new JTextField("Email");
        emailField.setBounds(50, 40, 300, 45);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setForeground(Color.GRAY);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (emailField.getText().equals("Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.GRAY);
                    emailField.setText("Email");
                }
            }
        });
        cardPanel.add(emailField);

        // Password field
        passField = new JPasswordField("Password");
        passField.setBounds(50, 105, 300, 45);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setForeground(Color.GRAY);
        passField.setEchoChar((char) 0);
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(passField.getPassword()).equals("Password")) {
                    passField.setText("");
                    passField.setEchoChar('●');
                    passField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(passField.getPassword()).isEmpty()) {
                    passField.setEchoChar((char) 0);
                    passField.setForeground(Color.GRAY);
                    passField.setText("Password");
                }
            }
        });
        cardPanel.add(passField);

        // Login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(125, 175, 150, 45);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(52, 152, 219));
            }
        });
        loginBtn.addActionListener(e -> loginUser());
        cardPanel.add(loginBtn);

        mainPanel.add(cardPanel);

        // ── "Don't have an account?" label ──
        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(new Color(44, 62, 80));
        noAccountLabel.setBounds(0, 565, 600, 25);
        noAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(noAccountLabel);

        // ── Register button ──
        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(225, 597, 150, 42);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 15));
        registerBtn.setForeground(new Color(52, 152, 219));
        registerBtn.setBackground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(52, 152, 219));
                registerBtn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(Color.WHITE);
                registerBtn.setForeground(new Color(52, 152, 219));
            }
        });
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });
        mainPanel.add(registerBtn);

        add(mainPanel);
        setVisible(true);
    }

    private void loginUser() {
        String email = emailField.getText();
        if (email.equals("Email") || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email!");
            return;
        }

        String password = String.valueOf(passField.getPassword());
        if (password.equals("Password") || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT id, name, password FROM users WHERE email=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String storedPassword = rs.getString("password");
                String inputPassword = RegisterPage.hashPasswordStatic(password);

                if (storedPassword.equals(inputPassword)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose();
                    new HomePage(userId, rs.getString("name"), email);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Password!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database!");
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}