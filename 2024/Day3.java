//Part-1

import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.isEmpty()) break;
            sb.append(s);
        }
        sc.close();
        String in = sb.toString();
        Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher m = p.matcher(in);
        long t = 0;
        while (m.find()) {
            int a = Integer.parseInt(m.group(1));
            int b = Integer.parseInt(m.group(2));
            t += (long) a * b;
        }
        System.out.println(t);
    }
}

//Part-2
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.isEmpty())
                break;
            sb.append(s);
        }
        sc.close();
        String in = sb.toString();
        Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
        Matcher m = p.matcher(in);
        long t = 0;
        boolean e = true;
        while (m.find()) {
            String x = m.group();
            if (x.equals("do()")) {
                e = true;
            } else if (x.equals("don't()")) {
                e = false;
            } else if (e) {
                int a = Integer.parseInt(m.group(1));
                int b = Integer.parseInt(m.group(2));
                t += (long) a * b;
            }
        }
        System.out.println(t);
    }
}
