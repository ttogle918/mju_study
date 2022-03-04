# 떡볶이떡 만들기

N, M = 4, 6 #개수, 길이
dduc_height = [19, 15, 10, 17]
minus = []
# H 최댓값

dduc_height = sorted(dduc_height, reverse=True)
i = 1
while i < len(dduc_height) :
  minus.append(dduc_height[0] - dduc_height[i])
  i = i + 1

s = 0
i = 0
num = 1
while s < M and i <= dduc_height[0] and num <= len(minus) :
  s = s + num 
  
  if i == minus[num-1] :
    num = num + 1
  i = i + 1
i = i-1
print(s, num, i)
print(dduc_height[0] - i)