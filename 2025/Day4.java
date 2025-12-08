//Part-1
import java.io.*;
import java.util.*;

public class Main {
    static int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> g = new ArrayList<>();
        String s;

        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty())
                continue;
            g.add(s);
        }

        int r = g.size();
        int c = g.get(0).length();
        int ans = 0;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (g.get(i).charAt(j) != '@')
                    continue;

                int cnt = 0;
                for (int k = 0; k < 8; k++) {
                    int ni = i + dr[k], nj = j + dc[k];
                    if (ni < 0 || nj < 0 || ni >= r || nj >= c)
                        continue;
                    if (g.get(ni).charAt(nj) == '@')
                        cnt++;
                }
                if (cnt < 4)
                    ans++;
            }
        }

        System.out.println(ans);
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {
    static int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> g0 = new ArrayList<>();
        String s;

        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (!s.isEmpty())
                g0.add(s);
        }

        int r = g0.size(), c = g0.get(0).length();
        char[][] g = new char[r][c];
        for (int i = 0; i < r; i++)
            g[i] = g0.get(i).toCharArray();

        int[][] d = new int[r][c];
        ArrayDeque<int[]> q = new ArrayDeque<>();
      
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (g[i][j] != '@')
                    continue;
                int cnt = 0;
                for (int k = 0; k < 8; k++) {
                    int ni = i + dr[k], nj = j + dc[k];
                    if (ni < 0 || nj < 0 || ni >= r || nj >= c)
                        continue;
                    if (g[ni][nj] == '@')
                        cnt++;
                }
                d[i][j] = cnt;
                if (cnt < 4)
                    q.add(new int[] { i, j });
            }
        }

        long ans = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int i = cur[0], j = cur[1];
            if (g[i][j] != '@')
                continue;

            g[i][j] = '.';
            ans++;

            for (int k = 0; k < 8; k++) {
                int ni = i + dr[k], nj = j + dc[k];
                if (ni < 0 || nj < 0 || ni >= r || nj >= c)
                    continue;
                if (g[ni][nj] != '@')
                    continue;

                d[ni][nj]--;
                if (d[ni][nj] < 4)
                    q.add(new int[] { ni, nj });
            }
        }

        System.out.println(ans);
    }
}
