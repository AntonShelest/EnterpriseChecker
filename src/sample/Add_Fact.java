package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Антоха
 * Date: 23.11.13
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */
public class Add_Fact extends JFrame{
    private int width = 300;
    private int height = 500;


    private JButton addFactButton;
    private JCheckBox checkBox_True;
    private JCheckBox checkBox_Init;
    private JPanel panel;
    private JTextField textField_Statement;
    private JList list_of_facts;
    private JButton button_Eraise_Fact;
    private JTextField textField_erase_numb;
    private JScrollPane scrollPane_List;
    private JTextField textField_id;
    private JButton addRuleButton;
    private JButton saveChangesButton;
    private JButton mainMenuButton;
    private JButton editRulesButton;
    private JSpinner spinner_eraise_numb;
    private JLabel label_statement;
    private JLabel label_id;
    private JLabel label_erase_numb;

    public Add_Fact(final Core core, final Base_of_facts fbase,final Base_of_rules rbase, final Add_Rule rule_form){
        super("Editing the base of facts");

        this.setLocation(1024 / 2 - this.width, 768 / 2 - this.height / 2);
        this.setSize(this.width, this.height);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        //Drawing form
        final JButton addFactButton = new JButton("Add fact");
        addFactButton.setSize(120, 40);
        addFactButton.setLocation(20,10);
        panel.add(addFactButton);

        final JButton button_Eraise_Fact = new JButton("Eraise fact");
        button_Eraise_Fact.setSize(120, 40);
        button_Eraise_Fact.setLocation(150,10);
        panel.add(button_Eraise_Fact);

        final JButton saveChangesButton = new JButton("Save changes");
        saveChangesButton.setSize(140, 20);
        saveChangesButton.setLocation(80,110);
        //panel.add(saveChangesButton);

        final JCheckBox checkBox_True = new JCheckBox("True");
        checkBox_True.setSize(20, 20);
        checkBox_True.setLocation(125,115);
        panel.add(checkBox_True);

        final JLabel label_true = new JLabel("Is true?");
        label_true.setSize(60,20);
        label_true.setLocation(80,115);
        panel.add(label_true);

        final JCheckBox checkBox_Init = new JCheckBox("Init");
        checkBox_Init.setSize(20,20);
        checkBox_Init.setLocation(60,115);
        panel.add(checkBox_Init);

        final JLabel label_init = new JLabel("Is init?");
        label_init.setSize(60,20);
        label_init.setLocation(20,115);
        panel.add(label_init);

        final JTextField textField_Statement = new JTextField();
        textField_Statement.setSize(120,20);
        textField_Statement.setLocation(20,70);
        panel.add(textField_Statement);

        final JLabel label_statement = new JLabel("Enter Statement");
        label_statement.setSize(120,20);
        label_statement.setLocation(20,50);
        panel.add(label_statement);

        final JTextField textField_id = new JTextField();
        textField_id.setSize(30,20);
        textField_id.setLocation(110,95);
        panel.add(textField_id);

        final JLabel label_id = new JLabel("Enter id for add");
        label_id.setSize(120,20);
        label_id.setLocation(20,95);
        panel.add(label_id);

        final JTextField textField_erase_numb = new JTextField();
        textField_erase_numb.setSize(30,20);
        textField_erase_numb.setLocation(240,95);
        panel.add(textField_erase_numb);

        final JLabel label_erase_numb = new JLabel("Enter id for erase");
        label_erase_numb.setSize(150,10);
        label_erase_numb.setLocation(150,70);
        panel.add(label_erase_numb);

        final JList list_of_facts = new JList();
        list_of_facts.setSize(250,200);
        list_of_facts.setLocation(20,150);
        panel.add(list_of_facts);

        final JScrollPane scrollPane_List = new JScrollPane();
        scrollPane_List.setSize(250,200);
        scrollPane_List.setLocation(20,150);
        panel.add(scrollPane_List);

        final JButton addRuleButton = new JButton("Add rule");
        addRuleButton.setSize(300, 15);
        addRuleButton.setLocation(0,0);
        //panel.add(addRuleButton);

        final JButton mainMenuButton = new JButton("Main menu");
        mainMenuButton.setSize(120, 40);
        mainMenuButton.setLocation(20,410);
        panel.add(mainMenuButton);

        final JButton editRulesButton = new JButton("Edit rules");
        editRulesButton.setSize(120, 40);
        editRulesButton.setLocation(150,410);
        panel.add(editRulesButton);

        setContentPane(panel);

        //Считываем базу фактов  и правил
        core.getFbase().read();
        core.getRbase().read(core);

        //Output facts into list
        list_of_facts.setListData(fbase.getList());
        scrollPane_List.getViewport().setView(list_of_facts);

        //Button "Add fact"
        addFactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;

                String statement;
                boolean flag;
                boolean init;

                int id = -1;
                try {id = Integer.parseInt(textField_id.getText());
                    if (id >= 0){
                        for(int i=0;i<fbase.getFacts().length;i++)
                            if (fbase.getFacts()[i].getId() == id){
                                id_flag = true;
                                JOptionPane.showMessageDialog(null,
                                "Fact with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                    }
                    else {JOptionPane.showMessageDialog(null,
                                    "Id of the fact can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                         id_flag = true;
                    }
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                    id_flag = true;
                }

                if (id_flag == false){
                    statement = (String)textField_Statement.getText();
                    flag = checkBox_True.isSelected();
                    init = checkBox_Init.isSelected();

                    fbase.add_fact(statement,flag,init,id);
                    core.getFbase().writeInFile();
                    textField_Statement.setText("");
                    textField_id.setText("");
                    list_of_facts.setListData(fbase.getList());
                }
            }
        });

        button_Eraise_Fact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean id_flag = false;
                boolean caution_flag = false; //флаг, предупреждающий, что удаляемый факт есть в правилах

                int id = -1;
                try {id = Integer.parseInt(textField_erase_numb.getText());
                    if (id >= 0){
                        for(int i=0;i<fbase.getFacts().length;i++)
                            if (fbase.getFacts()[i].getId() == id){
                                id_flag = true;
                                //Проверка, содержится ли удаляемый факт в правилах
                                for(int j=0;j<rbase.getRules().length;j++){
                                    for(int k=0;k<rbase.getRules()[j].getL_facts().length;k++)
                                        if (rbase.getRules()[j].getL_facts()[k].getId() == id)
                                            caution_flag = true;
                                    if (rbase.getRules()[j].getR_fact().getId() == id)
                                        caution_flag = true;
                                }
                                if (caution_flag) JOptionPane.showMessageDialog(null,
                                        "This fact is contained in base of rules! Regretfully, " +
                                        "you can not erase this :(", "", JOptionPane.ERROR_MESSAGE);
                                else if (id >= 0 && id <=3) JOptionPane.showMessageDialog(null,
                                        "This fact is main. Regretfully, " +
                                                "you can not erase this", "", JOptionPane.ERROR_MESSAGE);
                            }
                    }
                    else {JOptionPane.showMessageDialog(null,
                            "Id of the fact can`t be negative!", "", JOptionPane.ERROR_MESSAGE);
                        id_flag = false;
                    }
                    if (id_flag == false){
                        JOptionPane.showMessageDialog(null,
                                "No fact with such id!", "", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Enter integer for id!", "", JOptionPane.ERROR_MESSAGE);
                    id_flag = false;
                }

                if (id_flag && !caution_flag && !(id >= 0 && id <=3)) {
                    fbase.erase_fact(id);
                    core.getFbase().writeInFile();
                    list_of_facts.setListData(fbase.getList());
                    textField_erase_numb.setText("");
                }
            }
        });
        addRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.getFbase().writeInFile();
            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                rule_form.setVisible(false);
                Interface Form = new Interface(core, fbase, rbase);
                Form.setVisible(true);
            }
        });
        editRulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                rule_form.setVisible(true);
            }
        });
    }

    }
