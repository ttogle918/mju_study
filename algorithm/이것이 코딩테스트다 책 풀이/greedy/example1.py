# 거스름돈 구하기
money = 1260
n = money

list = [500, 100, 50, 10]
result = []

for i in list :
  r, temp = divmod(n, i)

  result.append(r)
  n = n - i * r
  print(n)
print(result)
