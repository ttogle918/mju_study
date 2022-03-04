class Solution {
  public int solution(int left, int right) {
      int answer = 0;
      double sq;
      while ( left <= right ) {
          sq = Math.sqrt(left);
          int val = (int) sq;
          if ( val*val == left )  answer -= left;
          else    answer += left;
          left++;
      }
      return answer;
  }
}