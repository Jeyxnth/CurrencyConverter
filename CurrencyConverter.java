import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCurrency, toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    private final Map<String, Double> rates = new HashMap<>();
    private final DecimalFormat df = new DecimalFormat("0.00");

    public CurrencyConverter() {
        setTitle("💱 Currency Converter");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Color primaryBlue = new Color(52, 152, 219);
        Color bgGray = new Color(245, 247, 250);

        // Exchange rates (relative to INR)
        rates.put("INR", 1.0);
        rates.put("USD", 0.012);
        rates.put("EUR", 0.011);
        rates.put("GBP", 0.0094);
        rates.put("JPY", 1.76);

        // Header
        JLabel title = new JLabel("Currency Converter", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(primaryBlue);
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 15));
        panel.setBorder(new EmptyBorder(10, 40, 10, 40));
        panel.setBackground(bgGray);

        panel.add(new JLabel("From Currency:"));
        fromCurrency = new JComboBox<>(rates.keySet().toArray(new String[0]));
        panel.add(fromCurrency);

        panel.add(new JLabel("To Currency:"));
        toCurrency = new JComboBox<>(rates.keySet().toArray(new String[0]));
        panel.add(toCurrency);

        panel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        panel.add(amountField);

        panel.add(new JLabel("Converted Amount:"));
        resultLabel = new JLabel("—", JLabel.LEFT);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setForeground(new Color(39, 174, 96));
        panel.add(resultLabel);

        add(panel, BorderLayout.CENTER);

        // Button
        JButton convertBtn = new JButton("Convert");
        convertBtn.setBackground(primaryBlue);
        convertBtn.setForeground(Color.WHITE);
        convertBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        convertBtn.setFocusPainted(false);
        convertBtn.setPreferredSize(new Dimension(120, 40));
        convertBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        convertBtn.addActionListener(e -> convertCurrency());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(bgGray);
        btnPanel.add(convertBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            double inINR = amount / rates.get(from);
            double converted = inINR * rates.get(to);

            resultLabel.setText(df.format(converted) + " " + to);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter a valid amount.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error converting currency: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
