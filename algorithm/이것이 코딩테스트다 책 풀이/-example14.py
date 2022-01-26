# 부품찾기
N = 5 # 부품 개수
shop_product_id = [8,3,7,9,2]
M = 3# 손님 주문
order_id = [5,7,9]

order = order_id
yesorno = ['yes', 'no']

shop_product_id.sort()
print(shop_product_id)

for i in range(len(order_id)) :
  mid = len(shop_product_id)
  print(mid)
  mid = int( mid / 2)
  j = mid
  while True :
    print(mid)
    if shop_product_id[mid] == order_id[i] :
      order[i] = yesorno[0]
      print(j,mid, shop_product_id[mid], 't')
      break
    
    if 1 > mid : 
      order[i] = yesorno[1]
      print(j,mid, shop_product_id[mid], 'f')
      break    
    mid = int( mid / 2)  
    
    if shop_product_id[mid] < order_id[i] :
      
      j = j + mid
      print(j, 1)
      print(j,mid, shop_product_id[mid])

    else :
      j = j - mid
      print(j, 2)
      print(j,mid, shop_product_id[mid])
    
    