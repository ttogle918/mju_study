# version 1
import math
def solution(left, right):
    sqrt_left = math.ceil(math.sqrt(left))
    sqrt_right = math.ceil(math.sqrt(right))
    
    if right == sqrt_right ** 2 :
        sqrt_right += 1
        
    m_sum = [i**2 for i in range(sqrt_left, sqrt_right)]
    ssum = 0
    
    for i in range(left, right+1) :
        ssum += i

    return ssum - 2 * sum(m_sum)

# version 2 : 다른 유저가 만든 코드
def solution(left, right):
    return sum([-n if int(n ** 0.5) == n ** 0.5 else n for n in range(left, right+1)])