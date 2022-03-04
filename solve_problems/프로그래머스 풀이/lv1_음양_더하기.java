class Solution {
  public int ret_sign(boolean b) {
      if ( b == true )    return 1;
      return -1;
  }
  public int solution(int[] absolutes, boolean[] signs) {
      int answer = 0;
      int len = absolutes.length;
      
      for (int i = 0; i < len; i++) 
          answer += absolutes[i] * ret_sign(signs[i]);
      
      return answer;
  }
}