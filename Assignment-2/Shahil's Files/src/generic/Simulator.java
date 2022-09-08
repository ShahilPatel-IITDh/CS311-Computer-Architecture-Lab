package generic;

import java.io.*;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.nio.ByteBuffer;

import generic.Operand.OperandType;
import java.util.stream.IntStream;

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
						if ((22 - limit) != 0) {
							String s = "0";
							int q = 22 - limit;
							repeat = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(repeat);
						x = x.concat(c);
					}
				}

				//code for load and store instructions
				if(gen_inst.equals("load") || gen_inst.equals("store")){
					
					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					
					//if the source is from register.
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					//if the destination is register.
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}


					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Label")) {
						String immediate = ParsedProgram.code.get(j).sourceOperand2.labelValue;
						int im_int = ParsedProgram.symtab.get(immediate);
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


					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Immediate")) {
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

				if (gen_inst.equals("add") || gen_inst.equals("sub") || gen_inst.equals("mul") || gen_inst.equals("div") || gen_inst.equals("and") || gen_inst.equals("or") || gen_inst.equals("xor") || gen_inst.equals("slt") || gen_inst.equals("sll") || gen_inst.equals("srl") || gen_inst.equals("sra")) {

					x = x.concat(inst.get(ParsedProgram.code.get(j).operationType.toString()));
					

					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}

					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}

					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}

					x = x.concat("000000000000");
				}

				if (gen_inst.equals("subi") || gen_inst.equals("addi") || gen_inst.equals("muli") || gen_inst.equals("divi") || gen_inst.equals("andi") || gen_inst.equals("ori") || gen_inst.equals("xori") || gen_inst.equals("slti") || gen_inst.equals("slli") || gen_inst.equals("srli") || gen_inst.equals("srai")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Immediate")) {
						int immi = ParsedProgram.code.get(j).sourceOperand2.value;
						String imm = Integer.toBinaryString(immi);
						int limm = imm.length();
						String lRepeated = "";
						if ((17 - limm) != 0) {
							String s = "0";
							int q = 17 - limm;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(immi));

					}
				}
				if (op.equals("beq") || op.equals("bgt") || op.equals("bne") || op.equals("blt")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}
					int n = 0;
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Label")){
						n = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue) - ParsedProgram.code.get(j).programCounter;
					}
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Immediate")){
						n = ParsedProgram.code.get(j).destinationOperand.value - ParsedProgram.code.get(j).programCounter;
					}
					//n = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue) - ParsedProgram.code.get(j).programCounter;
					if (n >= 0) {
						String np = Integer.toBinaryString(n);
						int npl = np.length();
						String npRepeated = "";
						if ((17 - npl) != 0) {
							String so = "0";
							int no = 17 - npl;
							npRepeated = IntStream.range(0, no).mapToObj(i -> so).collect(Collectors.joining(""));
						}
						x = x.concat(npRepeated);
						x = x.concat(np);
					}
					if (n < 0) {
						String c = Integer.toBinaryString(n);
						c = c.substring(15, 32);
						x = x.concat(c);
					}

				}
				if (op.equals("end")) {
					x = x.concat("11101000000000000000000000000000");
				}
				int inst_intmap = (int) Long.parseLong(x, 2);
				//System.out.println(inst_intmap);
				byte[] inst_bitmap = ByteBuffer.allocate(4).putInt(inst_intmap).array();
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
