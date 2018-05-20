package sample;

import javax.swing.*;
import java.io.*;

public class BaseOfRules {

    private File file;
    private String fileName = "rules";
    private boolean already_read_flag = false;
    private Rule[] rules;
    private int number = 0;

    public Rule[] getRules() {
        return rules;
    }

    public BaseOfRules(){
        rules = new Rule[number];

        file = new File(fileName);
    }

    public String[] getList(){
        String[] data = new String[rules.length];

        for(int i=0;i<rules.length;i++){
            data[i] = "B" + rules[i].getId() + ": " + rules[i].getStatement();
        }
        return data;
    }

    public void writeInFile(){
        //Определяем файл

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                for(int i=0;i<rules.length;i++){
                    out.print(rules[i].write());
                }

            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read(Core core) /*throws FileNotFoundException*/ {
        //Этот спец. объект для построения строки
        if (!already_read_flag){
            already_read_flag = true;
            StringBuilder sb = new StringBuilder();

            //exists(fileName);

            try {
                //Объект для чтения файла в буфер
                BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
                try {
                    //В цикле построчно считываем файл
                    String s;
                    String[] str = new String[0];
                    while ((s = in.readLine()) != null) {
                        sb.append(s);

                        String[] new_str = new String[str.length+1];
                        for(int i=0;i<str.length;i++)
                            new_str[i] = str[i];

                        new_str[str.length] = s;

                        add_rule(s, core);

                        str = new String[new_str.length];
                        for(int i=0;i<str.length;i++)
                            str[i] = new_str[i];
                        //Добавляем в базу


                        sb.append("\r\n");
                    }
                } finally {
                    //Также не забываем закрыть файл
                    in.close();
                }
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            //Возвращаем полученный текст с файла
            //return sb.toString();
        }
    }

    public void add_rule(Fact[] l_facts, Fact r_fact, int id){
        boolean id_flag = false;
        //Проверка на совпадение идентификаторов
        for(int i=0;i<rules.length;i++)
            if (rules[i].getId()== id) id_flag = true;
        if (id_flag == false){
            Rule rule = new Rule(l_facts, r_fact, id);
            Rule[] new_rules = new Rule[rules.length+1];

            for(int i=0;i<rules.length;i++)
                new_rules[i] = rules[i];
            new_rules[rules.length] = rule;

            rules = new Rule[new_rules.length];

            for(int i=0;i<new_rules.length;i++)
                rules[i] = new_rules[i];
            number = rules.length;
        }
        else JOptionPane.showMessageDialog(null, "Rule with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
    }

    public void add_rule(String str, Core core){
        boolean id_flag = false;



        Rule my_rule = new Rule(str, core);
        Fact[] l_facts = new Fact[my_rule.getL_facts().length];
        for(int i=0;i<my_rule.getL_facts().length;i++) {
            l_facts[i] = new Fact(my_rule.getL_facts()[i]);
        }
        Fact r_fact = new Fact(my_rule.getR_fact());
        int id = my_rule.getId();
        //Проверка на совпадение идентификаторов
        for(int i=0;i<rules.length;i++)
            if (rules[i].getId()== id) id_flag = true;
        if (id_flag == false){
            Rule rule = new Rule(l_facts, r_fact, id);
            Rule[] new_rules = new Rule[rules.length+1];

            for(int i=0;i<rules.length;i++)
                new_rules[i] = rules[i];
            new_rules[rules.length] = rule;

            rules = new Rule[new_rules.length];

            for(int i=0;i<new_rules.length;i++)
                rules[i] = new_rules[i];
            number = rules.length;
        }
        else JOptionPane.showMessageDialog(null, "Rule with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
    }


    public void change_rule(int id, Rule rule){
        for(int i=0;i<rules.length;i++)
            if (rules[i].getId() == id){
                rules[i].setL_facts(rule.getL_facts());
                rules[i].setR_fact(rule.getR_fact());
            }
    }

    public void erase_rule(int id_rule){
        int i_id = -1;
        for(int i=0;i<rules.length;i++)
            if (rules[i].getId() == id_rule) i_id = i;

        if (i_id == -1)
            JOptionPane.showMessageDialog(null,
                    "Caution! Error in BaseOfRules.erase_rule",
                    "", JOptionPane.ERROR_MESSAGE);

        if (i_id>=0 && i_id<=rules.length-1){
            for(int i=i_id;i<rules.length-1;i++)
                rules[i] = rules[i+1];

            Rule[] new_rules = new Rule[rules.length-1];

            for(int i=0;i<new_rules.length;i++)
                new_rules[i] = rules[i];

            rules = new Rule[new_rules.length];
            for(int i=0;i<rules.length;i++)
                rules[i] = new_rules[i];

            number = rules.length;
        }
        else if(i_id == rules.length){
            Rule[] new_rules = new Rule[rules.length-1];

            for(int i=0;i<new_rules.length;i++)
                new_rules[i] = rules[i];

            rules = new Rule[new_rules.length];
            for(int i=0;i<rules.length;i++)
                rules[i] = new_rules[i];

            number = rules.length;
        }

        else{
            JOptionPane.showMessageDialog(null, "You try to delete rule with id, that does not exist!", "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
