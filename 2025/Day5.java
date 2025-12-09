//Part-1
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> ls = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            ls.add(s);
        }

        int n = ls.size();
        int split = n;
        for (int i = 0; i < n; i++) {
            if (ls.get(i).trim().isEmpty()) {
                split = i;
                break;
            }
        }

        ArrayList<long[]> v = new ArrayList<>();
        for (int i = 0; i < split; i++) {
            String t = ls.get(i).trim();
            if (t.isEmpty())
                continue;
            String[] p = t.split("-");
            long a = Long.parseLong(p[0]);
            long b = Long.parseLong(p[1]);
            v.add(new long[] { a, b });
        }

        v.sort(Comparator.comparingLong(o -> o[0]));
        ArrayList<long[]> m = new ArrayList<>();
        for (long[] it : v) {
            if (m.isEmpty()) {
                m.add(new long[] { it[0], it[1] });
            } else {
                long[] last = m.get(m.size() - 1);
                if (it[0] <= last[1] + 0) { // overlapping or touching
                    if (it[1] > last[1])
                        last[1] = it[1];
                } else {
                    m.add(new long[] { it[0], it[1] });
                }
            }
        }

        long ans = 0;
      
        for (int i = split + 1; i < n; i++) {
            String t = ls.get(i).trim();
            if (t.isEmpty())
                continue;
            long x = Long.parseLong(t);
            if (fresh(x, m))
                ans++;
        }

        System.out.println(ans);
    }

    static boolean fresh(long x, ArrayList<long[]> m) {
        int lo = 0, hi = m.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long[] it = m.get(mid);
            if (x < it[0]) {
                hi = mid - 1;
            } else if (x > it[1]) {
                lo = mid + 1;
            } else {
                return true; // inside [L,R]
            }
        }
        return false;
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> ls = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            ls.add(s);
        }

        int n = ls.size();
        int sp = n;
        for (int i = 0; i < n; i++) {
            if (ls.get(i).trim().isEmpty()) {
                sp = i;
                break;
            }
        }

        ArrayList<long[]> v = new ArrayList<>();
        for (int i = 0; i < sp; i++) {
            s = ls.get(i).trim();
            if (s.isEmpty())
                continue;
            String[] p = s.split("-");
            long a = Long.parseLong(p[0]);
            long b = Long.parseLong(p[1]);
            v.add(new long[] { a, b });
        }

        if (v.isEmpty()) {
            System.out.println(0);
            return;
        }
        v.sort(Comparator.comparingLong(o -> o[0]));
        ArrayList<long[]> m = new ArrayList<>();
        long[] cur = v.get(0);
        long L = cur[0], R = cur[1];

        for (int i = 1; i < v.size(); i++) {
            long[] it = v.get(i);
            long a = it[0], b = it[1];
            if (a <= R) { 
                if (b > R)
                    R = b;
            } else { 
                m.add(new long[] { L, R });
                L = a;
                R = b;
            }
        }
        m.add(new long[] { L, R });

        long ans = 0;
        for (long[] it : m) {
            ans += (it[1] - it[0] + 1);
        }

        System.out.println(ans);
    }
}
