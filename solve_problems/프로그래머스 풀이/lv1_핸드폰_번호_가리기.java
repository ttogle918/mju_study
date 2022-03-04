class Solution {
  public String solution(String phone_number) {
      String answer = "";
      int i;
      for(i = 0; i < phone_number.length()-4; i++)
          answer += "*";
      
      answer += phone_number.substring(i);
      
      return answer;
  }
}