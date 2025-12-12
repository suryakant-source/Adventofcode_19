//Part-1
import java.io.*;
import java.util.*;

public class Main {
    static class E {
        int a, b;
        long d;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<int[]> pts = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;
            String[] p = line.split(",");
            int x = Integer.parseInt(p[0].trim());
            int y = Integer.parseInt(p[1].trim());
            int z = Integer.parseInt(p[2].trim());
            pts.add(new int[] { x, y, z });
        }
        int n = pts.size();
        if (n == 0) {
            System.out.println(0);
            return;
        }

        long mL = (long) n * (n - 1) / 2;
        if (mL > Integer.MAX_VALUE)
            throw new RuntimeException("too many edges");
        int m = (int) mL;
        E[] es = new E[m];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            int[] pi = pts.get(i);
            long xi = pi[0], yi = pi[1], zi = pi[2];
            for (int j = i + 1; j < n; j++) {
                int[] pj = pts.get(j);
                long dx = xi - pj[0];
                long dy = yi - pj[1];
                long dz = zi - pj[2];
                E e = new E();
                e.a = i;
                e.b = j;
                e.d = dx * dx + dy * dy + dz * dz;
                es[idx++] = e;
            }
        }

        Arrays.sort(es, 0, idx, (u, v) -> {
            int c = Long.compare(u.d, v.d);
            if (c != 0)
                return c;
            c = Integer.compare(u.a, v.a);
            if (c != 0)
                return c;
            return Integer.compare(u.b, v.b);
        });

        int[] p = new int[n];
        int[] sz = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = i;
            sz[i] = 1;
        }

        int k = 1000;
        int use = Math.min(k, idx);
        for (int i = 0; i < use; i++) {
            E e = es[i];
            int ra = f(p, e.a);
            int rb = f(p, e.b);
            if (ra != rb) {
                if (sz[ra] < sz[rb]) {
                    int t = ra;
                    ra = rb;
                    rb = t;
                }
                p[rb] = ra;
                sz[ra] += sz[rb];
            }
        }

        long[] cs = new long[n];
        int cCnt = 0;
        for (int i = 0; i < n; i++) {
            if (p[i] == i) {
                cs[cCnt++] = sz[i];
            }
        }
        if (cCnt == 0) {
            System.out.println(0);
            return;
        }
        Arrays.sort(cs, 0, cCnt);
        int take = Math.min(3, cCnt);
        long ans = 1;
        for (int i = 0; i < take; i++) {
            ans *= cs[cCnt - 1 - i];
        }
        System.out.println(ans);
    }

    static int f(int[] p, int x) {
        while (p[x] != x) {
            p[x] = p[p[x]];
            x = p[x];
        }
        return x;
    }
}


//Part-2
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l;
        long c = 0, m = 0, e = 0;
        while ((l = br.readLine()) != null) {
            l = l.trim();
            if (l.isEmpty()) continue;
            int len = l.length();
            c += len;
            m += mem(l);
            e += enc(l);
        }
        System.out.println(c - m);
        System.out.println(e - c);
    }

    private static int mem(String s) {
        int n = s.length(), r = 0, i = 1, e = n - 1;
        while (i < e) {
            char ch = s.charAt(i);
            if (ch != '\\') {
                r++;
                i++;
            } else {
                if (i + 1 >= e) break;
                char nx = s.charAt(i + 1);
                if (nx == '\\' || nx == '\"') {
                    r++;
                    i += 2;
                } else if (nx == 'x') {
                    if (i + 3 < n) {
                        r++;
                        i += 4;
                    } else break;
                } else {
                    r++;
                    i += 2;
                }
            }
        }
        return r;
    }

    private static int enc(String s) {
        int n = s.length(), extra = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '\"' || ch == '\\') extra++;
        }
        return n + 2 + extra;
    }
}
