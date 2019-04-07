import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        final String alphabeth = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            BigInteger n = in.nextBigInteger();
            BigInteger l = in.nextBigInteger();

            in.nextLine();

            String[] cellStrings = in.nextLine().split(" ");

            Cell first = null;
            Cell last = null;
            Cell current = null;
            Cell minimum = null;

            for (int j = 0; j < cellStrings.length; j++) {
                Cell cell = new Cell(j, new BigInteger(cellStrings[j]));
                if (j == 0) first = cell;
                else {
                    if (j == cellStrings.length - 1) last = cell;
                    cell.prev = current;
                    current.next = cell;
                }

                if (minimum == null || cell.value.compareTo(minimum.value) == -1) minimum = cell;
                current = cell;
            }

            List<BigInteger> factors = primeFactors(minimum.value);
            if (factors.size() == 0) factors.add(BigInteger.ONE);
            if (factors.size() == 1) factors.add(factors.get(0));
            if (factors.size() > 2) {
                List<BigInteger> tooBig = factors.stream().filter(f -> f.compareTo(n) > 0).collect(Collectors.toList());
                final BigInteger currentVal = minimum.value;
                factors.removeAll(tooBig.stream()
                    .map(f1 -> factors.stream().filter(f -> f1.multiply(f).equals(currentVal)).findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
            }

            if (minimum == last) {
                if (minimum.prev.value.mod(factors.get(0)).equals(BigInteger.ZERO)) {
                    minimum.factor1 = factors.get(0);
                    minimum.factor2 = factors.get(1);
                } else {
                    minimum.factor1 = factors.get(1);
                    minimum.factor2 = factors.get(0);
                }
            } else {
                if (minimum.next.value.mod(factors.get(0)).equals(BigInteger.ZERO)) {
                    minimum.factor1 = factors.get(1);
                    minimum.factor2 = factors.get(0);
                } else {
                    minimum.factor1 = factors.get(0);
                    minimum.factor2 = factors.get(1);
                }
            }

            current = minimum.next;
            while (current != null) {
                current.factor1 = current.prev.factor2;
                current.factor2 = current.value.divide(current.factor1);
                current = current.next;
            }

            current = minimum.prev;
            while (current != null) {
                current.factor2 = current.next.factor1;
                current.factor1 = current.value.divide(current.factor2);
                current = current.prev;
            }

            current = first;
            List<BigInteger> primes = new ArrayList<>(26);
            while (current != null) {
                primes.add(current.factor1);
                primes.add(current.factor2);
                current = current.next;
            }
            primes = primes.stream().distinct().sorted().collect(Collectors.toList());

            Map<BigInteger, Character> encodingTable = IntStream.range(0, primes.size()).boxed().collect(Collectors.toMap(primes::get, alphabeth::charAt));

            current = first;
            StringBuilder output = new StringBuilder();
            while (current != null) {
                output.append(current.output(encodingTable));
                current = current.next;
            }

            System.out.println(String.format("Case #%s: %s", i+1,  output.toString()));
        }
    }

    private static List<BigInteger> primeFactors(BigInteger n) {
        List<BigInteger> factors = new ArrayList<>();
        for (BigInteger i = BigInteger.valueOf(2L); i.compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                n = n.divide(i);
            }
        }
        return factors;
    }

    static class Cell {
        public long index;
        public BigInteger value;
        public BigInteger factor1;
        public BigInteger factor2;
        public Cell prev;
        public Cell next;

        public Cell(long index, BigInteger value) {
            this.index = index;
            this.value = value;
        }

        public String output(Map<BigInteger, Character> encodingTable) {
            StringBuilder output = new StringBuilder().append(encodingTable.get(factor1));
            if (next == null) output.append(encodingTable.get(factor2));
            return output.toString();
        }
    }
}