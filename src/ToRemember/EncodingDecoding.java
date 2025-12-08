package ToRemember;

public class EncodingDecoding {
    public static void main(String[] args) {
        int a = 5;
        int b = -4;
        System.out.println("a: " + a + " , b: " + b);

        long key = encode(a, b);
        System.out.println("key: " + key);

        int decode_a = decode(key, 0);
        int decode_b = decode(key, 1);
        System.out.println("decode_a: " + decode_a + " , decode_b: " + decode_b);
    }

    private static long encode(int a, int b) {
        return ((long) a << 32) | (b & ((1L << 32) - 1));
    }

    private static int decode(long key, int pos) {
        if (pos == 0) {
            return (int)(key >> 32);
        } else {
            return (int)(key & ((1L << 32) - 1));
        }
    }
}
