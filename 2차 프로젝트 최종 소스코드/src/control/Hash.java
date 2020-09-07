package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import frames.TPResultTable;
import model.Product;

public class Hash {
   // 데이터를 저장할 Product는 값과 다음 Product를 가진다.
   private int size;
   private Product[] hTable;
   private int num;   // num 글자 이상 검색가능.
   private int stock;	// 해당 재고수 이상 행사
   private int shelfLife;
   private EventData EData;
   InsertionSort sort;
   // Hash Table은 size와 배열 테이블을 생성한다.
   public Hash(int size) {
      this.size = size;
      this.hTable = new Product[size];
      this.num = 3;
      this.stock = 30;
      this.shelfLife = 3;
      this.EData = new EventData(this);
      sort = new InsertionSort();
   }

   // key 값으로 HashTable에 저장될 index를 반환한다.
   public int hashMethod(int key) {
      return key % size;
   }

   // 저장할 데이터로 key를 추출한다.
   // 실제 Hash 에는 특별한 알고리즘이 적용되 hashCode를 얻는다.
   public int getKey(String data) {
      int i = 0;
      long key = 0;
      boolean isNeg = false;
      // Check for negative sign; if it's there, set the isNeg flag
      if (data.charAt(0) == '-') {
         isNeg = true;
         i = 1;
      }
      // Process each character of the string;
      while (i < num) {
         key = key % size;
         key *= 2;
         key += (int)data.charAt(i++); // Minus the ASCII code of '0' to get
                                 // the value of the charAt(i++).
      }
      if (isNeg) {
         key = -key;
      }
      return (int) key;
   }

   // data를 HashTable에 저장한다.
   public int add(String data, int price, String type, String shop) {
      // data의 hashCode를 key와 저장할 index를 얻는다.
      int key = getKey(data);
      int hashValue = hashMethod(key);
      // 저장할 Product 생성
      Product product = new Product();
      product.setData(data);
      product.setPrice(price);
      product.setType(type);
      product.setShop(shop);
      
      // HashTable의 저장할 index가 비어있다면, 저장하고 끝낸다.
      if (hTable[hashValue] == null) {
         hTable[hashValue] = product;
         return 0;
      }
      Product pre = null;
      Product cur = hTable[hashValue];
      // 해당 index의 연결리스트는 값의 크기가 작은 값부터 큰 순서로 정렬되어있다.
      while (true) {
         if (cur == null) { // Hash Table에 저장할 index가 비어있으면
            hTable[hashValue] = product; // 해당 index에 저장
            return 0;
         } else if (getKey(cur.getData()) < key) { // 해당 index의 커서 노드의
            // 값이 key보다 작으면 커서를 하나 뒤로 옮긴다.
            pre = cur;
            cur = cur.next;
         } else { // 해당 index의 커서 노드의 값이 key 보다 크다.(여기에 저장)
            // 커서 노드가 HashTable의 첫번째 노드이면
            if (cur == hTable[hashValue]) {
               // 삽입 노드를 첫번째 노드로 삽입하고 다음 노드를 커서노드로 지정한다.
               product.next = cur;
               hTable[hashValue] = product;
               return 0;
            } else { // 첫번째 노드가 아니면
               // 삽입 노드의 다음 노드로 커서노드를 지정하고
               // 전 노드의 다음 노드를 삽입노드로 지정한다.
               product.next = cur;
               pre.next = product;
               return 0;
            }
         }
      }
   }

