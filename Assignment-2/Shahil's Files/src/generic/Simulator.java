package generic;

import java.io.*;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.nio.ByteBuffer;

import generic.Operand.OperandType;


public class Simulator {
		
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
			inst.put("load", "10110");
			inst.put("store", "10111");
			inst.put("beq", "11001");
			inst.put("bne", "11010");
			inst.put("blt", "11011");
			inst.put("bgt", "11100");
			inst.put("jmp", "11000");


			//mapping register key values to binary strings
			reg.put(0, "00000");
			reg.put(1, "00001");
			reg.put(2, "00010");
			reg.put(3, "00011");
			reg.put(4, "00100");
			reg.put(5, "00101");
			reg.put(6, "00110");
			reg.put(7, "00111");
			reg.put(8, "01000");
			reg.put(9, "01001");
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
			for (int i=0; i<ParsedProgram.data.size(); i++) {
				//the allocate function takes the capacity as argument
				byte[] dataUnit = ByteBuffer.allocate(4).putInt(ParsedProgram.data.get(i)).array();
				bos.write(dataUnit);
			}
			
			String x = ""; //initialize an empty string 

			for(int j=0; j<ParsedProgram.code.size(); j++){
				String gen_inst = ParsedProgram.code.get(j).getOperationType().toString();
				
				//now compare the gen_inst (generate inst) with various inst keys
				if(gen_inst.equals("jmp")){
					//concate the 'value' from hashtable for the 'key' which the parsedProgram.code instruction returns
					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					x = x.concat("00000");
					
					//pc is the abbreviation for program counter
					int pc = ParsedProgram.code.get(j).programCounter; //get the current program counter from parsed program files.
					
					int jk = 0;
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Label")){
						jk = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
					}
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Immediate")){
						jk = ParsedProgram.code.get(j).destinationOperand.value;
					}
					int val = jk - pc;
					if (val < 0) {
						String c = Integer.toBinaryString(val);
						c = c.substring(10, 32);
						x = x.concat(c);
					}
					if (val >= 0) {
						String c = Integer.toBinaryString(val);
						int limit = c.length();
						String lRepeated = "";
						if ((22 - limit) != 0) {
							String s = "0";
							int q = 22 - limit;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(c);
					}
				}
			}

		}
		//2. write the firstCodeAddress to the file
		//3. write the data to the file
		//4. assemble one instruction at a time, and write to the file
		//5. close the file
	}
	
}
