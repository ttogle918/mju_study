# 1로 만들기

X = 26
count = 0
list = [X, 'next']
torf = []
print(list)
def loop(start, end) : 
  global list
  
  for x in list[start:end] :
    
    
    if x % 5 == 0 :
      list.append(x/ 5)
      
    if x % 3 == 0 :
      list.append( x / 3)
    if x % 2 == 0 :
      list.append(x / 2)
    list.append(x - 1)
    print(list) 

  list.append('next')
  start = end + 1  
  end = len(list) - 1
  print("s")
  return start, end

start = 0
end = 1
while X > 1 :
  start, end = loop(start, end)
  if list.count(1) > 0 :
    break
print(list.count('next')-1)