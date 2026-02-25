import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class DeleteExpensePage extends JFrame {

    private int userId;
    private String userName;
    private String userEmail;

    private JTable table;
    private DefaultTableModel model;

    public DeleteExpensePage(int userId, String userName, String userEmail) {

        this.userId    = userId;
        this.userName  = userName;
        this.userEmail = userEmail;

        setTitle("Delete Expense");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== BACKGROUND: teal-blue → seafoam-green diagonal gradient =====
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

        
        JLabel title = new JLabel("DELETE EXPENSE");
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setForeground(new Color(20, 40, 80));
        title.setBounds(240, 28, 520, 50);
        mainPanel.add(title);

        
        JButton backBtn = createRoundedButton("BACK", new Color(0, 102, 255));
        backBtn.setBounds(760, 36, 90, 36);
        backBtn.addActionListener(e -> {
            new HomePage(userId, userName, userEmail);
            dispose();
        });
        mainPanel.add(backBtn);

       
        JPanel card = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(4, 6, getWidth() - 4, getHeight() - 4, 28, 28);
                g2.setColor(new Color(246, 249, 252));
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 28, 28);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBounds(55, 100, 800, 440);
        mainPanel.add(card);

       
        Color labelColor = new Color(20, 40, 80);
        Font  labelFont  = new Font("Arial", Font.BOLD, 14);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setFont(labelFont);
        dateLabel.setForeground(labelColor);
        dateLabel.setBounds(35, 55, 50, 30);
        card.add(dateLabel);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateSpinner.setBackground(Color.WHITE);
        dateSpinner.setFont(new Font("Arial", Font.PLAIN, 13));
        dateSpinner.setBorder(BorderFactory.createLineBorder(new Color(210, 220, 235), 1));
        dateSpinner.setBounds(90, 55, 200, 32);
        card.add(dateSpinner);

        
        JButton selectBtn = createRoundedButton("SELECT", new Color(0, 180, 80));
        selectBtn.setBounds(360, 55, 120, 36);
        card.add(selectBtn);

        
        JButton deleteBtn = createRoundedButton("DELETE", new Color(220, 30, 30));
        deleteBtn.setBounds(495, 55, 120, 36);
        card.add(deleteBtn);

       
        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        model.setColumnIdentifiers(new String[]{"Exp ID", "Date", "Category", "Description", "Amount"});

        table = new JTable(model);
        table.setRowHeight(36);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 228, 240));
        table.setBackground(new Color(246, 249, 252));
        table.setSelectionBackground(new Color(255, 180, 180));
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
        scrollPane.setBounds(20, 110, 760, 305);
        card.add(scrollPane);

        add(mainPanel);

        
        loadExpenses();

        
        selectBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row first!");
                return;
            }
            Object dateVal = model.getValueAt(selectedRow, 1);
            if (dateVal instanceof java.sql.Date) {
                dateSpinner.setValue(new java.util.Date(((java.sql.Date) dateVal).getTime()));
            }
        });

       
        deleteBtn.addActionListener(e -> deleteExpense());

        setVisible(true);
    }

   
    private void loadExpenses() {
        model.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM expenses WHERE user_id = ?");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDate("expense_date"),
                        rs.getString("category"),
                        rs.getString("description"),
                        String.format("%.2f", rs.getDouble("amount"))
                });
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading expenses: " + e.getMessage());
            e.printStackTrace();
        }
    }

      private void deleteExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
            return;
        }

        int expenseId = (int) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this expense?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "DELETE FROM expenses WHERE id = ? AND user_id = ?");
                pst.setInt(1, expenseId);
                pst.setInt(2, userId);
                pst.executeUpdate();
                pst.close();
                con.close();

                JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
                loadExpenses();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting expense: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

  
    private JButton createRoundedButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
               
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
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
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bg.darker());
                btn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
                btn.repaint();
            }
        });
        return btn;
    }

  
    public static void main(String[] args) {
        new DeleteExpensePage(1, "Test User", "test@example.com");
    }
}