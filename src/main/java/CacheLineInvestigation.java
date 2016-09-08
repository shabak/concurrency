/**
 * Created by nikolay on 9/8/16.
 */
public class CacheLineInvestigation {
    final static int ARRAY_SIZE = 2 * 1024 * 1024;

    public static void main(String[] args) {
        long[] array = new long[ARRAY_SIZE];
        for (int i = 0; i < 10; i++) {
            testMethod(array);
            System.out.println("---");
        }
    }
    private static void testMethod(long[] array) {
        for (int stepSize = 1; stepSize <= 64; stepSize *= 2) {
            long sum = 0;
            long t0 = System.nanoTime();
            for (int n = 0; n < 10; n++) {
                for (int k = 0; k < array.length; k += stepSize) {
                    sum += array[stepSize];
                }
            }
            long t1 = System.nanoTime();

            if (sum > 0) {
                throw new Error();
            }
            int stepCount = ARRAY_SIZE / stepSize;
            System.out.println("stepSize: " + 8 * stepSize + ", dT / stepCount: " + (t1 - t0) / stepCount);
        }
    }
}
