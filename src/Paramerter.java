import org.apache.commons.math3.random.MersenneTwister;

public class Paramerter {
    /**
     * 実験でのループ回数
     */
    public static final int loopnumber = 100000;
    public static final int agentnumber = 100;
    public static final int layernumber = 8;
    /**
     * すり合わせ許容度
     */
    public static final double confornitybias = 0.4;
    /**
     * ネットワーク間の接続性
     */
    public static double connectivity = 0.5;
    /**
     * 一貫性のなさへの許容度
     */
    public static double allowance = 0.3;
    /**
     * ランダムネットワークの接続次数の確率
     */
    public static double probability = 0.1;
    /**
     * 乱数生成
     */
    public static MersenneTwister rand;

    public static void generateRandom(long seed) {
        rand = new MersenneTwister(seed);
    }

    public static void setConnectivity(double i) {
        connectivity = i;
    }

    public static void setAllowance(double i) {
        allowance = i;
    }

    public static double getConnectivity() {
        return connectivity;
    }

    public static double getAllowance() {
        return allowance;
    }
}
