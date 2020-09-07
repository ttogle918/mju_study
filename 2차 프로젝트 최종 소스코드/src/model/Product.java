package model;

public class Product {
		private String data;	//상품명
		private String type;	//종류
		private int price;		//가격
		private String shop;	//파는 가게
		private String shopAddress;	//가게 주소
		private int stock;		// 재고 수
		private int shelflife;
		public Product next;
		
		public Product(){	}
		Product(String data, String type, int price, String shop, Product next){
			this.data = data;	this.type = type;	this.shop = shop;
			this.price = price; 	this.next = next;
		}
		
		public String getShopAddress() {return shopAddress;}
		public void setShopAddress(String shopAddress) {this.shopAddress = shopAddress;}
		public int getStock(){		return stock;	}
		public String getData(){	return data;	}
		public String getType(){	return type;	}
		public int getPrice(){	return price;	}
		public int getShelfLife(){	return shelflife;	}
		public String getShop(){	return shop;	}
		public void setData(String d){	data = d;	}
		public void setType(String t){	type = t;	}
		public void setPrice(int p){	price = p;	}
		public void setShop(String s){	shop = s;	}
		public void setStock(int n ){	stock = n;	}
		public void setShelfLife(int date ){	shelflife = date;	}
		public String toString() {
			try {
					System.out.println("상품명 :"+getData()+" 가격 :"+getPrice()+" 종류 :"+getType()+" 판매처 :"+getShop());
					return "";
			}catch(NullPointerException e){
				System.out.println("찾는 품목이 없습니다");
				return "";
			}
		}
}