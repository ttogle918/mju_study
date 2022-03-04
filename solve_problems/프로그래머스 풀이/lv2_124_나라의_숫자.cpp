#include <string>
#include <vector>

using namespace std;
// string 말고 char로는?
string ret(int n) {
    if (n == 0) return "1";
    if (n == 1) return "2";
    return "4";
}
string solution(int n) {
    string answer = "";
    n--;
    int mod = n % 3;
    
    while ( n > 2 ) {
        answer = ret(mod) + answer;
        n = n / 3 - 1;
        mod = n % 3;
    }
    answer = ret(mod) + answer;
    return answer;
}