import java.util.ArrayList;
class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        ArrayList<String> list_str = new ArrayList<String>();
        ArrayList<Integer> list_num = new ArrayList<Integer>();
        
        int i =0;
        int index = -1;
        while ( i < clothes.length) {
            index = list_str.indexOf(clothes[i][1]);
            if ( index == -1 ) {
                list_str.add(clothes[i][1]);
                list_num.add(1);
            }
            else {             
                list_num.set(index, list_num.get(index) + 1);
            }
            i++;
        }
        //  ( nC1 + nC0 ) *... 모든 경우 곱하기
        for(int k = 0; k < list_num.size(); k++) {
            answer  *= ( list_num.get(k) + 1 );     
        }
        // 안 입을 경우 1빼기
        answer -= 1;
        return answer;
    }    
}