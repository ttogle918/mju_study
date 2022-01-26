# 상하좌우
# n은 공간의 크키 5*5
# list는 이동계획서 내용
# x, y 좌표 구하기 (1, 1)부터 시작
n = 5
x = 1
y = 1
list = ['R', 'R', 'R', 'U', 'D', 'D']

for i in list :
  if i == 'L' :
    if x == 1 :
      continue
    x = x - 1
  elif i == 'U' :
    if y == 1 :
      continue
    y = y - 1
  elif i == 'R' :
    if x == n :
      continue
    x = x + 1
  elif i == 'D' :
    if y == n :
      continue
    y = y + 1

print(y, x)