package generic;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import generic.Operand.OperandType;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	public static HashMap<Instruction.OperationType, String> op_code = new HashMap<>();
	public static Set<String> R2I = new HashSet<String>();
	public static Set<String> R3 = new HashSet<String>();
	public static Set<String> conditional_branch = new HashSet<String>();

	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		try {
		//1. open the objectProgramFile in binary mode
		FileOutputStream op_file = new FileOutputStream(objectProgramFile);
		BufferedOutputStream buf_file = new BufferedOutputStream(op_file);
		
		//2. write the firstCodeAddress to the file
		byte[] encodedFirstAdd = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
		buf_file.write(encodedFirstAdd);

		//3. write the data to the file
		for(int datum: ParsedProgram.data){
			byte[] encodedDatum = ByteBuffer.allocate(4).putInt(datum).array();
			buf_file.write(encodedDatum);
		}

		//4. assemble one instruction at a time, and write to the file

		// initialize mapping between operations and their respective op_codes
		op_code.put(Instruction.OperationType.add, "00000");
		op_code.put(Instruction.OperationType.addi, "00001");
		op_code.put(Instruction.OperationType.sub, "00010");
		op_code.put(Instruction.OperationType.subi, "00011");
		op_code.put(Instruction.OperationType.mul, "00100");
		op_code.put(Instruction.OperationType.muli, "00101");
		op_code.put(Instruction.OperationType.div, "00110");
		op_code.put(Instruction.OperationType.divi, "00111");
		op_code.put(Instruction.OperationType.and, "01000");
		op_code.put(Instruction.OperationType.andi, "01001");
		op_code.put(Instruction.OperationType.or, "01010");
		op_code.put(Instruction.OperationType.ori, "01011");
		op_code.put(Instruction.OperationType.xor, "01100");
		op_code.put(Instruction.OperationType.xori, "01101");
		op_code.put(Instruction.OperationType.slt, "01110");
		op_code.put(Instruction.OperationType.slti, "01111");
		op_code.put(Instruction.OperationType.sll, "10000");
		op_code.put(Instruction.OperationType.slli, "10001");
		op_code.put(Instruction.OperationType.srl, "10010");
		op_code.put(Instruction.OperationType.srli, "10011");
		op_code.put(Instruction.OperationType.sra, "10100");
		op_code.put(Instruction.OperationType.srai, "10101");
		op_code.put(Instruction.OperationType.load, "10110");
		op_code.put(Instruction.OperationType.store, "10111");
		op_code.put(Instruction.OperationType.jmp, "11000");
		op_code.put(Instruction.OperationType.beq, "11001");
		op_code.put(Instruction.OperationType.bne, "11010");
		op_code.put(Instruction.OperationType.blt, "11011");
		op_code.put(Instruction.OperationType.bgt, "11100");
		op_code.put(Instruction.OperationType.end, "11101");

		R2I.add("00001");
		R2I.add("00011");
		R2I.add("00101");
		R2I.add("00111");
		R2I.add("01001");
		R2I.add("01011");
		R2I.add("01101");
		R2I.add("01111");
		R2I.add("10001");
		R2I.add("10011");
		R2I.add("10101");
		R2I.add("10110");
		R2I.add("10111");
		R2I.add("11001");
		R2I.add("11010");
		R2I.add("11011");
		R2I.add("11100");

		R3.add("00000");
		R3.add("00010");
		R3.add("00100");
		R3.add("00110");
		R3.add("01000");
		R3.add("01010");
		R3.add("01100");
		R3.add("01110");
		R3.add("10000");
		R3.add("10010");
		R3.add("10100");

		conditional_branch.add("11001");
		conditional_branch.add("11010");
		conditional_branch.add("11011");
		conditional_branch.add("11100");

		/*  
		 * other 2 cases are left, handel them separately :-
		 * -> jmp : "11000"
		 * -> end : "11101"
		 */

		for(Instruction instr: ParsedProgram.code){
			String encoded_instr="";
			
			// writing opcode to binary output
			encoded_instr = encoded_instr.concat(op_code.get(instr.getOperationType()));

			if(R3.contains(encoded_instr)){
				// R3

				encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand1.getValue())).replace(' ', '0'));
				encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand2.getValue())).replace(' ', '0'));
				encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.destinationOperand.getValue())).replace(' ', '0'));
				encoded_instr = encoded_instr.concat("000000000000");
			}
			else if(R2I.contains(encoded_instr)){
				// R2I
				
				if(conditional_branch.contains(encoded_instr)){
					// for conditional branches

					encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand1.getValue())).replace(' ', '0'));
					encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand2.getValue())).replace(' ', '0'));
					if(instr.destinationOperand.operandType == OperandType.Label){
						String new_addr;
						if(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.programCounter >= 0){
							new_addr = String.format("%17s", Integer.toBinaryString(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.programCounter)).replace(' ', '0');
						}
						else{
							new_addr = String.format("%17s", Integer.toBinaryString(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.programCounter)).replace(' ', '0').substring(15, 32);
						}
						encoded_instr = encoded_instr.concat(new_addr);
					}
					else{
						encoded_instr = encoded_instr.concat(String.format("%17s", Integer.toBinaryString(instr.destinationOperand.getValue())).replace(' ', '0'));
					}
				}
				else {
					// for all other types of R2I commands

					if(instr.sourceOperand2.operandType == OperandType.Immediate){
						// number as an input of imm

						encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand1.getValue())).replace(' ', '0'));
						encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.destinationOperand.getValue())).replace(' ', '0'));
						encoded_instr = encoded_instr.concat(String.format("%17s", Integer.toBinaryString(instr.sourceOperand2.getValue())).replace(' ', '0'));
					}
					else if(instr.sourceOperand2.operandType == OperandType.Label){
						//  label as an input of imm
	
						encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.sourceOperand1.getValue())).replace(' ', '0'));
						encoded_instr = encoded_instr.concat(String.format("%5s", Integer.toBinaryString(instr.destinationOperand.getValue())).replace(' ', '0'));
						encoded_instr = encoded_instr.concat(String.format("%17s", Integer.toBinaryString(ParsedProgram.symtab.get(instr.sourceOperand2.getLabelValue()))).replace(' ', '0'));
					}
				}
			}
			else if(encoded_instr.equalsIgnoreCase("11000")){
				// jmp
				encoded_instr = encoded_instr.concat("00000");
				if(instr.destinationOperand.operandType == OperandType.Immediate){
					encoded_instr = encoded_instr.concat(String.format("%22s", Integer.toBinaryString(instr.destinationOperand.getValue())).replace(' ', '0'));
				}
				if(instr.destinationOperand.operandType == OperandType.Label){
					if(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.getProgramCounter() >= 0){
						encoded_instr = encoded_instr.concat(String.format("%22s", Integer.toBinaryString(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.getProgramCounter())).replace(' ', '0'));
					}
					else{
						encoded_instr = encoded_instr.concat(String.format("%22s", Integer.toBinaryString(ParsedProgram.symtab.get(instr.destinationOperand.getLabelValue())-instr.getProgramCounter())).replace(' ', '0').substring(10, 32));
					}
				}
			}
			else if(encoded_instr.equalsIgnoreCase("11101")){
				// end
				encoded_instr = encoded_instr.concat("000000000000000000000000000");
			}
			int output_instr = (int)Long.parseLong(encoded_instr, 2);
			byte[] encodedInstruction = ByteBuffer.allocate(4).putInt(output_instr).array();
			buf_file.write(encodedInstruction);
		}
		
		//5. close the file
		buf_file.close();
		op_file.close();
		} catch (Exception e) {
			System.out.println("Error :-");
			System.out.println(e.toString());
		}
	}

}
