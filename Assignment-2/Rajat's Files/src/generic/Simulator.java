package generic;

import java.io.*;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.nio.ByteBuffer;
import java.io.FileOutputStream;
import generic.Operand.OperandType;
import java.io.IOException;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulator 
{
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
		try {
			FileOutputStream fos = new FileOutputStream(objectProgramFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			DataOutputStream dos = new DataOutputStream(bos);  

			// Hashtable for instruction set, converting string of instruction to string of numbers
			Hashtable <String, String> inst = new Hashtable<String, String>();
			// Hashtable for register
			Hashtable<Integer, String> reg = new Hashtable<Integer, String>();

			//map instruction strings to binary strings

			inst.put("add", "00000");
			inst.put("sub", "00010");
			inst.put("mul", "00100");
			inst.put("div", "00110");
			inst.put("and", "01000");
			inst.put("or", "01010");
			inst.put("xor", "01100");
			inst.put("slt", "01110");
			inst.put("sll", "10000");
			inst.put("srl", "10010");
			inst.put("sra", "10100");
			inst.put("addi", "00001");
			inst.put("subi", "00011");
			inst.put("muli", "00101");
			inst.put("divi", "00111");
			inst.put("andi", "01001");
			inst.put("ori", "01011");
			inst.put("xori", "01101");
			inst.put("slti", "01111");
			inst.put("slli", "10001");
			inst.put("srli", "10011");
			inst.put("srai", "10101");
			inst.put("blt", "11011");
			inst.put("bgt", "11100");
			inst.put("jmp", "11000");
			inst.put("load", "10110");
			inst.put("store", "10111");
			inst.put("beq", "11001");
			inst.put("bne", "11010");
			

			//mapping register key values to binary strings
			
			reg.put(5, "00101");
			reg.put(6, "00110");
			reg.put(7, "00111");
			reg.put(8, "01000");
			reg.put(9, "01001");
			reg.put(0, "00000");
			reg.put(1, "00001");
			reg.put(2, "00010");
			reg.put(3, "00011");
			reg.put(4, "00100");
			reg.put(10, "01010");
			reg.put(11, "01011");
			reg.put(12, "01100");
			reg.put(13, "01101");
			reg.put(14, "01110");
			reg.put(15, "01111");
			reg.put(16, "10000");
			reg.put(17, "10001");
			reg.put(18, "10010");
			reg.put(19, "10011");
			reg.put(20, "10100");
			reg.put(21, "10101");
			reg.put(22, "10110");
			reg.put(23, "10111");
			reg.put(24, "11000");
			reg.put(25, "11001");
			reg.put(26, "11010");
			reg.put(27, "11011");
			reg.put(28, "11100");
			reg.put(29, "11101");
			reg.put(30, "11110");
			reg.put(31, "11111");

			// need to verify what these piece of code does LOL :))
			for (var value: ParsedProgram.data) {
				//the allocate function takes the capacity as argument
				byte[] dataValue = ByteBuffer.allocate(4).putInt(value).array();
				bos.write(dataValue);
			}
			
			String x = ""; //initialize an empty string 
			// Initialising counter j
			int j=0;
			while(j<ParsedProgram.code.size()){

				int kgp=0;//Counter

				String gen_inst = ParsedProgram.code.get(j).getOperationType().toString(); String genr="";
				
				// Now compare the gen_inst (generate inst) with various inst keys
				if(gen_inst == "jmp"){

					// Concate the 'value' from hashtable for the 'key' which the parsedProgram.code instruction returns
					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					x = x.concat("00000");
					
					// PC is the abbreviation for program counter
					int pc = ParsedProgram.code.get(j).programCounter; //get the current program counter from parsed program files.
					
					int jk = 0;
					
					// Checking Whether the program is functioning correct or not
					if(kgp==10){
						int temp = ParsedProgram.code.size();//Initialising
						temp/=10;
						temp++;
					}

					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Label")){
						jk = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
					}
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Immediate")){
						jk = ParsedProgram.code.get(j).destinationOperand.value;
					}
					int value = jk - pc;

					if (value < 0) {
						String c = Integer.toBinaryString(value);
						c = c.substring(10, 32);
						x = x.concat(c);
					}

					if (value >= 0) {
						String c = Integer.toBinaryString(value);
						int limit = c.length();
						String repeat = "";
						genr.concat("0");
						if ((22 - limit) != 0) {
							String s = "0";
							int q = 22 - limit;
							repeat = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(repeat);
						x = x.concat(c);
					}
				}

				// Code for load and store instructions
				if((gen_inst == "load") || gen_inst ==("store")){
					
					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					
					// If the source is from register.
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString() == "Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					// If the destination is register.
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString() == "Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}


					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString() == "Label") {
						String immediate = ParsedProgram.code.get(j).sourceOperand2.labelValue;
						int im_int = ParsedProgram.symtab.get(immediate);
						String jade="";
						String imm = Integer.toBinaryString(im_int);
						int limit = imm.length();
						String repeat = "";
						if ((17 - limit) != 0) {
							String s = "0";
							int q = 17 - im_int;
							

							repeat = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(repeat);
						x = x.concat(Integer.toBinaryString(im_int));
					}


					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString() == "Immediate") {
						int im_int = ParsedProgram.code.get(j).sourceOperand2.value;
						
						String immediate = Integer.toBinaryString(im_int);
						int limit = immediate.length();
						String lRepeated = "";
						if ((17 - limit) != 0) {
							String s = "0";
							int q = 17 - limit;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(im_int));
					}
				}

				if (gen_inst == "add" || gen_inst == "sub" || gen_inst == "mul" || gen_inst == "div" || gen_inst == "and" || gen_inst=="or" || gen_inst=="xor" || gen_inst=="slt" || gen_inst=="sll" || gen_inst=="srl" || gen_inst=="sra") {

					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					

					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}

					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}

					x = x.concat("000000000000");
				}


				if (gen_inst=="subi" || gen_inst=="addi" || gen_inst=="muli" || gen_inst=="divi" || gen_inst=="andi" || gen_inst.equals("ori") || gen_inst.equals("xori") || gen_inst.equals("slti") || gen_inst.equals("slli") || gen_inst.equals("srli") || gen_inst.equals("srai")) {

					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));

					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}

					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString()=="Immediate") {
						int immediate = ParsedProgram.code.get(j).sourceOperand2.value;
						String imm = Integer.toBinaryString(immediate);
						int limit = imm.length();
						String lRepeated = "";
						if ((17 - limit) != 0) {
							String s = "0";
							int q = 17 - limit;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(immediate));
					}
				}

				if (gen_inst =="beq" || gen_inst=="bgt" || gen_inst=="bne" || gen_inst=="blt") {

					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));


					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString()=="Register") {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}

					int n = 0;

					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString()=="Label"){
						n = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue) - ParsedProgram.code.get(j).programCounter;
					}
					
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString()=="Immediate"){
						n = ParsedProgram.code.get(j).destinationOperand.value - ParsedProgram.code.get(j).programCounter;
					}

					
					if (n >= 0) {

						String n_str = Integer.toBinaryString(n);
						int str_len = 0;
						str_len = str_len + n_str.length();

						String npRepeated = "";
						String noprob = "";

						if ((17 - str_len) != 0) {
							String so = "0";
							int no = 17 - str_len;
							npRepeated = IntStream.range(0, no).mapToObj(i -> so).collect(Collectors.joining(""));
						}
						x = x.concat(npRepeated);

						if(kgp<0){
							kgp++;
							kgp=kgp%10;
						}

						x = x.concat(n_str);

					}

					if (n < 0) {
						String c = Integer.toBinaryString(n);
						//generate a substring c from 15th to 32nd position
						c = c.substring(15, 32);
						x = x.concat(c);
					}
						
				}

				j++; //incrementing the value of j.


				if (gen_inst.equals("end")) {
					x = x.concat("11101000000000000000000000000000");
					String church = "I";
				}
				int inst_map = (int) Long.parseLong(x, 2);
				
				byte[] inst_bitmap = ByteBuffer.allocate(4).putInt(inst_map).array();
				// 
				bos.write(inst_bitmap);
				x="";						
		}

		//2. write the firstCodeAddress to the file
		//3. write the data to the file
		//4. assemble one instruction at a time, and write to the file

		//5. close the file
		bos.close();

	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	
}
}
