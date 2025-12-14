//Part-1

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        long totalMinPresses = 0;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            long result = solveMachineJoltage(line);
            
            if (result != Long.MAX_VALUE) {
                totalMinPresses += result;
            }
        }

        System.out.println(totalMinPresses);
    }

    private static long solveMachineJoltage(String line) {
        int endBracket = line.indexOf(']');
        int startCurly = line.indexOf('{');
        
        String buttonSection = line.substring(endBracket + 1, startCurly).trim();
        String targetSection = line.substring(startCurly + 1, line.indexOf('}')).trim();

        String[] targetParts = targetSection.split(",");
        int numCounters = targetParts.length;
        double[] b = new double[numCounters];
        for (int i = 0; i < numCounters; i++) {
            b[i] = Integer.parseInt(targetParts[i].trim());
        }

        List<List<Integer>> buttonEffects = new ArrayList<>();
        int idx = 0;
        while (idx < buttonSection.length()) {
            int open = buttonSection.indexOf('(', idx);
            if (open == -1) break;
            int close = buttonSection.indexOf(')', open + 1);
            String inside = buttonSection.substring(open + 1, close).trim();
            
            List<Integer> effects = new ArrayList<>();
            if (!inside.isEmpty()) {
                String[] parts = inside.split(",");
                for (String p : parts) {
                    effects.add(Integer.parseInt(p.trim()));
                }
            }
            buttonEffects.add(effects);
            idx = close + 1;
        }

        int numButtons = buttonEffects.size();
        
        double[][] A = new double[numCounters][numButtons];
        int[] bounds = new int[numButtons];
        Arrays.fill(bounds, Integer.MAX_VALUE);

        for (int j = 0; j < numButtons; j++) {
            List<Integer> effect = buttonEffects.get(j);
            boolean affectsSomething = false;
            for (int counterIdx : effect) {
                if (counterIdx >= 0 && counterIdx < numCounters) {
                    A[counterIdx][j] = 1.0;
                    bounds[j] = Math.min(bounds[j], (int)b[counterIdx]);
                    affectsSomething = true;
                }
            }
            if (!affectsSomething) bounds[j] = 0; 
        }

        int m = numCounters;
        int n = numButtons;
        double[][] M = new double[m][n + 1];
        for(int i=0; i<m; i++) {
            System.arraycopy(A[i], 0, M[i], 0, n);
            M[i][n] = b[i];
        }

        int pivotRow = 0;
        int[] pivotColInRow = new int[m];
        Arrays.fill(pivotColInRow, -1);
        boolean[] isPivotCol = new boolean[n];

        for (int col = 0; col < n && pivotRow < m; col++) {
            int sel = -1;
            for (int row = pivotRow; row < m; row++) {
                if (Math.abs(M[row][col]) > 1e-9) {
                    sel = row;
                    break;
                }
            }
            if (sel == -1) continue;

            double[] tmp = M[sel];
            M[sel] = M[pivotRow];
            M[pivotRow] = tmp;

            double div = M[pivotRow][col];
            for (int k = col; k <= n; k++) {
                M[pivotRow][k] /= div;
            }

            for (int row = 0; row < m; row++) {
                if (row != pivotRow && Math.abs(M[row][col]) > 1e-9) {
                    double factor = M[row][col];
                    for (int k = col; k <= n; k++) {
                        M[row][k] -= factor * M[pivotRow][k];
                    }
                }
            }

            pivotColInRow[pivotRow] = col;
            isPivotCol[col] = true;
            pivotRow++;
        }

        for (int r = pivotRow; r < m; r++) {
            if (Math.abs(M[r][n]) > 1e-9) return Long.MAX_VALUE;
        }

        List<Integer> freeCols = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (!isPivotCol[j]) freeCols.add(j);
        }

        return search(0, freeCols, bounds, M, pivotColInRow, n, m);
    }

    private static long search(int idx, List<Integer> freeCols, int[] bounds, 
                               double[][] M, int[] pivotColInRow, int n, int m) {
        
        if (idx == freeCols.size()) {
            return Long.MAX_VALUE;
        }
        
        return Long.MAX_VALUE;
    }
    
    private static long search(int idx, List<Integer> freeCols, int[] bounds, 
                               double[][] M, int[] pivotColInRow, int n, int m, 
                               int[] currentAssignment) {
        
        if (idx == freeCols.size()) {
            double[] x = new double[n];
            for(int i=0; i<freeCols.size(); i++) {
                x[freeCols.get(i)] = currentAssignment[i];
            }
            
            for (int r = 0; r < m; r++) {
                int p = pivotColInRow[r];
                if (p == -1) continue;
                
                double val = M[r][n];
                for (int f : freeCols) {
                    val -= M[r][f] * x[f];
                }
                
                x[p] = val;
            }

            long sum = 0;
            for (double val : x) {
                long rounded = Math.round(val);
                if (Math.abs(val - rounded) > 1e-4) return Long.MAX_VALUE;
                if (rounded < 0) return Long.MAX_VALUE;
                sum += rounded;
            }
            return sum;
        }

        int col = freeCols.get(idx);
        int maxVal = bounds[col];
        long minFound = Long.MAX_VALUE;

        for (int val = 0; val <= maxVal; val++) {
            currentAssignment[idx] = val;
            long res = search(idx + 1, freeCols, bounds, M, pivotColInRow, n, m, currentAssignment);
            if (res < minFound) {
                minFound = res;
            }
        }
        return minFound;
    }

    private static long search(int idx, List<Integer> freeCols, int[] bounds, 
                               double[][] M, int[] pivotColInRow, int n, int m) {
        int[] currentAssignment = new int[freeCols.size()];
        return search(0, freeCols, bounds, M, pivotColInRow, n, m, currentAssignment);
    }
}


