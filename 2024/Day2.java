//Part-1

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = 0;
        while (sc.hasNextLine()) {
            String s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            String[] p = s.split("\\s+");
            int[] a = new int[p.length];
            for (int i = 0; i < p.length; i++) {
                a[i] = Integer.parseInt(p[i]);
            }
            if (isSafe(a)) c++;
        }
        sc.close();
        System.out.println(c);
    }

    static boolean isSafe(int[] a) {
        boolean inc = true, dec = true;
        for (int i = 1; i < a.length; i++) {
            int d = a[i] - a[i - 1];
            if (d < 1 || d > 3) inc = false;
            if (d > -1 || d < -3) dec = false;
        }
        return inc || dec;
    }
}

//Part-2
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = 0;
        while (sc.hasNextLine()) {
            String s = sc.nextLine().trim();
            if (s.isEmpty())
                break;
            String[] p = s.split("\\s+");
            int[] a = new int[p.length];
            for (int i = 0; i < p.length; i++) {
                a[i] = Integer.parseInt(p[i]);
            }
            if (isSafeWithDampener(a))
                c++;
        }
        sc.close();
        System.out.println(c);
    }

    static boolean isSafe(int[] a) {
        boolean inc = true, dec = true;
        for (int i = 1; i < a.length; i++) {
            int d = a[i] - a[i - 1];
            if (d < 1 || d > 3)
                inc = false;
            if (d > -1 || d < -3)
                dec = false;
        }
        return inc || dec;
    }

    static boolean isSafeWithDampener(int[] a) {
        if (isSafe(a))
            return true;
        for (int i = 0; i < a.length; i++) {
            int[] b = new int[a.length - 1];
            int k = 0;
            for (int j = 0; j < a.length; j++) {
                if (j != i)
                    b[k++] = a[j];
            }
            if (isSafe(b))
                return true;
        }
        return false;
    }
}
