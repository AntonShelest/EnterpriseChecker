package sample;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class BaseOfFacts {

    public static final String BASE_OF_FACTS_FILE_NAME = "facts";
    private Set<Fact> facts = new HashSet<>();


    public Set<Fact> getFacts(){
        return facts;
    }

    public void setFacts(Set<Fact> facts) {
        this.facts = facts;
    }

    public void read() {

        try (BufferedReader in = new BufferedReader(new FileReader(new File(BASE_OF_FACTS_FILE_NAME).getAbsoluteFile()))) {

            String s;
            while ((s = in.readLine()) != null) {
                addFact(s);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read facts from file");
        }
    }

    public void addFact(String statement, boolean flag, boolean init, int id){

        Fact f = new Fact(statement, flag, init, id);
        if (!facts.contains(f)){
            facts.add(f);
        }
        else JOptionPane.showMessageDialog(null, "Fact with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
    }

    public void changeFact(Fact fact){
        facts.remove(fact);
        facts.add(fact);
    }

    public void addFact(String str){
        Fact newFact = new Fact(str);
        addFact(newFact.getStatement(), newFact.isFlag(), newFact.isInit(), newFact.getId());
    }

    public void removeFact(int id) {

        Fact factToRemove = new Fact("", false, false, id); // [TODO] temporary solution

        if(!facts.remove(factToRemove)) {
            JOptionPane.showMessageDialog(null, "You try to delete fact with id, that is not exist!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return facts.stream().map(Fact::toString).reduce((s1, s2) -> s1 + System.lineSeparator() + s2).orElse("");
    }

    public String[] toData() {
        return facts.stream().map(Fact::toString).toArray(String[]::new);
    }

    public String[] toActualData() {
        return facts.stream().filter(Fact::isInit).map(Fact::toString).toArray(String[]::new);
    }

}
