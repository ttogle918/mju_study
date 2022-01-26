#include <string>
#include <vector>

using namespace std;

vector<int> solution(vector<int> progresses, vector<int> speeds) {
    vector<int> answer;
    int i = 0, size = progresses.size();
    int cal[size];
    
    if ( size == 1 ) {
        answer.push_back(1);
        return answer;
    }
    for ( ; i < size; i++ )
        cal[i] = (99 - progresses[i]) / speeds[i] + 1;
    
    i = 1;
    int current = cal[0];
    answer.push_back(1);

    while ( i < size ) {
        if ( current >= cal[i] )   answer.back()++;
        else {
            current = cal[i];
            answer.push_back(1);
        }
        i++;
    }
    return answer;
}