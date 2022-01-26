import java.util.stream.IntStream;
class Solution {
    public boolean findValue(int[] box, int v, int len) {
        for (int i = 0; i < len; i++) {
            if ( box[i] == v )   return true;
        }
        return false;
    }
    public int solution(int[] nums) {
        int half = nums.length / 2, idx = 0, b_idx = 0;
        int box[] = new int[half];
        while ( idx < nums.length && b_idx < half ) {
            if ( findValue(box, nums[idx], b_idx ) == false )
                box[b_idx++] = nums[idx];
            idx++;
        }
        
        return b_idx;
    }
}