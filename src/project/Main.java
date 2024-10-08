package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	public static void main(String[] args) throws IOException {
		// Validamos que el programa se ejecute de manera correcta, en caso de que no, controlamos el error con una alerta
		try {
			createReports();
			System.out.println("La generación de reportes ha sido exitosa");
		} catch (IOException e) {
			System.err.println("Ocurrió un error mientras se generaban los reportes: " + e.getMessage());
		}

	}
	
	
	public static void createReports()  throws IOException{
		List<List<String>> productsInfo = GenerateInfoFiles.readCsv("productsInfoFile");
		
		
		// Generamos el archivo CSV para el reporte de empleados
		File salesManCsvFile = new File("salesManReport.csv");
		FileWriter fileWriter = new FileWriter(salesManCsvFile);
		
		List<List<String>> totalSalesMen = GenerateInfoFiles.readCsv("salesManInfoFile");
		
		fileWriter.write("TipoDocumento;NumeroDocumento;NombreVendedor;ApellidosVendedor;CantidadDineroGenerado\n");
		
		HashMap<String, Integer> allProductsQty = new HashMap<>();
		HashMap<String, Long> orderedSalesMen = new HashMap<>();
		
		//Obtenemos la cantidad vendida total de un producto revisando su venta por cada vendedor
		for(Integer i = 0; i < GenerateInfoFiles.totalSalesMen; i++) {
			List<List<String>> perfil = GenerateInfoFiles.readCsv(totalSalesMen.get(i+1).get(2) + "-" + totalSalesMen.get(i + 1).get(1));
		
			HashMap<String, Integer> data = new HashMap<>();
		
			
			for(Integer j = 1; j < perfil.size(); j++) {
				
				String productId = perfil.get(j).get(0);
				String quantity = perfil.get(j).get(1);
				
				if (data.get(productId) == null) {
					data.put(productId, Integer.parseInt(quantity));
				} else {
					int oldQty = data.get(productId);
					data.put(productId, Integer.parseInt(quantity) + oldQty);
				}
					
			}
			
			ArrayList<String> soldProductsId = new ArrayList<>(data.keySet());
			long totalSold = 0;
			for(Integer j = 0; j < soldProductsId.size(); j++) {
				String productId = soldProductsId.get(j);
				for(Integer k = 0; k < productsInfo.size(); k++) {
					if (productsInfo.get(k).get(0).equals(productId)) {
						List<String> productInfo = productsInfo.get(k);
						Long productPrice = Long.parseLong(productInfo.get(2));
						
						
						totalSold += data.get(productId) * productPrice;
						
						
						//Suma las cantidades vendidas del producto
						if (allProductsQty.get(productId) == null) {
							allProductsQty.put(productId, data.get(productId));
						} else {
							int oldQty = allProductsQty.get(productId);
							allProductsQty.put(productId, data.get(productId) + oldQty);
						}
					}
				}
				
				
			}
			
			
			orderedSalesMen.put(perfil.get(0).get(1), totalSold);
			
			totalSold = 0;
			
		}

		List<Map.Entry<String, Long>> listSalesMen = new ArrayList<>(orderedSalesMen.entrySet());
		

		 
        // Ordenar usando Comparator
        Collections.sort(listSalesMen, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue()); // Orden descendente
            }
        });

        // Crear un LinkedHashMap para mantener el orden
        LinkedHashMap<String, Long> mapOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : listSalesMen) {
        	mapOrdenado.put(entry.getKey(), entry.getValue());
        }
        
        List<Map.Entry<String, Long>> orderedTotalSold = new ArrayList<>(mapOrdenado.entrySet());

     // Recorremos el array con los empleados ordenados (por cédula) y el array normal, comparamos en el segundo for para insertarlo en el CSV
		for(Integer i = 0; i < GenerateInfoFiles.totalSalesMen; i++) {			
			StringBuilder line = new StringBuilder();
			for(Integer j = 0; j < GenerateInfoFiles.totalSalesMen; j++) {
				if(totalSalesMen.get(j + 1).get(1).toString().equals(orderedTotalSold.get(i).toString().substring(0,9))) {
					List<List<String>> perfil = GenerateInfoFiles.readCsv(totalSalesMen.get(j+1).get(2) + "-" + totalSalesMen.get(j + 1).get(1));
					
					line.append(perfil.get(0).get(0));
					line.append(GenerateInfoFiles.SEPARATOR);
					line.append(perfil.get(0).get(1));
					line.append(GenerateInfoFiles.SEPARATOR);
					line.append(totalSalesMen.get(i+1).get(2));
					line.append(GenerateInfoFiles.SEPARATOR);
					line.append(totalSalesMen.get(i+1).get(3));
					line.append(GenerateInfoFiles.SEPARATOR);
					line.append(orderedTotalSold.get(i).toString().substring(9));
					
					line.append("\n");
					
					fileWriter.write(line.toString());
				}
			}
		}
	
		fileWriter.close();
		// Cerramos la creación del archivo de reportes de empleados

		
		// Generamos el archivo CSV para el reporte de productos
		File productsCsvFile = new File("productReport.csv");
		FileWriter prodcutsFileWrite = new FileWriter(productsCsvFile);
		
		
		prodcutsFileWrite.write("IdProducto;NombreProducto;ValorUnitario;CantidadVendida\n");
		
		HashMap<String, String> productsName = new HashMap<>();
		HashMap<String, String> productsUnitPrice= new HashMap<>();
		
	
		for(Integer i = 0; i < productsInfo.size() - 1; i++) {
			productsName.put(productsInfo.get(i+1).get(0), productsInfo.get(i+1).get(1));
		}
		
		for(Integer i = 0; i < productsInfo.size() - 1; i++) {
			productsUnitPrice.put(productsInfo.get(i+1).get(0), productsInfo.get(i+1).get(2));
		}
		
		List<Map.Entry<String, Integer>> listaEntradas = new ArrayList<>(allProductsQty.entrySet());

		 
        // Ordenar usando Comparator
        Collections.sort(listaEntradas, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue()); // Orden descendente
            }
        });

        // Crear un LinkedHashMap para mantener el orden
        LinkedHashMap<String, Integer> mapaOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listaEntradas) {
            mapaOrdenado.put(entry.getKey(), entry.getValue());
        }
        
        List<Map.Entry<String, Integer>> orderedQuantity = new ArrayList<>(mapaOrdenado.entrySet());
   
		
		// Recorremos el array con los productos ordenados para insertarlos en el CSV
		for(Integer i = 0; i < mapaOrdenado.size(); i++) {
			int quantity = Integer.parseInt(orderedQuantity.get(i).toString().substring(6));
			StringBuilder infoProduct = new StringBuilder();
			
			
			infoProduct.append(orderedQuantity.get(i).toString().substring(0,5));
			infoProduct.append(GenerateInfoFiles.SEPARATOR);
			infoProduct.append(productsName.get(orderedQuantity.get(i).toString().substring(0,5)));
			infoProduct.append(GenerateInfoFiles.SEPARATOR);
			infoProduct.append(productsUnitPrice.get(orderedQuantity.get(i).toString().substring(0,5)));
			infoProduct.append(GenerateInfoFiles.SEPARATOR);
			infoProduct.append(quantity);
			infoProduct.append("\n");
			
			prodcutsFileWrite.write(infoProduct.toString());
		}
		prodcutsFileWrite.close();
		// Cerramos la creación del archivo de reportes de productos
	}

}
