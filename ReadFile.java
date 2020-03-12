package Payroll;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {

	public static void main(String[] args) throws Exception{
		String[] x, z; 
		List<String> combinedArray;
		
			File f = new File("Payroll Report - Job Items - Samantha.txt");
			File g = new File("Payroll Report - TGL - Samantha.txt");				//Name files
			Scanner myReader = new Scanner(f);
			Scanner myFileReader = new Scanner(g);
			List<String> lines = new ArrayList<String>();
			List<String> newLines = new ArrayList<String>();
			
			while (myReader.hasNextLine()) {										//Read both files by line
				String data = myReader.nextLine();
				lines.add(data); 
				}
			
			while (myFileReader.hasNextLine()) {
				String data = myFileReader.nextLine();
				newLines.add(data);	 }
										
			calculateDiscounts(lines);
			leadGenerated(newLines);
			matchInvoices(lines, newLines);
			outputToFile();
			
			myReader.close();
			myFileReader.close();
		
	}
	
	public static List<Double> calculateDiscounts(List<String> lines) {
		
		String[] itemsArray = new String[lines.size()];
		String[] temp = new String[itemsArray.length];
		String[] priceArray = new String[itemsArray.length];
		String[] quantityArray = new String[itemsArray.length];
		String[] invoiceArray = new String[itemsArray.length];
		List<Double> commissionableSaleTotal = new ArrayList<>();
		String[] itemCodeArray = new String[itemsArray.length];
		double quantity, prices, compensation, totalDiscountAmt, commissionableSales; 
		float price;
		String itemCode, thisItem = " ", currentInvoice, currentString, currentLine, previousInvoice;
		boolean isDiscount = false, commissionDrop, match; int discountCount = 0, index = 0;
		
		
			for (int i =0; i < lines.size(); i++) {
				itemsArray[i] = lines.get(i);
			}
			int w = 0, x = 0, y =0, z = 0;
			for(int i = 0; i < itemsArray.length; i++) {
				
				String str = itemsArray[i];
				String strTwo = str.substring(0);
				temp = strTwo.split("	");	
				
				for(int a = 0; a < temp.length - 1; a++) {
					
					if(a == 3) 
						{invoiceArray[x] = temp[a]; x++; }	
					if(a == 4) 
						{priceArray[y] = temp[a]; y++; }
					if(a == 5)
						{quantityArray[z] = temp[a]; z++;}
					if(a == 0)
						{itemCodeArray[w] = temp[a]; w++; }					
				}
			}
			
				for(int b = 0; b < 1; b++) {
					for(int a = 1; a < itemsArray.length - 2; a++) {
					currentInvoice = invoiceArray[a];
					previousInvoice = invoiceArray[a - 1];

					System.out.println(currentInvoice + " / " + previousInvoice);
					
					
					
					quantity = Double.parseDouble(quantityArray[a]);
					itemCode = itemCodeArray[a];
					
					System.out.println(itemCode);
					
					
					if(currentInvoice.equals(previousInvoice)) {
						
						match = true;
						if(itemCode.contains("disc999001")) isDiscount = true;
						else if(itemCode.contains("disc999002")) isDiscount = true;
						else if(itemCode.contains("disc999003")) isDiscount = true;
						else isDiscount = false;
						if(isDiscount == true) { 
							discountCount++;
						}
						else discountCount = 0;

						
						System.out.println(match);
						System.out.println(discountCount);
																		
					}
					else discountCount = 0; 
					
					//Compensation if no discounts
					if(itemCode.contains("-L1")){thisItem = "L1"; compensation = 0.08; }											
					else if(itemCode.contains("-L2")){ thisItem = "L2"; compensation = .10; }
					else if(itemCode.contains("-L3")){ thisItem = "L3"; compensation = .13; }
					else if(itemCode.contains("-L4")){ thisItem = "L4"; compensation = .13; }
					else if(itemCode.contains("-L5")){ thisItem = "L5"; compensation = .13;}
					else compensation = 0;
					
					//Compensation if 1 discount
					if(discountCount == 1) {
						if(thisItem == "L1") compensation = .08;																		
						else if(thisItem == "L2") compensation = .10;
						else if(thisItem == "L3") compensation = .13;
						else if(thisItem == "L4") compensation = .13;
						else if(thisItem == "L5") compensation = .13;
					}
					
					//Compensation if 2 or more discounts
					if(discountCount > 1) {
						if(discountCount == 2 && thisItem == "L1") compensation = .06;										
						else if(discountCount >= 3 && thisItem == "L1") compensation = .04;
						else if(discountCount == 2 && thisItem == "L2") compensation = .07;
						else if(discountCount >= 3 && thisItem == "L2") compensation = .05;
						else if(discountCount == 2 && thisItem == "L3") compensation = .10;
						else if(discountCount >= 3 && thisItem == "L3") compensation = .07;
						else if(discountCount == 2 && thisItem == "L4") compensation = .10;
						else if(discountCount >= 3 && thisItem == "L4") compensation = .07;
						else if(discountCount == 2 && thisItem == "L5") compensation = .10;
						else if(discountCount >= 3 && thisItem == "L5") compensation = .07;
						else compensation = 0;
					
					}
					
					totalDiscountAmt = discountCount * .05;
					price = Float.parseFloat(priceArray[a]); 
					if(discountCount >= 2) commissionableSales = price * totalDiscountAmt;
					else commissionableSales = price; 
					
					System.out.println("Commissionable: " + price);
					
					System.out.println("Discount percent: " + totalDiscountAmt);
					
					System.out.println("Compensation: " + compensation);
					
					commissionableSaleTotal.add(totalDiscountAmt + compensation);
					
					System.out.println(" ***** ");
					
					
					
					}
					
				}
				return commissionableSaleTotal;
		
		}
	
	public static void leadGenerated(List<String> lines) {
		
	}
	
	
	public static void  matchInvoices(List<String> lines, List<String> newLines) {
		
	}
	
	public static void outputToFile() throws Exception {
		
		try {

	         File file = new File("C:\\Users\\BaconPlumbing10\\Documents\\abc.txt");
	         if (!file.exists()) {
	            file.createNewFile();
	         } 
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
	         BufferedWriter bw = new BufferedWriter(fw);
	         bw.write("test");
	         bw.close();
	         
	         System.out.println("Done");
	      } catch (Exception e) {
	         e.printStackTrace();
	      } 
		
	}
	
	
}
