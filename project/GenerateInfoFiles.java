package project;

import java.io.IOException;
import java.util.List;

public class GenerateInfoFiles {

	public static void main(String[] args) {
		try {
			createFiles();
			System.out.println("La generación de archivos aleatorios ha sido exitosa");
		} catch (IOException e) {
			System.err.println("Ocurrió un error mientras se generaban los archivos aleatorios: " + e.getMessage());
		}
		
	}
	
	public static void createFiles()  throws IOException{
		Providers.createProductsFile(Helpers.totalProducts);
		Providers.createSalesManInfoFile(Helpers.totalSalesMen);
		
		// Leer elementos en salesManInfoFile
		List<List<String>> records = Helpers.readCsv("salesManInfoFile");

		// Generar archivos de ventas por vendedor
		for (Integer i = 0; i < Helpers.totalSalesMen; i++) {
			List<String> sellerInfo = records.get(i + 1);

			Providers.createSalesMenFile(Helpers.totalSalesMen, sellerInfo.get(2), Long.parseLong(sellerInfo.get(1)), sellerInfo.get(0));
		}
	}
	
}
