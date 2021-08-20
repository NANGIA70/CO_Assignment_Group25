package simulator;

import java.io.*;

public class Write  {
	
	public static Simulator sim = new Simulator();
	
	public static void main(String[] args) {
//		Calling Simulator ka main :- to creat the required PCList
		Simulator.main(args);
		
	    try {
//	    	Open a new File Write
//	    	add the address of the file you want to edit...
	    	FileWriter myWriter = new FileWriter("E:\\EclipseCodes\\CO_ASSIGNMENT_2\\src\\simulator\\filename.txt", false);
	    	
//	    	Enter column headings
	    	myWriter.write("a" + "\t" + "b" + "\n");
	    	
//	    	Enter Coordinates
//	    	x1 y1
//	    	x2 y2
	    	for (int cycle = 0; cycle < Simulator.PCList.size(); cycle++) {
//	    		get mem_addr from the PCList made in class Simulator
	        	int mem_addr = Simulator.PCList.get(cycle);
//	        	x	y
	        	myWriter.write(cycle + "\t" + mem_addr + "\n");
	        } 
	    	
//	    	Close the File Writer
	        myWriter.close();
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
