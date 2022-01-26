# 성적이 낮은 순서로 학생 출력하기

N = 2
list = [["홍길동", 95], ["이순신", 77]]
print(list)
savedlist = []
for i in range(101) :
  savedlist.append(0)

for i in range(len(list)) :
  savedlist[list[i][1]] = list[i][0]

for i in range(101) :
  if savedlist[i] != 0 :
    print(savedlist[i])