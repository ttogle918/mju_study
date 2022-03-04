import java.util.ArrayList;
class Solution {
    int[] array;
    ArrayList<Integer> arraylist;
    public int solution(String numbers) {
        int answer = 0;
        array = new int[numbers.length()];
        arraylist = new ArrayList<Integer>();
        
        // string -> int, array에 담기
        for(int i = 0; i < numbers.length(); i++) 
            array[i] = Integer.parseInt(numbers.substring(i, i+1));
        // 순열 구하기 ( 모든 경우의 수 , array, depth, n, r)
        for (int i = 1; i <= array.length; i++) 
            permutation(array, 0, array.length, i);
        // 소수인지 확인
        for(int i = 0; i < arraylist.size(); i++) {
            if ( isPrime(arraylist.get(i)) )
                answer++;
        }
        return answer;
    }
     // 배열 저장
    public void print(int[] arr, int r) {
        int sum = 0;
        for (int i = 0; i < r; i++)
            sum = sum * 10 + arr[i];
        // 중복 제거
        if (!arraylist.contains(sum)) {
            arraylist.add(sum);     
        }
    }
    // blog참조
    public void permutation(int[] arr, int depth, int n, int r) {
        if (depth == r) {
            print(arr, r);
            return;
        }
 
        for (int i=depth; i<n; i++) {
            swap(arr, depth, i);
            permutation(arr, depth + 1, n, r);
            swap(arr, depth, i);
        }
    }
 
    static void swap(int[] arr, int depth, int i) {
        int temp = arr[depth];
        arr[depth] = arr[i];
        arr[i] = temp;
    }
    
    public boolean isPrime(int num){
        if (num < 2)   return false;
        if (num == 2)   return true;
        if (num % 2 == 0) return false;
        
        for (int i = 3; i <= num / 2 + 1; i=i+2) 
            if ( num % i == 0)  return false;
        
        return true;
    }
}