   // data를 HashTable에 저장한다. 재고도 add함.
   public int add(String data, int price, String type, String shop, int n) {
      // data의 hashCode를 key와 저장할 index를 얻는다.
      int key = getKey(data);
      int hashValue = hashMethod(key);
      // 저장할 Product 생성
      Product product = new Product();
      product.setData(data);
      product.setPrice(price);
      product.setType(type);
      product.setShop(shop);
      product.setStock(n);
      
      if(n > stock){
    	  EData.setStockData(hashValue);
      }
      
      // HashTable의 저장할 index가 비어있다면, 저장하고 끝낸다.
      if (hTable[hashValue] == null) {
         hTable[hashValue] = product;
         return 0;
      }
      Product pre = null;
      Product cur = hTable[hashValue];
      // 해당 index의 연결리스트는 값의 크기가 작은 값부터 큰 순서로 정렬되어있다.
      while (true) {
         if (cur == null) { // Hash Table에 저장할 index가 비어있으면
            hTable[hashValue] = product; // 해당 index에 저장
            return 0;
         } else if (getKey(cur.getData()) < key) { // 해당 index의 커서 노드의
            // 값이 key보다 작으면 커서를 하나 뒤로 옮긴다.
            pre = cur;
            cur = cur.next;
         } else { // 해당 index의 커서 노드의 값이 key 보다 크다.(여기에 저장)
            // 커서 노드가 HashTable의 첫번째 노드이면
            if (cur == hTable[hashValue]) {
               // 삽입 노드를 첫번째 노드로 삽입하고 다음 노드를 커서노드로 지정한다.
               product.next = cur;
               hTable[hashValue] = product;
               return 0;
            } else { // 첫번째 노드가 아니면
               // 삽입 노드의 다음 노드로 커서노드를 지정하고
               // 전 노드의 다음 노드를 삽입노드로 지정한다.
               product.next = cur;
               pre.next = product;
               return 0;
            }
         }
      }
   }

   public int add(String data, int price, String type, String shop, int n, int date) {
      // data의 hashCode를 key와 저장할 index를 얻는다.
      int key = getKey(data);
      int hashValue = hashMethod(key);
      // 저장할 Product 생성
      Product product = new Product();
      product.setData(data);
      product.setPrice(price);
      product.setType(type);
      product.setShop(shop);
      product.setStock(n);
      product.setShelfLife(date);
      
      if(n > this.stock){
    	  EData.setStockData(hashValue);
      }
      
      long curr = System.currentTimeMillis();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      String datetime = sdf.format(new Date(curr));
      datetime = datetime.substring(0, 4)+datetime.substring(5, 7)+datetime.substring(8, 10);
      
      if(date-Integer.parseInt(datetime) <= this.shelfLife){
    	  EData.setLifeData(hashValue);
      }
      // HashTable의 저장할 index가 비어있다면, 저장하고 끝낸다.
      if (hTable[hashValue] == null) {
         hTable[hashValue] = product;
         return 0;
      }
      Product pre = null;
      Product cur = hTable[hashValue];
      // 해당 index의 연결리스트는 값의 크기가 작은 값부터 큰 순서로 정렬되어있다.
      while (true) {
         if (cur == null) { // Hash Table에 저장할 index가 비어있으면
            hTable[hashValue] = product; // 해당 index에 저장
            return 0;
         } else if (getKey(cur.getData()) < key) { // 해당 index의 커서 노드의
            // 값이 key보다 작으면 커서를 하나 뒤로 옮긴다.
            pre = cur;
            cur = cur.next;
         } else { // 해당 index의 커서 노드의 값이 key 보다 크다.(여기에 저장)
            // 커서 노드가 HashTable의 첫번째 노드이면
            if (cur == hTable[hashValue]) {
               // 삽입 노드를 첫번째 노드로 삽입하고 다음 노드를 커서노드로 지정한다.
               product.next = cur;
               hTable[hashValue] = product;
               return 0;
            } else { // 첫번째 노드가 아니면
               // 삽입 노드의 다음 노드로 커서노드를 지정하고
               // 전 노드의 다음 노드를 삽입노드로 지정한다.
               product.next = cur;
               pre.next = product;
               return 0;
            }
         }
      }
   }
   
