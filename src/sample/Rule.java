package sample;

/**
 * Created with IntelliJ IDEA.
 * User: Антоха
 * Date: 25.11.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class Rule {
    public void setL_facts(Fact[] l_facts) {
        this.l_facts = l_facts;
    }

    public Fact[] getL_facts() {
        return l_facts;
    }

    private Fact[] l_facts;

    public Fact getR_fact() {
        return r_fact;
    }

    public void setR_fact(Fact r_fact) {
        this.r_fact = r_fact;
    }

    private Fact r_fact;

    public int getId() {
        return id;
    }

    private int id;

    public String write(){
        String str = "";
        str += "B" + id + ": ";
        for(int i=0;i<l_facts.length;i++){
            if (l_facts[i].isFlag())
                str += "A" + l_facts[i].getId() + ", ";
            else str += "~A" + l_facts[i].getId() + ", ";
        }
        if (r_fact.isFlag())
            str += "|= A" + r_fact.getId() + "\r\n";
        else str += "|= ~A" + r_fact.getId() + "\r\n";
        return str;
    }

    public void execute(Base_of_facts fbase){
        int i_flag = 0;
        for(int i=0;i<l_facts.length;i++)
            for(int j=0;j<fbase.getFacts().length;j++)
                if (l_facts[i].getId() == fbase.getFacts()[j].getId())
                    if (l_facts[i].isFlag() == fbase.getFacts()[j].isFlag() &&
                            fbase.getFacts()[j].isInit()){
                        i_flag++;
                    }
        if (i_flag == l_facts.length){
            fbase.change_fact(r_fact.getId(),r_fact);
        }
    }

    public String getStatement(){
        String data = "";

        for(int i=0;i<l_facts.length;i++)
            if (i<l_facts.length-1){
                if (l_facts[i].isFlag()) data += "A" + l_facts[i].getId() + ", ";
                else data += "~A" + l_facts[i].getId() + ", ";
            }
            else{
                if (l_facts[i].isFlag()) data += "A" + l_facts[i].getId() + "  ";
                else data += "~A" + l_facts[i].getId() + "  ";
            }
        if (r_fact.isFlag()) data += "|= A" + r_fact.getId();
        else data += "|= ~A" + r_fact.getId();

        return data;
    }

    public Rule(Fact[] l_f, Fact r_f, int iden){
        l_facts = new Fact[l_f.length];
        for(int i=0;i<l_f.length;i++)
            l_facts[i] = new Fact(l_f[i]);
        r_fact = new Fact(r_f);
        id = iden;
    }

    public Rule(Rule r){
        l_facts = new Fact[r.getL_facts().length];
        for(int i=0;i<r.getL_facts().length;i++)
            l_facts[i] = new Fact(r.getL_facts()[i]);

        r_fact = new Fact(r.getR_fact());
        id = r.getId();
    }

    public Rule(String str, Core core){
        char[] mas = new char[str.length()];

        str.getChars(0,str.length(),mas,0);

        int i=1;
        String id_str = "";
        while(mas[i]!=':'){
            id_str += mas[i];
            i++;
        }

        id = Integer.parseInt(id_str);

        int rty=90;
        if (id == 235){
            rty =1;
            rty =2;
            rty = core.getFbase().getFacts().length;
        }

        if (id == 6) {
            int r=0;
        }
        i++; i++;
        l_facts = new Fact[0];
        while(mas[i]!='|'){
            boolean not_flag = false;
            if (mas[i] == '~'){
                i++;
                not_flag = true;
            }
            i++;
            String next_id_str = "";
            int next_id =-1;
            while(mas[i]!=','){
                next_id_str += mas[i];
                i++;
            }
            next_id = Integer.parseInt(next_id_str);

            for(int qq=0;qq<core.getFbase().getFacts().length;qq++){
                if (core.getFbase().getFacts()[qq].getId() == next_id){
                    Fact[] new_l_facts = new Fact[l_facts.length+1];

                    for(int k=0;k<l_facts.length;k++)
                        new_l_facts[k] = l_facts[k];

                    new_l_facts[l_facts.length] = new Fact(core.getFbase().getFacts()[qq]);
                    new_l_facts[l_facts.length].setFlag(!not_flag);

                    l_facts = new Fact[new_l_facts.length];

                    for(int k=0;k<l_facts.length;k++)
                        l_facts[k] = new_l_facts[k];

                    i++; i++;
                }
            }
        }

        boolean r_not_flag = false;
        String r_fact_id_str = "";
        i++; i++; i++;
        if (mas[i] == '~'){
            i++;
            r_not_flag = true;

        }
        i++;
        int r_fact_id = -1;
        while(i<mas.length){
            r_fact_id_str += mas[i];
            i++;
        }
        r_fact_id = Integer.parseInt(r_fact_id_str);

        for(int j=0;j<core.getFbase().getFacts().length;j++){
            if (core.getFbase().getFacts()[j].getId() == r_fact_id){
                r_fact = new Fact(core.getFbase().getFacts()[j]);
                r_fact.setFlag(!r_not_flag);
            }
        }
    }
}
