#include <string>
#include <vector>

using namespace std;

void decent(const vector<int> tri, int h, int arr[], int height) {
    if (h == height)    return;
    if (h == 1) {
        arr[h] = arr[0] + tri[h];    
        arr[0] = arr[0] + tri[0];
    } 
    else {
        int before = arr[0];
        int after;
        arr[0] = arr[0] + tri[0];
        for (int i = 1; i < h; i++) {
            after = arr[i];
            if ( before > after )
                arr[i] = before + tri[i];
            else
                arr[i] = after + tri[i]; 
            before = after;
        }
        arr[h] = after + tri[h];
    }
}
int solution(vector<vector<int>> triangle) {
    int answer = 0;
    int height = triangle.size();
    int arr[height];
    
    arr[0] = triangle[0][0];
    for (int i = 1; i < height; i++)
        decent(triangle[i], i, arr, height);
    
    answer = arr[0];
    for (int i = 1; i < height; i++)
        if ( answer < arr[i] )
            answer = arr[i];
    
    return answer;
}