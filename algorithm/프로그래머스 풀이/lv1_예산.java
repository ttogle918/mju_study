import java.util.Arrays;

class Solution {
    public int solution(int[] d, int budget) {
        int answer = 0;
        Arrays.sort(d);
        int len = d.length;
        int sum = 0;
        while ( answer < len && sum < budget )
            sum += d[answer++];
        
        if ( sum > budget ) return answer-1;
        return answer;
    }
}