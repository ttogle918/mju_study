# 개미 전사
N = 4

list = [1,3,1,5]

answer = []

def loop(list) :
  global answer
  global start
  if len(list) == start + 2 :
    answer.append(min(list[start], list[start+1]))
    return 0
  if len(list) == start + 1 :
    answer.append(list[start])
    return 0

  if list[start] + list[start+2] <= list[start+1] :
    print(answer.pop())
    answer.append(list[start+1])
    start = start + 1
    print(answer)
  else :
    print(answer.pop())
    answer.append(list[start])
    start = start + 2
    print(answer)

  
  return start

start = 0
answer.append(list[start])
while start < len(list) :
  start = loop(list)
  if start == 0 :
    print(answer)
    break