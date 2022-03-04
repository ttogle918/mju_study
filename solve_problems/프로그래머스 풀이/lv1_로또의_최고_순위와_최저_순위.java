import java.util.Arrays;
class Solution {
    public int[] ret_grade(int grade, int zero) {
        if ( grade == 7 ) {
            if ( zero == 0 )    return new int[]{6, 6};
            return new int[]{grade-zero, 6};
        }
        if ( grade == 1 )    return new int[]{grade, grade};
        return new int[]{grade-zero, grade};
    
    }
    public int[] solution(int[] lottos, int[] win_nums) {
        int correct = 0;
        Arrays.sort(lottos);
        Arrays.sort(win_nums);
        
        int idx = 0, zero_num = 0, w_idx=0, comp;
        while (idx < 6 && lottos[idx] == 0 ){
            zero_num++; idx++;
        }
        while ( idx < 6 && w_idx < 6 ) {
            comp = Integer.compare(lottos[idx], win_nums[w_idx]);
            if ( comp == 0 ){
                correct++; idx++; w_idx++;
            }
            else if ( comp == -1 )  idx++;
            else    w_idx++;
        }

        return ret_grade(7 - correct, zero_num);
    }
}