package book.data.convert;

import java.util.Properties;

import book.data.convert.service.BookDataConvertService;
import book.data.convert.service.impl.BookDataConvertServiceImpl;
import book.data.convert.util.BookDataProperties;

public class BookDataConvert {

	private static BookDataConvertService service = new BookDataConvertServiceImpl();
	
	public static void main(String[] args) {
		//String fileName = args[0];
		String fileName = "resource/jsonInput.txt";
		System.out.println("Reading input ...");
		System.out.println("++++");
		String readFile = service.readDataFromFile(fileName);
		if(readFile != null){
			String detectFormat = service.detectDataFormat(readFile);
			if(detectFormat.equals("TXT"))
				System.out.println(service.readFileOutputFormat(readFile));
			else{
				System.out.println(readFile);
			}
			System.out.println("----");
			System.out.println("Guessing text format ...");
			if(detectFormat.equals("ERROR")){
				System.out.println("Input data format detect failed.");
			}
			else{
				System.out.println("Book data is in " + detectFormat + " format");
				Properties prop = BookDataProperties.getConvertFormatDetails();
				String targetFormat = prop.getProperty("targetFormat");
				String storageEnable = prop.getProperty("storageEnabled");
				String storageFile = prop.getProperty("storageFile");
				if(service.checkISBN(readFile)){
					System.out.println("Converting to " + targetFormat.toUpperCase() + " format");
					System.out.println("Here is the output...");
					System.out.println("++++");
					if(targetFormat.toUpperCase().equals(detectFormat)){
						System.out.println(readFile);
					}
					else if(targetFormat.equals("json")){
						service.txtToJsonConvert(readFile, storageEnable, storageFile);
					}
					else if(targetFormat.equals("txt")){
						service.jsonToTxtConvert(fileName, storageEnable, storageFile);
					}
					System.out.println("----");
				}
				else{
					System.out.println("Converting to " + targetFormat.toUpperCase() + 
							" format FAILED. Input file dosen't contains ISBN number.");
				}
			}
		}
		else{
			System.out.println("----");
		}
	}
}
