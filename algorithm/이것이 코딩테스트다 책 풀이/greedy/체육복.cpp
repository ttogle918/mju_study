#include <string>
#include <vector>
#include <iostream>
using namespace std;
void swap(vector<int> list, int i, int j) {
    int n = list[i];
    list[i] = list[j];
    list[j] = n;
}
int solution(int n, vector<int> lost, vector<int> reserve) {
    int answer = 0;
    int i, j, l, r, l_idx = 0, r_idx = 0;
    int l_size = lost.size();
    int r_size = reserve.size();
    
    // sort
    for (i = 1; i < l_size || i < r_size; i++) {
        for (j = 0; j < i; j++) {
            if ( i < l_size && lost[j] > lost[i] )    swap(lost, i, j);
            if ( i < r_size && reserve[j] > reserve[i] )    swap(reserve, i, j);
        }
    }
    
    // lost == reserve, remove idx
    while( r_idx < r_size && l_idx < l_size ) {
        l = lost[l_idx];
        r = reserve[r_idx];
        if ( l == r ) {
            lost.erase(lost.begin()+l_idx);
            l_size--;
            reserve.erase(reserve.begin()+r_idx);
            r_size--;
        }
        else if ( l > r )    r_idx++;            
        else if ( l < r )    l_idx++;
    }
    if ( l_size == 0 || r_size == 0 )   return n - lost.size();
    
    l_idx = 0;
    r_idx = 0;
    while( l_idx < l_size && r_idx < r_size ) {
        l = lost[l_idx];
        r = reserve[r_idx];
        if ( l == r+1 ) {
            lost.erase(lost.begin()+l_idx);
            l_size--;
            reserve.erase(reserve.begin()+r_idx);
            r_size--;
        }
        else if ( l+1 == r ) {
            lost.erase(lost.begin()+l_idx);
            l_size--;
            reserve.erase(reserve.begin()+r_idx);
            r_size--;
        }
        else if ( l > r )    r_idx++;            
        else if ( l < r )    l_idx++;
    }
    answer = n - lost.size();
    return answer;
}