# 미로탈출
from collections import deque
N = 5
M = 6
x = 0
y = 0

list = [[1,0,1,0,1,0], 
        [1,1,1,1,1,1], 
        [0,0,0,0,0,1], 
        [1,1,1,1,1,1], 
        [1,1,1,1,1,1]]

queue = deque()
answer = [[0,0]]
def move(list) :
  global N
  global M
  global x
  global y
  global queue
  direction = [0, 0, 0, 0] # x+1, y+1, x-1, y-1


  if x+1 < N and list[x+1][y] == 1 :
    print("1",x+1, y)
    queue.append([x+1,y])
    list[x+1][y] = -1
    direction[0] = 1
  if y+1 < M and list[x][y+1] == 1 :
    print("2",x, y+1)
    queue.append([x,y+1])
    list[x][y+1] = -1
    direction[1] = 1

  if 1 <= x and list[x-1][y] == 1 :
    print("3",x-1,y)
    queue.append([x-1,y])
    list[x-1][y] = -1
    direction[2] = 1

  if 1 <= y and list[x][y-1] == 1 :
    print("4", x,y-1)
    queue.append([x,y-1])
    list[x][y-1] = -1
    direction[3] = 1
  
  if x == N-1 and y == M-1 and list[x][y] == -1 :
    return 0 
  if direction.count(1) == 0 :
    pop = queue.pop()
    print("pop : ", pop)
    x = queue[len(queue)-1][0]
    y = queue[len(queue)-1][1]

  if direction[1] == 1 :
    y = y+1
  elif direction[0] == 1 :
    x = x+1
  elif direction[2] == 1 :
    x = x-1
  elif direction[3] == 1 :
    y = y-1
  

  
queue.append([x,y])
list[0][0] = -1
while True:
  ret = move(list)
  if y == M-1 and x == N-1:
    answer.append([x,y])
    print("while",x, y)
    break
  print("while",x, y)
  answer.append([x,y])

llast = queue[0]
last = queue[1]
for i in queue :
  if i == queue[0] or i == queue[1] :
    continue

  print(i)
  if (abs(last[0]-i[0]+last[1]-i[1]) == 1)or(abs(llast[0]-last[0]+llast[1]-last[1]) == 1):
      continue
  else :
    print(12)
    queue.remove(last)      
print(len(answer))
print(answer)