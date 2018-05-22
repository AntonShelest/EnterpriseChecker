package sample;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Rule {

    private int id;
    private Set<Fact> leftFacts;
    private Fact rightFact;

    public Fact getRightFact() {
        return rightFact;
    }

    public int getId() {
        return id;
    }

    public Set<Fact> getLeftFacts() {
        return leftFacts;
    }

    public void executeRule(BaseOfFacts fbase){
        if (leftFacts.stream()
                .allMatch(f -> fbase.getFacts().stream()
                        .anyMatch(ff -> ff.getId() == f.getId() && ff.isFlag() == f.isFlag() && ff.isInit()))) {
            fbase.changeFact(rightFact);
        }
    }

    public Rule(Set<Fact> leftFacts, Fact rightFact, int id){
        this.leftFacts = leftFacts;
        this.rightFact = rightFact;
        this.id = id;
    }


    public Rule(String str){

        if (!str.matches("B\\d:[~A\\d\\s]+\\|=\\s[~A\\d]")) {
            throw new IllegalArgumentException("Invalid format of rule string: " + str);
        }

        String[] words = str.split(":");

        id = Integer.parseInt(words[0].substring(1));

        String[] ruleStrings = words[1].split("\\|=");

        this.leftFacts = Stream.of(ruleStrings[0].split(","))
                .map(String::trim)
                .map(this::parseFact)
                .collect(toSet());

        this.rightFact = parseFact(ruleStrings[1]);
    }

    private Fact parseFact(String str) {
        boolean isTrue = !str.substring(0, 1).equals("~");
        int factId = Integer.parseInt(str.substring(isTrue ? 1 : 2));
        return new Fact(null, isTrue, true, factId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return id == rule.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
