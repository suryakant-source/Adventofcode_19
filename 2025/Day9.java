//Part-1
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        List<int[]> v = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty())
                continue;
            String[] p = s.split(",");
            v.add(new int[] { Integer.parseInt(p[0]), Integer.parseInt(p[1]) });
        }
        int n = v.size();
        long mx = 0;
        for (int i = 0; i < n; i++) {
            int[] a = v.get(i);
            for (int j = i + 1; j < n; j++) {
                int[] b = v.get(j);
                long w = Math.abs(a[0] - b[0]) + 1;
                long h = Math.abs(a[1] - b[1]) + 1;
                long ar = w * h;
                if (ar > mx)
                    mx = ar;
            }
        }
        System.out.println(mx);
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {

    static long[][] r;
    static int n;

    static boolean in(long x, long y) {
        boolean c = false;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            long x1 = r[i][0], y1 = r[i][1], x2 = r[j][0], y2 = r[j][1];
            boolean k = ((y1 > y) != (y2 > y)) && (x < (double) (x2 - x1) * (y - y1) / (double) (y2 - y1) + x1);
            if (k)
                c = !c;
        }
        return c;
    }

    static boolean on(long x, long y) {
        for (int i = 0; i < n; i++) {
            long x1 = r[i][0], y1 = r[i][1], x2 = r[(i + 1) % n][0], y2 = r[(i + 1) % n][1];
            if (x1 == x2) {
                if (x == x1 && y >= Math.min(y1, y2) && y <= Math.max(y1, y2))
                    return true;
            } else if (y1 == y2) {
                if (y == y1 && x >= Math.min(x1, x2) && x <= Math.max(x1, x2))
                    return true;
            }
        }
        return false;
    }

    static boolean inside(long x, long y) {
        return on(x, y) || in(x, y);
    }

    static boolean cut(long x1, long y1, long x2, long y2) {
        for (int i = 0; i < n; i++) {
            long ax = r[i][0], ay = r[i][1], bx = r[(i + 1) % n][0], by = r[(i + 1) % n][1];
            if (ax == bx) {
                long x = ax;
                if (x > x1 && x < x2) {
                    long sy = Math.min(ay, by), ey = Math.max(ay, by);
                    if (ey >= y1 + 1 && sy <= y2 - 1)
                        return true;
                }
            } else {
                long y = ay;
                if (y > y1 && y < y2) {
                    long sx = Math.min(ax, bx), ex = Math.max(ax, bx);
                    if (ex >= x1 + 1 && sx <= x2 - 1)
                        return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        ArrayList<long[]> v = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty())
                continue;
            String[] p = s.split(",");
            v.add(new long[] { Long.parseLong(p[0]), Long.parseLong(p[1]) });
        }
        n = v.size();
        r = new long[n][2];
        for (int i = 0; i < n; i++) {
            r[i][0] = v.get(i)[0];
            r[i][1] = v.get(i)[1];
        }

        long mx = 0;

        for (int i = 0; i < n; i++) {
            long x1 = r[i][0], y1 = r[i][1];
            for (int j = i + 1; j < n; j++) {
                long x2 = r[j][0], y2 = r[j][1];

                long xa = Math.min(x1, x2), xb = Math.max(x1, x2);
                long ya = Math.min(y1, y2), yb = Math.max(y1, y2);

                if (!inside(xa, ya))
                    continue;
                if (!inside(xa, yb))
                    continue;
                if (!inside(xb, ya))
                    continue;
                if (!inside(xb, yb))
                    continue;

                if (cut(xa, ya, xb, yb))
                    continue;

                long ar = (xb - xa + 1) * (yb - ya + 1);
                if (ar > mx)
                    mx = ar;
            }
        }

        System.out.println(mx);
    }
}

