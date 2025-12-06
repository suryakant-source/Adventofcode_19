//part-1
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        String[] arr = s.split(",");
        long ans = 0;

        for (String r : arr) {
            if (r.isEmpty())
                continue;
            String[] p = r.split("-");
            long l = Long.parseLong(p[0]);
            long h = Long.parseLong(p[1]);

            for (long x = l; x <= h; x++) {
                if (bad(x))
                    ans += x;
            }
        }

        System.out.println(ans);
    }

    static boolean bad(long x) {
        String s = Long.toString(x);
        int n = s.length();
        if ((n & 1) == 1)
            return false;
        int m = n / 2;
        return s.substring(0, m).equals(s.substring(m));
    }
}
//part-2
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        String[] a = s.split(",");
        long ans = 0;

        for (String r : a) {
            if (r.isEmpty())
                continue;
            String[] b = r.split("-");
            long L = Long.parseLong(b[0]);
            long R = Long.parseLong(b[1]);

            for (long x = L; x <= R; x++) {
                if (bad(x))
                    ans += x;
            }
        }

        System.out.println(ans);
    }
    static boolean bad(long x) {
        String s = Long.toString(x);
        int n = s.length();
        for (int d = 1; d * 2 <= n; d++) {
            if (n % d != 0)
                continue; 
            int rep = n / d;
            if (rep < 2)
                continue;

            String t = s.substring(0, d);
            boolean ok = true;
            for (int i = d; i < n; i += d) {
                if (!s.substring(i, i + d).equals(t)) {
                    ok = false;
                    break;
                }
            }
            if (ok)
                return true;
        }

        return false;
    }
}
