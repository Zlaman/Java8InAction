package lambdasinaction.chap1;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BenchmarkOfParallelStream {
    public static void main(String... args) {
        String[] array = new String[1000000];
        Arrays.fill(array, "AbadadwbasdasdasdagalamagA");
        System.out.println("Warmup...");
        for (int i = 0; i < 20; ++i) {
            sequential(array);
            parallel(array);
        }
        System.out.println("Benchmark...");
        for (int i = 0; i < 5; ++i) {
            System.out.printf("Run %d:  sequential %s  -  parallel %s\n",
                    i,
                    test(() -> sequential(array)),
                    test(() -> parallel(array)));
        }
    }
    private static void sequential(String[] array) {
        Arrays.stream(array).map(String::toLowerCase).collect(Collectors.toList());
    }
    private static void parallel(String[] array) {
        Arrays.stream(array).parallel().map(String::toLowerCase).collect(Collectors.toList());
    }
    private static String test(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long elapsed = System.currentTimeMillis() - start;
        return String.format("%4.2fs", elapsed / 1000.0);
    }
}