//Part-2
import java.io.*;
import java.util.*;

public class Main {

    private static final double EPS = 1e-9;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        long total = 0;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            long presses = solveMachine(line);
            if (presses == Long.MAX_VALUE) {
                throw new RuntimeException("No solution found for line: " + line);
            }
            total += presses;
        }

        System.out.println(total);
    }

    private static long solveMachine(String line) {
        int openCurly = line.indexOf('{');
        int closeCurly = line.indexOf('}', openCurly + 1);
        if (openCurly < 0 || closeCurly < 0) {
            throw new IllegalArgumentException("Invalid line (no {..}): " + line);
        }
        String targetsPart = line.substring(openCurly + 1, closeCurly).trim();
        String[] tParts = targetsPart.split(",");
        int m = tParts.length;
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(tParts[i].trim());
        }

        int closeBracket = line.indexOf(']');
        int buttonsStart = (closeBracket >= 0) ? closeBracket + 1 : 0;
        String buttonsPart = line.substring(buttonsStart, openCurly).trim();

        List<int[]> buttonEffects = new ArrayList<>();
        int idx = 0;
        while (idx < buttonsPart.length()) {
            int open = buttonsPart.indexOf('(', idx);
            if (open == -1) break;
            int close = buttonsPart.indexOf(')', open + 1);
            if (close == -1) break;
            String inside = buttonsPart.substring(open + 1, close).trim();
            if (inside.isEmpty()) {
                buttonEffects.add(new int[0]);
            } else {
                String[] parts = inside.split(",");
                int[] eff = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    eff[i] = Integer.parseInt(parts[i].trim());
                }
                buttonEffects.add(eff);
            }
            idx = close + 1;
        }

        int n = buttonEffects.size();

        if (n == 0) {
            for (int val : b) {
                if (val != 0) return Long.MAX_VALUE;
            }
            return 0;
        }

        int[][] A = new int[m][n];
        for (int j = 0; j < n; j++) {
            for (int counterIdx : buttonEffects.get(j)) {
                if (counterIdx >= 0 && counterIdx < m) {
                    A[counterIdx][j] += 1;
                }
            }
        }

        return minPressesForMachine(A, b);
    }

    private static long minPressesForMachine(int[][] A, int[] b) {
        int m = A.length;
        int n = (m == 0 ? 0 : A[0].length);

        if (m == 0 || n == 0) {
            for (int val : b) {
                if (val != 0) return Long.MAX_VALUE;
            }
            return 0;
        }

        double[][] M = new double[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = A[i][j];
            }
            M[i][n] = b[i];
        }

        int[] pivotColInRow = new int[m];
        Arrays.fill(pivotColInRow, -1);

        int row = 0;
        int nCols = n;
        for (int col = 0; col < nCols && row < m; col++) {
            int sel = -1;
            for (int i = row; i < m; i++) {
                if (Math.abs(M[i][col]) > EPS) {
                    sel = i;
                    break;
                }
            }
            if (sel == -1) continue;

            double[] tmp = M[sel];
            M[sel] = M[row];
            M[row] = tmp;

            double pivot = M[row][col];
            for (int j = 0; j <= nCols; j++) {
                M[row][j] /= pivot;
            }

            for (int i = 0; i < m; i++) {
                if (i == row) continue;
                double factor = M[i][col];
                if (Math.abs(factor) <= EPS) continue;
                for (int j = 0; j <= nCols; j++) {
                    M[i][j] -= factor * M[row][j];
                }
            }

            pivotColInRow[row] = col;
            row++;
        }

        for (int i = 0; i < m; i++) {
            boolean allZero = true;
            for (int j = 0; j < nCols; j++) {
                if (Math.abs(M[i][j]) > EPS) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && Math.abs(M[i][nCols]) > EPS) {
                return Long.MAX_VALUE;
            }
        }

        boolean[] isPivotCol = new boolean[nCols];
        for (int i = 0; i < m; i++) {
            int pc = pivotColInRow[i];
            if (pc >= 0) isPivotCol[pc] = true;
        }

        List<Integer> freeCols = new ArrayList<>();
        for (int j = 0; j < nCols; j++) {
            if (!isPivotCol[j]) freeCols.add(j);
        }

        int[] maxPress = new int[nCols];
        Arrays.fill(maxPress, Integer.MAX_VALUE);
        for (int j = 0; j < nCols; j++) {
            boolean used = false;
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < m; i++) {
                if (A[i][j] > 0) {
                    used = true;
                    int bound = b[i] / A[i][j];
                    if (bound < best) best = bound;
                }
            }
            if (!used) best = 0;
            maxPress[j] = best;
        }

        long[] best = new long[] { Long.MAX_VALUE };
        int[] assign = new int[freeCols.size()];

        dfsFreeVars(0, freeCols, maxPress, M, pivotColInRow, nCols, assign, 0L, best);

        return best[0];
    }

    private static void dfsFreeVars(int idx,
                                    List<Integer> freeCols,
                                    int[] maxPress,
                                    double[][] M,
                                    int[] pivotColInRow,
                                    int nCols,
                                    int[] assign,
                                    long currentFreeSum,
                                    long[] best) {

        int F = freeCols.size();
        if (idx == F) {
            double[] x = new double[nCols];

            for (int k = 0; k < F; k++) {
                int col = freeCols.get(k);
                x[col] = assign[k];
            }

            int m = M.length;
            for (int r = 0; r < m; r++) {
                int pc = pivotColInRow[r];
                if (pc < 0) continue;

                double val = M[r][nCols];
                for (int k = 0; k < F; k++) {
                    int col = freeCols.get(k);
                    double coeff = M[r][col];
                    if (Math.abs(coeff) > EPS) {
                        val -= coeff * x[col];
                    }
                }
                x[pc] = val;
            }

            long total = 0;
            for (int j = 0; j < nCols; j++) {
                double v = x[j];
                if (v < -EPS) return;

                long iv = Math.round(v);
                if (Math.abs(v - iv) > 1e-6) {
                    return;
                }
                total += iv;
                if (total >= best[0]) return;
            }

            if (total < best[0]) best[0] = total;
            return;
        }

        int col = freeCols.get(idx);
        int upper = maxPress[col];
        if (upper < 0) upper = 0;

        for (int v = 0; v <= upper; v++) {
            long newSum = currentFreeSum + v;
            if (best[0] != Long.MAX_VALUE && newSum >= best[0]) {
                break;
            }
            assign[idx] = v;
            dfsFreeVars(idx + 1, freeCols, maxPress, M, pivotColInRow,
                        nCols, assign, newSum, best);
        }
    }
}
