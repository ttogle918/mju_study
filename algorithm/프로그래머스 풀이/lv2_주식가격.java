import java.util.*;
class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];    
        
        Stack<Integer> pri = new Stack<>();
        Stack<Integer> index = new Stack<>();
        int time = 0;
        pri.push(prices[0]);
        index.push(0);
        
        int idx = 0;
        for (int i = 1; i < prices.length; i++) {
            time++;
            while(pri.empty() == false && pri.peek() > prices[i]) {        
                idx = index.pop();
                answer[idx] = time-idx;
                pri.pop();          
            }
            pri.push(prices[i]);
            index.push(i);
        }
        while (pri.empty() == false) {
            idx = index.pop();
            answer[idx] = time-idx;
            pri.pop();
        }
        return answer;
    }
}