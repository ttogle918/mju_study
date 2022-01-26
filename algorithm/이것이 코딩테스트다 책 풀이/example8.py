# 게임 개발
# n * m 크기 
# x는 북으로부터 떨어진 개수
# y는 서쪽으로부터 떨어진 개수
n = 4
m = 4

mx = 1
my = 1
d = 0
# [0, 1, 2, 3]  # 북 동 남 서
list = [[1,1,1,1], [1,0,0,1], [1,1,0,1], [1,1,1,1]]

cnt = 0


# 반시계방향 90도 돌기
def turn() :
  global d
  if d == 0 :
    d = 3
  else :
    d = d - 1

def findComplete(value, complete, loc) :
  if value == 0 : # 육지
    if complete.count(loc) == 0 :
      return 0
  
  return 1


if list[mx][my] != 0 :
  print("위치 설정 잘못되었습니다.")


n = True   # 움직일 자리가 있는가?
s = True
w = True
e = True
complete = [[mx, my]]

while True:

  if d == 0 : # 북

    if findComplete(list[mx-1][my], complete, [mx-1, my]) == 0 :
      mx = mx - 1
      cnt = cnt + 1
      complete.append([mx, my])
      print(mx, my)
      n = True
    else :
      n = False

  elif d == 1 : # 동
    if findComplete(list[mx+1][my], complete, [mx+1, my]) == 0 :
      mx = mx + 1
      cnt = cnt + 1
      print(mx, my)
      complete.append([mx, my])
      n = True
    else : 
      e = False
  
  elif d == 2 : # 남
    if findComplete(list[mx][my+1], complete, [mx, my+1]) == 0 :
      my = my + 1
      cnt = cnt + 1
      print(mx, my)
      complete.append([mx, my])
      n = True
    else :
      s = False
  
  elif d == 3 : # 서
    if findComplete(list[mx][my-1], complete, [mx, my-1]) == 0 :
      my = my - 1
      cnt = cnt + 1
      print(mx, my)
      complete.append([mx, my])
      n = True
    else :
      w = False
  turn()

  if w == False and n == False and s == False and e == False :
    if d == 0 :
      if list[mx][my+1] == 0 :
        continue
      print("정답 : " ,cnt)
      break;
