import java.util.HashMap;
import java.lang.Math;
class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        int y_width = 1, y_height = 1, y_sqrt = (int)Math.sqrt(yellow) + 1;
        // HashMap<Integer, Integer> map = HashMap<>();
        
        
        for (y_height = 1; y_height <= yellow/2 + 1; y_height++) {
            if ( yellow % y_height != 0) continue;
            y_width = yellow / y_height;
            System.out.println(yellow + " " + y_width);
            
            if ((brown+yellow) == (y_width+2) * (y_height+2)) {
                answer[0] = y_width+2;
                answer[1] = y_height+2;
                return answer;
            }
        }
                     
        answer = new int[]{0, 0};
        return answer;
    }
}