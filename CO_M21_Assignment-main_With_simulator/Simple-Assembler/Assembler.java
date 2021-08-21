package framework;

import java.io.*;

import java.util.*;

public class Assembler {
	
//	to display line numbers with errors
	public static int linenumber = 1; 
	
//	to find mem_add of labels
	public static int mem_addLabels = 0;  // Check whether mem_add starts from 0 or 1
	
//	to fine mem_add of Variables
	public static int mem_addVariables = 0;
	
//	for variables not declared at the beginning error
	public static boolean varNotStart = false; 
	
//	Initialization and Object Creation
//	Initialization of HashMap variable and HashMap labels
	public static HashMap<String, String> variable = new HashMap<String, String>();
	public static HashMap<String, String> labels = new HashMap<String, String>();
	
//	Object Creation for class HashMaps and class TypePrinting
	public static HashMaps hp = new HashMaps();
	public static TypePrinting tp = new TypePrinting();

	
//	Main Function to read the file
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Reads The file
		try {
			boolean hltpresent = false;
			
//			Take input to find the test file			
			ArrayList<String> A = new ArrayList<String>(256);
			Scanner s = new Scanner(System.in);
			while (s.hasNextLine()) {
				A.add(s.nextLine());
			}
//			the file to be traversed for the first time 
			for(int i = 0; i < A.size(); i++) {
				String line = A.get(i);
				String line2 = line;
				String [] words = line2.split(" ");
				
				processVar(line, words);				
			}
			
//			the file to be opened for reading  
//		    returns true if there is another line to read

			for(int i = 0; i < A.size(); i++) {
//				If hlt is succeeded by another line:- it isn't the last instruction
//				Therefore Check if hlt is succeeded by another line
				if (hltpresent) {
					throw new Exception("ERROR: hlt not being used as the last instruction in line " + linenumber);
				}
				String line = A.get(i);
				String line2 = line;
				String [] words = line2.split(" ");
				
//				hlt errors
				if (line.contains("hlt") || words[0].equals("hlt") || words[1].equals("hlt")) {
					hltpresent = true;
				}
				
//				Process takes line and words array
				process(line, words);
				linenumber++; // updates the line number
			}
			
//			Check if hlt is missing
			if (!hltpresent) {
				throw new Exception("ERROR: Missing hlt instruction");
			}
//			sc.close();     //closes the scanner  
		
		}  
		catch(IOException e) {
			e.printStackTrace(); 
		}
//		Tester:- process("mov R2 $7");
	}

	
public static void processVar(String line, String[] words) throws Exception {
	// TODO Auto-generated method stub	
	if (!words[0].equals("var")) {
//		Label Handling
		if (words[0].contains(":")) {
//			mylabel: add R0 R1 R2
			if (binStr(mem_addVariables).equals("-1")) {
				return;
			}
			labels.put(words[0].substring(0, words[0].length() - 1), binStr(mem_addVariables));
		}	
		mem_addVariables++;
	}
}


