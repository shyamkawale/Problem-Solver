package Maths;

public class PrefixSuffixOfNumber {

    public static void main(String[] args) {
        int num = 12345;
        System.out.println("original number: " + num);

        // 1
        // 12
        // 123
        // 1234...
        System.out.println("Prefixes: ");
        int tenFact = 1;
        while (num / tenFact != 0) {
            System.out.println(num / tenFact);
            tenFact = tenFact * 10;
        }

        //      5
        //     45
        //    345
        // ..2345
        System.out.println("Suffixes: ");
        tenFact = 10;
        while (num / tenFact != 0) {
            System.out.println(num % tenFact);
            tenFact = tenFact * 10;
        }
        System.out.println(num % tenFact);
    }
}
