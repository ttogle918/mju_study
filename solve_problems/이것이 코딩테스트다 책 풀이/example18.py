# 다익스트라
# 한 노드에서 다른 노드까지 쵣ㄴ경로 구하기

import heapq
import sys
input = sys.stdin.readline
INF = int(1e9)  # 무한

n , m = map(int, input().split())

start = int(input())  # 시작 노드번호 입력

graph = [[] for i in range(n+1)]

distance = [INF] * (n+1)

# 간선 정보 입력받기
for _ in range(m) :
  a, b, c = map(int, input().split())
  graph[a].append((b,c))
  

def dijkstra(start) :
  q = []
  heapq.heappush(q, (0, start))
  distance[start] = 0

  while q:
    dist, now = heaq.heappop(q)
    
    if distance[now] < dist :
      continue

    for i in graph[now] :
      cost = dist + i[1]

      if cost < distance[i[0]] :
        distance[i[0]] = cost
        heapq.heappush(q, (cost, i[0]))

dijkstra(start)

for i in range(1, n+1) :
  if distance[i] == INF:
    print("INFINITY")
  else :
    print(distance[i])
    