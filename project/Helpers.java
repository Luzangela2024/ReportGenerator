package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helpers {
	
	static String DELIMITER = ";";
	static int totalProducts = 10;
	static int totalSalesMen = 15;
	static int totalSalesRecords = 10;
	
	
	static List<String> getProductsName(){
		
		 List<String> productsName = Arrays.asList("Iphone 14", "Iphone 15 pro max", "Samsung Galaxy S22 Ultra", "Xiaomi 12",
				"Oppo Reno 7 5g", "Vivo V25 Pro", "Xiaomi Redmi Note 12", "Honor Magic 5 Lite 5G", "Samsung Galaxy S20",
				"Moto g4");
		 
		 return productsName;
	}
	
	static List<String> getProductsId(){
		
		 List<String> productsId = Arrays.asList("AXE23", "AXE12", "BWR09", "CYG21","TTI12", 
				 "QWC27", "CYG19", "PUY07", "BWR13","DEF13");
		 
		 return productsId;
	}
	
	
	static List<List<String>> readCsv(String fileName) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<>();
		new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(DELIMITER);
				records.add(Arrays.asList(values));
			}
		}

		return records;
	}
}
