import java.util.ArrayList;


public class Network {
    private static double p = Paramerter.probability;        //確率p
    private static StringBuffer buf = new StringBuffer();

    private int node_number = Paramerter.agentnumber;
    private ArrayList[] node;

    public Network() {
        setNode(new ArrayList[node_number]);
        for (int i = 0; i < node_number; i++) {
            getNode()[i] = new ArrayList<Integer>();
        }
    }

    public void generateGraph() {
        for (int i = 0; i < node_number; i++) {
            for (int j = i; j < node_number; j++) {
                if (i == j) continue;
                if (p >= Paramerter.rand.nextDouble()) {
                    getNode()[i].add(new Integer(j));
                }
            }
        }
        correctionToUndirectedGraph();
        //これはやったほうがいいときと、そうでないときがありそう
    }

    public void generateGraphForJustTesting() {
        for (int i = 0; i < node_number; i++) {
            for (int j = i; j < node_number; j++) {
                if (i == j) continue;
                if (p >= JustTesting.ram.nextDouble()) {
                    getNode()[i].add(new Integer(j));
                }
            }
        }
        correctionToUndirectedGraph();
        //これはやったほうがいいときと、そうでないときがありそう
    }

    public void correctionToUndirectedGraph() {
        for (int i = 0; i < node_number; i++) {
            for (int j = 0; j < getNode()[i].size(); j++) {
                int addelement = Integer.parseInt(getNode()[i].get(j).toString());
                if (getNode()[addelement].indexOf(i) == -1)
                    getNode()[addelement].add(new Integer(i));
            }
        }
    }

    public void displayLinking() {
        for (int i = 0; i < node_number; i++) {
            for (int j = 0; j < getNode()[i].size(); j++) {
                String element = getNode()[i].get(j).toString();
                buf.append(element);
                buf.append(",");
            }
            String elementline = buf.toString();
            buf.delete(0, buf.length());
            System.out.println(i + ":" + elementline);
        }
    }

    public ArrayList[] getNode() {
        return node;
    }

    public void setNode(ArrayList[] node) {
        this.node = node;
    }
}
