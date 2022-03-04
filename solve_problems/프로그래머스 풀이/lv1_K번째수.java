import java.util.*;
class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        int i, j, k;
        int row = 0;
        int[] list = {};

        
        while(true) {
            if(row == commands.length)  break;
            i = commands[row][0];
            j = commands[row][1];
            k = commands[row][2];
            
            
            list = Arrays.copyOfRange(array, i-1, j);
            Arrays.sort(list);
            answer[row] = list[k-1];
            
            row++;
        }
        return answer;
    }
}