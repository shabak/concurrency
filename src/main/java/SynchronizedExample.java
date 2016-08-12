import static java.lang.Thread.sleep;

/**
 * @author Nikolay Shabak (nikolay)
 * @since 11/08/16
 */
public final class SynchronizedExample {

    private static SynchronizedExample instance;

    private Integer balance;

    private SynchronizedExample() {
        balance = 10;
    }

    public static SynchronizedExample getInstance() {
        if (instance == null) {
            instance = new SynchronizedExample();
        }
        return instance;
    }

    public void withdraw(final int amount) {
        balance -= amount;
    }

    public Integer getBalance() {
        return balance;
    }

    private static abstract class AbstractCachePoint extends Thread {

        protected static final SynchronizedExample moneyAccount = getInstance();

        protected final Integer id;
        protected final String userName;
        protected final Integer cacheAmount;

        public AbstractCachePoint(Integer id, String userName, Integer cacheAmount) {
            this.id = id;
            this.userName = userName;
            this.cacheAmount = cacheAmount;
        }

        protected String getCheque(int previousAccountValue) {
            final StringBuilder message = new StringBuilder();
            message.append("Cash withdrawal by user ");
            message.append(userName);
            message.append(" from cash point ");
            message.append(id);
            message.append(" for ");
            message.append(cacheAmount);
            message.append(". Balance before withdrawal:  ");
            message.append(previousAccountValue);
            message.append(", after withdrawal: ");
            message.append(getInstance().getBalance());
            return message.toString();
        }
    }

    static final class CachePoint extends AbstractCachePoint {

        public CachePoint(Integer id, String userName, Integer cacheAmount) {
            super(id, userName, cacheAmount);
        }

        @Override
        public void run() {
            synchronized (moneyAccount) {
                final int previousAccountValue = getInstance().getBalance();

                if (previousAccountValue < cacheAmount) {
                    System.out.println(userName + " не смог снять деньги. Недостаточно средств");
                    return;
                }
                getInstance().withdraw(cacheAmount);

                System.out.println(getCheque(previousAccountValue));
            }
        }

    }

    static final class BrokenCachePoint extends AbstractCachePoint {

        public BrokenCachePoint(Integer id, String userName, Integer cacheAmount) {
            super(id, userName, cacheAmount);
        }

        @Override
        public void run() {
            final int previousAccountValue = getInstance().getBalance();

            //дадим потокам время запустится. Можно убрать этот кусок, но тогда будет сложнее воспроизвести
            // ситуацию с рассинхронизацией доступа к данным.
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //если на счете денег меньше, чем запрашивают, говорим что денег мало и не даем ничего
            //
            // Замечание: Эту проверку все 3 потока уже прошли успешно, так как их доступ к этому месту
            // не синхронизирован
            if (previousAccountValue < cacheAmount) {
                System.out.println(userName + " не смог снять деньги. Недостаточно средств");
                return;
            }
            //списываем указанную сумму
            //
            //Замечание: А в этом месте все 3 пототка с чистой совестью отнимают от существующего счета
            //требуемую сумму
            getInstance().withdraw(cacheAmount);

            System.out.println(getCheque(previousAccountValue));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Case #2. We use the good cash points");
        CachePoint point1 = new CachePoint(1, "User1", 5);
        CachePoint point2 = new CachePoint(2, "User2", 5);
        CachePoint point3 = new CachePoint(3, "User3", 5);

        point1.start();
        point2.start();
        point3.start();

        //подождем 100 мс. пока все потоки сделают своё дело
        sleep(1100);

        // установим значение счета обратно в 10
        getInstance().balance = 10;
        System.out.println();

        /**
         * Пример с неисправными банкоматами
         */

        System.out.println("Case #2. We use the broken cash points");
        BrokenCachePoint brokenPoint1 = new BrokenCachePoint(1, "User1", 5);
        BrokenCachePoint brokenPoint2 = new BrokenCachePoint(2, "User2", 5);
        BrokenCachePoint brokenPoint3 = new BrokenCachePoint(3, "User3", 5);

        brokenPoint1.start();
        brokenPoint2.start();
        brokenPoint3.start();

    }

}
