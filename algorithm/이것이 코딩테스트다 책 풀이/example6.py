# 시각
# n 입력 시 00시 00분 00초부터 N시 59분 59초까지 중 
# 3 포함되는 경우의 수 구하기

n = 5
h_count = 0
count = 0

# int형에서 %10 == 3 으로도 구할 수 있군
for i in range(60) :
  s = str(i)
  if s[0:1] == '3' or s[1:2] == '3' :
    count = count + 1
for i in range(n) :
  s = str(i)
  if s[0:1] == '3' or s[1:2] == '3' :
    h_count = h_count + 1
# 확률~
# h_count*60*60
# (n+1-h_count)*count*60
# (n+1-h_count)*(60-count)*count

print(h_count*60*60 + (n+1-h_count)*count*60 + (n+1-h_count)*(60-count)*count)