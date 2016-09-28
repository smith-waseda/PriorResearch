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
        test5_1_1(0.3);
    }

    public static void test4_1_1(double allowance) {
        System.out.println("test4_1_1:"+allowance);
        double average;
        try {
            File file = new File("D:\\4年\\研究室\\卒業論文\\先行研究再現\\先行研究出力\\test.txt");
            if ( !file.exists())
                file = new File("E:\\先行研究\\test.txt");

            if (checkBeforeWritefile(file)) {
                FileWriter filewriter = new FileWriter(file);

                for (int i = 0; i <= 10; i++) {
                    Paramerter.setConnectivity(0.1 * i);
                    Paramerter.setAllowance(allowance);
                    average=0;
                    System.out.println(i);
                    for (int j = 0; j < looptime; j++) {
                        seed = random.nextLong();
                        PriorReserch.trialPriorReserch(seed);
                        average += evaluateDispersion();
                        /*
                        if(i>=7 && i<=10){
                            System.out.println("date:"+i+":"+Math.sqrt(evaluateDispersion()));
                        }
                        */
                    }
                    System.out.println("date:"+Math.sqrt(average / looptime));
                    filewriter.write(Paramerter.getAllowance() + " " + String.format("%2f", Paramerter.getConnectivity()) + " " + Math.sqrt(average / looptime) + "\n");
                }
                filewriter.write("\n");
                filewriter.close();
            } else {
                System.out.println("ファイルに書き込めません");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void test4_1_2() {
        System.out.println("test4_1_2");
        double average;
        try {
            File file = new File("D:\\4年\\研究室\\卒業論文\\先行研究再現\\先行研究出力\\test2.txt");

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
                        average += differenceBetweenMinAndMax();
                    }
                    System.out.println("date:"+ (average / looptime));
                    filewriter.write(Paramerter.getAllowance() + " " + String.format("%2f", Paramerter.getConnectivity()) + " " + (average / looptime) + "\n");
                }
                filewriter.write("\n");
            } else {
                System.out.println("ファイルに書き込めません");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void test5_1_1(double allowance) {
        System.out.println("test5_1_1:"+allowance);
        double average;
        try {
            File file = new File("D:\\4年\\研究室\\卒業論文\\先行研究再現\\先行研究出力\\test3.txt");
            if ( !file.exists())
                file = new File("E:\\先行研究\\test.txt");

            if (checkBeforeWritefile(file)) {
                FileWriter filewriter = new FileWriter(file);

                for (int i = 0; i <= 10; i++) {
                    Paramerter.setConnectivity(0.1 * i);
                    Paramerter.setAllowance(allowance);
                    average=0;
                    System.out.println(i);
                    for (int j = 0; j < looptime; j++) {
                        seed = random.nextLong();
                        PriorReserch.trialPriorReserch(seed);
                        average += expressOpinion();
                    }
                    System.out.println("date:"+ (average / looptime));
                    filewriter.write(Paramerter.getAllowance() + " " + String.format("%2f", Paramerter.getConnectivity()) + " " + (average / looptime) + "\n");
                }
                filewriter.write("\n");
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

    public static double differenceBetweenMinAndMax(){
        double[] networkaverageopinion = new double[Paramerter.layernumber];
        for(int i=0;i<Paramerter.layernumber;i++){
            for(int j=0;j<Paramerter.agentnumber;j++){
                networkaverageopinion[i]+=PriorReserch.agent[j].opinionlayer[i];
            }
            networkaverageopinion[i]=networkaverageopinion[i]/Paramerter.agentnumber;
        }
        double max=networkaverageopinion[0];
        double min=networkaverageopinion[0];
        for(int i=0;i<Paramerter.layernumber;i++){
            if(max<networkaverageopinion[i])
                max=networkaverageopinion[i];
            if(min>networkaverageopinion[i])
                min=networkaverageopinion[i];
        }
        return max-min;
    }

    public static double expressOpinion(){
        double persentage=0;
        for(int i=0;i<Paramerter.agentnumber;i++){
            for(int j=0;j<Paramerter.layernumber;j++){
                if(!PriorReserch.agent[i].isOpinionexpress(j)){
                    break;
                }
                if(j==Paramerter.layernumber-1){
                    persentage+=0.01;
                }
            }
        }
        return persentage;
    }
}
