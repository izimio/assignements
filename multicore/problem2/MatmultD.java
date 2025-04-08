import java.util.Scanner;

public class MatmultD {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int thread_no=0;
        if (args.length==1) thread_no = Integer.valueOf(args[0]);
        else thread_no = 1;
    
        int[][] a = readMatrix();
        int[][] b = readMatrix();

        long startTime = System.currentTimeMillis();
        int[][] c = multMatrix(a, b, thread_no);
        long endTime = System.currentTimeMillis();

        // printMatrix(a);
        // printMatrix(b);
        // printMatrix(c);

        // System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime - startTime);
        // System.out.printf("Calculation Time: %d ms\n" , endTime-startTime);
        double performance = 1.0 / ((endTime-startTime) / 1000.0);
        System.out.println(performance);
    }

    public static int[][] readMatrix() {
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = sc.nextInt();

        return result;
    }

    public static int[][] multMatrix(int[][] a, int[][] b, int thread_no) {
        if (a.length == 0) return new int[0][0];
        if (a[0].length != b.length) return null;

        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        int[][] result = new int[m][p];
        Thread[] threads = new Thread[thread_no];

        int rowsPerThread = (m + thread_no - 1) / thread_no; // ceiling division

        for (int t = 0; t < thread_no; t++) {
            final int threadId = t;
            final int startRow = threadId * rowsPerThread;
            final int endRow = Math.min(startRow + rowsPerThread, m);

            threads[t] = new Thread(() -> {
                long threadStart = System.currentTimeMillis();

                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < p; j++) {
                        int sum = 0;
                        for (int k = 0; k < n; k++) {
                            sum += a[i][k] * b[k][j];
                        }
                        result[i][j] = sum;
                    }
                }

                long threadEnd = System.currentTimeMillis();
                // System.out.printf("Thread %2d executed in %4d ms\n", threadId, threadEnd - threadStart);
            });

            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
