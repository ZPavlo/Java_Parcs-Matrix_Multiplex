import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

import java.io.File;
import java.util.Scanner;


public class Main {

    private static final int MAX_RANGE_SIZE = 100;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("input"));
        task curtask = new task();
        curtask.addJarFile("FindDiv.jar");
        AMInfo info = new AMInfo(curtask, null);

        long num = sc.nextInt();
        Range start = null;
        Range last = null;

        System.out.println("Input: " + num);

        for (int i = 2; i < num; i+= MAX_RANGE_SIZE)
        {
            Range current = new Range(num, i, Math.min(i + MAX_RANGE_SIZE, num) + 1);

            if (start == null)
            {
                start = current;
            }
            if (last != null) {
                last.setNext(current);
            }
            last =  current;
        }

        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("FindDiv");
        c.write(start);

        System.out.println("Waiting for result...");
        System.out.println("Result: " + (new FindDiv()).run_test(start));

        while (start != null)
        {
            for (Long n_i: start.getRes())
            {
                System.out.print(n_i + " ");
            }
            start = start.getNext();
        }
        curtask.end();
    }

}
