package processor.pipeline;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import generic.Instruction;
import generic.Operand;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public static char opposite(char c)
	{
		if(c == '0'){
			return '1';
		}
		return '0';
		// return (c == '0') ? '1' : '0';
	}

	public static String twos_Complement(String binaryString)
	{
		String twos_comp = "";
		String ones_comp = "";

		for (int i = 0; i < binaryString.length(); i++){
			//flip the bits and store it in ones_comp
			ones_comp+= opposite(binaryString.charAt(i));
		}

		StringBuilder builder = new StringBuilder(ones_comp);
		boolean flag = false;

		for (int i = ones_comp.length() - 1; i > 0; i--){
			
			//if the digit is one, then make it 0 and add 1 to next bit
			if (ones_comp.charAt(i) == '1'){
				builder.setCharAt(i, '0');
			}

			//if the bit is 0, simply make it one
			else{
				builder.setCharAt(i, '1');
				flag = true;
				break;
			}
		}

		if (flag == false){
			builder.append("1", 0, 7);
		}

		twos_comp = builder.toString();
		return twos_comp;
	}

	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable()){
			//TODO

			OperationType[] operationType = OperationType.values();

			int inst = IF_OF_Latch.getInstruction();

			String inst_binary = Integer.toBinaryString(inst);

			if(inst_binary.length() != 32){

				int limit = inst_binary.length();
				String lRepeated = "";

				if ((32 - limit) != 0) {
					String s = "0";
					int q = 32 - limit;
					lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
				}
				inst_binary += lRepeated;

			}

			//the first 5 bits will represent opcode so select the substring from instruction
			String opcode_binary = inst_binary.substring(0, 5);

			//convert the binary string to integer
			int opcode_integer = Integer.parseInt(opcode_binary, 2);

			//take operation object from operationType Array list
			OperationType operation = operationType[opcode_integer];

			Instruction i = new Instruction();

			// Operand rs1;
			int reg_no;

			// Operand rs2;
			// Operand rd;
			String cons;
			int cons_val;

			if(operation.toString().equals("add")|| operation.toString().equals("sub")|| operation.toString().equals("mul")|| operation.toString().equals("div")|| operation.toString().equals("and")|| operation.toString().equals("or")|| operation.toString().equals("xor")|| operation.toString().equals("slt")|| operation.toString().equals("sll")|| operation.toString().equals("srl")|| operation.toString().equals("sra")){

				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(5, 10), 2);
				rs1.setValue(reg_no);

				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(10, 15), 2);
				rs2.setValue(reg_no);
				
				//destination register

				Operand rd = new Operand();
				rd.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(15, 20), 2);
				rd.setValue(reg_no);

				i.setOperationType(operationType[opcode_integer]);
				i.setDestinationOperand(rd);
				i.setSourceOperand1(rs1);
				i.setSourceOperand2(rs2);
			}

			else if(operation.toString().equals("end")){
				//terminate the simulator
				i.setOperationType(operationType[opcode_integer]);
			}

			else if(operation.toString().equals("jmp")){
				Operand op = new Operand();
				//select substring from 10th to 32nd bit
				cons = inst_binary.substring(10, 32);
				cons_val = Integer.parseInt(cons, 2);

				if (cons.charAt(0) == '1'){
					//if the MSB is 1 we take two's complement
					cons = twos_Complement(cons);
					//upadte the cons_val because we are now considering the new cons i.e. 2's complement
					cons_val = Integer.parseInt(cons, 2) * -1;
				}

				if (cons_val != 0){
					op.setOperandType(OperandType.Immediate);
					op.setValue(cons_val);
				}
				else{
					reg_no = Integer.parseInt(inst_binary.substring(5, 10), 2);
					op.setOperandType(OperandType.Register);
					op.setValue(reg_no);
				}

				i.setOperationType(operationType[opcode_integer]);
				i.setDestinationOperand(op);
			}

			else if(operation.toString().equals("beq")|| operation.toString().equals("bne")|| operation.toString().equals("blt")|| operation.toString().equals("bgt")){

				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);

				reg_no = Integer.parseInt(inst_binary.substring(5, 10), 2);
				rs1.setValue(reg_no);

				// destination register
				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(10, 15), 2);
				rs2.setValue(reg_no);

				// Immediate value
				Operand rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				cons = inst_binary.substring(15, 32);
				cons_val = Integer.parseInt(cons, 2);
				if (cons.charAt(0) == '1'){
					cons = twos_Complement(cons);
					cons_val = Integer.parseInt(cons, 2) * -1;
				}

				rd.setValue(cons_val);

				i.setOperationType(operationType[opcode_integer]);
				i.setDestinationOperand(rd);
				i.setSourceOperand1(rs1);
				i.setSourceOperand2(rs2);
			}

			else {
				// Source register 1
				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(5, 10), 2);
				rs1.setValue(reg_no);

				// Destination register
				Operand rd = new Operand();
				rd.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(inst_binary.substring(10, 15), 2);
				rd.setValue(reg_no);

				// Immediate values
				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Immediate);
				cons = inst_binary.substring(15, 32);
				cons_val = Integer.parseInt(cons, 2);

				if (cons.charAt(0) == '1'){
					cons = twos_Complement(cons);
					cons_val = Integer.parseInt(cons, 2) * -1;
				}
				rs2.setValue(cons_val);

				i.setOperationType(operationType[opcode_integer]);
				i.setDestinationOperand(rd);
				i.setSourceOperand1(rs1);
				i.setSourceOperand2(rs2);
			}

			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);

		}
	}

}
