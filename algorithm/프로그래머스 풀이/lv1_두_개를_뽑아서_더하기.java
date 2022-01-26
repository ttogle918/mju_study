import java.util.Arrays;
class Solution {
    public boolean checkNumber(int[] ans, int n) {
        for( int num : ans )
            if ( n == num ) return true;
        return false;
    }
    public int[] solution(int[] numbers) {
        int len = numbers.length;
        int[] answer = new int[(len) * (len-1)/2+1];
        Arrays.fill(answer, -1);
        int idx = 0, count = 0;
        
        for ( int i = 0; i < len-1; i++) {
            for ( int j = i+1; j < len; j++) {
                if ( !checkNumber(answer, numbers[i] + numbers[j]) ){
                    answer[idx++] = numbers[i] + numbers[j];
                    count++;
                }
            }
        }
        answer = Arrays.copyOf(answer, count);
        Arrays.sort(answer);        
        return answer;
    }
}