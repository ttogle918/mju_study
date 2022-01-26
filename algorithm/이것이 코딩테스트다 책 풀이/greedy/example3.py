# 숫자 카드 게임
n = 3
m = 3
# n(가로) * m(세로) 행렬
# 행 선택 -> 가장 작은 숫자뽑ㅇ보자.
# 최종적으로 ㄱ자ㅏㅇ 높은숫자뽑기
list = [[3, 1, 2], [4, 1, 4], [2, 2, 2]]

row1 = list[0]
row1.sort()
print(row1)
row2 = list[1]
row2.sort()
print(row2)
row3 = list[2]
row3.sort()
print(row3)

col = [list[0][0], list[1][0], list[2][0]]

result = max(col)

print(result)
col_index = col.index(result)
col[col_index] = list[col_index][1]
# list.remove(result) # remove는 값을 찾아서 없앰. del은 인덱스를없앰
