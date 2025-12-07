//Part-1
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        long ans = 0;

        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty()) continue;

            int best = -1;
            int mx = -1;

            for (int i = 0; i < s.length(); i++) {
                int d = s.charAt(i) - '0';
                if (mx != -1) {
                    int v = mx * 10 + d;
                    if (v > best) best = v;
                }
                if (d > mx) mx = d;
            }

            if (best > 0) ans += best;
        }

        System.out.println(ans);
    }
}
//Part-2
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        long ans = 0;

        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty())
                continue;
            int k = 12;
            int n = s.length();
            int rem = n - k;
            Deque<Character> st = new ArrayDeque<>();

            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);

                while (!st.isEmpty() && rem > 0 && st.peekLast() < c) {
                    st.pollLast();
                    rem--;
                }
                st.addLast(c);
            }

            while (st.size() > k)
                st.pollLast();
            long v = 0;
            for (char c : st)
                v = v * 10 + (c - '0');

            ans += v;
        }

        System.out.println(ans);
    }
}
