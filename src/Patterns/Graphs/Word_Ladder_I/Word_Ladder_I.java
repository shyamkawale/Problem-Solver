package Patterns.Graphs.Word_Ladder_I;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import Helpers.DataConvertor;
import Helpers.ProblemSolver;

public class Word_Ladder_I extends ProblemSolver {

    public static void main(String[] args) {
        new Word_Ladder_I().readInput();
    }

    @Override
    public void processParameters(String[] args) {
        String beginWord = DataConvertor.toString(args[0]);
        String endWord = DataConvertor.toString(args[1]);
        String[] wordList = DataConvertor.toStringArray(args[2]);

        int res = ladderLength(beginWord, endWord, Arrays.asList(wordList));
        int res2 = ladderLength2(beginWord, endWord, Arrays.asList(wordList));
        System.out.println(res + " " + res2);
    }

    private int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        Set<String> vis = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        Set<String> set = new HashSet<>();
        for(String word: wordList) {
            set.add(word);
        }

        queue.offer(beginWord);
        vis.add(beginWord);
        int steps = 1;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int s=0; s<size; s++) {
                String polledWord = queue.poll();

                if(polledWord.equals(endWord)) {
                    return steps;
                }

                for(int i=0; i<polledWord.length(); i++) {
                    for(char ch='a'; ch<='z'; ch++) {
                        char charArray[] = polledWord.toCharArray();
                        if (ch == charArray[i]) continue;
                        charArray[i] = ch;
                        String transformedWord = new String(charArray);

                        if(set.contains(transformedWord) && !vis.contains(transformedWord)) {
                            queue.offer(transformedWord);
                            vis.add(transformedWord);
                        }
                    }
                }
            }

            steps++;
        }
        return 0;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Set<String> visited = new HashSet<>();
        int result = dfs(beginWord, endWord, wordSet, visited);

        // If no path was found, return 0 as per problem statement
        return result == Integer.MAX_VALUE ? 0 : result;
    }

    private int dfs(String currentWord, String endWord, Set<String> wordSet, Set<String> visited) {
        if (currentWord.equals(endWord)) {
            return 1; // Base case: reached endWord
        }

        visited.add(currentWord); // Mark current as visited
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < currentWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                char charArray[] = currentWord.toCharArray();
                if (ch == charArray[i]) continue;
                charArray[i] = ch;
                String transformedWord = new String(charArray);

                if (wordSet.contains(transformedWord) && !visited.contains(transformedWord)) {
                    int steps = dfs(transformedWord, endWord, wordSet, visited);
                    if (steps != Integer.MAX_VALUE) {
                        min = Math.min(min, steps + 1); // Add 1 for the current transformation
                    }
                }
            }
        }

        visited.remove(currentWord); // Backtrack (Acting like a path Visited)

        return min;
    }

}
