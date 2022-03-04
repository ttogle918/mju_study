class Solution {
  public int solution(int n) {
      int answer = 0;
      String str = "";
      // 10 -> 3(reverse)
      while ( n > 0 ) {
          str +=Integer.toString(n % 3);
          n = n / 3;
      }
      
      int idx = str.length()-1;
      n = 1;
      while ( idx >= 0 ) {
          answer += (str.charAt(idx) - '0') * n;
          n *= 3;
          idx--;
      }       
      return answer;
  }
}