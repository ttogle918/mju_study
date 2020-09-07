package control;

import model.Product;

public class QuickSort {
	private Product[] product;
	private int size;
	public QuickSort(Product m_product) {
		this.product = new Product[10];
		if(m_product != null){
			this.product[0] = m_product;		
			size = 1;
		}
	}
	public void Sort(){
		if(product[0] != null){
			getArray();
			quickSort(0, size-1);	
			PrintAll();
		}else{
			System.out.println("해당 제품이 없습니다.");
		}
	}
	public Product[] getArray(){
		if(product[0] == null) return null;
		int i = 1;
		Product p = product[0];
		while(p.next != null){
			product[i] = p.next;
			p = p.next;
			size++;
		}
		return product;
	}
	public int partition(int left, int right) { 
	      int i = left, j = right; 
	      Product t; 
	      int pivot = product[(left + right)/2].getPrice(); 
	      while (i <= j) {
	            while (product[i].getPrice() < pivot)	i++; 
	            while (product[j].getPrice() > pivot)	j--; 
	            if (i <= j) {
	                  t = product[i]; 
	                  product[i] =  product[j]; 
	                  product[j] = t; 
	                  i++; 	j--; 
	            }
	      }
	      return i; 
	}
	public void quickSort(int left, int right) { 
	      int index = partition(left, right); 
	      if (left < index - 1) 
	            quickSort(left, index - 1); 
	      if (index < right) 
	            quickSort(index, right); 
	}
	public void PrintAll(){
		for(int i = 0; i < size; i++){
			product[i].toString();
		}
		System.out.println("");
	}
}