#include <string>
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

bool solution(vector<string> phone_book) {
    bool answer = true;
    int len = 0;
    sort(phone_book.begin(), phone_book.end());
    
    for (int i = 1; i < phone_book.size(); i++) {
        len = phone_book[i-1].length();
        if (phone_book[i-1] == phone_book[i].substr(0, len))
            return false;
    }
    return answer;
}