package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateInfoFiles {

	
	static int totalProducts;
	final static String SEPARATOR = ";";
	static int totalSalesMen = 15;
	static int totalSalesRecords = 10;
	
	public static void main(String[] args) {
		// Validamos que el programa se ejecute de manera correcta, en caso de que no, controlamos el error con una alerta
		try {
			createFiles();
			System.out.println("La generación de archivos aleatorios ha sido exitosa");
		} catch (IOException e) {
			System.err.println("Ocurrió un error mientras se generaban los archivos aleatorios: " + e.getMessage());
		}
		
	}
	
	// Método para leer los archivos CSV creados
	static List<List<String>> readCsv(String fileName) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<>();
		new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(SEPARATOR);
				records.add(Arrays.asList(values));
			}
		}

		return records;
	}
	
	// pasamos la lógica de creación a varios métodos y los llamamos dentro de un método "controlador"
	public static void createFiles()  throws IOException{
		createProductsFile(10);
		
		
		
		createSalesManInfoFile(15);
		
		// Leer elementos en salesManInfoFile
		List<List<String>> records = readCsv("salesManInfoFile");

		// Generar archivos de ventas por vendedor
		for (Integer i = 0; i < totalSalesMen; i++) {
			List<String> sellerInfo = records.get(i + 1);

			createSalesMenFile(10, sellerInfo.get(2), Long.parseLong(sellerInfo.get(1)), sellerInfo.get(0));
		}
	}
	
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
	
	public static void createProductsFile( int productsCount  ) throws IOException{
		totalProducts = productsCount;
		
		File productCsvFile = new File("productsInfoFile.txt");
		FileWriter fileWriter = new FileWriter(productCsvFile);
		
		fileWriter.write("IDProducto;NombreProducto;PrecioPorUnidad\n");
		
		
		for (int i = 0; i < productsCount; i++) {
			StringBuilder productInfo = new StringBuilder();
			
			productInfo.append(getProductsId().get(i));
			productInfo.append(SEPARATOR);
			productInfo.append(getProductsName().get(i));
			productInfo.append(SEPARATOR);
			productInfo.append(getRandomNumber(1000000, 3500000));
			productInfo.append("\n");
			
			fileWriter.write(productInfo.toString());
		}
		
		fileWriter.close();
		
	}
	
	public static void createSalesManInfoFile( int salesmanCount )  throws IOException{
		List<String> names = Arrays.asList("Alejandro", "Andrea", "Carlos", "Carmen", "Daniel", "Daniela", "Eduardo",
				"Elena", "Fernando", "Isabel", "Javier", "Jimena", "José", "Julia", "Luis", "Lucía", "Manuel", "María",
				"Miguel", "Marta", "Óscar", "Olivia", "Pablo", "Paula", "Rafael", "Sara", "Sergio", "Sofía", "Víctor",
				"Valeria");
		
		List<String> lastNames = Arrays.asList("García", "González", "Rodríguez", "Fernández", "López", "Martínez",
				"Sánchez", "Pérez", "Gómez", "Martín", "Jiménez", "Ruiz", "Hernández", "Díaz", "Moreno", "Muñoz",
				"Álvarez", "Romero", "Alonso", "Gutiérrez", "Navarro", "Torres", "Domínguez", "Vázquez", "Ramos", "Gil",
				"Ramírez", "Serrano", "Blanco", "Molina");
		
		List<String> docTypes = Arrays.asList("CC", "CE");
		
		File salesManCsvFile = new File("salesManInfoFile.txt");
		FileWriter fileWriter = new FileWriter(salesManCsvFile);
		
		fileWriter.write("TipoDocumento;NumeroDocumento;Nombres;Apellidos\n");
		
		for (int i = 0; i < salesmanCount; i++) {
			StringBuilder salesManInfo = new StringBuilder();

			salesManInfo.append(docTypes.get(getRandomNumber(0, 1)));
			salesManInfo.append(SEPARATOR);
			salesManInfo.append(Long.toString(118324456 + i));
			salesManInfo.append(SEPARATOR);
			salesManInfo.append(names.get(getRandomNumber(0, 29)));
			salesManInfo.append(SEPARATOR);
			salesManInfo.append(lastNames.get(getRandomNumber(0, 29)) + " " + lastNames.get(getRandomNumber(0, 29)));
			salesManInfo.append("\n");

			fileWriter.write(salesManInfo.toString());
		}
		fileWriter.close();
	}
	
	public static void createSalesMenFile( int randomSalesCount, String name, long id, String docType ) throws IOException {
		
		File sellerCsvFile = new File(name + "-" + id + ".txt");
		FileWriter fileWriter = new FileWriter(sellerCsvFile);
		
		fileWriter.write(docType + ";" + id + "\n");
		
		for (int i = 0; i < randomSalesCount; i++) {
			StringBuilder sellerInfo = new StringBuilder();

			sellerInfo.append(getProductsId().get(getRandomNumber(0, totalProducts - 1)));
			sellerInfo.append(SEPARATOR);
			sellerInfo.append(getRandomNumber(1, 30));
			sellerInfo.append(SEPARATOR);
			sellerInfo.append("\n");

			fileWriter.write(sellerInfo.toString());
		}

		fileWriter.close();
		
	}
	
	// método auxiliar para generar números de manera aleatoria
	private static int getRandomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	
}
