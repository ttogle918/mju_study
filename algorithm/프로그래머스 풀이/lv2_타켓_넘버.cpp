#include <string>
#include <vector>

using namespace std;
void recur(int num, vector<int> &s, int idx){
    vector<int> temp;
    temp.swap(s);
    for ( int i = 0; i < idx; i++) {
        s.push_back(temp[i] + num);
        s.push_back(temp[i] - num);
    }
}

int solution(vector<int> numbers, int target) {
    int answer = 0;
    int size = numbers.size();
    int idx = 2;
    vector<int> sum = {numbers[0], (-1)*numbers[0]};
    
    for (int i = 1; i < size; i++, idx*=2) {
        recur(numbers[i], sum, idx);
    }
    for (vector<int>::iterator itor = sum.begin(); itor != sum.end(); itor++) {
        if (*itor == target)    answer++; 
    }
    return answer;
}