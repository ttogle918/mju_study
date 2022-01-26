# 1이 될 때까지
# N - 1 , N / K 수행하여 1만들기
# result = 횟수
n = 17
k = 4
count = 0

while n != 1 :
  div, mod = divmod(n, k)
  if mod == 0 :
    n = div
    count += 1
  else :  
    n = n - 1
    count += 1

print(count)