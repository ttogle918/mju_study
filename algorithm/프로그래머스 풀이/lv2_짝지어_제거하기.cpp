#include <iostream>
#include <string>
using namespace std;

int solution(string s)
{
    int answer = 0;
    int len = s.length();
    
    if ( len % 2 == 1 ) return 0;
    
    char stack[len];
    stack[0] = s[0];
    int tail = 0, i = 1;
    
    while ( i < len ) {
        if ( tail == -1 || stack[tail] != s[i] )
            stack[++tail] = s[i];
        else if ( stack[tail] == s[i] )
            tail--;
        
        i++;
    }
    
    if ( tail == -1 )    return 1;
    return answer;
}