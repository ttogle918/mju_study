import java.util.Arrays;
class Solution {
    public String solution(String[] participant, String[] completion) {
        Arrays.sort(participant);
        Arrays.sort(completion);
        int i = 0;

        for( ; i < completion.length; i++) {
            if (!completion[i].equals(participant[i]))
                return participant[i];
        }
        return participant[participant.length-1];
    }
}