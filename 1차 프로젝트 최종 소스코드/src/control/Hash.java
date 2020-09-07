package control;

import frames.TPResultTable;
import model.Product;

public class Hash {
	// �����͸� ������ Product�� ���� ���� Product�� ������.
	private int size;
	private Product[] hTable;

	// Hash Table�� size�� �迭 ���̺��� �����Ѵ�.
	public Hash(int size) {
		this.size = size;
		this.hTable = new Product[size];
	}

	// key ������ HashTable�� ����� index�� ��ȯ�Ѵ�.
	public int hashMethod(int key) {
		return key % size;
	}

	// ������ �����ͷ� key�� �����Ѵ�.
	// ���� Hash ���� Ư���� �˰����� ����� hashCode�� ��´�.
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
		while (i < data.length()) {
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

	// data�� HashTable�� �����Ѵ�.
	public int add(String data, int price, String type, String shop) {
		// data�� hashCode�� key�� ������ index�� ��´�.
		int key = getKey(data);
		int hashValue = hashMethod(key);
		// ������ Product ����
		Product product = new Product();
		product.setData(data);
		product.setPrice(price);
		product.setType(type);
		product.setShop(shop);
		// HashTable�� ������ index�� ����ִٸ�, �����ϰ� ������.
		if (hTable[hashValue] == null) {
			hTable[hashValue] = product;
			return 0;
		}
		Product pre = null;
		Product cur = hTable[hashValue];
		// �ش� index�� ���Ḯ��Ʈ�� ���� ũ�Ⱑ ���� ������ ū ������ ���ĵǾ��ִ�.
		while (true) {
			if (cur == null) { // Hash Table�� ������ index�� ���������
				hTable[hashValue] = product; // �ش� index�� ����
				return 0;
			} else if (getKey(cur.getData()) < key) { // �ش� index�� Ŀ�� �����
				// ���� key���� ������ Ŀ���� �ϳ� �ڷ� �ű��.
				pre = cur;
				cur = cur.next;
			} else { // �ش� index�� Ŀ�� ����� ���� key ���� ũ��.(���⿡ ����)
				// Ŀ�� ��尡 HashTable�� ù��° ����̸�
				if (cur == hTable[hashValue]) {
					// ���� ��带 ù��° ���� �����ϰ� ���� ��带 Ŀ������ �����Ѵ�.
					product.next = cur;
					hTable[hashValue] = product;
					return 0;
				} else { // ù��° ��尡 �ƴϸ�
					// ���� ����� ���� ���� Ŀ����带 �����ϰ�
					// �� ����� ���� ��带 ���Գ��� �����Ѵ�.
					product.next = cur;
					pre.next = product;
					return 0;
				}
			}
		}
	}

	// data�� HashTable�� �����Ѵ�. ��� add��.
	public int add(String data, int price, String type, String shop, int n) {
		// data�� hashCode�� key�� ������ index�� ��´�.
		int key = getKey(data);
		int hashValue = hashMethod(key);
		// ������ Product ����
		Product product = new Product();
		product.setData(data);
		product.setPrice(price);
		product.setType(type);
		product.setShop(shop);
		product.setStock(n);
		// HashTable�� ������ index�� ����ִٸ�, �����ϰ� ������.
		if (hTable[hashValue] == null) {
			hTable[hashValue] = product;
			return 0;
		}
		Product pre = null;
		Product cur = hTable[hashValue];
		// �ش� index�� ���Ḯ��Ʈ�� ���� ũ�Ⱑ ���� ������ ū ������ ���ĵǾ��ִ�.
		while (true) {
			if (cur == null) { // Hash Table�� ������ index�� ���������
				hTable[hashValue] = product; // �ش� index�� ����
				return 0;
			} else if (getKey(cur.getData()) < key) { // �ش� index�� Ŀ�� �����
				// ���� key���� ������ Ŀ���� �ϳ� �ڷ� �ű��.
				pre = cur;
				cur = cur.next;
			} else { // �ش� index�� Ŀ�� ����� ���� key ���� ũ��.(���⿡ ����)
				// Ŀ�� ��尡 HashTable�� ù��° ����̸�
				if (cur == hTable[hashValue]) {
					// ���� ��带 ù��° ���� �����ϰ� ���� ��带 Ŀ������ �����Ѵ�.
					product.next = cur;
					hTable[hashValue] = product;
					return 0;
				} else { // ù��° ��尡 �ƴϸ�
					// ���� ����� ���� ���� Ŀ����带 �����ϰ�
					// �� ����� ���� ��带 ���Գ��� �����Ѵ�.
					product.next = cur;
					pre.next = product;
					return 0;
				}
			}
		}
	}

	public int add(String data, int price, String type, String shop, int n, int date) {
		// data�� hashCode�� key�� ������ index�� ��´�.
		int key = getKey(data);
		int hashValue = hashMethod(key);
		// ������ Product ����
		Product product = new Product();
		product.setData(data);
		product.setPrice(price);
		product.setType(type);
		product.setShop(shop);
		product.setStock(n);
		product.setShelfLife(date);
		// HashTable�� ������ index�� ����ִٸ�, �����ϰ� ������.
		if (hTable[hashValue] == null) {
			hTable[hashValue] = product;
			return 0;
		}
		Product pre = null;
		Product cur = hTable[hashValue];
		// �ش� index�� ���Ḯ��Ʈ�� ���� ũ�Ⱑ ���� ������ ū ������ ���ĵǾ��ִ�.
		while (true) {
			if (cur == null) { // Hash Table�� ������ index�� ���������
				hTable[hashValue] = product; // �ش� index�� ����
				return 0;
			} else if (getKey(cur.getData()) < key) { // �ش� index�� Ŀ�� �����
				// ���� key���� ������ Ŀ���� �ϳ� �ڷ� �ű��.
				pre = cur;
				cur = cur.next;
			} else { // �ش� index�� Ŀ�� ����� ���� key ���� ũ��.(���⿡ ����)
				// Ŀ�� ��尡 HashTable�� ù��° ����̸�
				if (cur == hTable[hashValue]) {
					// ���� ��带 ù��° ���� �����ϰ� ���� ��带 Ŀ������ �����Ѵ�.
					product.next = cur;
					hTable[hashValue] = product;
					return 0;
				} else { // ù��° ��尡 �ƴϸ�
					// ���� ����� ���� ���� Ŀ����带 �����ϰ�
					// �� ����� ���� ��带 ���Գ��� �����Ѵ�.
					product.next = cur;
					pre.next = product;
					return 0;
				}
			}
		}
	}

	// data�� �ִ� ��ġ�� ��´�.
	public int get(String data) {
		int key = getKey(data);
		int hashValue = hashMethod(key);
		// data�� �ִ� index�� ù��° ��带 ��´�.
		Product cur = hTable[hashValue];
		while (true) {
			if (cur == null) { // index �� ����ִٸ� -1 ��ȯ
				return -1;
			} else if (getKey(cur.getData()) == key) { // Ŀ���� ���� Ű�� ������ 0 ��ȯ
				return hashValue;
			} else if (getKey(cur.getData()) > key) { // Ŀ���� ���� Ű���� ũ�� -1 ��ȯ
				// ����Ʈ�� ���� ������ ū ������ ���ĵǾ��ִ�.
				return -1;
			} else { // Ŀ���� ���� Ű���� ������ ���� ���� Ŀ�� �̵�
				cur = cur.next;
			}
		}
	}

	// data�� �ִ� ��带 ��ȯ�Ѵ�.
	public Product getProduct(String data) {
		int key = getKey(data);
		int hashValue = hashMethod(key);
		// HashTable�� index�� ù��° ���� �ι�° ���
		Product cur = hTable[hashValue];
		Product pre = cur.next;
		while (true) {
			if (cur == null) { // Ŀ�� ��尡 null �̸� null ��ȯ
				return null;
			} else if (pre == null) {
				return cur;
			} else if (getKey(cur.getData()) == key) { // Ŀ�� ����� ���� Ű�� ������ �� ��带
														// ��ȯ
				return cur;
			} else if (getKey(cur.getData()) > key) { // Ŀ���� ���� Ű���� ũ�� null ��ȯ
				System.out.println("data>key");
				return cur;
			} else { // Ŀ���� ���� Ű���� ������ Ŀ���� �������� �̵�
				pre = cur;
				cur = cur.next;
			}
		}
	}

	// data �� ����
	public int remove(String data) {
		Product pre = null;
		// data�� �ִ� ����� ����带 �������� null�̸� -1 ��ȯ
		if ((pre = getProduct(data)) == null) {
			return -1;
		}
		// �� ��尡 data�� ������带 ����Ű�� �Ѵ�.
		// data ��带 �ǳʶٰ� �����Ѵ� (����)
		Product cur = pre.next;
		pre.next = cur.next;
		return 0;
	}

//	public int getdata(String data) {
//		try {
//			System.out.println("�˻� ���");
//			System.out.println("��ǰ�� :" + getProduct(data).getData());
//			System.out.println("���� :" + getProduct(data).getPrice());
//			System.out.println("���� :" + getProduct(data).getType());
//			System.out.println("�Ǹ�ó :" + getProduct(data).getShop());
//		} catch (NullPointerException e) {
//			System.out.println("ã�� ǰ���� �����ϴ�");
//			return -1;
//		}
//		return 0;
//	}

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
					result.append("[��ǰ��: " + cur.getData() + "����: " + cur.getPrice() + "����: " + cur.getType() + "] ");
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
			if (productname.equals(product.getData())) {
				rTable.tableUpdate(product.getData(), product.getPrice(), product.getType(), product.getShop(),
						product.getStock(), product.getShelfLife());
				product = product.next;
			} else {
				product = product.next;
			}
		}
	}

	public void PriceSort(String productname, TPResultTable rTable) {
		InsertionSort sort = new InsertionSort();
		sort.setArray(getProduct(productname), productname);
		rTable.tableSetEmpty();
		sort.PriceSort();
		Product product[] = sort.getProduct();
		if (product == null) {
			return;
		}
		int i = 0;
		while (i < product.length) {
			if(product[i] != null){
			if (productname.equals(product[i].getData())) {
				rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
						product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
			}
			i++;
			}else{
				return;
			}
		}
	}

	public void StockSort(String productname, TPResultTable rTable) {
		InsertionSort sort = new InsertionSort();
		sort.setArray(getProduct(productname), productname);
		rTable.tableSetEmpty();
		sort.StockSort();
		Product product[] = sort.getProduct();
		if (product == null) {
			return;
		}
		int i = 0;
		while (i < product.length) {
			if(product[i] != null){
			if (productname.equals(product[i].getData())) {
				rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
						product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
			}
			i++;
			}else{
				return;
			}
		}
	}
	
	public void ShelflifeSort(String productname, TPResultTable rTable) {
		InsertionSort sort = new InsertionSort();
		sort.setArray(getProduct(productname), productname);
		rTable.tableSetEmpty();
		sort.ShelflifeSort();
		Product product[] = sort.getProduct();
		if (product == null) {
			return;
		}
		int i = 0;
		while (i < product.length) {
			if(product[i] != null){
			if (productname.equals(product[i].getData())) {
				rTable.tableUpdate(product[i].getData(), product[i].getPrice(), product[i].getType(),
						product[i].getShop(), product[i].getStock(), product[i].getShelfLife());
			}
			i++;
			}else{
				return;
			}
		}
	}
}
