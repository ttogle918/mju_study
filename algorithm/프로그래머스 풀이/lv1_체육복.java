import java.util.Arrays;
class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        int answer = n;
        int idx = 0;
        Arrays.sort(lost);
        Arrays.sort(reserve);
        int i = 0, j = 0;
        // lost == reserve
        for (i=0; i < lost.length; i++)
            for (j=0; j < reserve.length; j++) {
                if (lost[i] < reserve[j])  break;
                if (reserve[j] < lost[i])  continue;
                if (lost[i] == reserve[j]) {
                    reserve[j] = -1;
                    lost[i] = -1;
                    continue;
                }    
            }
        
        for (i = 0; i < lost.length; i++) {
            if (lost[i] == -1)    continue;
            answer--;
            for (j = idx; j < reserve.length; j++) {
                if (reserve[j] == -1)   continue;
                
                if (reserve[j] == lost[i]-1 || reserve[j] == lost[i]+1 ) {                    
                    reserve[j] = -1;
                    lost[i] = -1;
                    idx = j+1;
                    answer++;
                    break;
                }
            }
        }
        
        return answer;
    }
}