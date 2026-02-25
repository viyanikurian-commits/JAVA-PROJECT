import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    private int userId;
    private String userName;
    private String userEmail;

    
    public HomePage(int userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        initializeUI();
    }

    
    public HomePage() {
        this.userId = 0;
        this.userName = "Guest";
        this.userEmail = "Not Available";
        initializeUI();
    }

    
    private void initializeUI() {

        setTitle("Travel Expense Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        Color bgColor = new Color(160, 205, 210);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        JLabel title = new JLabel("TRAVEL EXPENSE TRACKER", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 30, 30));

        titlePanel.add(title, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(bgColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Subtitle "TRACK HERE !"
        JLabel subtitle = new JLabel("TRACK HERE !", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.BOLD, 18));
        subtitle.setForeground(new Color(60, 80, 80));
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        centerPanel.add(subtitle, BorderLayout.NORTH);

        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 30, 80));

        Color addColor    = new Color(0, 200, 100);   
        Color viewColor   = new Color(0, 150, 255);   
        Color updateColor = new Color(255, 180, 0);   
        Color deleteColor = new Color(255, 70, 70);   
        JButton addExpense    = createButton("ADD EXPENSE",    "+",  addColor);
        JButton viewExpense   = createButton("VIEW EXPENSE",   "≡",  viewColor);
        JButton updateExpense = createButton("UPDATE EXPENSE", "↺",  updateColor);
        JButton deleteExpense = createButton("DELETE EXPENSE", "🗑", deleteColor);

        addExpense.addActionListener(e -> {
            new AddExpensePage(userId, userName, userEmail);
            dispose();
        });

        viewExpense.addActionListener(e -> {
            new ViewExpensesPage(userId, userName, userEmail);
            dispose();
        });

        updateExpense.addActionListener(e -> {
            new UpdateExpensePage(userId, userName, userEmail);
            dispose();
        });

        deleteExpense.addActionListener(e -> {
            new DeleteExpensePage(userId, userName, userEmail);
            dispose();
        });

        buttonPanel.add(addExpense);
        buttonPanel.add(viewExpense);
        buttonPanel.add(updateExpense);
        buttonPanel.add(deleteExpense);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(bgColor);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        
        Color logoutColor = new Color(180, 50, 50); // dark red

        JButton logoutBtn = new JButton("⏻  LOGOUT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        logoutBtn.setBackground(logoutColor);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setOpaque(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setPreferredSize(new Dimension(130, 38));

        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(logoutColor.darker());
                logoutBtn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(logoutColor);
                logoutBtn.repaint();
            }
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPage();
            }
        });

        
        Color profileColor = new Color(0, 102, 255);

        JButton profileBtn = new JButton("PROFILE") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        profileBtn.setBackground(profileColor);
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setFont(new Font("Arial", Font.BOLD, 14));
        profileBtn.setOpaque(false);
        profileBtn.setContentAreaFilled(false);
        profileBtn.setBorderPainted(false);
        profileBtn.setFocusPainted(false);
        profileBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileBtn.setPreferredSize(new Dimension(110, 38));

        profileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileBtn.setBackground(profileColor.darker());
                profileBtn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileBtn.setBackground(profileColor);
                profileBtn.repaint();
            }
        });

        profileBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Name: " + userName +
                    "\nEmail: " + userEmail,
                    "User Profile",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        bottomPanel.add(logoutBtn,  BorderLayout.WEST);
        bottomPanel.add(profileBtn, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    
    private JButton createButton(String text, String icon, Color color) {

        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Rounded background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                // Icon circle (slightly darker shade)
                Color iconBg = color.darker();
                g2.setColor(iconBg);
                int circleSize = (int)(getHeight() * 0.55);
                int circleX = 18;
                int circleY = (getHeight() - circleSize) / 2;
                g2.fillOval(circleX, circleY, circleSize, circleSize);

                // Icon text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                int iconTextX = circleX + (circleSize - fm.stringWidth(icon)) / 2;
                int iconTextY = circleY + (circleSize + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(icon, iconTextX, iconTextY);

                // Main button text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                fm = g2.getFontMetrics();
                int textX = circleX + circleSize + 14;
                int textY = getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent();
                g2.drawString(text, textX, textY);

                g2.dispose();
            }
        };

        button.setBackground(color);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 80));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.repaint();
            }
        });

        return button;
    }
}