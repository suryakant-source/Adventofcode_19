import java.util.*;

public class Main {
    static int rows, cols;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.isEmpty()) break;
            lines.add(s);
        }
        sc.close();

        rows = lines.size();
        cols = lines.get(0).length();
        char[][] g = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            g[i] = lines.get(i).toCharArray();
        }

        // ==================== PART 1 ====================
        // Count rolls accessible by forklift (neighbors < 4)
        int p1 = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (g[i][j] == '@' && countNeighbors(g, i, j) < 4) {
                    p1++;
                }
            }
        }
        System.out.println("Part 1: " + p1);

        // ==================== PART 2 ====================
        // Iteratively remove rolls until no more can be removed
        char[][] g2 = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            g2[i] = g[i].clone();
        }

        int p2 = 0;
        while (true) {
            List<int[]> toRemove = new ArrayList<>();
            
            // Find all accessible rolls
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (g2[i][j] == '@' && countNeighbors(g2, i, j) < 4) {
                        toRemove.add(new int[]{i, j});
                    }
                }
            }
            
            // Stop if no more can be removed
            if (toRemove.isEmpty()) break;
            
            // Remove all accessible rolls
            for (int[] pos : toRemove) {
                g2[pos[0]][pos[1]] = '.';
            }
            p2 += toRemove.size();
        }
        System.out.println("Part 2: " + p2);
    }

    // Count neighbors with '@' in 8 directions
    static int countNeighbors(char[][] g, int r, int c) {
        int cnt = 0;
        for (int d = 0; d < 8; d++) {
            int nr = r + dx[d];
            int nc = c + dy[d];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && g[nr][nc] == '@') {
                cnt++;
            }
        }
        return cnt;
    }
}
