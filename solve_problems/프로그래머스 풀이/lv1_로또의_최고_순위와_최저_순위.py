def solution(lottos, win_nums):
    lottos.sort()
    win_nums.sort()

    cnt_zero = lottos.count(0)
    sum = 0    
    for win in win_nums :     # lotto 번호는 6가지이다.
        try :
            start = lottos.index(win)+1
            sum += 1
        except : 
            start = 0
        lottos = lottos[start:]     # sort되어있기 때문에 앞에 글자는 없어도 된다.
    
    answer = [sum+cnt_zero, sum]
    return [6 if num < 2 else 7-num for num in answer ]

    
        
    return answer