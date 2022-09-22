package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Operand.OperandType;
import processor.Processor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		return (c == '0') ? '1' : '0';
	}

	public static String twosComplement(String binary)
	{
		String twos = "", ones = "";
		for (int i = 0; i < binary.length(); i++)
		{
			ones += opposite(binary.charAt(i));
		}

		StringBuilder builder = new StringBuilder(ones);
		boolean flag = false;
		for (int i = ones.length() - 1; i > 0; i--)
		{
			if (ones.charAt(i) == '1')
			{
				builder.setCharAt(i, '0');
			}
			else
			{
				builder.setCharAt(i, '1');
				flag = true;
				break;
			}
		}
		if (!flag)
		{
			builder.append("1", 0, 7);
		}
		twos = builder.toString();
		return twos;
	}

	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			OperationType[] operationType = OperationType.values();
			int inst = IF_OF_Latch.getInstruction();
			String instb = Integer.toBinaryString(inst);
			if(instb.length()!=32){
				int limm = instb.length();
				String lRepeated = "";
				if ((32 - limm) != 0) {
					String s = "0";
					int q = 32 - limm;
					lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
				}
				instb = lRepeated+instb;
			}
			String opcode = instb.substring(0, 5);
			int opcodei = Integer.parseInt(opcode, 2);
			OperationType operation = operationType[opcodei];
			Instruction instn = new Instruction();
			Operand rs1;
			int reg_no;
			Operand rs2;
			Operand rd;
			String cons;
			int cons_val;
			if(operation.toString().equals("add")
					|| operation.toString().equals("sub")
					|| operation.toString().equals("mul")
					|| operation.toString().equals("div")
					|| operation.toString().equals("and")
					|| operation.toString().equals("or")
					|| operation.toString().equals("xor")
					|| operation.toString().equals("slt")
					|| operation.toString().equals("sll")
					|| operation.toString().equals("srl")
					|| operation.toString().equals("sra") ){
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(5, 10), 2);
				rs1.setValue(reg_no);

				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(10, 15), 2);
				rs2.setValue(reg_no);

				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(15, 20), 2);
				rd.setValue(reg_no);

				instn.setOperationType(operationType[opcodei]);
				instn.setSourceOperand1(rs1);
				instn.setSourceOperand2(rs2);
				instn.setDestinationOperand(rd);
			}
			else if(operation.toString().equals("end")){
				instn.setOperationType(operationType[opcodei]);
			}
			else if(operation.toString().equals("jmp")){
				Operand op = new Operand();
				cons = instb.substring(10, 32);
				cons_val = Integer.parseInt(cons, 2);
				if (cons.charAt(0) == '1')
				{
					cons = twosComplement(cons);
					cons_val = Integer.parseInt(cons, 2) * -1;
				}
				if (cons_val != 0)
				{
					op.setOperandType(OperandType.Immediate);
					op.setValue(cons_val);
				}
				else
				{
					reg_no = Integer.parseInt(instb.substring(5, 10), 2);
					op.setOperandType(OperandType.Register);
					op.setValue(reg_no);
				}

				instn.setOperationType(operationType[opcodei]);
				instn.setDestinationOperand(op);
			}
			else if(operation.toString().equals("beq")
					|| operation.toString().equals("bne")
					|| operation.toString().equals("blt")
					|| operation.toString().equals("bgt")){
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(5, 10), 2);
				rs1.setValue(reg_no);

				// destination register
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(10, 15), 2);
				rs2.setValue(reg_no);

				// Immediate value
				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				cons = instb.substring(15, 32);
				cons_val = Integer.parseInt(cons, 2);
				if (cons.charAt(0) == '1')
				{
					cons = twosComplement(cons);
					cons_val = Integer.parseInt(cons, 2) * -1;
				}
				rd.setValue(cons_val);

				instn.setOperationType(operationType[opcodei]);
				instn.setSourceOperand1(rs1);
				instn.setSourceOperand2(rs2);
				instn.setDestinationOperand(rd);
			}
			else {
				// Source register 1
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(5, 10), 2);
				rs1.setValue(reg_no);

				// Destination register
				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				reg_no = Integer.parseInt(instb.substring(10, 15), 2);
				rd.setValue(reg_no);

				// Immediate values
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Immediate);
				cons = instb.substring(15, 32);
				cons_val = Integer.parseInt(cons, 2);
				if (cons.charAt(0) == '1')
				{
					cons = twosComplement(cons);
					cons_val = Integer.parseInt(cons, 2) * -1;
				}
				rs2.setValue(cons_val);

				instn.setOperationType(operationType[opcodei]);
				instn.setSourceOperand1(rs1);
				instn.setSourceOperand2(rs2);
				instn.setDestinationOperand(rd);
			}

			OF_EX_Latch.setInstruction(instn);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
