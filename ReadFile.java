package Payroll;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {

	public static void main(String[] args) throws Exception{
		String[] x, z; List<Double> commissions;
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
										
			commissions = calculateDiscounts(lines);
			leadGenerated(newLines);
			matchInvoices(lines, newLines);
			outputToFile(lines);
			
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
		double quantity, prices, compensation, totalDiscountAmt, commissionableSales, price, newPrice;; 
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
	
					
					
					quantity = Double.parseDouble(quantityArray[a]);
					itemCode = itemCodeArray[a];
					
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

																		
					}
					else discountCount = 0; match = false;
					
					
					//Compensation if no discounts
					if(itemCode.contains("-L1")){thisItem = "L1"; compensation = 0.08; index = a; }											
					else if(itemCode.contains("-L2")){ thisItem = "L2"; compensation = .10; index = a; }
					else if(itemCode.contains("-L3")){ thisItem = "L3"; compensation = .13; index = a; }
					else if(itemCode.contains("-L4")){ thisItem = "L4"; compensation = .13; index = a; }
					else if(itemCode.contains("-L5")){ thisItem = "L5"; compensation = .13; index = a; }
					else compensation = 0; index = a;
					
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
					if(isDiscount == true) newPrice = price - commissionableSales;
					else price = Float.parseFloat(priceArray[index]); 
					
					if(discountCount >= 2) commissionableSales = price * totalDiscountAmt;
					else commissionableSales = price; 

					commissionableSaleTotal.add(totalDiscountAmt + compensation);
					
					System.out.println("Item Code: " + itemCode);
					System.out.println("Commissionable: " + commissionableSales);
					System.out.println("Price: " + price);
					
					}
					
				}
				return commissionableSaleTotal;
		
		}
	
	public static Double[] leadGenerated(List<String> newLines) {
		String[] leadArray = new String[newLines.size()];
		String[] temp = new String[leadArray.length];
		String[] leadGen = new String[leadArray.length];
		String[] invoiceArray = new String[leadArray.length];
		Double[] commission = new Double[leadArray.length];
		
		for (int i =0; i < newLines.size(); i++) {
			leadArray[i] = newLines.get(i);
		}
		
		int x = 0, y = 0;
		for(int i = 0; i < leadArray.length; i++) {
			
			String str = leadArray[i];
			String strTwo = str.substring(0);
			temp = strTwo.split("	");	
			
			for(int a = 0; a < temp.length; a++) {
				
				if(a == 0) 
					{invoiceArray[x] = temp[a]; x++; }	
				if(a == 1) 
					{leadGen[y] = temp[a]; y++; }
				
			}
		}
		
		for(int i = 0; i < leadArray.length; i++) {
			if(leadGen[i].contains("Erin McDaniel")) commission[i] = .03;
			else commission[i] = .05;
			
			
		}
		
		return commission;
	}
	
	public static void calculateCommByTech() {
		
		
	}
	
	
	public static void  matchInvoices(List<String> lines, List<String> newLines) {
		String[] leadArray = new String[newLines.size()];
		String[] itemsArray = new String[lines.size()];
		String currentInvoice, currentLine, currentString;
		List<String> matchedInvoiceLines = new ArrayList<>();
		
		for (int i =0; i < lines.size(); i++) {
			itemsArray[i] = lines.get(i);
		}
		
		for(int a =0; a < newLines.size(); a++) {
			leadArray[a] = newLines.get(a);
		}
		
	
		
	}
	
	public static void outputToFile(List<String> lines) throws Exception {
		
		try {

	         File file = new File("C:\\Users\\BaconPlumbing10\\Documents\\abc.txt");
	         if (!file.exists()) {
	            file.createNewFile();
	         } 
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
	         BufferedWriter bw = new BufferedWriter(fw);
	         for(int i = 0; i < lines.size(); i++) {
	        	 bw.write(lines.get(i) + "\n"); }
	         
	         bw.close();
	         
	         System.out.println("Done");
	      } catch (Exception e) {
	         e.printStackTrace();
	      } 
		
	}
	
	
}
