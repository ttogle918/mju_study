# 음료수 얼려먹기

N = 4
M = 5

list = [[0, 0, 1, 1, 0], [0, 0, 0, 1, 1], [1, 1, 1, 1, 1], [0,0,0,0,0]]
i = 0
j = 0

def findHole(list, i, j) :
  hole = []
  count = 0

  if i < 0 or i >= N or j < 0 or j >= M :
    return -1
  if list[i][j] == -1 :
    return 1
  if list[i][j] == 0 :
    count = count + 1
    list[i][j] = -1

  if i < N - 1 :
    if list[i+1][j] == 0 :
      count = count + 1
      list[i+1][j] = -1

    if j < M - 1 :
      if list[i][j+1] :
        count = count + 1
        list[i][j+1] = -1

    if list[i+1][j+1] == 0 :
      count = count + 1
      list[i+1][j+1] = -1

  if i > 0 :
    if list[i-1][j] == 0 :
      count = count + 1
      list[i-1][j] = -1

    if j > 0 :
      if list[i-1][j-1] == 0 :
        count = count + 1
        list[i-1][j-1]= -1

      if list[i][j-1] == 0 :
        count = count + 1
        list[i][j-1] = -1
  
  if count == 0 : 
    return 0

  if count > 0 and i < N-1 and j < M-1:
    print(count,"1")
    return count + findHole(list, i+1, j) + findHole(list, i, j+1)
  if count > 0 and i < N-1 :
    print(count + "2")
    return count + findHole(list, i+1, j)
  if count > 0 and j < M-1:
    print(count + "3")
    return count + findHole(list, i, j+1)
  if i > 0 and j > 0 :
    print(count + "4")
    return count
  
  return count
answer = 0
while True :
  cnt = findHole(list, i, j)
  if answer == -1 : 
     print("wrong i, j")
     break
  elif answer == 0 :
    i = i + 1
    j = j + 1
  else : 
    answer = answer + 1
    print(answer)
    break