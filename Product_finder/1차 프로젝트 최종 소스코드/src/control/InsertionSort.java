package control;

import model.Product;

public class InsertionSort {
	private Product product[];
	private int size;

	public InsertionSort() {
		product = new Product[10];
		size = 0;
	}
	
	public Product[] getProduct() {
		return product;
	}
	// 연결리스트를 배열로 바꿔 저장.
	public void setArray(Product p, String str) {
		size = 0;
		if (p == null) {
			return;
		}
		while (p != null) {
			if (p.getData().equals(str)) {
				product[size] = p;
				size++;
				p = p.next;
			} else {
				p = p.next;
			}
		}
	}

	// 가격이 낮은 상품부터 오름차순으로 정렬.
	public Product PriceSort() {
		if (size < 2)
			return null;
		Product t;
		for (int j = size - 2; j >= 0; j--) {
			for (int i = size - 1; i > j; i--) {
				if (product[i].getPrice() < product[j].getPrice()) {
					t = product[j];
					product[j] = product[i];
					product[i] = t;
				}
			}
		}
		return product[0];
	}

	// 재고가 적은 상품부터 오름차순으로 정렬.
	public Product StockSort() {
		if (size < 2)
			return null;
		Product t;
		for (int j = size - 2; j >= 0; j--) {
			for (int i = size - 1; i > j; i--) {
				if (product[i].getStock() < product[j].getStock()) {
					t = product[j];
					product[j] = product[i];
					product[i] = t;
				}
			}
		}
		return product[0];
	}
	
	public Product ShelflifeSort() {
		if (size < 2)
			return null;
		Product t;
		for (int j = size - 2; j >= 0; j--) {
			for (int i = size - 1; i > j; i--) {
				if (product[i].getShelfLife() < product[j].getShelfLife()) {
					t = product[j];
					product[j] = product[i];
					product[i] = t;
				}
			}
		}
		return product[0];
	}

	// 가격 출력. 재고는 포함되지 않음.
	public void PricePrint() {
		System.out.println("상품 :" + product[0].getData() + "종류 :" + product[0].getType() + "개수 :" + size);
		for (int i = 0; i < size; i++) {
			System.out.print(product[i].toString() + " ");
		}
	}

	// 재고 포함 출력.
	public void StockPrint() {
		System.out.println("상품 :" + product[0].getData() + "종류 :" + product[0].getType() + "개수 :" + size);
		for (int i = 0; i < size; i++) {
			System.out.println("재고 :" + product[i].getStock() + product[i].toString() + " ");
		}
	}
	
}