#include <string>
#include <vector>

using namespace std;

vector<int> solution(vector<int> prices) {
    int len = prices.size();
    vector<int> answer(len, -1);
    int i, j, top=0, t, time=0, pri;
    int stack[len];    // idx 넣기(시간)
    stack[0] = 0;
    
    for (i = 1; i < len; i++) {
        pri = prices[i];
        if ( top == -1 || pri>=prices[stack[top]])    stack[++top] = i;
        else {  // decrese
            for (j=top; j > -1 && pri < prices[stack[j]]; j--) {
                answer[stack[j]] = i - stack[j];
                top--;
            }
            stack[++top] = i;
        }
    }
    for (i = 0 ; i <= top; i++) {
        t = stack[i];
        answer[t] = len - t-1;
    }
    return answer;
}