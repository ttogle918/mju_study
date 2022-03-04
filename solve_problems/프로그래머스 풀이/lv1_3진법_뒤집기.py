# version 1
def solution(n):
    answer = 0
    position = 1
    
    while n > 0 :
        n, mod = divmod(n, 3)
        answer += mod * position
        position = position * 10
    
    answer += n * position
    answer = str(answer)
    
    t_answer = ''
    for s in answer :
        t_answer += s
    
    position = 3
    t_answer = int(answer[0])
    
    for n in answer[1:] :
        t_answer += int(n) * position
        position *= 3
    return t_answer

# version 2
def solution(n):
    answer = ''
    
    while n > 0 :
        answer += str(n % 3)
        n = n // 3      # 3으로 나눈 값에 int한 결과와 같다.

    return int(answer, 3)    # t_answer이 3진법일 때 10진법의 값