//Part-1
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        while (sc.hasNextLine()) {
            String s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            String[] p = s.split("\\s+");
            l.add(Integer.parseInt(p[0]));
            r.add(Integer.parseInt(p[1]));
        }
        sc.close();
        Collections.sort(l);
        Collections.sort(r);
        long t = 0;
        for (int i = 0; i < l.size(); i++) {
            t += Math.abs(l.get(i) - r.get(i));
        }
        System.out.println(t);
    }
}

//Part 2
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> l = new ArrayList<>();
        Map<Integer, Integer> r = new HashMap<>();
        while (sc.hasNextLine()) {
            String s = sc.nextLine().trim();
            if (s.isEmpty())
                break;
            String[] p = s.split("\\s+");
            l.add(Integer.parseInt(p[0]));
            int v = Integer.parseInt(p[1]);
            r.put(v, r.getOrDefault(v, 0) + 1);
        }
        sc.close();
        long t = 0;
        for (int n : l) {
            t += (long) n * r.getOrDefault(n, 0);
        }
        System.out.println(t);
    }
}
