import org.apache.commons.math3.random.MersenneTwister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Sumi on 2016/08/31.
 */
public class Testing {
    private static final int looptime = 50;
    private static long seed;
    private static MersenneTwister random = new MersenneTwister(1);

    public static void main(String[] args) {

    /*    for (int i = 0; i < looptime; i++) {
            seed = random.nextLong();
            PriorReserch.trialPriorReserch(seed);
        }
*/
        test4_1_1();
    }

    public static void test4_1_1() {
        double average;
        try {
            File file = new File("D:\\4年\\研究室\\卒業論文\\先行研究再現\\先行研究出力\\test.txt");

            if (checkBeforeWritefile(file)) {
                FileWriter filewriter = new FileWriter(file);

                for (int i = 0; i <= 10; i++) {
                    Paramerter.setConnectivity(0.1 * i);
                    Paramerter.setAllowance(0.3);
                    average=0;
                    System.out.println(i);
                    for (int j = 0; j < looptime; j++) {
                        seed = random.nextLong();
                        PriorReserch.trialPriorReserch(seed);
                        average += evaluateDispersion();
                    }
                    System.out.println("date:"+Math.sqrt(average / looptime));
                    filewriter.write(Paramerter.getAllowance() + " " + String.format("%2f", Paramerter.getConnectivity()) + " " + Math.sqrt(average / looptime) + "\n");
                }
                filewriter.write("\n");
                /*
                for (int i = 0; i <= 5; i++) {
                    Paramerter.setConnectivity(0.2 * i);
                    Paramerter.setAllowance(0.5);
                    average=0;
                    for (int j = 0; j < looptime; j++) {
                        seed = random.nextLong();
                        PriorReserch.trialPriorReserch(seed);
                        average += evaluateDispersion();

                    }
                    filewriter.write(Paramerter.getAllowance() + " " + String.format("%2f", Paramerter.getConnectivity()) + " " + Math.sqrt(average / looptime) + "\n");
                }
                */
                filewriter.write("\n");
                filewriter.close();
            } else {
                System.out.println("ファイルに書き込めません");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static boolean checkBeforeWritefile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            }
        }

        return false;
    }

    public static double evaluateAverage() {
        double total = 0;
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.layernumber; j++) {
                total += PriorReserch.agent[i].opinionlayer[j];
            }
        }
        //System.out.println("average:"+total/(Paramerter.agentnumber*Paramerter.layernumber));
        return total / (Paramerter.agentnumber * Paramerter.layernumber);
    }

    public static double evaluateDispersion() {
        double average = evaluateAverage();
        double total = 0;
        for (int i = 0; i < Paramerter.agentnumber; i++) {
            for (int j = 0; j < Paramerter.layernumber; j++) {
                total += (PriorReserch.agent[i].opinionlayer[j] - average) * (PriorReserch.agent[i].opinionlayer[j] - average);
            }
        }
        return total / (Paramerter.agentnumber * Paramerter.layernumber);
    }
}
