package sample;

public class Core {

    public int[] getRules_chain() {
        return rules_chain;
    }

    public void clearChain(){
        rules_chain = new int[0];
    }

    private int[] rules_chain;
    private BaseOfFacts fbase;
    private BaseOfRules rbase;

    public BaseOfFacts getFbase(){
        return fbase;
    }

    public BaseOfRules getRbase(){
        return rbase;
    }

    public void sweepRules(){
        for(int i=0;i<rbase.getRules().length;i++)
            rbase.getRules()[i].execute(fbase);
    }

    public String description(){
        boolean performed_flag = true;
        int fact_id =-1;
        String str = "";

        for(int i=0;i<rules_chain.length;i++){
            for(int j=0;j<rbase.getRules().length;j++){
                if (rbase.getRules()[j].getId() == rules_chain[i]){
                    for(int k=0;k<rbase.getRules()[j].getL_facts().length;k++){
                        for(int q=0;q<fbase.getFacts().length;q++){
                            if (rbase.getRules()[j].getL_facts()[k].getId() ==
                                    fbase.getFacts()[q].getId()){
                                if (rbase.getRules()[j].getL_facts()[k].isFlag() !=
                                        fbase.getFacts()[q].isFlag() ||
                                        !fbase.getFacts()[q].isInit()){
                                    performed_flag = false;
                                    fact_id = fbase.getFacts()[q].getId();
                                }
                            }
                        }
                    }
                    if (performed_flag){
                        str += "факт - "+ rbase.getRules()[j].getR_fact().getStatement()
                                + " - выполняется:" + "\r\n";
                        for(int k=0;k<rbase.getRules()[j].getL_facts().length;k++){
                            str += "    - " + rbase.getRules()[j].getL_facts()[k].getStatement()
                                    + "\r\n";
                        }
                        str += "\r\n";
                    }

                    else{
                        str += "факт - "+ rbase.getRules()[j].getR_fact().getStatement()
                                + " - не выполняется:" + "\r\n";
                        for(int k=0;k<rbase.getRules()[j].getL_facts().length;k++){
                            if (rbase.getRules()[j].getL_facts()[k].getId() ==
                                    fact_id) {
                                if (!rbase.getRules()[j].getL_facts()[k].isInit())
                                    str += "    - недостаточно знаний, чтобы проверить факт - "
                                        + rbase.getRules()[j].getL_facts()[k].getStatement()
                                        + " - свидетельствующий о выполнении исходного факта"
                                        + "\r\n";
                                else str += "    - факт - "
                                        + rbase.getRules()[j].getL_facts()[k].getStatement()
                                        + " - противоречит выполнению исходного факта" + "\r\n";
                            }
                        }
                        str += "\r\n";
                    }
                }
            }
        }


        return str;
    }

    public Fact findRules(int id_right_fact){
        boolean id_flag = false;
        for(int i=0;i<rbase.getRules().length;i++){
            if (rbase.getRules()[i].getR_fact().getId() == id_right_fact){
                //Добавляем правило в "обратную" цепочку
                int[] new_rules_chain = new int[rules_chain.length+1];

                for(int j=0;j<rules_chain.length;j++)
                    new_rules_chain[j] = rules_chain[j];
                new_rules_chain[rules_chain.length] = rbase.getRules()[i].getId();

                rules_chain = new int[new_rules_chain.length];

                for(int j=0;j<rules_chain.length;j++)
                    rules_chain[j] = new_rules_chain[j];
                //
                id_flag = true;

                int rule_counter = 0;
                if (id_right_fact == 5) {
                    int fh=9;}
                for(int j=0;j<rbase.getRules()[i].getL_facts().length;j++){
                    Fact rez = new Fact(findRules(rbase.getRules()[i].getL_facts()[j].getId()));
                    if ((rbase.getRules()[i].getL_facts()[j].isFlag() ==
                    rez.isFlag()) &&
                    rez.isInit()){
                        rule_counter++;
                    }
                }
                if (rule_counter == rbase.getRules()[i].getL_facts().length){
                    Fact return_fact = new Fact(rbase.getRules()[i].getR_fact());

                    for(int q=0;q<fbase.getFacts().length;q++)
                        if (fbase.getFacts()[q].getId() == return_fact.getId()){
                            fbase.getFacts()[q].setInit(true);
                            fbase.getFacts()[q].setFlag(return_fact.isFlag());
                        }
                    return_fact.setInit(true);
                    return return_fact;
                }
                else{
                    Fact return_fact = new Fact(rbase.getRules()[i].getR_fact());
                    return_fact.setFlag(!return_fact.isFlag());
                    return_fact.setInit(false);
                    return return_fact;
                }
            }
        }
        if (!id_flag)
            for(int i=0;i<fbase.getFacts().length;i++)
                if (fbase.getFacts()[i].getId() == id_right_fact){
                    Fact return_fact = new Fact(fbase.getFacts()[i]);
                    return return_fact;
                }
        return fbase.getFacts()[0];
    }

    public Core(){
        fbase = new BaseOfFacts();
        rbase = new BaseOfRules();
        rules_chain = new int[0];

        Fact a0 = new Fact("Компания перспективна",true,false,0);
        Fact[] ff = {a0};
        fbase.setFacts(ff);

        Interface Form = new Interface(this, fbase, rbase);
        Form.setVisible(true);
    }
}
