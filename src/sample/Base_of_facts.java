package sample;

import com.sun.corba.se.impl.logging.OMGSystemException;

import javax.swing.*;
import java.io.*;

public class Base_of_facts {
    public File getFile() {
        return file;
    }

    private File file;
    private String fileName = "facts";
    private boolean already_read_flag = false;


    public Fact[] getFacts(){
        return facts;
    }

    public void setFacts(Fact[] facts) {
        this.facts = facts;
        number = facts.length;
    }

    private Fact facts[];
    private int number = 0;

    public Base_of_facts(){
        facts = new Fact[number];

        file = new File(fileName);
    }

    public String[] getList(){
        String[] data = new String[facts.length];

        for(int i=0;i<facts.length;i++)
            data[i] = facts[i].getStatement() + " " + facts[i].isFlag() + " init: " + facts[i].isInit();

        for(int i=0;i<data.length;i++)
            data[i] = "A" + facts[i].getId() + ": " + data[i];
        return data;
    }

    public String[] getListInit(){
        int init_numb = 0;
        for(int i=0;i<facts.length;i++)
            if (facts[i].isInit()) init_numb++;
        String[] data = new String[init_numb];

        int k = 0;
        for(int i=0;i<facts.length;i++)
            if (facts[i].isInit()){
                data[k] = "A" + facts[i].getId() + ": " +
                     facts[i].getStatement() + " " + facts[i].isFlag() + " init: " + facts[i].isInit();
                k++;
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
                for(int i=0;i<facts.length;i++){
                    out.print(facts[i].write());
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

    public void read() /*throws FileNotFoundException*/ {
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

                        add_fact(s);

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

    public void add_fact(String statement, boolean flag, boolean init,int id){
        boolean id_flag = false;
        //Проверка на совпадение идентификаторов
        for(int i=0;i<facts.length;i++)
            if (facts[i].getId()== id) id_flag = true;
        if (id_flag == false){
            Fact f = new Fact(statement, flag, init, id);
            Fact[] new_facts;
            new_facts = new Fact[facts.length+1];

            for(int i=0;i<facts.length;i++)
                new_facts[i] = facts[i];
            new_facts[facts.length] = f;

            facts = new Fact[new_facts.length];

            for(int i=0;i<new_facts.length;i++)
                facts[i] = new_facts[i];
            number = facts.length;
        }
        else JOptionPane.showMessageDialog(null, "Fact with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
    }

    public void change_fact(int id, Fact fact){
        for(int i=0;i<facts.length;i++)
            if (facts[i].getId() == id){
                facts[i].setFlag(fact.isFlag());
                facts[i].setInit(true);
            }
    }

    public void add_fact(String str){
        boolean id_flag = false;

        Fact my_fact = new Fact(str);
        if (my_fact.getId() != 0){
            String statement = my_fact.getStatement();
            boolean flag = my_fact.isFlag();
            boolean init = my_fact.isInit();
            int id = my_fact.getId();

            //Проверка на совпадение идентификаторов
            for(int i=0;i<facts.length;i++)
                if (facts[i].getId()== id) id_flag = true;
            if (id_flag == false){
                Fact f = new Fact(statement, flag, init, id);
                Fact[] new_facts;
                new_facts = new Fact[facts.length+1];

                for(int i=0;i<facts.length;i++)
                    new_facts[i] = facts[i];
                new_facts[facts.length] = f;

                facts = new Fact[new_facts.length];

                for(int i=0;i<new_facts.length;i++)
                    facts[i] = new_facts[i];
                number = facts.length;
            }
            else JOptionPane.showMessageDialog(null, "Fact with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void erase_fact(int id_fact){
            int i_id = -1;
            for(int i=0;i<facts.length;i++)
                if (facts[i].getId() == id_fact) i_id = i;

            if (i_id == -1)
                JOptionPane.showMessageDialog(null,
                "Caution! Error in Base_of_fact.erase_fact",
                "", JOptionPane.ERROR_MESSAGE);

            if (i_id>=0 && i_id<=facts.length-1){
                for(int i=i_id;i<facts.length-1;i++)
                    facts[i] = facts[i+1];

                Fact[] new_facts = new Fact[facts.length-1];

                for(int i=0;i<new_facts.length;i++)
                    new_facts[i] = facts[i];

                facts = new Fact[new_facts.length];
                for(int i=0;i<facts.length;i++)
                    facts[i] = new_facts[i];

                number = facts.length;
            }
            else if(i_id == facts.length){
                Fact[] new_facts = new Fact[facts.length-1];

                for(int i=0;i<new_facts.length;i++)
                    new_facts[i] = facts[i];

                facts = new Fact[new_facts.length];
                for(int i=0;i<facts.length;i++)
                    facts[i] = new_facts[i];

                number = facts.length;
            }
            else{
                JOptionPane.showMessageDialog(null, "You try to delete fact with id, that is not exist!", "", JOptionPane.ERROR_MESSAGE);
            }
    }
}
