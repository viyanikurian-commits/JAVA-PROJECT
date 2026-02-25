import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.Date;

public class UpdateExpensePage extends JFrame {

    private int userId;
    private String userName;
    private String userEmail;

    private JTable table;
    private DefaultTableModel model;

    private JSpinner      dateSpinner;
    private JComboBox<String> categoryBox;
    private JTextField    descriptionField;
    private JTextField    amountField;

    private int selectedExpenseId = -1;

    public UpdateExpensePage(int userId, String userName, String userEmail) {

        this.userId    = userId;
        this.userName  = userName;
        this.userEmail = userEmail;

        setTitle("Update Expense");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(72, 160, 175),
                        w, h, new Color(90, 185, 155));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        
        JLabel title = new JLabel("UPDATE EXPENSE");
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setForeground(new Color(20, 40, 80));
        title.setBounds(280, 28, 500, 48);
        mainPanel.add(title);

        
        JButton backBtn = createRoundedButton("BACK", new Color(66, 133, 244));
        backBtn.setBounds(855, 36, 90, 36);
        backBtn.addActionListener(e -> {
            new HomePage(userId, userName, userEmail);
            dispose();
        });
        mainPanel.add(backBtn);

                JPanel card = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // shadow
                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(4, 6, getWidth() - 4, getHeight() - 4, 28, 28);
                // card
                g2.setColor(new Color(246, 249, 252));
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 28, 28);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBounds(55, 95, 880, 450);
        mainPanel.add(card);

       
        // -- Date label + spinner
        Color labelColor = new Color(20, 40, 80);
        Font  labelFont  = new Font("Arial", Font.BOLD, 14);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setFont(labelFont);
        dateLabel.setForeground(labelColor);
        dateLabel.setBounds(35, 55, 50, 30);
        card.add(dateLabel);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        styleSpinner(dateSpinner);
        dateSpinner.setBounds(90, 55, 200, 32);
        card.add(dateSpinner);

        
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(labelFont);
        categoryLabel.setForeground(labelColor);
        categoryLabel.setBounds(35, 95, 80, 28);
        categoryLabel.setVisible(false);
        card.add(categoryLabel);

        String[] categories = {"Food","Transport","Entertainment","Bills","Shopping","Health","Other"};
        categoryBox = new JComboBox<>(categories);
        styleCombo(categoryBox);
        categoryBox.setBounds(120, 95, 160, 28);
        categoryBox.setVisible(false);
        card.add(categoryBox);

        
        JLabel descLabel = new JLabel("Description");
        descLabel.setFont(labelFont);
        descLabel.setForeground(labelColor);
        descLabel.setBounds(300, 95, 100, 28);
        descLabel.setVisible(false);
        card.add(descLabel);

        descriptionField = new JTextField();
        styleTextField(descriptionField);
        descriptionField.setBounds(400, 95, 200, 28);
        descriptionField.setVisible(false);
        card.add(descriptionField);

        
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(labelFont);
        amountLabel.setForeground(labelColor);
        amountLabel.setBounds(620, 95, 70, 28);
        amountLabel.setVisible(false);
        card.add(amountLabel);

        amountField = new JTextField();
        styleTextField(amountField);
        amountField.setBounds(695, 95, 140, 28);
        amountField.setVisible(false);
        card.add(amountField);

        
        JButton editBtn = createRoundedButton("EDIT", new Color(72, 170, 80));
        editBtn.setBounds(480, 55, 110, 36);
        card.add(editBtn);

        
        JButton updateBtn = createRoundedButton("UPDATE", new Color(66, 133, 244));
        updateBtn.setBounds(605, 55, 120, 36);
        card.add(updateBtn);

       
        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        model.setColumnIdentifiers(new String[]{"Exp ID","Date","Category","Description","Amount"});

        table = new JTable(model);
        table.setRowHeight(36);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 228, 240));
        table.setBackground(new Color(246, 249, 252));
        table.setSelectionBackground(new Color(210, 230, 255));
        table.setSelectionForeground(new Color(20, 40, 80));
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(new Color(50, 65, 90));
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(new Color(20, 40, 80));
        header.setBackground(new Color(246, 249, 252));
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 215, 235)));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!sel) setBackground(row % 2 == 0
                        ? new Color(246, 249, 252) : new Color(238, 244, 250));
                return this;
            }
        };
        for (int i = 0; i < 5; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(246, 249, 252));
        // Table starts below form row; leaves room for hidden extra row
        scrollPane.setBounds(20, 140, 840, 290);
        card.add(scrollPane);

        
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first!");
                return;
            }

            selectedExpenseId = (int) model.getValueAt(selectedRow, 0);

            Object dateVal = model.getValueAt(selectedRow, 1);
            if (dateVal instanceof java.sql.Date) {
                dateSpinner.setValue(new Date(((java.sql.Date) dateVal).getTime()));
            }

            categoryBox.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
            descriptionField.setText(model.getValueAt(selectedRow, 3).toString());
            amountField.setText(model.getValueAt(selectedRow, 4).toString());


            categoryLabel.setVisible(true);
            categoryBox.setVisible(true);
            descLabel.setVisible(true);
            descriptionField.setVisible(true);
            amountLabel.setVisible(true);
            amountField.setVisible(true);


            scrollPane.setBounds(20, 145, 840, 285);
            card.revalidate();
            card.repaint();
        });

      
        updateBtn.addActionListener(e -> {
            if (selectedExpenseId == -1) {
                JOptionPane.showMessageDialog(this, "Click EDIT first!");
                return;
            }
            try {
                Date selectedDate = (Date) dateSpinner.getValue();
                java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

                String category    = categoryBox.getSelectedItem().toString();
                String description = descriptionField.getText().trim();
                double amount      = Double.parseDouble(amountField.getText().trim());

                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "UPDATE expenses SET expense_date=?, category=?, description=?, amount=? WHERE id=? AND user_id=?");
                pst.setDate(1, sqlDate);
                pst.setString(2, category);
                pst.setString(3, description);
                pst.setDouble(4, amount);
                pst.setInt(5, selectedExpenseId);
                pst.setInt(6, userId);
                pst.executeUpdate();
                pst.close();
                con.close();

                JOptionPane.showMessageDialog(this, "Expense Updated Successfully!");
                selectedExpenseId = -1;

               
                categoryLabel.setVisible(false);   categoryBox.setVisible(false);
                descLabel.setVisible(false);        descriptionField.setVisible(false);
                amountLabel.setVisible(false);      amountField.setVisible(false);
                scrollPane.setBounds(20, 140, 840, 290);
                card.repaint();

                loadExpenses();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a valid number!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(mainPanel);
        loadExpenses();
        setVisible(true);
    }


    private void loadExpenses() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT id, expense_date, category, description, amount FROM expenses WHERE user_id=?");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDate("expense_date"),
                        rs.getString("category"),
                        rs.getString("description"),
                        String.format("%.2f", rs.getDouble("amount"))
                });
            }
            rs.close(); pst.close(); con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    private JButton createRoundedButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bg.darker()); btn.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(bg); btn.repaint(); }
        });
        return btn;
    }

    private void styleTextField(JTextField f) {
        f.setBackground(Color.WHITE);
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 235), 1),
                new EmptyBorder(3, 8, 3, 8)));
    }

    private void styleSpinner(JSpinner s) {
        s.setBackground(Color.WHITE);
        s.setFont(new Font("Arial", Font.PLAIN, 13));
        s.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 235), 1));
    }

    private void styleCombo(JComboBox<String> c) {
        c.setBackground(Color.WHITE);
        c.setFont(new Font("Arial", Font.PLAIN, 13));
        c.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 235), 1));
    }
}