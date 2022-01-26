import java.util.Arrays;
class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Arrays.sort(phone_book);
        
        int i = 1;
        String str = "";
        int len = 0;
        int len_back = 0;
        
        while( i < phone_book.length ) {
            len = phone_book[i-1].length();
            len_back = phone_book[i].length();
            
            if (len > len_back) {
                i++;
                continue;
            }
            str = phone_book[i].substring(0, len);
            
            if (str.equals(phone_book[i-1])) {
                answer = false;
                return answer;
            }
            i++;
        }
        
        return answer;
    }
}