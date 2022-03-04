public class Combination { 
  public static void main(String[] args) { 
    int[] arr = new int[5];
    combination(arr, 0, 5, 3, 0);
  } 
    
  public static void combination(int[] arr, int index, int n, int r, int target) { 
    if (r == 0) print(arr, index);
    else if (target == n) return; 
    else { 
      arr[index] = target; 
      
      combination(arr, index + 1, n, r - 1, target + 1); 
      combination(arr, index, n, r, target + 1); } 
    }
    //end combination() 
    
  public static void print(int[] arr, int length) { 
    for (int i = 0; i < length; i++) 
      System.out.print(arr[i]);
    System.out.println(""); 
  } 
}
//출처: https://gorakgarak.tistory.com/523



/* import java.util.*;
import static java.util.stream.Collectors.*;

class Solution {
    public int solution(String[][] clothes) {
        return Arrays.stream(clothes)
                .collect(groupingBy(p -> p[1], mapping(p -> p[0], counting())))
                .values()
                .stream()
                .collect(reducing(1L, (x, y) -> x * (y + 1))).intValue() - 1;
    }
}
*/


import java.util.*;
class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        ArrayList<Integer> second = new ArrayList<>();
        ArrayList<Boolean> complete = new ArrayList<>();
        
        for (int i : prices){
            second.add(0);
            complete.add(false);
        }
        
        for (int i = 1; i < prices.length; i++){
            for (int j = 0; j < i; j++){
                if(complete.get(j) == true)    continue;
                second.set(j, second.get(j)+1);   
                if (prices[j] > prices[i]) 
                    complete.set(j, true);
            }
        }
        
        int i = 0;
        for (int t : second) {
            answer[i] = t;
            i++;
        }
        return answer;
    }
}