import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class PriorReserch {

    public static Agent[] agent;
    private static Network[] network;
    private static ArrayList[][] connectedlayer;
    private static ArrayList[] connectedagent; //あるエージェントがどの他の発言できるエージェントとつながっているか

    public static void trialPriorReserch(long seed) {
        Paramerter.generateRandom(seed);
        initParamerter();
        GenerateGraph();
        setAgentRelationship();
        //displayAgentRelationship();
        //displayConnectedLayer();

        int x = 0;
        //displayOpinionAndExpress(1);
        while (x != Paramerter.loopnumber) {
            for(int i=0;i<Paramerter.agentnumber;i++){
                turnOfFormation3(i);
                pressureAndSilence(i);
            }
            x++;
        }
        for (int k = 0; k < 8; k++) {
           //displayOpinionAndExpress(k);
        }
    }

    public static void initParamerter() {
        agent = new Agent[Paramerter.agentnumber];
        network = new Network[Paramerter.layernumber];
        connectedlayer = new ArrayList[Paramerter.agentnumber][Paramerter.agentnumber];
        connectedagent = new ArrayList[Paramerter.agentnumber];

        for (int i = 0; i < Paramerter.agentnumber; i++) {
            agent[i] = new Agent(Paramerter.rand.nextDouble());
            connectedagent[i] = new ArrayList<Integer>();
        }
    }

    public static void GenerateGraph() {
        for (int i = 0; i < Paramerter.layernumber; i++) {
            network[i] = new Network();
            network[i].generateGraph();
            // network[i].displayLinking();
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
                while(! agent[parseInt(network[i].getNode()[n].get(m).toString())].isOpinionexpress(i)){
                    m = Paramerter.rand.nextInt(network[i].getNode()[n].size());
                }
                Agent.formationOfOpinion(agent[n],agent[parseInt(network[i].getNode()[n].get(m).toString())],i);}
        }
    }

    public static void turnOfFormation2(int n) {
        /**
         * すべての層とノードでエージェントが黙った場合にこの処理を飛ばす
         */
        resetCouldExchangeAgentRelationship(n);
        if(connectedagent[n].size()==0)
            return;
        int randomagentnumber = Paramerter.rand.nextInt(connectedagent[n].size());
        int randomagent = parseInt(connectedagent[n].get(randomagentnumber).toString());
        int randomlayernumber =Paramerter.rand.nextInt(connectedlayer[n][randomagent].size());
        int randomlayer = parseInt(connectedlayer[n][randomagent].get(randomlayernumber).toString());
        while(!agent[randomagent].isOpinionexpress(randomlayer)){
            randomlayernumber =Paramerter.rand.nextInt(connectedlayer[n][randomagent].size());
            randomlayer = parseInt(connectedlayer[n][randomagent].get(randomlayernumber).toString());
        }
        Agent.formationOfOpinion(agent[n],agent[randomagent],randomlayer);
    }

    /**
     * 微妙に未完成
     * @param n
     */
    public static void turnOfFormation3(int n){
        int falsetoexchange=0;
        for(int i=0;i<Paramerter.layernumber;i++){
            if(!agent[n].isOpinionexpress(i))
                falsetoexchange++;
        }
        if(falsetoexchange==Paramerter.layernumber)
            return;
        int randomlayer = Paramerter.rand.nextInt(Paramerter.layernumber);
        while(!agent[n].isOpinionexpress(randomlayer)){
            randomlayer=Paramerter.rand.nextInt(Paramerter.layernumber);
        }
        if(network[randomlayer].getNode()[n].size()==0)
            return;
        int randomagentnumber= Paramerter.rand.nextInt(network[randomlayer].getNode()[n].size());
        int randomagent = parseInt(network[randomlayer].getNode()[n].get(randomagentnumber).toString());
        if(!agent[randomagent].isOpinionexpress(randomlayer))
            return;
        Agent.formationOfOpinion(agent[n],agent[randomagent],randomlayer);
    }

    public static void setCouldExchangeAgentRelationship(){
        for(int i=0; i<Paramerter.agentnumber;i++){
            for(int j=0;j<Paramerter.layernumber;j++){
                for(int k=0;k<network[j].getNode()[i].size();k++){
                    int element = parseInt(network[j].getNode()[i].get(k).toString());
                    if(agent[element].isOpinionexpress(j))
                        connectedagent[i].add(element);
                }
            }
        }
    }

    public static void resetCouldExchangeAgentRelationship(int i){
        connectedagent[i].clear();
        for(int j=0;j<Paramerter.layernumber;j++){
            for(int k=0;k<network[j].getNode()[i].size();k++){
                int element = parseInt(network[j].getNode()[i].get(k).toString());
                if(agent[element].isOpinionexpress(j))
                    connectedagent[i].add(element);
            }
        }
    }

    /**
     * 別々のコミュニティで同じエージェント同士がつながっているとき、それらの関係性を各エージェントで記録する
     */
    public static void setAgentRelationship() {

        initConnectedLayer();

        /*
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.layernumber; j++) {
                for (int k = j; k < Paramerter.layernumber; k++) {
                    if (j == k)
                        continue;
                    for (int l = 0; l < network[j].getNode()[i].size(); l++) {
                        int element1 = parseInt(network[j].getNode()[i]
                                .get(l).toString());
                        for (int m = 0; m < network[k].getNode()[i].size(); m++) {
                            int element2 = parseInt(network[k]
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
        */
        for(int i=0;i<Paramerter.agentnumber;i++) {
            for (int j = 0; j < Paramerter.layernumber; j++) {
                for (int k = 0;k<network[j].getNode()[i].size();k++){
                    int element = parseInt(network[j].getNode()[i].get(k).toString());
                    connectedlayer[i][element].add(j);
                    if(agent[i].getAgentRelationship().indexOf(
                            element) == -1)
                        agent[i].getAgentRelationship().add(element);
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
        if(connectedlayer[i][j].size()==0)
            return;
        double maxexpress = agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(0).toString())];
        double minexpress = agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(0).toString())];
        for (int k = 0; k < connectedlayer[i][j].size(); k++) {
            if (maxexpress < agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(k).toString())])
                maxexpress = agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(k).toString())];
            if (minexpress > agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(k).toString())])
                minexpress = agent[i].opinionlayer[parseInt(connectedlayer[i][j].get(k).toString())];
        }
        double expressdiff = Math.abs(maxexpress
                - minexpress);
        if (expressdiff > Paramerter.allowance) {
            for (int k = 0; k < connectedlayer[i][j].size(); k++) {
                agent[i].setOpinionexpress(false, parseInt(connectedlayer[i][j].get(k).toString()));
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
