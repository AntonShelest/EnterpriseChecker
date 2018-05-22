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

    public Fact(Fact f){

        if (f.getId() < 0) JOptionPane.showMessageDialog(null, "Fact id < 0!", "", JOptionPane.ERROR_MESSAGE);

        id = f.getId();
        statement = f.getStatement();
        flag = f.isFlag();
        init = f.isInit();
    }

    public Fact(String str){

        if (str.matches("^[^:]+:[^:]+:[^:]+:[^:]+$")) {
            throw new IllegalArgumentException("Cannot parse fact from string: " + str);
        }

        String[] words = str.split(":");

        id = Integer.parseInt(words[0]);
        statement = words[1];
        flag = Boolean.parseBoolean(words[2]);
        init = Boolean.parseBoolean(words[3]);
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

    @Override
    public String toString(){
        String str = "";
        str += "A" + id + ": " + statement + " flag: " + flag + " init: " + init + "\r\n";
        return str;
    }
}
