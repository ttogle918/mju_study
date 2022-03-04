#include <string>
#include <vector>
#include <cstdlib>
#include <iostream>
using namespace std;
string rethand(string hand, int n, int &l, int &r) {
    if ( hand == "left" ){
        l = n;
        return "L";
    }
    else if ( hand == "right" ){
        r = n;
        return "R";
    }
    return "";
}
string solution(vector<int> numbers, string hand) {
    string answer = "";
    int i ;
    int l=10, r=11, l_d, r_d, n;
    int row[12] = {3, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3};
    int col[12] = {2, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 0};
    
    for (i = 0; i < numbers.size(); i++) {
        n = numbers[i];
        
        // L
        if ( col[n] == 1 ) {
            answer += "L";
            l = n;
        }
        // R
        else if ( col[n] == 0 ) {
            answer += "R";
            r = n;
        }
        // mid col[n]==2
        else {
            l_d = abs(row[n] - row[l]);
            if ( col[l] == 1 )  l_d++;

            r_d = abs(row[n] - row[r]);
            if ( col[r] == 0 )  r_d++;
            
            if ( l_d == r_d ) 
                answer += rethand(hand, n, l, r);
            else if ( l_d < r_d ) {
                answer += "L";
                l = n;
            }
            else {
                answer += "R";
                r = n;
            }
        }
    }
    
    return answer;
}