#include <string>
#include <vector>

using namespace std;

vector<int> solution(vector<int> answers) {
    vector<int> answer;
    int man1[] = {1, 2, 3, 4, 5};
    int man2[] = {2, 1, 2, 3, 2, 4, 2, 5};
    int man3[] = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
    int sum[3] = {0,0,0};
    
    int i, s;
    for (i = 0; i < answers.size(); i++) {
        if ( man1[i % 5] == answers[i] )
            sum[0]++;
        if ( man2[i % 8] == answers[i] )
            sum[1]++;
        if ( man3[i % 10] == answers[i] )
            sum[2]++;
    }
    answer.push_back(0);    // push idx
    for (i = 1; i < 3; i++) {
        s = sum[i];
        if ( sum[answer[0]] < s ) {
            answer.clear();
            answer.push_back(i);
        }
        else if ( sum[answer[0]] == s ){
            answer.push_back(i);   
        }
    }
    for (i = 0; i < answer.size(); i++)
        answer[i]++;
    return answer;
}