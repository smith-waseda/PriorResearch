import java.util.ArrayList;

public class PriorReserch {

    public static Agent[] agent;
    private static Network[] network;
    private static ArrayList[][] connectedlayer;

    public static void trialPriorReserch(long seed) {
        Paramerter.generateRandom(seed);
        initParamerter();
        GenerateGraph();
        initAgent();
        setAgentRelationship();
        // displayAgentRelationship();
        // displayConnectedLayer();

        int x = 0;
        //displayOpinionAndExpress(1);
        while (x != Paramerter.loopnumber) {
            for(int i=0;i<Paramerter.agentnumber;i++){
                turnOfFormation(i);
                pressureAndSilence(i);
            }
            x++;
        }
        for (int k = 0; k < 8; k++) {
        //    displayOpinionAndExpress(k);
        }
    }

    public static void initParamerter() {
        agent = new Agent[Paramerter.agentnumber];
        network = new Network[Paramerter.layernumber];
        connectedlayer = new ArrayList[Paramerter.agentnumber][Paramerter.agentnumber];
    }

    public static void GenerateGraph() {
        for (int i = 0; i < Paramerter.layernumber; i++) {
            network[i] = new Network();
            network[i].generateGraph();
            // network[i].displayLinking();
        }
    }

    public static void initAgent() {
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            agent[i] = new Agent(Paramerter.rand.nextDouble());
        }
    }

    /**
     * ネットワーク内での意見形成
     */
    public static void turnOfFormation(int n) {
        /**
         * すべての層とノードでエージェントが黙った場合にこの処理を飛ばす
         */
        if (Agent.numberofopinionexpress >= (Paramerter.agentnumber * Paramerter.layernumber))
            return;
        for(int i=0;i<Paramerter.layernumber;i++){
            if(network[i].getNode()[n].size()==0)
                continue;
            else{
                int counter=0;
                for(int j=0;j<network[i].getNode()[n].size();j++){
                    if(agent[j].isOpinionexpress(i) == false)
                        counter++;
                }
                if(counter>=network[i].getNode()[n].size())
                    continue;;
                int m = Paramerter.rand.nextInt(network[i].getNode()[n].size());
                while(! agent[Integer.parseInt(network[i].getNode()[n].get(m).toString())].isOpinionexpress(i)){
                    m = Paramerter.rand.nextInt(network[i].getNode()[n].size());
                }
                Agent.formationOfOpinion(agent[n],agent[Integer.parseInt(network[i].getNode()[n].get(m).toString())],i);}
        }
    }

    /**
     * 別々のコミュニティで同じエージェント同士がつながっているとき、それらの関係性を各エージェントで記録する
     */
    public static void setAgentRelationship() {

        initConnectedLayer();

        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.layernumber; j++) {
                for (int k = j; k < Paramerter.layernumber; k++) {
                    if (j == k)
                        continue;
                    for (int l = 0; l < network[j].getNode()[i].size(); l++) {
                        int element1 = Integer.parseInt(network[j].getNode()[i]
                                .get(l).toString());
                        for (int m = 0; m < network[k].getNode()[i].size(); m++) {
                            int element2 = Integer.parseInt(network[k]
                                    .getNode()[i].get(m).toString());
                            if (element1 == element2) {

                                // あるノードと2つ以上の層でつなっがているエージェントをarralylistに格納する
                                if (agent[i].getAgentRelationship().indexOf(
                                        element1) == -1)
                                    agent[i].getAgentRelationship().add(
                                            element1);
                                if (agent[i].getAgentRelationship().indexOf(
                                        element2) == -1)
                                    agent[i].getAgentRelationship().add(
                                            element2);

                                // あるノードともう一つのノードがどの層でつながっているかの情報をarraylistに格納する
                                if (connectedlayer[i][element1].indexOf(j) == -1)
                                    connectedlayer[i][element1].add(j);
                                if (connectedlayer[i][element2].indexOf(k) == -1)
                                    connectedlayer[i][element2].add(k);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * あるノードともう一つのノードがどの層でつながっているかの情報のarraylistを初期化する
     */
    public static void initConnectedLayer() {
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.agentnumber; j++) {
                connectedlayer[i][j] = new ArrayList<Integer>();
            }
        }
    }

    /**
     * 別々のコミュニティで同じエージェント同士がつながっている時、どれとどれがつながっているかを表示する
     */
    public static void displayAgentRelationship() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < agent[i].getAgentRelationship().size(); j++) {
                String element = agent[i].getAgentRelationship().get(j)
                        .toString();
                buf.append(element);
                buf.append(",");
            }
            String elementline = buf.toString();
            buf.delete(0, buf.length());
            System.out.println(i + ":" + elementline);
        }
    }

    /**
     * あるエージェントと別のエージェントがどの層でつながっているか表示する
     */
    public static void displayConnectedLayer() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.agentnumber; j++) {
                for (int k = 0; k < connectedlayer[i][j].size(); k++) {
                    String element = connectedlayer[i][j].get(k).toString();
                    buf.append(element);
                    buf.append(",");
                }
                String elementline = buf.toString();
                buf.delete(0, buf.length());
                System.out.println("[" + i + "][" + j + "]:" + elementline);
            }
        }
    }

    /**
     * ネットワーク間での圧力と沈黙
     */
    public static void pressureAndSilence(int n) {
        if (Paramerter.rand.nextDouble() > Paramerter.connectivity || agent[n].getAgentRelationship().size() == 0) {
            return;
        }
        int m = agent[n].getAgentRelationship().get(Paramerter.rand.nextInt(agent[n].getAgentRelationship().size()));
        punishAgent(n,m);
    }

    /**
     * 二枚舌の発見および懲罰を行うターン
     *
     * @param i agent1
     * @param j agent2
     */
    public static void punishAgent(int i, int j) {
        double maxexpress = agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(0).toString())];
        double minexpress = agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(0).toString())];
        for (int k = 0; k < connectedlayer[i][j].size(); k++) {
            if (maxexpress < agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(k).toString())])
                maxexpress = agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(k).toString())];
            if (minexpress > agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(k).toString())])
                minexpress = agent[i].opinionlayer[Integer.parseInt(connectedlayer[i][j].get(k).toString())];
        }
        double expressdiff = Math.abs(maxexpress
                - minexpress);
        if (expressdiff > Paramerter.allowance) {
            for (int k = 0; k < connectedlayer[i][j].size(); k++) {
                agent[i].setOpinionexpress(false, Integer.parseInt(connectedlayer[i][j].get(k).toString()));
            }
        }
    }

    /**
     * ある層の全てのエージェントの意見と意見の表明のパラメーターを表示する
     *
     * @param layernumber
     */
    public static void displayOpinionAndExpress(int layernumber) {
        System.out.println(layernumber);
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            System.out.println(i + ":opinion,express:"
                    + agent[i].opinionlayer[layernumber] + ","
                    + agent[i].isOpinionexpress(layernumber));
        }
    }
}
