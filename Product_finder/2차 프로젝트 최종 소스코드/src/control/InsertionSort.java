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
	// 연결리스트를 벡터로 바꿔 저장.
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
	// 가격이 낮은 상품부터 오름차순으로 정렬.
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

	// 재고가 적은 상품부터 오름차순으로 정렬.
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
		String latitude = ""; // 위도
		String longitude = ""; // 경도
		String location[] = new String[2];
		String apiKey = "AIzaSyDKaKzfCBN51istGLDKqAZENbEn9AUHRFg";// AIzaSyBImu02E7b_WdhaEDnlKqAfDo-v4dcrhAM
		// UTF-8로 주소 인코딩
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			// http://maps.googleapis.com/maps/api/geocode/xml?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=true
			// 구글 주소로 위경도 얻는 url(만약 프리미엄 API(유료고객)를 사용해야 한다면 아래 주소를 API에 맞게 변환해야
			// 한다.)
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
												// 위도값
												latitude = nodeList2.getTextContent();
											} else if ("lng".equals(nodeList2.getNodeName())) {
												// 경도값
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
				System.out.println("해당 주소의 좌표를 잃어오는 것에 실패함 에러코드" + nodeMomList.item(1).getTextContent());
				/////////////////////////////////////////////////////////////////////////////
				// 상태코드에 따른 실패처리를 개별적으로 해야함
				// -"OK"는 오류가 발생하지 않았음
				// -"ZERO_RESULTS"는 지오코딩이 성공했지만 반환된 결과가 없음
				// -"OVER_QUERY_LIMIT"는 할당량을 초과
				// -"REQUEST_DENIED"는 요청이 거부
				// -"INVALID_REQUEST"는 일반적으로 쿼리(address 또는 latlng)가 누락
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
	// 가격 출력. 재고는 포함되지 않음.
	public void PricePrint() {
		System.out.println("상품 :" + product.elementAt(0).getData() + "종류 :" + product.elementAt(0).getType() + "개수 :" + size);
		for (int i = 0; i < size; i++) {
		//	System.out.print(product.[i].toString() + " ");
		}
	}

	// 재고 포함 출력.
	public void StockPrint() {
		System.out.println("상품 :" + product.elementAt(0).getData() + "종류 :" + product.elementAt(0).getType() + "개수 :" + size);
		for (int i = 0; i < size; i++) {
			System.out.println("재고 :" + product.elementAt(i).getStock() + product.elementAt(i).toString() + " ");
		}
	}
	
}