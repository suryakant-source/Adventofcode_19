//Part-1
import java.io.*;
import java.util.*;

public class Main {
    static Map<String, List<String>> g = new HashMap<>();
    static Map<String, Long> memo = new HashMap<>();

    static long dfs(String u) {
        if (u.equals("out"))
            return 1;
        if (memo.containsKey(u))
            return memo.get(u);
        long sum = 0;
        for (String v : g.getOrDefault(u, List.of()))
            sum += dfs(v);
        memo.put(u, sum);
        return sum;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty())
                continue;
            String[] p = s.split(":");
            String u = p[0].trim();
            String[] outs = p[1].trim().split(" ");
            List<String> list = new ArrayList<>();
            for (String t : outs)
                if (!t.isEmpty())
                    list.add(t);
            g.put(u, list);
        }
        System.out.println(dfs("you"));
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {

    static class S {
        String n;
        int d, f;

        S(String n, int d, int f) {
            this.n = n;
            this.d = d;
            this.f = f;
        }

        public int hashCode() {
            return Objects.hash(n, d, f);
        }

        public boolean equals(Object o) {
            if (!(o instanceof S))
                return false;
            S x = (S) o;
            return n.equals(x.n) && d == x.d && f == x.f;
        }
    }

    static Map<String, List<String>> g = new HashMap<>();
    static Map<S, Long> m = new HashMap<>();
    static Set<S> a = new HashSet<>();

    static long dfs(String n, int d, int f) {
        if (n.equals("dac"))
            d = 1;
        if (n.equals("fft"))
            f = 1;
        if (n.equals("out"))
            return (d == 1 && f == 1) ? 1 : 0;
        S st = new S(n, d, f);
        if (a.contains(st))
            return 0;
        if (m.containsKey(st))
            return m.get(st);
        a.add(st);
        long t = 0;
        for (String x : g.getOrDefault(n, List.of()))
            t += dfs(x, d, f);
        a.remove(st);
        m.put(st, t);
        return t;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ln;
        while ((ln = br.readLine()) != null) {
            ln = ln.trim();
            if (ln.isEmpty())
                continue;
            int i = ln.indexOf(':');
            if (i < 0)
                continue;
            String s = ln.substring(0, i).trim();
            String r = ln.substring(i + 1).trim();
            List<String> o = new ArrayList<>();
            if (!r.isEmpty())
                for (String w : r.split("\\s+"))
                    if (w.matches("[a-z]+"))
                        o.add(w);
            g.put(s, o);
        }
        System.out.println(dfs("svr", 0, 0));
    }
}
