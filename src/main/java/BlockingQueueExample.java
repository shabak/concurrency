import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Nikolay Shabak (nikolay)
 * @since 10/08/16
 */
public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue<String> drop = new ArrayBlockingQueue(1, true);
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }

    private static class Producer implements Runnable {
        List<String> messages = Arrays.asList(
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "Wouldn't you eat ivy too?"
        );

        private BlockingQueue<String> drop;

        public Producer(BlockingQueue<String> d) {
            this.drop = d;
        }

        public void run() {
            try {
                for (String s : messages) {
                    drop.put(s);
                }
                drop.put("DONE");
            } catch (InterruptedException intEx) {
                System.out.println("Interrupted! " +
                                   "Last one out, turn out the lights!");
            }
        }
    }

    private static class Consumer implements Runnable {
        private BlockingQueue<String> drop;

        public Consumer(BlockingQueue<String> d) {
            this.drop = d;
        }

        public void run() {
            try {
                String msg = null;
                while (!((msg = drop.take()).equals("DONE"))) {
                    System.out.println(msg);
                }
            } catch (InterruptedException intEx) {
                System.out.println("Interrupted! " + "Last one out, turn out the lights!");
            }
        }
    }
}
