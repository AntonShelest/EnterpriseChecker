package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame{
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int width = 300;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int height = 200;

    private JButton button1Button;
    private JButton button2Button;
    private JPanel panel1;
    private JButton aboutButton;
    private JCheckBox checkBox1;
    private JTable table1;

    public Interface(final Core core, final BaseOfFacts fbase, final BaseOfRules rbase) {

        this.setSize(this.getWidth(), this.getHeight());
        this.setLocation(1024 / 2 - this.getWidth() / 2, 768 / 2 - this.getHeight() / 2);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 3, 2, 2));
        container.add(button1Button);
        container.add(button2Button);
        container.add(aboutButton);

        button1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Company fcompany = new Company(core);
                fcompany.setVisible(true);
                setVisible(false);

            }
        });
        button2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logging_form log_form = new Logging_form(core, fbase, rbase);
                log_form.setVisible(true);
                setVisible(false);
                /*Add_Rule rule_form = new Add_Rule(core, fbase, rbase);
                final Add_Fact fact_form = new Add_Fact(core, fbase, rbase,rule_form);
                fact_form.setVisible(true);
                setVisible(false);   */
            }
        });
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "This product was created by Anton Shelest. All light reserved." +
                        " All types of illegal coping is punishable by law!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
