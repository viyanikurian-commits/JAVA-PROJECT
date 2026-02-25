import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewExpensesPage extends JFrame {

    private int userId;
    private String userName;
    private String userEmail;

    private JTable table;
    private DefaultTableModel model;
    private JLabel emptyLabel;

    public ViewExpensesPage(int userId, String userName, String userEmail) {

        this.userId   = userId;
        this.userName = userName;
        this.userEmail = userEmail;

        setTitle("View Expenses");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0,    new Color(72, 160, 175),   
                        w, h,    new Color(90, 185, 155));  
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        
        JLabel title = new JLabel("VIEW EXPENSES");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(20, 40, 80));   
        title.setBounds(240, 30, 500, 55);
        mainPanel.add(title);

        JButton backBtn = new JButton("BACK") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        backBtn.setBackground(new Color(66, 133, 244));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBounds(755, 42, 90, 36);
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { backBtn.setBackground(new Color(50, 115, 220)); backBtn.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent e)  { backBtn.setBackground(new Color(66, 133, 244)); backBtn.repaint(); }
        });
        backBtn.addActionListener(e -> {
            new HomePage(userId, userName, userEmail);
            dispose();
        });
        mainPanel.add(backBtn);

        
        JPanel container = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Soft shadow
                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(4, 6, getWidth() - 4, getHeight() - 4, 28, 28);
                // White card
                g2.setColor(new Color(246, 249, 252));
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 28, 28);
                g2.dispose();
            }
        };
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        container.setBounds(80, 110, 740, 370);

        
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        model.setColumnIdentifiers(new String[]{
                "Expense ID", "Date", "Category", "Description", "Amount"
        });

        table = new JTable(model);
        table.setRowHeight(38);
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
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!isSelected) {
                    setBackground(row % 2 == 0
                            ? new Color(246, 249, 252)
                            : new Color(238, 244, 250));
                }
                return this;
            }
        };
        for (int i = 0; i < 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(246, 249, 252));

        container.add(scrollPane, BorderLayout.CENTER);

        emptyLabel = new JLabel("No expenses added yet", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emptyLabel.setForeground(new Color(100, 120, 150));
        emptyLabel.setVisible(false);
        emptyLabel.setBorder(new EmptyBorder(10, 0, 6, 0));
        container.add(emptyLabel, BorderLayout.SOUTH);

        mainPanel.add(container);
        add(mainPanel);

        loadExpenses();

        setVisible(true);
    }

    
    private void loadExpenses() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT id, expense_date, category, description, amount " +
                    "FROM expenses WHERE user_id = ? ORDER BY expense_date DESC");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getDate("expense_date"),
                        rs.getString("category"),
                        rs.getString("description"),
                        String.format("%.2f", rs.getDouble("amount"))
                });
            }

            emptyLabel.setVisible(!hasData);

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading expenses: " + e.getMessage());
        }
    }
}