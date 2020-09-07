package control;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Product;

public class InsertionSort {
	private Vector<Product> product;
	private int size;

	public InsertionSort() {
		product = new Vector<Product>();
		size = 0;
	}
	
	public Vector<Product> getProduct() {
		return product;
	}
	// ���Ḯ��Ʈ�� ���ͷ� �ٲ� ����.
	public void setArray(Product p, String str) {
		if (p == null) {
			return;
		}
		while (p != null) {
			if(p.getData().length() >= str.length()
					&& p.getData().substring(0, str.length()).equals(str)) {
				product.add(p);
				p = p.next;
			} else {
				p = p.next;
			}
		}
	}
	// ������ ���� ��ǰ���� ������������ ����.
	public Vector<Product> PriceSort() {
		if (product.size() < 2)		return null;
		
		for (int j = product.size() - 2; j >= 0; j--) {
			for (int i = product.size() - 1; i > j; i--) {
				if (product.elementAt(i).getPrice() < product.elementAt(j).getPrice()) {
					Collections.swap(product,i,j);
				}
			}
		}
		return product;
	}

	// ��� ���� ��ǰ���� ������������ ����.
	public Vector<Product> StockSort() {
		if (product.size() < 2)		return null;
		
		for (int j = product.size() - 2; j >= 0; j--) {
			for (int i = product.size() - 1; i > j; i--) {
				if (product.elementAt(i).getStock() < product.elementAt(j).getStock()) {
					Collections.swap(product,i,j);
				}
			}
		}
		return product;
	}
	public Vector<Product> ShelflifeSort() {
		if (product.size() < 2)		return null;
		
		for (int j = product.size() - 2; j >= 0; j--) {
			for (int i = product.size() - 1; i > j; i--) {
				if (product.elementAt(i).getShelfLife() < product.elementAt(j).getShelfLife()) {
					Collections.swap(product,i,j);
				}
			}
		}
		return product;
	}
	public Vector<Product> distanceSort(String userAddress) {
		double d = 1.00;
		String[] userGeocoding = geoCoding(userAddress);
		System.out.println(product.size());
		if (product.size() < 2)
			return null;
		for(int i=0; i < product.size(); i++ ){
			if(Double.compare(distance(userGeocoding, geoCoding(product.elementAt(i).getShopAddress())), d) > 0)
				product.remove(i);
		}
		return product;
	}
	
	public String[] geoCoding(String address) {
		String latitude = ""; // ����
		String longitude = ""; // �浵
		String location[] = new String[2];
		String apiKey = "AIzaSyDKaKzfCBN51istGLDKqAZENbEn9AUHRFg";// AIzaSyBImu02E7b_WdhaEDnlKqAfDo-v4dcrhAM
		// UTF-8�� �ּ� ���ڵ�
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			// http://maps.googleapis.com/maps/api/geocode/xml?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=true
			// ���� �ּҷ� ���浵 ��� url(���� �����̾� API(�����)�� ����ؾ� �Ѵٸ� �Ʒ� �ּҸ� API�� �°� ��ȯ�ؾ�
			// �Ѵ�.)
			// String googleUrl =
			// "http://maps.google.com/maps/api/geocode/xml?address="
			// + address + "&client="+apiKey+"&sensor=false&language=ko";
			String googleUrl = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + address + "&sensor=true";
			//System.out.println(googleUrl);

			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(googleUrl);
			doc.setDocumentURI(googleUrl);
			NodeList nodeMomList = doc.getDocumentElement().getChildNodes();
			if ("OK".equals(nodeMomList.item(1).getTextContent())) {
				for (int i = 0; i < nodeMomList.getLength(); i++) {
					Node row = nodeMomList.item(i);
					NodeList childList = row.getChildNodes();
					if ("result".equals(row.getNodeName())) {
						for (int a = 0; a < childList.getLength(); a++) {
							Node nodeList = childList.item(a);
							NodeList child1 = nodeList.getChildNodes();
							if ("geometry".equals(nodeList.getNodeName())) {
								for (int b = 0; b < child1.getLength(); b++) {
									Node nodeList1 = child1.item(b);
									NodeList child2 = nodeList1.getChildNodes();
									if ("location".equals(nodeList1.getNodeName())) {
										for (int c = 0; c < child2.getLength(); c++) {
											Node nodeList2 = child2.item(c);
											// NodeList child3 =
											// nodeList2.getChildNodes();
											if ("lat".equals(nodeList2.getNodeName())) {
												// ������
												latitude = nodeList2.getTextContent();
											} else if ("lng".equals(nodeList2.getNodeName())) {
												// �浵��
												longitude = nodeList2.getTextContent();
											}
										}
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			} else {
				System.out.println("�ش� �ּ��� ��ǥ�� �Ҿ���� �Ϳ� ������ �����ڵ�" + nodeMomList.item(1).getTextContent());
				/////////////////////////////////////////////////////////////////////////////
				// �����ڵ忡 ���� ����ó���� ���������� �ؾ���
				// -"OK"�� ������ �߻����� �ʾ���
				// -"ZERO_RESULTS"�� �����ڵ��� ���������� ��ȯ�� ����� ����
				// -"OVER_QUERY_LIMIT"�� �Ҵ緮�� �ʰ�
				// -"REQUEST_DENIED"�� ��û�� �ź�
				// -"INVALID_REQUEST"�� �Ϲ������� ����(address �Ǵ� latlng)�� ����
				/////////////////////////////////////////////////////////////////////////////
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		location[0]=latitude;
		location[1]=longitude;
		return location;
	}
	    private static double distance(String[] userAddres, String[] shopAddress) {
	    	double lat1 = Double.parseDouble(userAddres[0]);
	    	double lat2 = Double.parseDouble(shopAddress[0]);
	    	String unit = "kilometer";
	        double theta = Double.parseDouble(shopAddress[1])- Double.parseDouble(userAddres[1]);
	        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	         
	        dist = Math.acos(dist);
	        dist = rad2deg(dist);
	        dist = dist * 60 * 1.1515;
	         
	        if (unit.equals("kilometer")) {
	            dist = dist * 1.609344;
	        } else if(unit == "meter"){
	            dist = dist * 1609.344;
	        } 
	 
	        return (dist);
	    }
	    // This function converts decimal degrees to radians
	    private static double deg2rad(double deg) {
	        return (deg * Math.PI / 180.0);
	    }     
	    // This function converts radians to decimal degrees
	    private static double rad2deg(double rad) {
	        return (rad * 180 / Math.PI);
	    }
	// ���� ���. ���� ���Ե��� ����.
	public void PricePrint() {
		System.out.println("��ǰ :" + product.elementAt(0).getData() + "���� :" + product.elementAt(0).getType() + "���� :" + size);
		for (int i = 0; i < size; i++) {
		//	System.out.print(product.[i].toString() + " ");
		}
	}

	// ��� ���� ���.
	public void StockPrint() {
		System.out.println("��ǰ :" + product.elementAt(0).getData() + "���� :" + product.elementAt(0).getType() + "���� :" + size);
		for (int i = 0; i < size; i++) {
			System.out.println("��� :" + product.elementAt(i).getStock() + product.elementAt(i).toString() + " ");
		}
	}
	
}