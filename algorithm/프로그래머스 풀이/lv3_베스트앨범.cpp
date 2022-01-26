#include <string>
#include <vector>
#include <iostream>
using namespace std;

void compare(vector<int> ps, int *f, int *s, int p, int i) {
    if ( p > ps[*f]) {
        *s = *f;
        *f = i;
    } else if ( p > ps[*s] || (p == ps[*f] && ps[*s] == -1) ) {
        *s = i;
    }
}

vector<int> solution(vector<string> genres, vector<int> plays) {
    vector<int> answer;
    string genre[100];    
    int first[100], second[100], sum[100] = {0,};
    int i, j, idx=0, play, max = plays[0], size = genres.size();
    bool ishave = false;
    std::fill_n(second, 100, -1);
    for (i = 0; i < size; i++){
        ishave = false;
        play = plays[i];
        for (j = 0; j < idx; j++) {
            if ( genre[j] == genres[i]) {
                ishave = true;
                sum[j] += play;
                compare(plays, &first[j], &second[j], play, i);
                break;
            }
        }
        if (ishave == false) {
            genre[idx] = genres[i];
            sum[idx] = play;
            first[idx++] = i;
        }
    }
    int line[idx];
    int maxnum = 0;
    max = sum[0];
    for (i = 0 ; i < idx; i++) {
        for (j = 0; j < idx; j++) {
            if ( max < sum[j]){
                maxnum = j;
                max = sum[j];
            }
        }
        line[i] = maxnum;
        sum[maxnum] = -1;
        max = 0;
        maxnum = -1;
    }
    
    for (i = 0; i < idx; i++) {
        answer.push_back(first[line[i]]);
        if ( second[line[i]] == -1 )    continue;
        answer.push_back(second[line[i]]);
    }
    return answer;
}