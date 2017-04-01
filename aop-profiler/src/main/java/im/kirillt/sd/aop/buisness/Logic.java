package im.kirillt.sd.aop.buisness;

public class Logic {

    static final int BASE_SLEEP = 500;

    public void start() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(i * BASE_SLEEP);
            firstStep(i);
        }
    }

    public void firstStep(int steps) throws InterruptedException {
        if (steps <= 0) {
            return;
        }
        System.err.println(String.format("firstStep(%d)", steps));
        for (int i = 0; i < steps; i++) {
            Thread.sleep(2 * BASE_SLEEP);
            firstStep(i - 1);
        }
    }
}
