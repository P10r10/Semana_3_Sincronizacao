import java.util.Random;

class Account {  // thread safe with synchronized

    private double balance;

    public Account() {
        balance = 0;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    private class Client extends Thread {
        
        private double total = 0;
        private final Random random = new Random();
        
        @Override
        public void run() {
            while (!interrupted()) {
                int val = random.nextInt(4); // deposit [0-4[
                deposit(val);
                total += val;
            }
        }

        public double getTotal() {
            return total;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 10; // number of clients (threads)
        Account acc = new Account();
        Client[] clients = new Client[THREADS];
        double totalDeposited = 0;
        for (int i = 0; i < THREADS; i++) {
            clients[i] = acc.new Client();
        }
        for (int i = 0; i < THREADS; i++) {
            clients[i].start();
        }
        Thread.sleep(5); // 5 milliseconds
        for (int i = 0; i < THREADS; i++) {
            clients[i].interrupt();
        }
        for (int i = 0; i < THREADS; i++) {
            clients[i].join();
        }

        System.out.println("  account balance: " + acc.getBalance());
        for (int i = 0; i < THREADS; i++) {
            totalDeposited += clients[i].getTotal();
        }
        System.out.println("clients deposited: " + totalDeposited);
    }
}