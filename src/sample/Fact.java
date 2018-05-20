package sample;

import javax.swing.*;

public class Fact {

    private String statement;
    private boolean flag;
    private boolean init;
    private int id;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fact(String stat, boolean fl, boolean in, int id){
        statement = stat;
        flag = fl;
        init = in;
        if (id < 0) JOptionPane.showMessageDialog(null, "Fact id < 0!", "", JOptionPane.ERROR_MESSAGE);
        else this.id = id;
    }

    @Override
    public String toString(){
        String str = "";
        str += "A" + id + ": " + statement + " flag: " + flag + " init: " + init + "\r\n";
        return str;
    }

    public Fact(Fact f){
        int iden = -1;

        statement = f.getStatement();
        flag = f.isFlag();
        init = f.isInit();
        iden = f.getId();
        if (iden < 0) JOptionPane.showMessageDialog(null, "Fact id < 0!", "", JOptionPane.ERROR_MESSAGE);
        else id = iden;
    }

    public Fact(String str){
        int iden = -1;
        char[] mas = new char[str.length()];

        str.getChars(0,str.length(),mas,0);

        int i=1;
        String id_str = "";
        while(mas[i]!=':'){
              id_str += mas[i];
            i++;
        }

            id = Integer.parseInt(id_str);

            i++; i++;
            String statement_str = "";
            while(mas[i]!='f'){
                statement_str += mas[i];
                i++;
            }
            statement = statement_str;

            while(mas[i]!=':') i++;
            i++; i++;
            String flag_str = "";
            while(mas[i]!=' '){
                flag_str += mas[i];
                i++;
            }
            flag = Boolean.parseBoolean(flag_str);

            while(mas[i]!=':') i++;
            i++; i++;
            String init_str = "";
            while(i<mas.length){
                init_str += mas[i];
                i++;
            }
            init = Boolean.parseBoolean(init_str);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fact fact = (Fact) o;

        return id == fact.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