//	Process function to go through the file line by line
	public static void process(String line, String [] words) throws Exception {
		// TODO Auto-generated method stub
//		Check if comment
		if (iscomment(line)) {
			return;
		}
		
//		Check if Empty Line
		if (line.equals("")) {
			return;
		}
		
//		Variable Handling: Entering into variables HashMap
//		var x 
//		add r1 r2 r3 ==> varNotStart = true
//		var y 
//		mov r4 r1
//		hlt
		if (words[0].equals("var")) {
//			Check whether the variable isn't declared at the beginning
			if (varNotStart) {
				throw new Exception("ERROR: Variable " + words[1] +" not declared at the beginning" + linenumber);
			}
			else {
//				Check whether the number of variables exceeded 256
				if (binStr(mem_addVariables).equals("-1")) {
					return;
				}
				variable.put(words[1], binStr(mem_addVariables));
				mem_addVariables++;
				return;
			}
		}
		else {
//			The moment something other than var is encountered, we can't have any more variable declarations
			varNotStart = true;
		}
		
//		Label Handling: Entering into labels HashMap
		if (words[0].contains(":")) {
//			mylabel: add R0 R1 R2
//			if (binStr(mem_addLabels).equals("-1")) {
//				return;
//			}
//			labels.put(words[0].substring(0, words[0].length() - 1), binStr(mem_addLabels));
//			change the array words to remove the label :- Reduces work in code
			words = remove(words);
		}
		
//		Check if Label doesn't have an instruction
		if (words.length == 0) {
			throw new Exception("ERROR: Label doesn't have an instruction " + linenumber);
		}
		
//		Wrong function name error
		if (!HashMaps.opcode.containsKey(words[0])) {
			throw new Exception("ERROR: Unrecognised function name in line " + linenumber);
		}
		
//		Type Selection
		if (words[0].equals("add") || words[0].equals("sub") || words[0].equals("mul") || words[0].equals("xor") || words[0].equals("or") || words[0].equals("and")) {
//			Type A :- add, sub, mul, xor, or, and (6)
			TypeA(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("rs") || words[0].equals("ls")) {
//			Type B :- rs, ls (2)
			TypeB(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("div") || words[0].equals("not") || words[0].equals("cmp")) {
//			Type C :- div, not, cmp (3)
			TypeC(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("mov")) {
//			Type Mov :- mov (2)
			TypeMov(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("ld") || words[0].equals("st")) {
//			Type D :- ld, st (2)
			TypeD(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("jmp") || words[0].equals("jlt") || words[0].equals("jgt") || words[0].equals("je")) {
//			Type E :- jmp, jlt, jgt, je (4)
			TypeE(line, words);
			mem_addLabels++;
			return;
		}
		else if (words[0].equals("hlt")) {
//			Type F :- hlt (1)
			TypeF(line, words);
			mem_addLabels++;
			return;
		}
		else {
			throw new Exception("ERROR: Unrecognised function name in line " + linenumber);
		}
	}	
	
	
//	Type Specific Functions
//	Type A
	public static void TypeA(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		add R0 R1 R2
//		Opcode("00000", "A", "add", 3, 0, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 4 || line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Register Name Error
		registerErrorCheck(words, 3, linenumber, false);
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		Register r1 = HashMaps.register.get(words[1]);
		Register r2 = HashMaps.register.get(words[2]);
		Register r3 = HashMaps.register.get(words[3]);
		
//		Printing
		TypePrinting.TypeAPrint(o.opcode, r1.address, r2.address, r3.address);
	}
	
//	Type B
	public static void TypeB(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		rs R1 $Imm
//		Opcode("00000", "B", "rs", 1, 1, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 3 || !line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Register Name Error
		registerErrorCheck(words, 1, linenumber, false);
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		Register r1 = HashMaps.register.get(words[1]);
		
//		Get Integer Value of Immediate 
		int Imm = Integer.parseInt(words[2].substring(1));
		
//		Check for Illegal Immediate Values
		if (binStr(Imm).equals("-1")) {
			return;
		}
		
//		Printing
		TypePrinting.TypeBPrint(o.opcode, r1.address, binStr(Imm));
	}
	
//	Type C
	public static void TypeC(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		div R3 R4
//		Opcode("00000", "C", "div", 2, 0, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 3 || line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Register Name Error
		registerErrorCheck(words, 2, linenumber, false);
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		Register r1 = HashMaps.register.get(words[1]);
		Register r2 = HashMaps.register.get(words[2]);
		
//		Printing
		TypePrinting.TypeCPrint(o.opcode, r1.address, r2.address);
	}
	
//	Type Mov
	public static void TypeMov(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		mov R1 $Imm
//		mov R1 R2 
		
		if (words[2].contains("$")) {
//			Case 1: mov R1 $Imm
//			Opcode("00000", "B", "mov", 1, 1, 0)
//			Register("R0", "000")
			
//			Wrong Syntax error
			if (words.length != 3 || !line.contains("$")) {
				throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
			}
			
//			Register Name Error
			registerErrorCheck(words, 1, linenumber, false);
			
//			Get required opcode and registers from HashMaps
			Opcode o = HashMaps.opcode.get(words[0]);
			Register r1 = HashMaps.register.get(words[1]);
			
//			Get Integer Value of Immediate 
			int Imm = Integer.parseInt(words[2].substring(1));
			
//			Check for Illegal Immediate Values
			if (binStr(Imm).equals("-1")) {
				return;
			}
			
//			Printing
			TypePrinting.TypeBPrint("00010", r1.address, binStr(Imm));	
		}
		else {
//			Case 2: mov R1 R2 
//			Opcode("00000", "C", "add", 2, 0, 0)
//			Register("R0", "000")
			
//			Wrong Syntax error
			if (words.length != 3 || line.contains("$")) {
				throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
			}
			
//			Register Name Error
			registerErrorCheck(words, 2, linenumber, true);
			
//			Get required opcode and registers from HashMaps
			Opcode o = HashMaps.opcode.get(words[0]);
			Register r1 = HashMaps.register.get(words[1]);
			Register r2 = HashMaps.register.get(words[2]);
			
//			Printing
			TypePrinting.TypeCPrint("00011", r1.address, r2.address);	
		}
	}
	
//	Type D
	public static void TypeD(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		ld R1 mem_add
//		Opcode("00000", "A", "add", 3, 0, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 3 || line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Register Name Error
		registerErrorCheck(words, 1, linenumber, false);
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		Register r1 = HashMaps.register.get(words[1]);
		
//		Type D can only take variables as mem_add
//		if label used as mem_address :- Give Error
		if (labels.containsKey(words[2])) {
//			ERROR
			throw new Exception("ERROR: Misuse of label as variable " + linenumber);
		}
//		Check if variable used as mem_address
		else if (variable.containsKey(words[2])) {
//			Printing
			TypePrinting.TypeDPrint(o.opcode, r1.address, variable.get(words[2]));
		}
		else {
//			ERROR
			throw new Exception("ERROR: Use of Undefined Variable in line " + linenumber);
		}
	}
	
//	Type E
	public static void TypeE(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		jmp mem_add
//		Opcode("00000", "A", "add", 3, 0, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 2 || line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		
//		Type E can only take labels as mem_add
//		Check if label used as mem_address
		if (labels.containsKey(words[1])) {
//			Printing
			TypePrinting.TypeEPrint(o.opcode, labels.get(words[1]));
		}
//		Check if variable used as mem_address
		else if (variable.containsKey(words[1])) {
//			ERROR
			throw new Exception("ERROR: Misuse of variables as labels " + linenumber);
		}
		else {
//			ERROR
			throw new Exception("ERROR: Use of Undefined Label " + linenumber);
		}
	}
	
//	Type F
	public static void TypeF(String line, String[] words) throws Exception {
		// TODO Auto-generated method stub
//		hlt
//		Opcode("00000", "A", "add", 3, 0, 0)
//		Register("R0", "000")
		
//		Wrong Syntax error
		if (words.length != 1 || line.contains("$")) {
			throw new Exception("ERROR: Wrong Syntax used for instructions in line " + linenumber);
		}
		
//		Get required opcode and registers from HashMaps
		Opcode o = HashMaps.opcode.get(words[0]);
		
//		Printing
		TypePrinting.TypeFPrint(o.opcode);
	}
	
	
	
// Initial Check Functions
//	Check if comment
	public static boolean iscomment(String line) {
		if (line.contains("//")) {
			return true;
		}
		return false;
	}
	
	
//	Convert to binary
	public static String binStr(int val) {
//		Convert immediate and memory address to binary
		if (val < 0 || val > 255) {
			System.out.println("ERROR: Illegal Value... Not in range [0, 255] in line " + linenumber);
			return "-1";
		}
		String x = Integer.toBinaryString(val);
		String ans = "";
		if (x.length() < 8) {
//			we need to add zeros
			for (int i = 0; i < 8 - x.length(); i++) {
				ans = ans + "0";
			}
			ans = ans + x;
		}
		else {
			ans = ans + x;
		}
		return ans;
	}
	
	
//	Error Functions
//	Register name error
	public static void registerErrorCheck(String [] words, int n, int count, boolean ismov) throws Exception {
//		ld R1 mem_add ==> (words, 1, count, false)
//		ld mem_add R1 ==> (words, 1, count, false) ==> unrecognised register name
		for (int i = 1; i <= n; i++) {
//			Check for Illegal flag use :- Flag can only be used with mov ka case 2
			if(!ismov) {
				if (words[i].equals("FLAGS")) {
					throw new Exception("ERROR: Illegal use of Flag in line " + count);
				}
			}
			
//			Check for wrong register name
			if(HashMaps.register.containsKey(words[i])) {
				continue;
			}
			else {
				throw new Exception("ERROR: Unrecognised Register Name in line " + count);
			}
		}
	}
	
	
//	Miscellaneous Functions
	public static String [] remove(String [] arr) {
		String [] newArr = new String[arr.length - 1];
		for (int i = 1, k = 0; i < arr.length; i++) {
			newArr[k++] = arr[i];
		}
		return newArr;
	}	
}


class HashMaps {
//	HashMap for registers
	public static HashMap<String, Register> register = new HashMap<String, Register>();
	
//	HashMap for opcodes and various properties
	public static HashMap<String, Opcode> opcode = new HashMap<String, Opcode>();
	
//	Constructor
	public HashMaps() {
		// TODO Auto-generated method stub
		CreateRegisterMap();
		CreateOpcodeMap();
	}
	
//	Function to build the HashMap for registers
	public static void CreateRegisterMap() {
//		R0
		Register R0 = new Register("R0", "000");
		register.put("R0", R0);
		
//		R1
		Register R1 = new Register("R1", "001");
		register.put("R1", R1);
		
//		R2
		Register R2 = new Register("R2", "010");
		register.put("R2", R2);
		
//		R3
		Register R3 = new Register("R3", "011");
		register.put("R3", R3);
		
//		R4
		Register R4 = new Register("R4", "100");
		register.put("R4", R4);
		
//		R5
		Register R5 = new Register("R5", "101");
		register.put("R5", R5);
		
//		R6
		Register R6 = new Register("R6", "110");
		register.put("R6", R6);
		
//		Flags
		Register R7 = new Register("FLAGS", "111");
		register.put("FLAGS", R7);
	}
	
//	Function to build the HashMap for opcodes and various properties
	public static void CreateOpcodeMap() {
//		Add
//		add R0 R1 R2
		Opcode op1 = new Opcode("00000", "A", "add", 3, 0, 0);
		opcode.put("add", op1);
		
//		Sub
//		sub R0 R1 R2
		Opcode op2 = new Opcode("00001", "A", "sub", 3, 0, 0);
		opcode.put("sub", op2);
		
//		Mov $Imm
//		mov R0 $value
		Opcode op3 = new Opcode("00010","B" ,"mov", 1, 1, 0);
		opcode.put("mov", op3);
		
//		Mov register
//		mov R1 R2
		Opcode op4 = new Opcode("00011","C" ,"mov", 2, 0, 0);
		opcode.put("mov", op4);
		
//		Load
//		ld R0 mem_addr
		Opcode op5 = new Opcode("00100", "D", "ld", 1, 0, 1);
		opcode.put("ld", op5);
		
//		Store
//		st R0 mem_addr
		Opcode op6 = new Opcode("00101", "D", "st", 1, 0, 1);
		opcode.put("st", op6);
		
//		Multiply
//		mul R0 R1 R2
		Opcode op7 = new Opcode("00110", "A", "mul", 3, 0, 0);
		opcode.put("mul", op7);
		
//		Divide
//		div R0 R1 
		Opcode op8 = new Opcode("00111", "C","div", 2, 0, 0);
		opcode.put("div", op8);
		
//		Right Shift
//		rs R0 $Imm
		Opcode op9 = new Opcode("01000", "B","rs", 1, 1, 0);
		opcode.put("rs", op9);
		
//		Left Shift
//		ls R0 $Imm
		Opcode op10 = new Opcode("01001", "B","ls", 1, 1, 0);
		opcode.put("ls", op10);
		
//		Exclusive Or
//		xor R1 R2 R3
		Opcode op11 = new Opcode("01010", "A","xor", 3, 0, 0);
		opcode.put("xor", op11);
		
//		OR
//		or R1 R2 R3
		Opcode op12 = new Opcode("01011", "A","or", 3, 0, 0);
		opcode.put("or", op12);
		
//		AND
//		and R1 R2 R3
		Opcode op13 = new Opcode("01100", "A","and", 3, 0, 0);
		opcode.put("and", op13);
		
//		INVERT
//		not R1 R2 
		Opcode op14 = new Opcode("01101", "C","not", 2, 0, 0);
		opcode.put("not", op14);
		
//		COMPARE
//		cmp R1 R2 
		Opcode op15 = new Opcode("01110", "C","cmp", 2, 0, 0);
		opcode.put("cmp", op15);
		
//		UNCONDITIONAL JUMP
//		jmp mem_addr 
		Opcode op16 = new Opcode("01111", "E","jmp", 0, 0, 1);
		opcode.put("jmp", op16);
		
//		JUMP IF LESS THAN 
//		jlt mem_addr 
		Opcode op17 = new Opcode("10000", "E","jlt", 0, 0, 1);
		opcode.put("jlt", op17);
		
//		JUMP IF GREATER THAN 
//		jgt mem_addr 
		Opcode op18 = new Opcode("10001", "E","jgt", 0, 0, 1);
		opcode.put("jgt", op18);
		
//		JUMP IF EQUAL 
//		je mem_addr 
		Opcode op19 = new Opcode("10010", "E","je", 0, 0, 1);
		opcode.put("je", op19);
		
//		HALT 
//		hlt 
		Opcode op20 = new Opcode("10011", "F","hlt", 0, 0, 0);
		opcode.put("hlt", op20);
	}
}

class Register {
	String register;
	String address;
//	String value; Simulator mei aayega

//	Constructor
	public Register(String register, String address) {
		// TODO Auto-generated constructor stub
		this.register = register;
		this.address = address;
	}	
}


class Opcode {
//	{func : (opcode, #registers)}
	String type;
	String func;
	String opcode;
	int reg; // # of register
	int constants; // # of constants
	int mem_addr; // # of memory addresses
	
//	Constructor
	public Opcode(String opcode,String type,  String func, int reg, int constants, int mem_addr) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.func = func;
		this.opcode = opcode;
		this.reg = reg;
		this.constants = constants;
		this.mem_addr = mem_addr;
	}	
}


class TypePrinting {
//	Type-wise Printing Functions
	
//	Type A
	public static void TypeAPrint (String opcode, String reg1, String reg2, String reg3) {
//		add R0 R1 R2
		System.out.println(opcode + "00" + reg1 + reg2 + reg3);
	}
	
//	Type B
	public static void TypeBPrint (String opcode, String reg1, String Imm) {
//		mov R1 $Imm
		System.out.println(opcode + reg1 + Imm);
	}
	
//	Type C
	public static void TypeCPrint (String opcode, String reg1, String reg2) {
//		div R3 R4
		System.out.println(opcode + "00000" + reg1 + reg2);
	}
	
//	Type D
	public static void TypeDPrint (String opcode, String reg1, String mem_add) {
//		ld R1 mem_add
		System.out.println(opcode + reg1 + mem_add);
	}
	
//	Type E
	public static void TypeEPrint (String opcode, String mem_add) {
//		jmp mem_add
		System.out.println(opcode + "000" + mem_add);
	}
	
//	Type F
	public static void TypeFPrint (String opcode) {
//		halt
		System.out.println(opcode + "00000000000");
	}
}


class Flags {
//	12 unused V L G E
//	0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
	int [] flags = new int[16];
	
//	Constructor
	public Flags() {
		// TODO Auto-generated constructor stub
		for (int i = 0; i < flags.length; i++) {
			flags[i] = 0;
		}
	}

	//	Functions to be performed
	public void reset() {
		for (int i = 0; i < flags.length; i++) {
			flags[i] = 0;
		}
	}
	
	public void overflow() {
//		V change karna hai
//		12 is V
		flags[12] = 1;
	}
	
	public void lessThan() {
//		L change karna hai
//		13 is L
		flags[13] = 1;
	}
	
	public void greaterThan() {
//		G change karna hai
//		14 is G
		flags[14] = 1;
	}
	
	public void equal() {
//		E change karna hai
//		15 is E
		flags[15] = 1;
	}
}