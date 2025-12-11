//Part-1
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> a = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            if (s.endsWith("\r")) s = s.substring(0, s.length() - 1);
            a.add(s);
        }
        int R = a.size();
        char[][] g = new char[R][];
        for (int i = 0; i < R; i++) g[i] = a.get(i).toCharArray();

                int sr = -1, sc = -1;
        for (int i = 0; i < R; i++)
            for (int j = 0; j < g[i].length; j++)
                if (g[i][j] == 'S') { sr = i; sc = j; }

        long cnt = 0;
        final int BIG = 10000;
        Set<Integer> cur = new HashSet<>();

       +
        if (sr + 1 < R && sc < g[sr + 1].length)
            cur.add((sr + 1) * BIG + sc);

        while (!cur.isEmpty()) {
            Set<Integer> nxt = new HashSet<>();

            for (int code : cur) {
                int r = code / BIG;
                int c = code % BIG;

                if (r < 0 || r >= R) continue;
                if (c < 0 || c >= g[r].length) continue;

                char ch = g[r][c];
                if (ch == 'S') ch = '.';

                if (ch == '.') {
                    if (r + 1 < R && c < g[r + 1].length)
                        nxt.add((r + 1) * BIG + c);
                }
                else if (ch == '^') {  
                  cnt++;

                    int nr = r + 1;
                    if (nr < R) {
                        if (c - 1 >= 0 && c - 1 < g[nr].length)
                            nxt.add(nr * BIG + (c - 1));
                        if (c + 1 < g[nr].length)
                            nxt.add(nr * BIG + (c + 1));
                    }
                }
                else {
                    if (r + 1 < R && c < g[r + 1].length)
                        nxt.add((r + 1) * BIG + c);
                }
            }
            cur = nxt;
        }

        System.out.println(cnt);
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> a = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            if (s.endsWith("\r")) s = s.substring(0, s.length() - 1);
            if (s.isEmpty()) continue; 
            a.add(s);
        }
        int R = a.size();
        if (R == 0) {
            System.out.println(0);
            return;
        }

        char[][] g = new char[R][];
        for (int i = 0; i < R; i++) g[i] = a.get(i).toCharArray();
        int sr = -1, sc = -1;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (g[i][j] == 'S') {
                    sr = i;
                    sc = j;
                }
            }
        }
        if (sr == -1) {
            System.out.println(0);
            return;
        }
        long[][] dp = new long[R][];
        for (int i = 0; i < R; i++) dp[i] = new long[g[i].length];

        long ans = 0;

        
        if (sr + 1 < R && sc >= 0 && sc < g[sr + 1].length) {
            dp[sr + 1][sc] = 1;
        } else {
            System.out.println(1);
            return;
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < g[r].length; c++) {
                long w = dp[r][c];
                if (w == 0) continue;

                char ch = g[r][c];
                if (ch == 'S') ch = '.'; 

                int nr = r + 1;

                if (ch == '.') {
                    if (nr < R && c >= 0 && c < g[nr].length) {
                        dp[nr][c] += w;
                    } else {
                        ans += w;
                    }
                } else if (ch == '^') {
                    int lc = c - 1;
                    if (nr < R && lc >= 0 && lc < g[nr].length) {
                        dp[nr][lc] += w;
                    } else {
                        ans += w;
                    }
                    int rc = c + 1;
                    if (nr < R && rc >= 0 && rc < g[nr].length) {
                        dp[nr][rc] += w;
                    } else {
                        ans += w;
                    }
                } else {
                    if (nr < R && c >= 0 && c < g[nr].length) {
                        dp[nr][c] += w;
                    } else {
                        ans += w;
                    }
                }
            }
        }
        System.out.println(ans);
    }
}
