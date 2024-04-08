package by.spaces.calculator.calculations;

import java.util.Arrays;
import java.util.concurrent.*;

public class PrimeNumbersCount {
    private static int MAX;
    private static int cycle;
    private static int startPos;
    private static int threadsNum;

    public PrimeNumbersCount(int start, int max, int threads,int cycleParam){
        startPos = start;
        MAX = max;
        cycle = cycleParam;
        threadsNum = threads;
    }

    public PrimeNumbersCount(int start, int max, int threads){
        startPos = start;
        MAX = max;
        cycle = 10000;
        threadsNum = threads;
    }

    public PrimeNumbersCount(int start, int max){
        startPos = start;
        MAX = max;
        cycle = 10000;
        threadsNum = MAX >= 50_000_000 ? 4 : 2;
    }

    public PrimeNumbersCount(int max){
        startPos = 2;
        MAX = max;
        cycle = 10000;
        threadsNum = MAX >= 50_000_000 ? 4 : 2;
    }

    private static class CountPrimesTask implements Callable<Integer> {
        private int count;
        private final int start;
        private final int threadsNum;
        private final boolean[] isPrimeArray;

        public CountPrimesTask(int start, int threadsNum) {
            this.count = 0;
            this.start = start;
            this.threadsNum = threadsNum;
            this.isPrimeArray = new boolean[cycle + 1];
        }

        @Override
        public Integer call() {
            int i;
            for (i = start; i < MAX - cycle; i += cycle * threadsNum) {
                count += countPrimes(i, i + cycle, isPrimeArray);
            }
            if (i - MAX < 0) {
                count += countPrimes(i, MAX + 1, isPrimeArray);
            }
            return count;
        }

        private int countPrimes(int start, int end, boolean[] isPrime) {
            Arrays.fill(isPrime, true);
            int primeCount = 0;
            for (int i = 2; i * i <= end; i++) {
                if (i >= start && !isPrime[i - start]) continue;
                for (int j = Math.max(i * i, (start + i - 1) / i * i); j <= end; j += i) {
                    isPrime[j - start] = false;
                }
            }
            for (int i = Math.max(start, 2); i < end; i++) {
                if (isPrime[i - start]) primeCount++;
            }
            return primeCount;
        }
    }

    public String[] calculatePrimeCount() throws ExecutionException, InterruptedException {
        if (threadsNum < 1 || threadsNum > 30)
            throw new IllegalArgumentException("the number of threads should be from 1 to 30");

        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);
        Future<Integer>[] futures = new Future[threadsNum];

        for (int i = 0; i < threadsNum; i++) {
            int start = i * cycle + startPos;
            futures[i] = executor.submit(new CountPrimesTask(start, threadsNum));
        }

        long startTimeMulti = System.currentTimeMillis();
        int countMulti = 0;
        for (int i = 0; i < threadsNum; i++) {
            try {
                countMulti += futures[i].get();
            } catch (InterruptedException | ExecutionException e) {
                executor.shutdownNow();
                throw e;
            }
        }

        double elapsedTimeMulti = (System.currentTimeMillis() - startTimeMulti) / 1000.0;
        executor.shutdown();
        return new String[]{String.valueOf(countMulti), String.valueOf(elapsedTimeMulti)};
    }
}
