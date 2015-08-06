package sample;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Company extends JFrame{
    private int width = 300;
    private int height = 400;

    private JButton getResultButton;
    private JTextField textField_revenue;
    private JTextField textField_income;
    private JTextField textField_debt;
    private JTextField textField_capital;
    private JTextField textField_cash;
    private JTextField textField_receivables;
    private JTextField textField_fixedAssets;
    private JLabel label_debt;
    private JLabel label_revenue;
    private JLabel label_income;
    private JLabel label_capital;
    private JLabel label_cash;
    private JLabel label_receivables;
    private JLabel label_fixedAssets;
    private double revenue = 0;
    private double income = 0;
    private double debt = 0;
    private double capital = 0;
    private double cash = 0;
    private double receivables = 0;
    private double fixedAssets = 0;
    private boolean exception = false;

    public Company(final Core core){
        super("Filling information about the company");

        this.setLocation(1024 / 2 - this.width / 2, 768 / 2 - this.height / 2);
        this.setSize(this.width, this.height);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));

        panel.add(label_revenue);
        panel.add(textField_revenue);
        panel.add(label_income);
        panel.add(textField_income);
        panel.add(label_debt);
        panel.add(textField_debt);
        panel.add(label_capital);
        panel.add(textField_capital);
        panel.add(label_cash);
        panel.add(textField_cash);
        panel.add(label_receivables);
        panel.add(textField_receivables);
        panel.add(label_fixedAssets);
        panel.add(textField_fixedAssets);
        panel.add(getResultButton);

        setContentPane(panel);
        getResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Field "revenue"
                exception = false;

                try {revenue = Double.parseDouble(textField_revenue.getText());
                    if (revenue < 10000){
                        JOptionPane.showMessageDialog(null, "Revenue should be not less than 10 000", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter double for Total revenue!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "income"
                try {income = Double.parseDouble(textField_income.getText());
                    if (income < - 10*revenue && revenue>0){
                        JOptionPane.showMessageDialog(null, "Losses are huge!", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                    else if (income > 0.9 * revenue && income < revenue && revenue>0)
                        JOptionPane.showMessageDialog(null, "Warning: This business is not legal! (too high income ratio, call police)", "", JOptionPane.ERROR_MESSAGE);
                    else if (income >= revenue && revenue>0){
                        JOptionPane.showMessageDialog(null, "Income < Revenue!", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter double for Total Income!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "debt"
                try {debt = Double.parseDouble(textField_debt.getText());
                    if (debt < 0){
                        JOptionPane.showMessageDialog(null, "Debt can`t be negative", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter Double for Debt!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "capital"
                try {capital = Double.parseDouble(textField_capital.getText());
                    if (capital < 0){
                        JOptionPane.showMessageDialog(null, "Capital can`t be negative", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter Double for Capital!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "revenue"
                try {cash = Double.parseDouble(textField_cash.getText());
                    if (cash < 0)  {
                        JOptionPane.showMessageDialog(null, "Cash can`t be negative", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter Double for Total cash!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "receivables"
                try {receivables = Double.parseDouble(textField_receivables.getText());
                    if (receivables < 0)   {
                        JOptionPane.showMessageDialog(null, "receivables should be not less than 0", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) { JOptionPane.showMessageDialog(null, "Enter Double for receivables!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;}

                //Field "fixedAssets"
                try {fixedAssets = Double.parseDouble(textField_fixedAssets.getText());
                    if (fixedAssets < 0) {
                        JOptionPane.showMessageDialog(null, "fixed Assets should be not less than 0", "", JOptionPane.ERROR_MESSAGE);
                        exception = true;
                    }
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter Double for fixed Assets!", "", JOptionPane.ERROR_MESSAGE);
                    exception = true;
                }

                if (!exception){
                    core.getFbase().read();
                    core.getRbase().read(core);

                    if (income/revenue > 0.01) {
                        core.getFbase().getFacts()[1].setInit(true);
                        core.getFbase().getFacts()[1].setFlag(true);
                    }
                    if (income/revenue < -0.01){
                        core.getFbase().getFacts()[1].setInit(true);
                        core.getFbase().getFacts()[1].setFlag(false);
                    }
                    if ((cash+receivables)/debt > 0.15){
                        core.getFbase().getFacts()[2].setInit(true);
                        core.getFbase().getFacts()[2].setFlag(false);
                    }
                    else{
                        core.getFbase().getFacts()[2].setInit(true);
                        core.getFbase().getFacts()[2].setFlag(true);
                    }
                    if ((debt)/capital > 0.8){
                        core.getFbase().getFacts()[3].setInit(true);
                        core.getFbase().getFacts()[3].setFlag(true);
                    }
                    else{
                        core.getFbase().getFacts()[3].setInit(true);
                        core.getFbase().getFacts()[3].setFlag(false);
                    }

                    Explain explain = new Explain(core);
                    explain.setVisible(true);
                    setVisible(false);
                }
            }
        });
    }
}
