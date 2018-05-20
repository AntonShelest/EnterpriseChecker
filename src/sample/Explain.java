package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Explain extends JFrame{
    private int width = 700;
    private int height = 600;

    private JList list_init_facts;
    private JTextArea textArea_explain;
    private JScrollPane ScrollPane_init_facts;
    private JButton mainMenuButton;
    private JLabel explanationLabel;
    private JScrollPane explanationScrollPane;

    public Explain(final Core core){
        super("Explanation");

        this.setLocation(1024 / 2 - this.width / 2, 768 / 2 - this.height / 2);
        this.setSize(this.width, this.height);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,1));

        panel.add(list_init_facts);
        panel.add(ScrollPane_init_facts);
        panel.add(explanationLabel);
        panel.add(textArea_explain);
        panel.add(explanationScrollPane);
        panel.add(mainMenuButton);
        setContentPane(panel);
        ScrollPane_init_facts.getViewport().setView(list_init_facts);
        explanationScrollPane.getViewport().setView(textArea_explain);





        String str = "";
        Fact rez = new Fact(core.findRules(0));
        if (rez.isInit() && (rez.isFlag())){
            str = "Компания перспективна для инвестирования" + "\r\n";
            /*str += "Это следует изтого что: \r\n";
            for(int i=0;i<core.getRbase().getRules().length;i++){
                if (core.getRbase().getRules()[i].getRightFact().getId() == rez.getId()){
                    for(int j=0;j<core.getRbase().getRules()[i].getLeftFacts().length;j++){
                        str += "    - " + core.getRbase().getRules()[i].getLeftFacts()[j].getStatement()
                                + "\r\n";
                    }
                }
            } */
        }
        else if (rez.isInit() && !(rez.isFlag())) str = "Компания неперспективна для инвестирования" + "\r\n";
        else str = "Недостаточно информации для формирования вывода о финансовом состоянии предприятия" + "\r\n";
        str += "\r\n";


        /*for(int i=0;i<core.getRules_chain().length;i++){
            for(int j=0;j<core.getRbase().getRules().length;j++){
                if (core.getRbase().getRules()[j].getId() == core.getRules_chain()[i]){
                    if (core.getRbase().getRules()[j].getRightFact().isInit() &&
                            core.getRbase().getRules()[j].getRightFact().isFlag()){
                        str += "факт о том, что "
                            + core.getRbase().getRules()[j].getRightFact().getStatement()
                            + ", выполняется вследствие того, что: " + "\r\n";
                    }
                    else if (core.getRbase().getRules()[j].getRightFact().isInit() &&
                            !core.getRbase().getRules()[j].getRightFact().isFlag()){
                        str += "факт о том, что "
                                + core.getRbase().getRules()[j].getRightFact().getStatement()
                                + ", не выполняется вследствие того, что: " + "\r\n";
                    }
                    else {
                        str += "недостаточно данных для того, чтобы проверить факт о том, что "
                                + core.getRbase().getRules()[j].getRightFact().getStatement()
                                + ", поскольку: " + "\r\n";
                    }
                    for(int k=0;k<core.getRbase().getRules()[j].getLeftFacts().length;k++){
                        if (core.getRbase().getRules()[j].getRightFact().isInit()){
                            str += "    -" +
                            core.getRbase().getRules()[j].getLeftFacts()[k].getStatement() + "\r\n";
                        }
                        else{
                            if (!core.getRbase().getRules()[j].getLeftFacts()[k].isInit())
                            str += "    -" + " недостаточно данных для проверки факта о том, что "
                                    + core.getRbase().getRules()[j].getLeftFacts()[k].getStatement()
                                    + "\r\n";
                        }
                        str += "\r\n";
                    }

                }
            }
        } */

        str += core.description();
        list_init_facts.setListData(core.getFbase().toActualData());
        textArea_explain.setText(str);
        core.clearChain();
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Interface Form = new Interface(core, core.getFbase(), core.getRbase());
                Form.setVisible(true);
            }
        });
    }
}
