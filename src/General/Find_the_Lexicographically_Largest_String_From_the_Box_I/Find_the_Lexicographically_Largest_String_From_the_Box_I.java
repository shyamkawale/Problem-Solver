package General.Find_the_Lexicographically_Largest_String_From_the_Box_I;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Find_the_Lexicographically_Largest_String_From_the_Box_I extends ProblemSolver {

    public static void main(String[] args) {
        new Find_the_Lexicographically_Largest_String_From_the_Box_I().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String word = DataConvertor.toString(args[0]);
        int numFriends = DataConvertor.toInt(args[1]);

        String res1 = answerString_new(word, numFriends);
        String res2 = answerString(word, numFriends);
        System.out.println(res1 + " " + res2);
    }

    public String answerString(String word, int numFriends) {
        if (numFriends == 1) {
            return word;
        }
        int len = word.length();

        int maxSubStrLen = len - numFriends + 1;
        String maxSubStr = "";
        int start = 0;
        int end = 0;
        while (end < len) {
            if (end - start + 1 < maxSubStrLen) { // small window
                end++;
            } else if (end - start + 1 == maxSubStrLen) { // equal window => so maintain it
                String subStr = word.substring(start, end + 1);
                if (maxSubStr.compareTo(subStr) < 0) {
                    maxSubStr = subStr;
                }
                start++;
                end++;
            }
        }

        // find lexicographically bigger strings with small string length
        while (start < len) {
            String subStr = word.substring(start);
            if (maxSubStr.compareTo(subStr) < 0) {
                maxSubStr = subStr;
            }
            start++;
        }

        return maxSubStr;
    }

    public String answerString_new(String word, int numFriends) {
        if (numFriends == 1) {
            return word;
        }
        int n = word.length(), m = n - numFriends + 1;
        String res = "", cur = "";
        for (int i = 0; i < n; i++) {
            cur = word.substring(i, Math.min(i + m, n));
            if (res.compareTo(cur) < 0) {
                res = cur;
            }
        }
        return res;
    }
}
