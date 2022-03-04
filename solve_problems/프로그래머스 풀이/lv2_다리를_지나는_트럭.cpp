#include <string>
#include <vector>
#include <memory.h>
using namespace std;
// return t_idx(tail)
void push(int list[], int s_idx, int &t_idx, int len, int value, int q_len[], int time ) {
    list[t_idx] = value;
    q_len[t_idx] = time;
    if ( ++t_idx == len ) t_idx = 0;
}
// value return
void fpop(int list[], int &s_idx, int d_idx, int len) {
    s_idx++;
    if ( s_idx == len )     s_idx = 0;
}
bool isfull(int len, int s, int d, int w_sum) {
    if ( w_sum == 0 )   return false;
    if ( d == s )  return true;
    if ( d == len && s == 0)  return true;
    return false;
}
bool isempty(int len, int s, int d, int w_sum) {
    if (w_sum == 0) return true;
    return false;
}
int solution(int bridge_length, int weight, vector<int> truck_weights) {
    int answer = 0;
    int queue[bridge_length];
    int q_len[bridge_length];
    int time = 1, w_sum = truck_weights[0], i = 1, s_idx=0, d_idx=1;
    q_len[0] = 1;
    queue[0] = truck_weights[0];
    while ( i < truck_weights.size() ) {
        time++;
        // -
        if ( time - q_len[s_idx] == bridge_length ) {
            if ( !isempty(bridge_length, s_idx, d_idx, w_sum) ){
                w_sum -= queue[s_idx];
                printf("- %d time : %d q_len : %d \n", queue[s_idx], time, q_len[s_idx]);
                fpop(queue, s_idx, d_idx, bridge_length);
            }
        }
        // +
        if ( !isfull(bridge_length, s_idx, d_idx, w_sum)) {
            w_sum += truck_weights[i];
            if ( w_sum > weight ) {
                w_sum -= truck_weights[i];
                time = bridge_length + q_len[s_idx]-1; 
            } else {
                push(queue, s_idx, d_idx, bridge_length, truck_weights[i++], q_len, time);
                printf("push\n");
            }
        } else  time = bridge_length + q_len[s_idx]-1;
    }
    if (d_idx == 0) d_idx = bridge_length;
    answer = q_len[d_idx-1] + bridge_length;
    return answer;
}