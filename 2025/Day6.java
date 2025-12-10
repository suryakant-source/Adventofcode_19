//Part-1
import java.io.*;
import java.util.*;

public class Main {
    static boolean blankCol(char[][] g, int R, int c) {
        for (int i = 0; i < R; i++) {
            if (g[i][c] != ' ')
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> a = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            a.add(s);
        }

        int R = a.size();
        int C = 0;
        for (String t : a)
            C = Math.max(C, t.length());

        char[][] g = new char[R][C];
        for (int i = 0; i < R; i++) {
            String t = a.get(i);
            for (int j = 0; j < C; j++) {
                g[i][j] = j < t.length() ? t.charAt(j) : ' ';
            }
        }

        long ans = 0;
        int j = 0;

        while (j < C) {
            if (blankCol(g, R, j)) {
                j++;
                continue;
            }

            int L = j;
            while (j < C && !blankCol(g, R, j))
                j++;
            int Rcol = j - 1;

            char op = '?';
            for (int i = 0; i < R; i++) {
                for (int c = L; c <= Rcol; c++) {
                    char ch = g[i][c];
                    if (ch == '+' || ch == '*')
                        op = ch;
                }
            }

            // extract numbers in this block
            ArrayList<Long> nums = new ArrayList<>();
            for (int i = 0; i < R; i++) {
                int c = L;
                while (c <= Rcol) {
                    char ch = g[i][c];
                    if (Character.isDigit(ch)) {
                        long v = 0;
                        while (c <= Rcol && Character.isDigit(g[i][c])) {
                            v = v * 10 + (g[i][c] - '0');
                            c++;
                        }
                        nums.add(v);
                    } else {
                        c++;
                    }
                }
            }

            long val = (op == '+') ? 0L : 1L;
            for (long x : nums) {
                if (op == '+')
                    val += x;
                else
                    val *= x;
            }

            ans += val;
        }

        System.out.println(ans);
    }
}
//Part-2
import java.io.*;
import java.util.*;

public class Main {
    static boolean blankCol(char[][] g, int R, int c) {
        for (int i = 0; i < R; i++) {
            if (g[i][c] != ' ')
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> a = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            a.add(s);
        }

        int R = a.size();
        int C = 0;
        for (String t : a)
            C = Math.max(C, t.length());

        char[][] g = new char[R][C];
        for (int i = 0; i < R; i++) {
            String t = a.get(i);
            for (int j = 0; j < C; j++) {
                g[i][j] = (j < t.length()) ? t.charAt(j) : ' ';
            }
        }

        long ans = 0;
        int j = 0;

        while (j < C) {

            if (blankCol(g, R, j)) {
                j++;
                continue;
            }

            int L = j;
            while (j < C && !blankCol(g, R, j))
                j++;
            int Rc = j - 1;

            char op = '?';
            for (int c = L; c <= Rc; c++) {
                char ch = g[R - 1][c];
                if (ch == '+' || ch == '*')
                    op = ch;
            }

            ArrayList<Long> nums = new ArrayList<>();
            for (int c = L; c <= Rc; c++) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < R - 1; i++) {
                    char ch = g[i][c];
                    if (Character.isDigit(ch))
                        sb.append(ch);
                }
                if (sb.length() > 0) {
                    nums.add(Long.parseLong(sb.toString()));
                }
            }

            long val = (op == '+') ? 0L : 1L;
            for (long x : nums) {
                if (op == '+')
                    val += x;
                else
                    val *= x;
            }

            ans += val;
        }

        System.out.println(ans);
    }
}
