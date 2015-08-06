package sample;

import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.JList;

public class Add_Rule extends JFrame{
    private int width = 300;
    private int height = 500;
    private int l_part_dim = 0;
    private Fact[] l_part = new Fact[0];
    private Fact r_part;
    private boolean l_part_flag = false;

    private JButton addFactInLeftButton;
    private JButton enterRightPartButton;
    private JTextField textField_id_l;
    private JTextField textField_id_r;
    private JCheckBox isLeftFactTrueCheckBox;
    private JCheckBox isRightFactTrueCheckBox;
    private JButton addRuleButton;
    private JButton eraseRuleButton;
    private JList list_of_rules;
    private JTextField textField_left_part;
    private JTextField textField_right_part;
    private JScrollPane scrollPane_List;
    private JTextField textField_add_id;
    private JTextField textField_erase_rule;

    public Add_Rule(final Core core, final Base_of_facts fbase, final Base_of_rules rbase){
        super("Editing the base of ruless");

        this.setLocation(1024 / 2, 768 / 2 - this.height / 2);
        this.setSize(this.width, this.height);

        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        //Drawing form
        final JButton addRuleButton = new JButton("Add rule");
        addRuleButton.setSize(120, 30);
        addRuleButton.setLocation(20,10);
        addRuleButton.setEnabled(false);
        panel.add(addRuleButton);

        final JTextField textField_add_id = new JTextField();
        textField_add_id.setSize(30, 20);
        textField_add_id.setLocation(110, 45);
        textField_add_id.setEditable(false);
        panel.add(textField_add_id);

        final JLabel label_id = new JLabel("Enter id for add");
        label_id.setSize(120,20);
        label_id.setLocation(20,45);
        panel.add(label_id);

        final JTextField textField_erase_rule = new JTextField();
        textField_erase_rule.setSize(30, 20);
        textField_erase_rule.setLocation(250, 45);
        panel.add(textField_erase_rule);

        final JLabel label_erase_numb = new JLabel("Enter id for erase");
        label_erase_numb.setSize(150,10);
        label_erase_numb.setLocation(150,45);
        panel.add(label_erase_numb);

        final JButton eraseRuleButton = new JButton("Erase rule");
        eraseRuleButton.setSize(130, 30);
        eraseRuleButton.setLocation(150,10);
        panel.add(eraseRuleButton);

        final JButton addFactInLeftButton = new JButton("Add fact in left part");
        addFactInLeftButton.setSize(120, 20);
        addFactInLeftButton.setLocation(20,70);
        panel.add(addFactInLeftButton);

        final JButton enterRightPartButton = new JButton("Enter right part");
        enterRightPartButton.setSize(120, 20);
        enterRightPartButton.setLocation(160,70);
        panel.add(enterRightPartButton);

        final JTextField textField_id_l = new JTextField();
        textField_id_l.setSize(30, 20);
        textField_id_l.setLocation(20,95);
        panel.add(textField_id_l);

        final JCheckBox isLeftFactTrueCheckBox = new JCheckBox();
        isLeftFactTrueCheckBox.setSize(20, 20);
        isLeftFactTrueCheckBox.setLocation(100,95);
        panel.add(isLeftFactTrueCheckBox);

        final JTextField textField_id_r = new JTextField();
        textField_id_r.setSize(30, 20);
        textField_id_r.setLocation(160,95);
        panel.add(textField_id_r);

        final JLabel label_true_l = new JLabel("Is true?");
        label_true_l.setSize(60,20);
        label_true_l.setLocation(50,95);
        panel.add(label_true_l);

        final JCheckBox isRightFactTrueCheckBox = new JCheckBox();
        isRightFactTrueCheckBox.setSize(20, 20);
        isRightFactTrueCheckBox.setLocation(240,95);
        panel.add(isRightFactTrueCheckBox);

        final JLabel label_true_r = new JLabel("Is true?");
        label_true_r.setSize(60,20);
        label_true_r.setLocation(190,95);
        panel.add(label_true_r);

        final JTextField textField_left_part = new JTextField();
        textField_left_part.setSize(120, 20);
        textField_left_part.setLocation(20, 120);
        textField_left_part.setEditable(false);
        panel.add(textField_left_part);

        final JLabel label_implication = new JLabel("|=");
        label_implication.setSize(20,20);
        label_implication.setLocation(145,120);
        panel.add(label_implication);

        final JTextField textField_right_part = new JTextField();
        textField_right_part.setSize(70, 20);
        textField_right_part.setLocation(160,120);
        textField_right_part.setEditable(false);
        panel.add(textField_right_part);

        final JList list_of_rules = new JBList();
        list_of_rules.setSize(250,200);
        list_of_rules.setLocation(20,150);
        panel.add(list_of_rules);

        final JScrollPane scrollPane_List = new JScrollPane();
        scrollPane_List.setSize(250,200);
        scrollPane_List.setLocation(20,150);
        panel.add(scrollPane_List);

        setContentPane(panel);

        textField_id_l.setText("");

        //Считываем базу
        core.getFbase().read();
        core.getRbase().read(core);

        //Output rules into list

        list_of_rules.setListData(rbase.getList());

        scrollPane_List.getViewport().setView(list_of_rules);
        addRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addFactInLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;

                int id = -1;
                try {id = Integer.parseInt(textField_id_l.getText());
                    if (id >= 0){
                        for(int i=0;i<fbase.getFacts().length;i++)
                            if (fbase.getFacts()[i].getId() == id){
                                id_flag = true;

                                Fact[] new_l_part = new Fact[l_part.length+1];
                                for(int j=0;j<l_part.length;j++)
                                    new_l_part[j] = new Fact(l_part[j]);

                                l_part = new Fact[l_part.length+1];
                                for(int j=0;j<l_part.length-1;j++)
                                    l_part[j] = new Fact(new_l_part[j]);

                                l_part[l_part.length-1] = new Fact(fbase.getFacts()[i]);
                                //l_part[l_part.length-1].setInit(true);
                                l_part[l_part.length-1].setFlag(isLeftFactTrueCheckBox.isSelected());
                                if (isLeftFactTrueCheckBox.isSelected())
                                    textField_left_part.setText(textField_left_part.getText()
                                    + "A" + fbase.getFacts()[i].getId() + ", ");
                                else textField_left_part.setText(textField_left_part.getText()
                                        + "~A" + fbase.getFacts()[i].getId() + ", ");
                                l_part_flag = true;
                            }
                        if (!id_flag) JOptionPane.showMessageDialog(null,
                                "No fact with such id, sorry", "", JOptionPane.ERROR_MESSAGE);
                    }
                    else {JOptionPane.showMessageDialog(null,
                            "Id of the rule can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                        id_flag = true;
                    }
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                    id_flag = true;
                }
            }
        });
        enterRightPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;
                if (l_part_flag){
                    int id = -1;
                    try {id = Integer.parseInt(textField_id_r.getText());
                        if (id >= 0){
                            for(int i=0;i<fbase.getFacts().length;i++)
                                if (fbase.getFacts()[i].getId() == id){
                                    id_flag = true;

                                    r_part = new Fact(fbase.getFacts()[i].getStatement(),
                                            fbase.getFacts()[i].isFlag(),fbase.getFacts()[i].isInit(),
                                            fbase.getFacts()[i].getId());
                                    //r_part.setInit(true);
                                    r_part.setFlag(isRightFactTrueCheckBox.isSelected());
                                    if (isRightFactTrueCheckBox.isSelected())
                                        textField_right_part.setText("A" + fbase.getFacts()[i].getId());
                                    else textField_right_part.setText("~A" + fbase.getFacts()[i].getId());
                                }
                            if (!id_flag) JOptionPane.showMessageDialog(null,
                                    "No fact with such id, sorry", "", JOptionPane.ERROR_MESSAGE);
                        }
                        else {JOptionPane.showMessageDialog(null,
                                "Id of the rule can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                            id_flag = true;
                        }
                    }
                    catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                        id_flag = true;
                    }
                }
                else JOptionPane.showMessageDialog(null, "Enter left part, please", "", JOptionPane.ERROR_MESSAGE);
                addRuleButton.setEnabled(true);
                textField_add_id.setEditable(true);
                textField_id_l.setText("");
            }
        });
        addRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;

                int id = -1;  int ewwwe = fbase.getFacts()[0].getId();
                try {
                    id = Integer.parseInt(textField_add_id.getText());
                    if (id >= 0) {
                        for (int i = 0; i < rbase.getRules().length; i++)
                            if (rbase.getRules()[i].getId() == id) {
                                id_flag = true;
                                JOptionPane.showMessageDialog(null,
                                        "Rule with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
                            }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Id of the rule can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                        id_flag = true;
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                    id_flag = true;
                }

                if (id_flag == false) {
                    rbase.add_rule(l_part, r_part, id);
                    l_part = new Fact[0];
                    textField_left_part.setText("");
                    textField_right_part.setText("");
                    list_of_rules.setListData(rbase.getList());

                    addRuleButton.setEnabled(false);
                    textField_add_id.setEditable(false);
                    textField_add_id.setText("");
                    textField_id_l.setText("");
                    textField_id_r.setText("");
                    l_part_flag = false;
                    //isLeftFactTrueCheckBox.setSelected(false);
                    //isRightFactTrueCheckBox.setSelected(false);
                }
        rbase.writeInFile();
            }
        });
        eraseRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;

                int id = -1;
                try {id = Integer.parseInt(textField_erase_rule.getText());
                    if (id >= 0){
                        for(int i=0;i<rbase.getRules().length;i++)
                            if (rbase.getRules()[i].getId() == id){
                                id_flag = true;
                            }
                    }
                    else {JOptionPane.showMessageDialog(null,
                            "Id of the fact can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                        id_flag = false;
                    }
                    if (id_flag == false){
                        JOptionPane.showMessageDialog(null,
                                "No rule with such id!", "", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                    id_flag = false;
                }

                if (id_flag) {
                    rbase.erase_rule(id);
                    list_of_rules.setListData(rbase.getList());
                    textField_erase_rule.setText("");
                }
            core.getRbase().writeInFile();
            }

        });
    }
}
