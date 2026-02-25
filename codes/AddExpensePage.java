import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class AddExpensePage extends JFrame {

    private int userId;
    private String userName;
    private String userEmail;

    public AddExpensePage(int userId, String userName, String userEmail) {

        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;

        setTitle("Add Expense");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
               
                Color c1 = new Color(72, 160, 175);
                Color c2 = new Color(90, 185, 155);
                GradientPaint gp = new GradientPaint(0, 0, c1, w, h, c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        
        JLabel title = new JLabel("Add your expense !");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(20, 40, 80));   
        title.setBounds(100, 35, 400, 50);
        mainPanel.add(title);

        
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(460, 42, 90, 36);
        backBtn.setBackground(new Color(66, 133, 244));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { backBtn.setBackground(new Color(50, 115, 220)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { backBtn.setBackground(new Color(66, 133, 244)); }
        });
        backBtn.addActionListener(e -> {
            new HomePage(userId, userName, userEmail);
            dispose();
        });
        mainPanel.add(backBtn);

        
        JPanel container = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 248, 252));   
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        container.setOpaque(false);
        container.setBounds(65, 100, 480, 350);
        mainPanel.add(container);

        
        Color labelColor = new Color(30, 50, 90);       
        Color fieldBg    = Color.WHITE;
        Color fieldBorder= new Color(210, 220, 235);
        Font  labelFont  = new Font("Arial", Font.PLAIN, 15);

        int labelX  = 30;
        int fieldX  = 170;
        int fieldW  = 270;
        int rowH    = 32;

        
        JLabel dateLbl = new JLabel("Date");
        dateLbl.setFont(labelFont);
        dateLbl.setForeground(labelColor);
        dateLbl.setBounds(labelX, 35, 120, rowH);
        container.add(dateLbl);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setBackground(fieldBg);
        styleField(dateSpinner, fieldBorder);
        dateSpinner.setBounds(fieldX, 35, fieldW, rowH);
        container.add(dateSpinner);

        
        JLabel catLbl = new JLabel("Category");
        catLbl.setFont(labelFont);
        catLbl.setForeground(labelColor);
        catLbl.setBounds(labelX, 90, 120, rowH);
        container.add(catLbl);

        String[] categories = {
                "Select Category", "Food", "Transport",
                "Entertainment", "Bills", "Shopping", "Health", "Other"
        };
        JComboBox<String> catBox = new JComboBox<>(categories);
        catBox.setBackground(fieldBg);
        catBox.setFont(new Font("Arial", Font.PLAIN, 13));
        catBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fieldBorder, 1),
                new EmptyBorder(2, 6, 2, 6)
        ));
        catBox.setBounds(fieldX, 90, fieldW, rowH);
        container.add(catBox);

        
        JLabel descLbl = new JLabel("Description");
        descLbl.setFont(labelFont);
        descLbl.setForeground(labelColor);
        descLbl.setBounds(labelX, 145, 120, rowH);
        container.add(descLbl);

        JTextField descField = new JTextField();
        styleField(descField, fieldBorder);
        descField.setBounds(fieldX, 145, fieldW, rowH);
        container.add(descField);

        
        JLabel amtLbl = new JLabel("Amount");
        amtLbl.setFont(labelFont);
        amtLbl.setForeground(labelColor);
        amtLbl.setBounds(labelX, 200, 120, rowH);
        container.add(amtLbl);

        JTextField amtField = new JTextField();
        styleField(amtField, fieldBorder);
        amtField.setBounds(fieldX, 200, fieldW, rowH);
        container.add(amtField);

        
        JButton saveBtn = new JButton("SAVE") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        saveBtn.setBackground(new Color(72, 170, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setBorderPainted(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setBounds(130, 270, 100, 38);
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { saveBtn.setBackground(new Color(55, 150, 65)); saveBtn.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent e)  { saveBtn.setBackground(new Color(72, 170, 80)); saveBtn.repaint(); }
        });

        saveBtn.addActionListener(e -> {
            try {
                Date selectedDate = (Date) dateSpinner.getValue();
                String description = descField.getText().trim();
                String amountText  = amtField.getText().trim();

                if (description.isEmpty() || amountText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }
                if (catBox.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(this, "Please select a category!");
                    return;
                }

                double amount = Double.parseDouble(amountText);
                java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO expenses(user_id, expense_date, category, description, amount) VALUES (?, ?, ?, ?, ?)");
                pst.setInt(1, userId);
                pst.setDate(2, sqlDate);
                pst.setString(3, catBox.getSelectedItem().toString());
                pst.setString(4, description);
                pst.setDouble(5, amount);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Expense Added Successfully!");
                pst.close();
                con.close();

                new HomePage(userId, userName, userEmail);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a valid number!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        container.add(saveBtn);

        
        JButton cleanBtn = new JButton("CLEAN") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(72, 170, 80));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        cleanBtn.setForeground(new Color(72, 170, 80));
        cleanBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cleanBtn.setBorderPainted(false);
        cleanBtn.setContentAreaFilled(false);
        cleanBtn.setFocusPainted(false);
        cleanBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cleanBtn.setBounds(250, 270, 100, 38);
        cleanBtn.addActionListener(e -> {
            descField.setText("");
            amtField.setText("");
            catBox.setSelectedIndex(0);
            dateSpinner.setValue(new Date());
        });
        container.add(cleanBtn);

        mainPanel.add(container);
        add(mainPanel);
        setVisible(true);
    }

    
    private void styleField(JComponent comp, Color borderColor) {
        comp.setBackground(Color.WHITE);
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                new EmptyBorder(3, 8, 3, 8)
        ));
        comp.setFont(new Font("Arial", Font.PLAIN, 13));
    }
}