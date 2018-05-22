package sample;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class BaseOfRules {

    public static final String BASE_OF_RULES_FILE_NAME = "rules";
    private Set<Rule> rules = new HashSet<>();

    public Set<Rule> getRules() {
        return rules;
    }

    public void read(Core core) /*throws FileNotFoundException*/ {
        try (BufferedReader in = new BufferedReader(new FileReader(new File(BASE_OF_RULES_FILE_NAME).getAbsoluteFile()))) {

            String s;
            while ((s = in.readLine()) != null) {
                addRule(s, core);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read facts from file");
        }
    }

    public void addRule(Fact[] leftFacts, Fact rightFact, int id){

        Rule rule = new Rule(leftFacts, rightFact, id);

        if(!rules.add(rule)) {
            JOptionPane.showMessageDialog(null, "Rule with such id already exist!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRule(String str, Core core){
        Rule rule = new Rule(str);
        addRule(rule.getLeftFacts(), rule.getRightFact(), rule.getId());
    }

    public void changeRule(Rule rule){
        rules.remove(rule);
        rules.add(rule);
    }

    public void removeRule(Rule rule){
        if (!rules.remove(rule)) {
            JOptionPane.showMessageDialog(null, "You try to delete rule with id, that does not exist!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return rules.stream().map(Rule::toString).reduce((s1, s2) -> s1 + System.lineSeparator() + s2).orElse("");
    }

    public String[] toData() {
        return rules.stream().map(Rule::toString).toArray(String[]::new);
    }

}
