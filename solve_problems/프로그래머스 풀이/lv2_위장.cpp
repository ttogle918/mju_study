#include <string>
#include <vector>
#include <iostream>
using namespace std;

int solution(vector<vector<string>> clothes) {
    int answer = 0;
    string kind[31];
    int num[31] = {0,};
    bool ishave = false;
    int i, j, idx=0, size = clothes.size();
    
    for (i = 0; i < size; i++) {
        ishave = false;
        for ( j=0; j < idx; j++) {
            if ( kind[j] == clothes[i][1] ){
                num[j] += 1;
                ishave = true;
                break;
            }
        }
        if ( !ishave ){
            kind[idx] = clothes[i][1];
            num[idx++] = 1;
        }
    }
    int mul = 1;
    for (i = 0; i < idx; i++)
        if ( num[i] != 0)   mul *= (num[i]+1);
    
    answer = mul - 1;
    return answer;
}