   public int add(String data, int price, String type, String shop, int n, int date, String address) {
	      // data의 hashCode를 key와 저장할 index를 얻는다.
	      int key = getKey(data);
	      int hashValue = hashMethod(key);
	      // 저장할 Product 생성
	      Product product = new Product();
	      product.setData(data);
	      product.setPrice(price);
	      product.setType(type);
	      product.setShop(shop);
	      product.setStock(n);
	      product.setShelfLife(date);
	      product.setShopAddress(address);
	      
	      if(n > this.stock){
	    	  EData.setStockData(hashValue);
	    	  System.out.println(n);
	      }
	      long curr = System.currentTimeMillis();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	      String datetime = sdf.format(new Date(curr));
	      
	      datetime = datetime.substring(0, 4)+datetime.substring(5, 7)+datetime.substring(8, 10);
	      if(date-Integer.parseInt(datetime) <= this.shelfLife){
	    	  EData.setLifeData(hashValue);    
	      }
	      // HashTable의 저장할 index가 비어있다면, 저장하고 끝낸다.
	      if (hTable[hashValue] == null) {
	         hTable[hashValue] = product;
	         return 0;
	      }
	      Product pre = null;
	      Product cur = hTable[hashValue];
	      // 해당 index의 연결리스트는 값의 크기가 작은 값부터 큰 순서로 정렬되어있다.
	      while (true) {
	         if (cur == null) { // Hash Table에 저장할 index가 비어있으면
	            hTable[hashValue] = product; // 해당 index에 저장
	            return 0;
	         } else if (getKey(cur.getData()) < key) { // 해당 index의 커서 노드의
	            // 값이 key보다 작으면 커서를 하나 뒤로 옮긴다.
	            pre = cur;
	            cur = cur.next;
	         } else { // 해당 index의 커서 노드의 값이 key 보다 크다.(여기에 저장)
	            // 커서 노드가 HashTable의 첫번째 노드이면
	            if (cur == hTable[hashValue]) {
	               // 삽입 노드를 첫번째 노드로 삽입하고 다음 노드를 커서노드로 지정한다.
	               product.next = cur;
	               hTable[hashValue] = product;
	               return 0;
	            } else { // 첫번째 노드가 아니면
	               // 삽입 노드의 다음 노드로 커서노드를 지정하고
	               // 전 노드의 다음 노드를 삽입노드로 지정한다.
	               product.next = cur;
	               pre.next = product;
	               return 0;
	            }
	         }
	      }
	   }

   // data가 있는 위치를 얻는다.
   public int get(String data) {
      int key = getKey(data);
      int hashValue = hashMethod(key);
      // data가 있는 index의 첫번째 노드를 얻는다.
      Product cur = hTable[hashValue];
      while (true) {
         if (cur == null) { // index 가 비어있다면 -1 반환
            return -1;
         } else if (getKey(cur.getData()) == key) { // 커서의 값과 키가 같으면 0 반환
            return hashValue;
         } else if (getKey(cur.getData()) > key) { // 커서의 값이 키보다 크면 -1 반환
            // 리스트는 작은 값에서 큰 값으로 정렬되어있다.
            return -1;
         } else { // 커서의 값이 키보다 작으면 다음 노드로 커서 이동
            cur = cur.next;
         }
      }
   }

   // data가 있는 노드를 반환한다.
   public Product getProduct(String data) {
      int key = getKey(data);
      int hashValue = hashMethod(key);
      // HashTable의 index의 첫번째 노드와 두번째 노드
      Product cur = hTable[hashValue];
      Product pre = cur.next;
      while (true) {
         if (cur == null) { // 커서 노드가 null 이면 null 반환
            return null;
         } else if (pre == null) {
            return cur;
         } else if (getKey(cur.getData()) == key) { // 커서 노드의 값이 키와 같으면 전 노드를
                                          // 반환
            return cur;
         } else if (getKey(cur.getData()) > key) { // 커서의 값이 키보다 크면 null 반환
            System.out.println("data>key");
            return cur;
         } else { // 커서의 값이 키보다 작으면 커서를 다음으로 이동
            pre = cur;
            cur = cur.next;
         }
      }
   }

   // data 를 제거
   public boolean remove(String data, String shop) {
		Product pre = null;
		// data가 있는 노드의 전노드를 가져오고 null이면 -1 반환
		if ((pre = getProduct(data)) == null) {
			return false;
		}
		// 전 노드가 data의 다음노드를 가리키게 한다.
		// data 노드를 건너뛰게 연결한다 (제거)
		while(!pre.getShop().equals(shop)){
			pre=pre.next;
		}
		
		Product cur = pre.next;
		pre.next = cur.next;
		return true;
	}

   public void getPrice(String data) {
      if (getProduct(data) != null) {
         System.out.println(getProduct(data).getPrice());
      }
   }

