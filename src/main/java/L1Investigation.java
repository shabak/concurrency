/**
 * Created by nikolay on 9/8/16.
 */
public class L1Investigation {
    final static int ARRAY_SIZE = 2 * 1024 * 1024;
    public static final int COUNT = 10;

    public static void main(String[] args) {
        byte[] array = new byte[64 * 1024];
        for (int testIndex = 0; testIndex < 10; testIndex++) {
            testMethod(array);
            System.out.println("---");
        }
    }
    private static void testMethod(byte[] array) {
        for (int len = 8192; len <= array.length; len += 8192) {
            long sum = 0;
            long t0 = System.nanoTime();
            for (int n = 0; n < 100; n++) {
                for (int index = 0; index < len; index += 64) {
                    array[index] = 1;
                }
            }
            long dT = System.nanoTime() - t0;

            if (sum > 0) {
                throw new Error();
            }

            System.out.println("len: " + len + ", dT: " + dT + ", 10*dT/len: " + (10 * dT) / len);
        }
    }
}
