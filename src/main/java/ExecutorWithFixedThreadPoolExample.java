import static java.lang.Thread.sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Nikolay Shabak (nikolay)
 * @since 30/09/16
 */
public class ExecutorWithFixedThreadPoolExample {

    private static int workersCount = 10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(workersCount);
        executorService.invokeAll(IntStream.range(0, workersCount)
                .mapToObj(currentWorker -> Executors.callable(
                        () -> {
                            try {
                                System.out.println("Start work #" + currentWorker);
                                for (int i = 0; i < 5; i++) {
                                    sleep(100);
                                    System.out.println("Milestone #" + i + " work #" + currentWorker);
                                }
                                System.out.println("End work #" + currentWorker);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                ))
                .collect(Collectors.toList()));
        System.out.println("Hi there!");
    }
}
