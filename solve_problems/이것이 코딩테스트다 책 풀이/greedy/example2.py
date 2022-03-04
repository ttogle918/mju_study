# 큰 수의 법칙
n = 5
m = 8
k = 3
# n = len, m번, k개 이하

list = [2,4,5,4,6]
list.sort()

first = list[n-1]
second = list[n-2]
if first == second :  
    print(first*m)
    #return first * m

div, mod = divmod(m, k+1)
result = 0

result = ( first * k + second ) * div
# for i in range(div) : 
#  result = result+ first * k + second 
 
result += first * mod
print(result)
#return