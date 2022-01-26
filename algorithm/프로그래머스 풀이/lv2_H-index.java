class Solution {
  public int solution(int[] citations) {
      int answer = 0;
      int i,j, len = citations.length;
      // citations sort
      for (i = 1; i < len; i++)
          for ( j = 0; j < i; j++) 
              if ( citations[i] > citations[j])
                  swap(citations, i, j);
      
      for (i = 0; i < len && i+1 <= citations[i]; i++);
      answer = i;
      return answer;
  }
  public void swap(int[] c, int i, int j) {
      int t = c[i];
      c[i]=c[j];
      c[j] = t;
  }
}