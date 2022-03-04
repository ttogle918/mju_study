# 두 배열의 원소 교체
N, K = 5, 3
A = [1,2,5,4,3]
B = [5,5,6,6,5]


minimun = min(A)
maximun = max(B)

for i in range(K) :
  indexA = A.index(min(A))
  indexB = B.index(max(B))
  A[indexA], B[indexB] = B[indexB], A[indexA]

print(sum(A))