//part-1
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        int pos = 50; 
        int countZero = 0; 

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            char dir = line.charAt(0);
            int dist = Integer.parseInt(line.substring(1));

            if (dir == 'L') {
                pos = (pos - dist) % 100;
                if (pos < 0)
                    pos += 100;
            } else { 
                pos = (pos + dist) % 100;
            }

            if (pos == 0)
                countZero++;
        }

        System.out.println(countZero);
    }
}

//Part -2 
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        int pos = 50; // starting dial position
        long countZero = 0; // total clicks landing on 0

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            char dir = line.charAt(0);
            int dist = Integer.parseInt(line.substring(1));

            // Count full 100-click cycles (each cycle guarantees one "0")
            int fullCycles = dist / 100;
            int rem = dist % 100;
            countZero += fullCycles;

            // Apply full cycles to position
            if (dir == 'L') {
                pos = (pos - fullCycles * 100) % 100;
            } else { // R
                pos = (pos + fullCycles * 100) % 100;
            }
            if (pos < 0)
                pos += 100;

            // Now simulate only the remaining <= 99 clicks
            for (int i = 0; i < rem; i++) {
                if (dir == 'L') {
                    pos--;
                    if (pos < 0)
                        pos = 99;
                } else {
                    pos++;
                    if (pos >= 100)
                        pos = 0;
                }
                if (pos == 0)
                    countZero++;
            }
        }

        System.out.println(countZero);
    }
}

