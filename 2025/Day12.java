//Part-1
import java.io.*;
import java.util.*;

public class Main {

    static class Shape {
        int area;
        int black, white;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null)
            lines.add(s);

        Map<Integer, Shape> shapes = new HashMap<>();
        int i = 0;

        // Read shapes
        while (i < lines.size() && lines.get(i).trim().matches("\\d+:")) {
            int id = Integer.parseInt(lines.get(i).replace(":", "").trim());
            i++;
            List<String> grid = new ArrayList<>();
            while (i < lines.size() && !lines.get(i).isBlank())
                grid.add(lines.get(i++));

            Shape sh = new Shape();
            for (int r = 0; r < grid.size(); r++) {
                for (int c = 0; c < grid.get(r).length(); c++) {
                    if (grid.get(r).charAt(c) == '#') {
                        sh.area++;
                        if ((r + c) % 2 == 0)
                            sh.black++;
                        else
                            sh.white++;
                    }
                }
            }
            shapes.put(id, sh);
            i++;
        }

        int answer = 0;

        // Read regions
        while (i < lines.size()) {
            String line = lines.get(i++).trim();
            if (line.isEmpty())
                continue;

            String[] p = line.split(":");
            String[] wh = p[0].split("x");
            int W = Integer.parseInt(wh[0]);
            int H = Integer.parseInt(wh[1]);

            int regionArea = W * H;
            int regionBlack = (regionArea + 1) / 2;
            int regionWhite = regionArea / 2;

            int needArea = 0, needBlack = 0, needWhite = 0;
            String[] cnt = p[1].trim().split(" ");

            for (int k = 0; k < cnt.length; k++) {
                int v = Integer.parseInt(cnt[k]);
                Shape sh = shapes.get(k);
                needArea += v * sh.area;
                needBlack += v * sh.black;
                needWhite += v * sh.white;
            }

            if (needArea <= regionArea &&
                    needBlack <= regionBlack &&
                    needWhite <= regionWhite) {
                answer++;
            }
        }

        System.out.println(answer);
    }
}


