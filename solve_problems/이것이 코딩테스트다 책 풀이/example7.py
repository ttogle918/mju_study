# 왕실의 나이트
# 주어진 어떤 위치에 위치해있다. L자형태로만 이동 가능
# 수평으로 2칸, 수직 1칸
# 수직 2칸, 수평 1칸
# 위치가 주어졌을 때 이동할 수 있는 경우의 수 구하기(한 번만 이동)

n = 8

location = 'a1'
x = location[0:1]
y = location[1:2]
list = ['RU', 'RD', 'LU', 'LD', 'DR', 'DL', 'UR', 'UL']
# 2 * 4
count = 8
def defaultRemove(list, str) :
  if list.count(str) > 0  :
    list.remove(str)

  defaultRemove(list, 'LU')
  defaultRemove(list, 'LD')
  defaultRemove(list, 'UL')
  defaultRemove(list, 'DL')
elif x == 'b' :
  defaultRemove(list, 'LU')
  defaultRemove(list, 'LD')
elif x == 'g' :
  defaultRemove(list, 'RU')
  defaultRemove(list, 'RD')

elif x == 'h' :
  defaultRemove(list, 'RU')
  defaultRemove(list, 'RD')
  defaultRemove(list, 'UR')
  defaultRemove(list, 'DR')
  
if y == '1' :
  defaultRemove(list, 'UR')
  defaultRemove(list, 'UL')
  defaultRemove(list, 'RU')
  defaultRemove(list, 'LU')
  
elif y == '2' : 
  defaultRemove(list, 'UR')
  defaultRemove(list, 'UL')
  
elif y == '7' :
  defaultRemove(list, 'DR')
  defaultRemove(list, 'DL')
  
elif y == '8' :
  defaultRemove(list, 'DR')
  defaultRemove(list, 'DL')
  defaultRemove(list, 'RD')
  defaultRemove(list, 'LD')
  
print(len(list))