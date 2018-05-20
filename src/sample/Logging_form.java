package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logging_form extends JFrame{
    private int width = 200;
    private int height = 150;

    private JButton loginButton;
    private JTextField textField_login;
    private JLabel passwordLabel;
    private JLabel loginLabel;
    private JButton backButton;
    private JPasswordField passwordField;

    public Logging_form(final Core core, final BaseOfFacts fbase, final BaseOfRules rbase){
        super("Logging");

        this.setLocation(1024 / 2 - this.width, 768 / 2 - this.height / 2);
        this.setSize(this.width, this.height);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 2, 2));

        panel.add(loginLabel);
        panel.add(textField_login);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);
        setContentPane(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean pas_flag = true;
                char[] password = {'1'};
                char[] entered_password = passwordField.getPassword();
                if (entered_password.length == 0 || textField_login.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Fill all fields", "", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if (entered_password.length == password.length){
                        for(int i=0;i<password.length;i++)
                            if (entered_password[i] != password[i]) pas_flag = false;
                    }
                    else pas_flag = false;
                    if (textField_login.getText().equals("admin") &&
                            pas_flag){
                        Add_Rule rule_form = new Add_Rule(core, fbase, rbase);
                        final Add_Fact fact_form = new Add_Fact(core, fbase, rbase, rule_form);
                        fact_form.setVisible(true);
                        setVisible(false);
                    }
                    else JOptionPane.showMessageDialog(null, "Wrong login or password", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interface main_form = new Interface(core, fbase, rbase);
                main_form.setVisible(true);
                setVisible(false);
            }
        });
    }
}
