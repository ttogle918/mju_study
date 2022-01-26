import java.util.Arrays;
import java.util.ArrayList;
class Solution {
    public int solution(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int biggest = nums[len-1] + nums[len-2] + nums[len-3];
        ArrayList<Integer> list = new ArrayList();
        list.add(2);
        int n = 3, list_len = 1, i=0;
        
        while ( n <= biggest ) {
            for ( i = 0; i < list_len && n % list.get(i) != 0; i++);
            if ( i == list_len ){
                list.add(n);
                list_len++;
            }
            n += 2;
        }

        int first, second, third, answer = 0;
        for (i = 0; i < len-2; i++) {
            first = nums[i];
            for (int j = i+1; j < len-1; j++) {
                second = nums[j];
                for ( int k = j+1; k < len; k++) {
                    third = nums[k];
                    
                    if ( list.contains(first+second+third) )
                        answer++;
                }
            }   
        }
        return answer;
    }
}