import org.apache.commons.math3.random.MersenneTwister;

/**
 * Created by Sumi on 2016/09/09.
 */
public class JustTesting {
    public static MersenneTwister ram = new MersenneTwister(1);
    private static double[] agent = new double[100];
    private static Network  network;

    public static void main(String[] args) {
        double total=0;
        for(int j=0;j<100;j++) {
            for (int i = 0; i < 100; i++) {
                agent[i] = ram.nextDouble();
            }
            GenerateGraph();
            /*
            for(int k=0;k<100000;k++){
                changeOpinion();
            }
            */
            System.out.println(Math.sqrt(evaluateDispersion()));
            total+=Math.sqrt(evaluateDispersion());
        }
        System.out.println(total/100);
    }

    public static double average(){
        double total=0;
        for(int i=0;i<100;i++){
            total+=agent[i];
        }
        return total/100;
    }

    public static double evaluateDispersion() {
        double average = average();
        double total = 0;
        for (int i = 0; i < 100; i++) {
            total+= (agent[i]-average)*(agent[i]-average);
        }
        return total / 100;
    }
    public static void changeOpinion(){
        int agent1= ram.nextInt(100);
        if(network.getNode()[agent1].size()==0) return;
        int agent2 = ram.nextInt(network.getNode()[agent1].size());
        if(Math.abs(agent[agent1]-agent[agent2])>0.4){
            return;
        }
        double tmp=(agent[agent1]+agent[agent2])/2;
        agent[agent1]=tmp;
        agent[agent2]=tmp;
    }

    public static void GenerateGraph() {
            network = new Network();
            network.generateGraphForJustTesting();
            // network[i].displayLinking();
    }
}
