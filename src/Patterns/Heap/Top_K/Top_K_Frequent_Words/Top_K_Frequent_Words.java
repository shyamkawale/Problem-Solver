package Patterns.Heap.Top_K.Top_K_Frequent_Words; 
 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Helpers.DataConvertor;
import Helpers.ProblemSolver; 
 
public class Top_K_Frequent_Words extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new Top_K_Frequent_Words().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        String[] words = DataConvertor.toTypeArray(args[0], String.class); 
        int k = DataConvertor.toInt(args[1]);
 
        List<String> res = topKFrequent(words, k); 
        System.out.println(res);
    } 
 
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for(String s: words){
            freqMap.put(s, freqMap.getOrDefault(s, 0)+1);
        }

        Queue<String> maxHeap = new PriorityQueue<String>(new HeapComparator(freqMap));
        for(String key: freqMap.keySet()){
            maxHeap.offer(key);
        }

        List<String> res = new ArrayList<String>();
        for(int i=0; i<k; i++){
            res.add(maxHeap.poll());
        }

        return res;
    }

    public class HeapComparator implements Comparator<String>{
        Map<String, Integer> freqMap;

        public HeapComparator(Map<String, Integer> freqMap) {
            this.freqMap = freqMap;
        }

        @Override
        public int compare(String a, String b){
            int freqComparison = Integer.compare(freqMap.get(b), freqMap.get(a));

            if(freqComparison != 0){
                return freqComparison;
            }

            int lexicalComparison = a.compareTo(b);

            return lexicalComparison;
        }
    }
} 
