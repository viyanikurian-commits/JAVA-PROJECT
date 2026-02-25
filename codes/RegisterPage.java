import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    JTextField nameField, emailField;
    JPasswordField passField, confirmField;
    
    public RegisterPage() {
        setTitle("Track your expense!");
        setSize(600, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        getContentPane().setBackground(new Color(152, 211, 211));
        
        
        JLabel titleLabel = new JLabel("Track your expense!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setBounds(0, 60, 600, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        
        
        JLabel createLabel = new JLabel("CREATE ACCOUNT");
        createLabel.setFont(new Font("Arial", Font.BOLD, 24));
        createLabel.setForeground(new Color(25, 55, 90));
        createLabel.setBounds(0, 180, 600, 30);
        createLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(createLabel);
        
        
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBounds(100, 240, 400, 380);
        registerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        add(registerPanel);
        
        
        nameField = new JTextField();
        nameField.setBounds(40, 30, 320, 50);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        nameField.setText("Username");
        nameField.setForeground(Color.GRAY);
        
        
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (nameField.getText().equals("Username")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("Username");
                    nameField.setForeground(Color.GRAY);
                }
            }
        });
        registerPanel.add(nameField);
        
        
        emailField = new JTextField();
        emailField.setBounds(40, 95, 320, 50);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        emailField.setText("Email");
        emailField.setForeground(Color.GRAY);
        
        
        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (emailField.getText().equals("Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Email");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
        registerPanel.add(emailField);
        
        
        passField = new JPasswordField();
        passField.setBounds(40, 160, 320, 50);
        passField.setFont(new Font("Arial", Font.PLAIN, 16));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passField.setEchoChar((char) 0);
        passField.setText("Password");
        passField.setForeground(Color.GRAY);
        
       
        passField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(passField.getPassword()).equals("Password")) {
                    passField.setText("");
                    passField.setForeground(Color.BLACK);
                    passField.setEchoChar('●');
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(passField.getPassword()).isEmpty()) {
                    passField.setEchoChar((char) 0);
                    passField.setText("Password");
                    passField.setForeground(Color.GRAY);
                }
            }
        });
        registerPanel.add(passField);
        
        
        confirmField = new JPasswordField();
        confirmField.setBounds(40, 225, 320, 50);
        confirmField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        confirmField.setEchoChar((char) 0);
        confirmField.setText("Confirm Password");
        confirmField.setForeground(Color.GRAY);
        
        
        confirmField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(confirmField.getPassword()).equals("Confirm Password")) {
                    confirmField.setText("");
                    confirmField.setForeground(Color.BLACK);
                    confirmField.setEchoChar('●');
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(confirmField.getPassword()).isEmpty()) {
                    confirmField.setEchoChar((char) 0);
                    confirmField.setText("Confirm Password");
                    confirmField.setForeground(Color.GRAY);
                }
            }
        });
        registerPanel.add(confirmField);
        
        
        JButton registerBtn = new JButton("CREATE ACCOUNT");
        registerBtn.setBounds(100, 300, 200, 50);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setBackground(new Color(52, 152, 219));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
       
        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(52, 152, 219));
            }
        });
        
        registerBtn.addActionListener(e -> registerUser());
        registerPanel.add(registerBtn);
        
        
        JButton backBtn = new JButton("← Back to Login");
        backBtn.setBounds(200, 650, 200, 40);
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setBackground(new Color(152, 211, 211));
        backBtn.setForeground(new Color(25, 55, 90));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setForeground(new Color(52, 152, 219));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setForeground(new Color(25, 55, 90));
            }
        });
        
        backBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });
        add(backBtn);
        
        setVisible(true);
    }
    
   
    public static String hashPasswordStatic(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void registerUser() {
        
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());
        
        
        if (name.equals("Username") || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!");
            return;
        }
        
        if (email.equals("Email") || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email!");
            return;
        }
        
        if (pass.equals("Password") || pass.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password!");
            return;
        }
        
        if (confirm.equals("Confirm Password") || confirm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please confirm your password!");
            return;
        }
        
        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, hashPasswordStatic(pass)); // hashed password
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registered Successfully!");
            con.close();
            dispose();
            new LoginPage();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
}