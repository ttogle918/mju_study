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
	// ���Ḯ��Ʈ�� �迭�� �ٲ� ����.
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

	// ������ ���� ��ǰ���� ������������ ����.
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

	// ��� ���� ��ǰ���� ������������ ����.
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

	// ���� ���. ���� ���Ե��� ����.
	public void PricePrint() {
		System.out.println("��ǰ :" + product[0].getData() + "���� :" + product[0].getType() + "���� :" + size);
		for (int i = 0; i < size; i++) {
			System.out.print(product[i].toString() + " ");
		}
	}

	// ��� ���� ���.
	public void StockPrint() {
		System.out.println("��ǰ :" + product[0].getData() + "���� :" + product[0].getType() + "���� :" + size);
		for (int i = 0; i < size; i++) {
			System.out.println("��� :" + product[i].getStock() + product[i].toString() + " ");
		}
	}
	
}