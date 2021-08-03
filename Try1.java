package framework;

import java.util.Scanner;
import java.io.File;

public class Try1 {
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception
	  {
	    // pass the path to the file as a parameter
	    File file = new File("C:\\Users\\pankaj\\Desktop\\test.txt");
	    Scanner sc = new Scanner(file);
	  
	    while (sc.hasNextLine()) {
	    	
	    	Opcode();
	    	System.out.println(sc.nextLine());
	    }
	      
	  }

	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		Opcode();
		String reg1 = scan.next();
		TypeA("00110", "00", Registers(reg1), "001", "010");
		TypeA("00110", "00", "011", "001", "010");
	}
	
	public static void Opcode() {
		String func = scan.next();
		System.out.println(func);
		if (func == "add") {
			add();
		}
	}
	
	public static String Registers(String reg) {
		if (reg == "R0") {
			return "011";
		}
		else if (reg == "R1") {
			return "001";
		}
		return "011";
	}
	
	public static void add() {
		String reg1 = scan.next();
		System.out.println(reg1);
		String reg2 = scan.next();
		System.out.println(reg2);
		String reg3 = scan.next();
		TypeA("00000", "00", Registers(reg1), Registers(reg2), Registers(reg3));
	}
	
	public static void TypeA(String opcode, String unused, String reg1, String reg2, String reg3) {
		System.out.println(opcode + unused + reg1 + reg2 + reg3);
	}

}


