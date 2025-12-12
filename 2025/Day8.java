//Part-1
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l;
        long c = 0, m = 0;
        while ((l = br.readLine()) != null) {
            l = l.trim();
            if (l.isEmpty()) continue;
            c += l.length();
            m += mem(l);
        }
        System.out.println(c - m);
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