   public String toString() {
      StringBuffer result = new StringBuffer();
      Product cur = null;
      for (int i = 0; i < size; i++) {
         result.append("[" + i + "]\t");
         cur = hTable[i];
         if (cur == null) {
            result.append("n ");
         } else {
            while (cur != null) {
               result.append("[상품명: " + cur.getData() + "가격: " + cur.getPrice() + "종류: " + cur.getType() + "] ");
               cur = cur.next;
            }
         }
         result.append("\n");
      }
      return result.toString();
   }

   public Product[] getHashTable() {
      return hTable;
   }

   public void searchProduct(String productname, TPResultTable rTable) {
      rTable.tableSetEmpty();
      Product product = getProduct(productname);
      if (product == null) {
         return;
      }
      while (product != null) {
         if(productname.length() < product.getData().length()+1 &&
               productname.equals(product.getData().substring(0, productname.length()))){
            rTable.tableUpdate(product.getData(), product.getPrice(), product.getType(), product.getShop(),
                  product.getStock(), product.getShelfLife());
            product = product.next;   
            // 밑에꺼가 속할수도잇..
         }else if (productname.equals(product.getData())) {
            rTable.tableUpdate(product.getData(), product.getPrice(), product.getType(), product.getShop(),
                  product.getStock(), product.getShelfLife());
            product = product.next;
         } else {
            product = product.next;
         }
      }
   }

   public void PriceSort(String productname, TPResultTable rTable) {
      sort = new InsertionSort();
	  sort.setArray(getProduct(productname.substring(0, 3)), productname);
      rTable.tableSetEmpty();
      sort.PriceSort();
      Product []product = sort.getProduct().toArray(new Product[sort.getProduct().size()]);
      if (product == null) {
         return;
      }
      int i = 0;
      while (i < product.length) {
         if(product[i] != null){
         if (productname.equals(product[i].getData().substring(0, productname.length()))) {
            rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
                  product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
         }
         	i++;
         }
      }
   }

   public void StockSort(String productname, TPResultTable rTable) {
	  sort = new InsertionSort();
	  sort.setArray(getProduct(productname.substring(0, 3)), productname);
      rTable.tableSetEmpty();
      sort.StockSort();
      Product product[] = sort.getProduct().toArray(new Product[sort.getProduct().size()]);
      if (product == null) {
         return;
      }
      int i = 0;
      while (i < product.length) {
         if(product[i] != null){
        	 if (productname.equals(product[i].getData().substring(0, productname.length()))) {
        		 rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
        				product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
        	 }
        	 i++;
         }
      }
   }
   
   public void ShelflifeSort(String productname, TPResultTable rTable) {
	   sort = new InsertionSort();
		  
	   sort.setArray(getProduct(productname.substring(0, 3)), productname);
	   rTable.tableSetEmpty();
	   sort.ShelflifeSort();
	   Product product[] = sort.getProduct().toArray(new Product[sort.getProduct().size()]);
	   if (product == null) {
		   return;
	   }
	   int i = 0;
	   while (i < product.length) {
		   if(product[i] != null){
			   if (productname.equals(product[i].getData().substring(0, productname.length()))) {
				   rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
						   product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
			   }
			   i++;
		   }else{
			   return;
		   }
	   }
   	}
   public Product[] distanceSort(String productname, TPResultTable rTable, String userAddress) {
	      sort = new InsertionSort();
	      sort.setArray(getProduct(productname.substring(0, 3)), productname);
	      rTable.tableSetEmpty();
	      sort.distanceSort(userAddress);
	      Product product[] = sort.getProduct().toArray(new Product[sort.getProduct().size()]);
	      if (product == null) {
	         return null;
	      }
	      int i = 0;
	      while (i < product.length) {
			   if(product[i] != null){
				   if (productname.equals(product[i].getData().substring(0, productname.length()))) {
					   rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
							   product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
				   }
				   i++;
			   }else{
				   return null;
			   }
		   }
	      return product;
	   }

   public int getStock() {	return stock;	}
   public int getShelfLife() {	return shelfLife;	}
   public void setEData(EventData eData) {
	   this.EData = eData;
   }
}