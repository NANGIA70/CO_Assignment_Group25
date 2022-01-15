package simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Simulator {
	
//	Object Creation is done for class HashMaps and class FLAGS
	public static HashMaps hp = new HashMaps();
	public static Flags fl = new Flags();
	
//	Initializing the Program Counter with 0
	public static int PC = 0;
	
//	Creating a list of all the Program Counters encountered
//	To be used  in Write.java
	public static ArrayList<Integer> PCList = new ArrayList<Integer>(256);
	
//	Creating the Memory as a List to Store all the instructions provided in the test cases
	public static ArrayList<String> MemList = new ArrayList<String>(256);
	
	public static void Initialize() {
		// Initializing the memory with "0000000000000000"
		for (int i = 0; i < 256; i++) {
			 MemList.add("0000000000000000");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Initializing Memory
		Initialize();
		
//		Take input 			
		Scanner s = new Scanner(System.in);
		int index = 0;
		while (s.hasNextLine()) {
			MemList.set(index, s.nextLine());
			index++;
		}
		
//		Traverse the ArrayList/Data
		while (true) {
//			Making a list of program counters
			PCList.add(PC);
			
//			Enter the process
			process(MemList.get(PC));
			
//			Stop at halt
			String func = MemList.get(PC - 1).substring(0, 5);
			
			if (func.equals("10011")) {
				break;
			}
		}
		
//		Print Memory
		for (int i = 0; i < MemList.size(); i++) {
			System.out.println(MemList.get(i));
		}
	}
	
	
//	Main Process
	public static void process(String instruction) {
		// TODO Auto-generated method stub
//		00001001001
		String func = instruction.substring(0, 5);
		
		if (func.equals("00000")) { 
//			1
//			add();
//			add R0 R1 R2
//			00000_00_000_000_000
			add(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));
		}
		
		else if (func.equals("00001")) { 
//			2
//			sub()
//			sub R0 R1 R2
//			00000_00_000_000_000
			sub(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));
		}
		
		else if (func.equals("00010")) { 
//			3
//			movImm()
//			mov $Imm
//			00000_000_00000000(imm)
			movImm(instruction.substring(5,8),instruction.substring(8,16));
		}
		
		else if (func.equals("00011")) { 
//			4
//			movReg()
//			mov R0 R1
//			00000_00000_000_000
			movReg(instruction.substring(10,13),instruction.substring(13,16));

		}
		
		else if (func.equals("00100")) {
//			5
//			load()
//			ld R0 mem_add
//			00000_000_00000000(mem_addr)
			load(instruction.substring(5,8),instruction.substring(8,16));
		}
		
		else if (func.equals("00101")) { 
//			6
//			store()
//			st R0 mem_add
//			00000_000_00000000(mem_addr)
			st(instruction.substring(5,8),instruction.substring(8,16));
		}
		
		else if (func.equals("00110")) { 
//			7
//			mul()
//			mul R0 R1 R2
//			00000_00_000_000_000
			mul(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));

		}
		
		else if (func.equals("00111")) { 
//			8
//			div()
//			div R1 R2
//			00000_00000_000_000
			div(instruction.substring(10,13),instruction.substring(13,16));
			
		}
		
		else if (func.equals("01000")) { 
//			9
//			rs()
//			rs R1 &Imm
//			00000_000_00000000(imm)
			rs(instruction.substring(5,8),instruction.substring(8,16));
		}
		
		else if (func.equals("01001")) { 
//			10
//			ls()
//			ls R1 &Imm
//			00000_000_00000000
			ls(instruction.substring(5,8),instruction.substring(8,16));
		}
		
		else if (func.equals("01010")) { 
//			11
//			xor()
//			xor R0 R1 R2
//			00000_00_000_000_000
			xor(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));
		}
		
		else if (func.equals("01011")) { 
//			12
//			or()
//			or R0 R1 R2
//			00000_00_000_000_000
			or(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));
		}
		
		else if (func.equals("01100")) { 
//			13
//			and()
//			and R0 R1 R2
//			00000_00_000_000_000
			and(instruction.substring(7,10), instruction.substring(10,13), instruction.substring(13,16));
		}
		
		else if (func.equals("01101")) { 
//			14
//			not()
//			not R0 R1
//			00000_00000_000_000
			not(instruction.substring(10,13),instruction.substring(13,16));
		}
		
		else if (func.equals("01110")) { 
//			15
//			cmp()
//			cmp R1 R2
//			00000_00000_000_000
			cmp(instruction.substring(10,13),instruction.substring(13,16));
		}
		
		else if (func.equals("01111")) { 
//			16
//			jmp()
//			jmp mem_add
//			00000_000_00000000(mem addr)
			jmp(instruction.substring(8,16));
		}
		
		else if (func.equals("10000")) { 
//			17
//			jlt()
//			jlt mem_add
//			00000_000_00000000(mem addr)
			jlt(instruction.substring(8,16));
		}
		
		else if (func.equals("10001")) { 
//			18
//			jgt()
//			jgt mem_add
//			00000_000_00000000(mem addr)
			jgt(instruction.substring(8,16));
		}
		
		else if (func.equals("10010")) { 
//			19
//			je()
//			je mem_add
//			00000_000_00000000(mem addr)
			je(instruction.substring(8,16));
		}
		
		else if (func.equals("10011")) { 
//			20
//			hlt()
//			10000_00000000000	
			hlt();
		}
	}

	public static void hlt() {
		 // TODO Auto-generated method stub
//		1. Reset flags
		fl.reset();
	
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void je(String mem_addr) {
			// TODO Auto-generated method stub
//		 1. Change PC to mem_addr if equal is set
		if (fl.ifEqual()) {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC = binInt(mem_addr);
		}
		else {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC++;
		}
	}
	
	
	public static void jgt(String mem_addr) {
			// TODO Auto-generated method stub
//		 1. Change PC to mem_addr if greater than is set
		if (fl.ifGreaterThan()) {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC = binInt(mem_addr);
		}
		else {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC++;
		}
	}
	
	
	public static void jlt(String mem_addr) {
		// TODO Auto-generated method stub
//		1. Change PC to mem_addr if less than is set
		if (fl.ifLessThan()) {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC = binInt(mem_addr);
		}
		else {
//			2. Reset flags
			fl.reset();
			
//			3. Print Call
			print();
			
//			4. PC update
			PC++;
		}
	}
	
	
	public static void jmp(String mem_addr) {
		 // TODO Auto-generated method stub
//		 1. Reset flags
		 fl.reset();
		
//		2. Print Call
		 print();
		
//		3. PC = mem_addr
		 PC = binInt(mem_addr);
	}
	
	
	public static void st(String reg1, String mem_addr) {
		// TODO Auto-generated method stub
//		st R1 mem_add
//		00101_001_10101010
		Register r1 = HashMaps.register.get(reg1);
		
//		1. Reset flags
		fl.reset();
		
//		2.1) Take r1.value directly
//		2.2) Replace the Memlist at index binInt(mem_add) 
		MemList.set(binInt(mem_addr), r1.value);
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void load(String reg1, String mem_addr) {
		// TODO Auto-generated method stub
//		ld R1 mem_add
//		00101_001_10101010
		Register r1 = HashMaps.register.get(reg1);
		
//		1. Reset flags
		fl.reset();
		
//		2.1) Take MemList value at the index binInt(mem_add) 
//		2.2) store in r1
		r1.value = MemList.get(binInt(mem_addr));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void cmp(String reg1, String reg2) {
		// TODO Auto-generated method stub
//		check if reg1 == reg2
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		
//		No changes to be made in values for this function, since we are just comparing the values.
		
//		1. Reset flags
		fl.reset();
		
//		2.1) if reg1 == reg2 then implement E
		if (binInt(r1.value) == binInt(r2.value)) {
			fl.equal();
		}
		
//		2.2) if reg1 > reg then implement G
		else if (binInt(r1.value) > binInt(r2.value)) {
			fl.greaterThan();
		}
		
//		2.3) if reg1< reg2 then implement L
		else if (binInt(r1.value) < binInt(r2.value)) {
			fl.lessThan();
		}
		
//		3. Print Call
		 print();
		
//		4. PC update
		 PC++;
		
	}
	
	
	public static void not(String reg1, String reg2) {
		// TODO Auto-generated method stub
//		0010 => 1101
//		1's complement
		Register r0 = HashMaps.register.get(reg1);
		Register r1 = HashMaps.register.get(reg2);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2.1) r1.value ki string => bin se int mei convert
//		2.2) use ~ on r1.value ka int
//		2.3) convert to bin and store in r0.value
		r0.value = binStrRF(~binInt(r1.value));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void div(String reg3, String reg4) {
		// TODO Auto-generated method stub
//		 R0 = reg3 / reg4 (quotient)
//		 R1 = reg3 % reg4 (remiander)
		
		Register r0 = HashMaps.register.get("000");
		Register r1 = HashMaps.register.get("001");
		Register r3 = HashMaps.register.get(reg3);
		Register r4 = HashMaps.register.get(reg4);

//		Changes to be made in values
//		1. Reset Flags
		fl.reset();
		
//		2. Divide karo aur reg0 mae quotient aur reg 1 mae remiander
		r0.value = binStrRF(binInt(r3.value) / binInt(r4.value));
		r1.value = binStrRF(binInt(r3.value) % binInt(r4.value));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void movReg(String reg1, String reg2) {
		// TODO Auto-generated method stub
//		reg1 = reg2
//		Changes to be made in the Values 
		
		Register r1 = HashMaps.register.get(reg1);
		
//		Check if second reg is flags
		if (reg2.equals("111")) {
//			1. Flags ki value reg 1 mei daalni hai 
			r1.value = fl.FlagsStr();
		}
		else {
			Register r2 = HashMaps.register.get(reg2);
			
//			1.b) reg 2 ki value reg 1 mei daalni hai 
			r1.value = r2.value;
		}
		
//		2. Reset Flags
		fl.reset();
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void ls(String reg, String Imm) {
		// TODO Auto-generated method stub
//		0010 => 0100
//		reg >>> Imm times
		Register r = HashMaps.register.get(reg);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2.1) Imm and r.value string => bin to int  convert
//		2.2) use << on r.value imm ki integers
		r.value = binStrRF(binInt(r.value) << binInt(Imm));
//		r.value = binStrRF((int)(binInt(r.value) * Math.pow(2, binInt(Imm))));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void rs(String reg, String Imm) {
		// TODO Auto-generated method stub
//		0010 => 0001
//		reg >>> Imm times
		Register r = HashMaps.register.get(reg);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2.2) Imm and r.value ki string => bin to int  convert
//		2.3) use >>> on r.value imm ki integers
		r.value = binStrRF(binInt(r.value) >>> binInt(Imm));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void movImm(String reg, String Imm) {
			// TODO Auto-generated method stub
//		reg = Imm
		Register r = HashMaps.register.get(reg);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. Imm ki string => bin se int mei convert => convert to 16 bit r ke liye string
		r.value = binStrRF(binInt(Imm));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
		}
	
	
	public static void and(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 & reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. reg2 & reg3 ki string => bin to int  convert  => integers to bitwise and => back to binary string
		r1.value = 	binStrRF(binInt(r2.value) & binInt(r3.value));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void or(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 | reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. reg2 & reg3 ki string => bin se int mei convert  => integers ko bitwise or => back to binary string
		r1.value = 	binStrRF(binInt(r2.value) | binInt(r3.value));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;
	}
	
	
	public static void xor(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 ^ reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. reg2 & reg3 ki string => bin se int mei convert  => integers ko xor => back to binary string
		r1.value = 	binStrRF(binInt(r2.value) ^ binInt(r3.value));
		
//		3. Print Call
		print();
		
//		4. PC update
		PC++;	
	}
	
	
	public static void mul(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 * reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. reg2 & reg3 ki string => bin se int mei convert  => integers ko mul => back to binary string
		r1.value = 	binStrRF(binInt(r2.value) * binInt(r3.value));
		
//		3. Check for overflow => 16 bits allowed => convert r1.value to int => should be less than 65535
		if (binInt(r1.value) > 65535) {
			fl.overflow();
		}
		
//		4. Print Call
		print();
		
//		5. PC update
		PC++;	
	}
	
	
	public static void sub(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 - reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values
//		1. Reset Flags
		fl.reset();
		
//		2.1) Check for overflow => reg 3 > reg 2
		if (binInt(r3.value) > binInt(r2.value)) {
			r1.value = 	binStrRF(0);
			fl.overflow();
		}
		else {
//			2.2) reg2 & reg3 ki string => bin se int mei convert  => integers ko sub => back to binary string
			r1.value = 	binStrRF(binInt(r2.value) - binInt(r3.value));
		}
		
//		4. Print Call
		print();
		
//		5. PC update
		PC++;	
	}
	
	
	public static void add(String reg1, String reg2, String reg3) {
		// TODO Auto-generated method stub
//		reg1 = reg2 + reg3
		Register r1 = HashMaps.register.get(reg1);
		Register r2 = HashMaps.register.get(reg2);
		Register r3 = HashMaps.register.get(reg3);
		
//		Changes to be made in the Values 
//		1. Reset Flags
		fl.reset();
		
//		2. reg2 & reg3 ki string => bin se int mei convert  => integers ko add => back to binary string
		r1.value = 	binStrRF(binInt(r2.value) + binInt(r3.value));
		
//		3. Check for overflow => 16 bits allowed => convert r1.value to int => should be less than 65535
		if (binInt(r1.value) > 65535) {
			fl.overflow();
		}
		
//		4. Print Call
		print();
		
//		5. PC update
		PC++;	
	}
	
	public static void print() {
		System.out.print(binStrPC(PC) + " ");
		System.out.print(HashMaps.register.get("000").value + " ");
		System.out.print(HashMaps.register.get("001").value + " ");
		System.out.print(HashMaps.register.get("010").value + " ");
		System.out.print(HashMaps.register.get("011").value + " ");
		System.out.print(HashMaps.register.get("100").value + " ");
		System.out.print(HashMaps.register.get("101").value + " ");
		System.out.print(HashMaps.register.get("110").value + " ");
		fl.printFlags();
	}


//	MEM function
	public static String MEM(String PC) {
//		Takes a 8 bit string:- PC as input
//		Return a 16 bit string:- Instruction as output
		
//		find the line number :- Convert PC to int from binary
		int getPC = binInt(PC);
		
//		find corresponding instruction
		for (int i = 0; i < MemList.size(); i++) {
			if (i == getPC) {
//				return the instruction
				return MemList.get(i);
			}
		}
		return ""; 
	}

	
//	Conversions ke Functions
//	Converting from Binary to Integer
	public static int binInt(String val) {
//		0001 => 0 * 2^3 + 0 * 2^2 + 0 * 2^1 + 1 * 2^0
		int Val = Integer.parseInt(val);
		int n = val.length();
		int sum = 0;
		for (int i = 0; i < n; i++) {
			int digit = Val % 10;
			sum = (int)(sum + (digit * Math.pow(2, i)));
			Val = Val / 10;
			}
		return sum;
	}
	
	
//	Convert to Binary from String
//	For Registers:- 16 bits
	public static String binStrRF(int val) {
//		Convert immediate and memory address to binary
//		if (val < 0 || val > 255) {
//			System.out.println("ERROR: Illegal Value... Not in range [0, 255] in line " + linenumber);
//			return "-1";
//		}
		String x = Integer.toBinaryString(val);
		String ans = "";
		if (x.length() < 16) {
//			we need to add zeros
			for (int i = 0; i < 16 - x.length(); i++) {
				ans = ans + "0";
			}
			ans = ans + x;
		}
		else if (x.length() == 16){
			ans = ans + x;
		}
		else {
			ans = ans + x.substring(1);
		}
		return ans;
	}
	
	
//	For PC:- 8 bits
	public static String binStrPC(int val) {
//		Convert immediate and memory address to binary
//		if (val < 0 || val > 255) {
//			System.out.println("ERROR: Illegal Value... Not in range [0, 255] in line " + linenumber);
//			return "-1";
//		}
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
}


class HashMaps {
//	 HashMap for registers
	public static HashMap<String, Register> register = new HashMap<String, Register>();
	
//	 HashMap for opcodes and various properties
	public static HashMap<String, Opcode> opcode = new HashMap<String, Opcode>();
	
//	 Constructor
	public HashMaps() {
		// TODO Auto-generated method stub
		CreateRegisterMap();
		CreateOpcodeMap();
	}
	
//	Function to build the HashMap for registers
	public static void CreateRegisterMap() {
//		R0
		Register R0 = new Register("R0", "000");
		register.put("000", R0);
		
//		R1
		Register R1 = new Register("R1", "001");
		register.put("001", R1);
		
//		R2
		Register R2 = new Register("R2", "010");
		register.put("010", R2);
		
//		R3
		Register R3 = new Register("R3", "011");
		register.put("011", R3);
		
//		R4
		Register R4 = new Register("R4", "100");
		register.put("100", R4);
		
//		R5
		Register R5 = new Register("R5", "101");
		register.put("101", R5);
		
//		R6
		Register R6 = new Register("R6", "110");
		register.put("110", R6);
		
//		Flags
		Register R7 = new Register("FLAGS", "111");
		register.put("111", R7);
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
	String value; //Simulator mei aayega

//	Constructor
	public Register(String register, String address) {
		// TODO Auto-generated constructor stub
		this.register = register;
		this.address = address;
		this.value = "0000000000000000";
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
	
	public boolean ifLessThan() {
//		L check karna hai
//		13 is L
		if (flags[13] == 1) {
			return true;
		}
		return false;
	}
	
	public boolean ifGreaterThan() {
//		G check karna hai
//		14 is G
		if (flags[14] == 1) {
			return true;
		}
		return false;
	}
	
	public boolean ifEqual() {
//		E check karna hai
//		15 is E
		if (flags[15] == 1) {
			return true;
		}
		return false;
	}
	
	public void printFlags() {
		for (int i = 0; i < flags.length; i++) {
			System.out.print(flags[i]);
		}
		System.out.println();
	}
	
	public String FlagsStr() {
		String f = "";
		for (int i = 0; i < flags.length; i++) {
			f += Integer.toString(flags[i]);
		}
		return f;
	}
}
