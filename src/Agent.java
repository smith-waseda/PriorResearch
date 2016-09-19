import java.util.ArrayList;

public class Agent {

    private boolean[] opinionexpress;
    public static int numberofopinionexpress;
    public double[] opinionlayer;
    private ArrayList<Integer> agentrelationship;

    public Agent(double i) {
        opinionexpress = new boolean[Paramerter.layernumber];
        numberofopinionexpress = 0;
        opinionlayer = new double[Paramerter.layernumber];
        this.agentrelationship = new ArrayList<Integer>();
        for (int j = 0; j < Paramerter.layernumber; j++) {
            this.opinionlayer[j] = i;
            this.setOpinionexpress(true, j);
        }
    }

    /**
     * あるノードともう一個のノードの意見を平均化する
     *
     * @param agent1
     * @param agent2
     * @param layernumber
     */
    public static void formationOfOpinion(Agent agent1, Agent agent2,
                                          int layernumber) {
        if (Paramerter.confornitybias > Math
                .abs(agent1.opinionlayer[layernumber]
                        - agent2.opinionlayer[layernumber])) {
            double tmp = (agent1.opinionlayer[layernumber] + agent2.opinionlayer[layernumber]) / 2;
            agent1.opinionlayer[layernumber] = tmp;
            agent2.opinionlayer[layernumber] = tmp;
        }
    }

    public boolean isOpinionexpress(int layernumber) {
        return opinionexpress[layernumber];
    }

    public void setOpinionexpress(boolean opinionexpress, int layernumber) {
        if (this.opinionexpress[layernumber] == true){
            numberofopinionexpress++;}
        this.opinionexpress[layernumber] = opinionexpress;
    }

    public ArrayList<Integer> getAgentRelationship() {
        return agentrelationship;
    }

}
