package sample;

import java.io.File;

    public class Core {

    public int[] getRules_chain() {
        return rules_chain;
    }

    public void clearChain(){
        rules_chain = new int[0];
    }

    private int[] rules_chain;

    public Base_of_facts getFbase(){
        return fbase;
    }

    private Base_of_facts fbase;

    public Base_of_rules getRbase(){
        return rbase;
    }

    private Base_of_rules rbase;

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
        fbase = new Base_of_facts();
        rbase = new Base_of_rules();
        rules_chain = new int[0];

        Fact a0 = new Fact("Компания перспективна",true,false,0);
        Fact[] ff = {a0};
        fbase.setFacts(ff);

        /*Fact a1 = new Fact("Прибыль положительна",true,true,1);
        Fact a2 = new Fact("Собственный капитал превышает заёмный",true,true,2);
        Fact a3 = new Fact("Цена на акции растет",true,false,3);
        Fact a4 = new Fact("Выплачиваются дивиденды",true,true,4);
        Fact a5 = new Fact("Слухи о компании оптимистичны",true,true,5);

        Fact[] ff = {a0,a1,a2,a3,a4,a5};

        //fbase.add_fact("Акции падают",true,true);
        //fbase.add_fact("Долг растёт",true,true);
        //fbase.add_fact("Директор сердчает",true,true);

        fbase.setFacts(ff);

        rbase = new Base_of_rules();

        Fact[] r_left1 = new Fact[2];
        r_left1[0] = new Fact(a4);
        r_left1[1] = new Fact(a5);
        Fact aa3 = new Fact(a3);
        aa3.setInit(true);
        aa3.setFlag(true);
        rbase.add_rule(r_left1,aa3,0);

        Fact[] r_left2 = new Fact[3];
        r_left2[0] = new Fact(a1);
        r_left2[1] = new Fact(a2);
        r_left2[2] = new Fact(a3);
        Fact aa0 = new Fact(a0);
        aa0.setInit(true);
        aa0.setFlag(true);
        rbase.add_rule(r_left2,aa0,1);  */

        Interface Form = new Interface(this, fbase, rbase);
        Form.setVisible(true);
    }
}
