#include <string>
#include <vector>
#include <iostream>
using namespace std;

int solution(vector<vector<int>> board, vector<int> moves) {
    int answer = 0;
    int len = moves.size();
    int N = board.size();
    int i, j, value, m;
    int idx_top[N];
    vector<int> bucket;
    
    // top 구하기
    for (i = 0; i < N; i++) {
        for (j = 0; j < N && board[j][i] == 0; j++);
        idx_top[i] = j;
    }
    m = moves[0]-1;
    bucket.push_back(board[idx_top[m]][m]);
    board[idx_top[m]][m] = 0;
    idx_top[m]++;
    
    // stack
    for (i = 1; i < len; i++) {
        m = moves[i]-1;
        if (idx_top[m] >= N )    continue;
        
        value = board[idx_top[m]][m];
        
        if ( *(bucket.end()-1) == value){
            cout << i << ": " << value << endl;    
            bucket.pop_back();
            answer += 2;
        }
        else 
            bucket.push_back(value);
        
        board[idx_top[m]][m] = 0;
        idx_top[m]++;
    }
    
    return answer;
}