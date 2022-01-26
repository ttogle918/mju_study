import java.util.Arrays;

class Solution {
    public int[] solution(int[] answers) {
        int[] answer;
        int[][] man = {{1,2,3,4,5},{2,1,2,3,2,4,2,5},{3,3,1,1,2,2,4,4,5,5}};
        int[] sum = {0,0,0};
        int[] t = {0,0,0};

        for (int i = 0; i < 3; i++) 
            sum[i] = compareAnswer(answers, man[i]);
        
        int i = 1, idx = 1;
        int max = sum[0];
        t[0] = 1;
        while (i < sum.length) {
            if (max < sum[i]) {
                max = sum[i];
                t[0] = i+1;
                idx = 1;
            }
            else if (max == sum[i]) {
                t[idx] = i+1;
                idx++;
            }
            i++;
        }
        
        answer = new int[idx];
        for (i = 0; i < idx; i++) {
            answer[i] = t[i];
        }
        Arrays.sort(answer);
        
        return answer;
    }
    
    public int compareAnswer(int[] answers, int[] man) {
        int size = man.length;
        int sum = 0;
        for(int i = 0, a = 0; a < answers.length; i++, a++){
            if(i == size)   i = 0;
            
            if(man[i] == answers[a] )  sum = sum + 1;
        }
        return sum;
    }
}