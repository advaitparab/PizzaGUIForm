import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PizzaGUIFrame extends JFrame {
    private final JTextArea receiptArea;
    private final JComboBox<String> sizeComboBox;
    private final JRadioButton thinCrust, regularCrust, deepDishCrust;
    private final JCheckBox[] toppings;
    private final ButtonGroup crustGroup;

    private final double[] sizePrices = {8.00, 12.00, 16.00, 20.00};

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust Panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(new TitledBorder("Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");
        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder("Size"));
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(new TitledBorder("Toppings"));
        String[] toppingNames = {"Pepperoni", "Mushrooms", "Sausage", "Pineapple", "Bacon", "Onions"};
        toppings = new JCheckBox[toppingNames.length];
        for (int i = 0; i < toppingNames.length; i++) {
            toppings[i] = new JCheckBox(toppingNames[i]);
            toppingsPanel.add(toppings[i]);
        }

        // Receipt Panel
        JPanel receiptPanel = new JPanel();
        receiptArea = new JTextArea(10, 40);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        receiptPanel.add(scrollPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        orderButton.addActionListener(e -> generateReceipt());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Layout
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(crustPanel);
        topPanel.add(sizePanel);
        topPanel.add(toppingsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(receiptPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateReceipt() {
        StringBuilder receipt = new StringBuilder("=========================================\n");

        String crust = "";
        if (thinCrust.isSelected()) crust = "Thin";
        else if (regularCrust.isSelected()) crust = "Regular";
        else if (deepDishCrust.isSelected()) crust = "Deep-dish";

        if (crust.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a crust.");
            return;
        }

        int sizeIndex = sizeComboBox.getSelectedIndex();
        double basePrice = sizePrices[sizeIndex];
        String size = (String) sizeComboBox.getSelectedItem();

        int toppingCount = 0;
        StringBuilder toppingList = new StringBuilder();
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                toppingList.append(topping.getText()).append("\n");
                toppingCount++;
            }
        }

        if (toppingCount == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one topping.");
            return;
        }

        double toppingCost = toppingCount * 1.00;
        double subtotal = basePrice + toppingCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        receipt.append(String.format("Crust: %s\n", crust));
        receipt.append(String.format("Size: %s - $%.2f\n", size, basePrice));
        receipt.append(String.format("Toppings (%d):\n%s", toppingCount, toppingList));
        receipt.append(String.format("Sub-total: $%.2f\n", subtotal));
        receipt.append(String.format("Tax: $%.2f\n", tax));
        receipt.append("--------------------------------------------------\n");
        receipt.append(String.format("Total: $%.2f\n", total));
        receipt.append("=========================================\n");

        receiptArea.setText(receipt.toString());
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        receiptArea.setText("");
    }
}
