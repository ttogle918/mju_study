import java.util.*;

class Solution {
    public void swap(int i, int j, int[] plays) {
        int temp = plays[i];
        plays[i] = plays[j];
        plays[j] = temp;
    }
    
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = new int[200];
        String[] genr = new String[100];
        int[] g_play = new int[200];    // genre 중 play최댓값 2번째도
        int[] sum = new int[100];
        int len = plays.length;
        int i, j, index;    // index : ger 개수, ger개수 * 2 를 넣어야함
        boolean ishave = false;
        
        for (i = 0; i < 200; i++)
            answer[i] = -1;
        
        for (i = 0, index = 0; i < len; i++) {
            ishave = false;
            for ( j = 0; j < index; j++)
                if ( genr[j].equals(genres[i]) ) {
                    ishave = true;
                    sum[j] += plays[i];
                    if ( g_play[j*2] < plays[i] ) {
                        g_play[j*2+1] = g_play[j*2];
                        answer[j*2+1] = answer[j*2];
                        g_play[j*2] = plays[i];
                        answer[j*2] = i;
                    } else if (g_play[j*2+1] == 0 ||
                            (g_play[j*2+1] < plays[i] && plays[i] <= g_play[j*2]) ) {
                        g_play[j*2+1] = plays[i];
                        answer[j*2+1] = i;
                    }
                    break;
                }
            if ( !ishave ) {
                genr[index] = genres[i];
                sum[index] = plays[i];
                g_play[index*2] = plays[i];
                answer[index*2] = i;
                index++;
            }
        }
        // sum, 순서 sort
        int[] line = new int[index];
        for (i = 0; i < index; i++) line[i] = i;
        
        for (i = 1; i < index; i++ )
            for (j = 0; j < i; j++)
                if ( sum[i] > sum[j]) {
                    swap(i, j, line);
                    swap(i, j, sum);
                }       
        
        j = 0;
        int[] t_answer = new int[index*2];
        for (i = 0; i < index; i++) {
            t_answer[j++] = answer[line[i]*2];
            if( answer[line[i]*2+1] == -1)    continue;
            t_answer[j++] = answer[line[i]*2+1];
        }
        answer = Arrays.copyOfRange(t_answer, 0, j);
        return answer;
    }
}