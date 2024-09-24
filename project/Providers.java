package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Providers {

	static int totalProducts;
	final static String separator = ";";
	
	public static void createProductsFile( int productsCount  ) throws IOException{
		totalProducts = productsCount;
		
		File productCsvFile = new File("productsInfoFile.csv");
		FileWriter fileWriter = new FileWriter(productCsvFile);
		
		fileWriter.write("IDProducto;NombreProducto;PrecioPorUnidad\n");
		
		
		for (int i = 0; i < productsCount; i++) {
			StringBuilder productInfo = new StringBuilder();
			
			productInfo.append(Helpers.getProductsId().get(i));
			productInfo.append(separator);
			productInfo.append(Helpers.getProductsName().get(i));
			productInfo.append(separator);
			productInfo.append(getRandomNumber(1000000, 3500000));
			productInfo.append("\n");
			
			fileWriter.write(productInfo.toString());
		}
		
		fileWriter.close();
		
	}
	
	public static void createSalesManInfoFile( int salesmanCount ) throws IOException{
		List<String> names = Arrays.asList("Alejandro", "Andrea", "Carlos", "Carmen", "Daniel", "Daniela", "Eduardo",
				"Elena", "Fernando", "Isabel", "Javier", "Jimena", "José", "Julia", "Luis", "Lucía", "Manuel", "María",
				"Miguel", "Marta", "Óscar", "Olivia", "Pablo", "Paula", "Rafael", "Sara", "Sergio", "Sofía", "Víctor",
				"Valeria");
		
		List<String> lastNames = Arrays.asList("García", "González", "Rodríguez", "Fernández", "López", "Martínez",
				"Sánchez", "Pérez", "Gómez", "Martín", "Jiménez", "Ruiz", "Hernández", "Díaz", "Moreno", "Muñoz",
				"Álvarez", "Romero", "Alonso", "Gutiérrez", "Navarro", "Torres", "Domínguez", "Vázquez", "Ramos", "Gil",
				"Ramírez", "Serrano", "Blanco", "Molina");
		
		List<String> docTypes = Arrays.asList("CC", "CE");
		
		File salesManCsvFile = new File("salesManInfoFile.csv");
		FileWriter fileWriter = new FileWriter(salesManCsvFile);
		
		fileWriter.write("TipoDocumento;NumeroDocumento;Nombres;Apellidos\n");
		
		for (int i = 0; i < salesmanCount; i++) {
			StringBuilder salesManInfo = new StringBuilder();

			salesManInfo.append(docTypes.get(getRandomNumber(0, 1)));
			salesManInfo.append(separator);
			salesManInfo.append(Long.toString(118324456 + i));
			salesManInfo.append(separator);
			salesManInfo.append(names.get(getRandomNumber(0, 29)));
			salesManInfo.append(separator);
			salesManInfo.append(lastNames.get(getRandomNumber(0, 29)) + " " + lastNames.get(getRandomNumber(0, 29)));
			salesManInfo.append("\n");

			fileWriter.write(salesManInfo.toString());
		}
		fileWriter.close();
	}
	
	public static void createSalesMenFile( int randomSalesCount, String name, long id, String docType ) throws IOException {
		
		File sellerCsvFile = new File(name + "-" + id + ".csv");
		FileWriter fileWriter = new FileWriter(sellerCsvFile);
		
		fileWriter.write(docType + ";" + id + "\n");
		
		for (int i = 0; i < randomSalesCount; i++) {
			StringBuilder sellerInfo = new StringBuilder();

			sellerInfo.append(Helpers.getProductsId().get(getRandomNumber(0, totalProducts - 1)));
			sellerInfo.append(separator);
			sellerInfo.append(getRandomNumber(1, 30));
			sellerInfo.append(separator);
			sellerInfo.append("\n");

			fileWriter.write(sellerInfo.toString());
		}

		fileWriter.close();
		
	}
	
	
	private static int getRandomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
}



