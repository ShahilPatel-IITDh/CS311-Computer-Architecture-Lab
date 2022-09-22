package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import processor.Processor;

import java.util.Arrays;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX()
	{
		if(OF_EX_Latch.isEX_enable())
		{
			Instruction cmd = OF_EX_Latch.getInstruction();
			//System.out.println("Inst--> "+cmd);
			EX_MA_Latch.setInstruction(cmd);
			OperationType cmd_op = cmd.getOperationType();
			//System.out.println("Operation--> "+cmd_op);
			//System.out.println(Arrays.asList(OperationType.values()));
			int cmd_op_opcode = Arrays.asList(OperationType.values()).indexOf(cmd_op);
			//System.out.println("Operation Value--> "+cmd_op_opcode);
			//System.out.println("");
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			int ALU_output = 0;

			if(cmd_op_opcode < 21 && cmd_op_opcode % 2 == 0)
			{
				int SOP1 = containingProcessor.getRegisterFile().getValue(
						cmd.getSourceOperand1().getValue());
				int SOP2 = containingProcessor.getRegisterFile().getValue(
						cmd.getSourceOperand2().getValue());

				if(cmd_op.toString().equals("add")){
					ALU_output = SOP1 + SOP2;
				}
				else if(cmd_op.toString().equals("sub")){
					ALU_output = SOP1 - SOP2;
				}
				else if(cmd_op.toString().equals("mul")){
					ALU_output = SOP1 * SOP2;
				}
				else if(cmd_op.toString().equals("div")){
					ALU_output = SOP1 / SOP2;
					int r = SOP1 % SOP2;
					containingProcessor.getRegisterFile().setValue(31, r);
				}
				else if(cmd_op.toString().equals("and")){
					ALU_output = SOP1 & SOP2;
				}
				else if(cmd_op.toString().equals("or")){
					ALU_output = SOP1 | SOP2;
				}
				else if(cmd_op.toString().equals("xor")){
					ALU_output = SOP1 ^ SOP2;
				}
				else if(cmd_op.toString().equals("slt")){
					if(SOP1 < SOP2)
						ALU_output = 1;
					else
						ALU_output = 0;
				}
				else if(cmd_op.toString().equals("sll")){
					ALU_output = SOP1 << SOP2;
				}
				else if(cmd_op.toString().equals("srl")){
					ALU_output = SOP1 >>> SOP2;
				}
				else if(cmd_op.toString().equals("sra")){
					ALU_output = SOP1 >> SOP2;
				}
			}
			else if(cmd_op_opcode < 23)
			{
				int con = cmd.getSourceOperand1().getValue();
				int SOP1 = containingProcessor.getRegisterFile().getValue(con);
				int SOP2 = cmd.getSourceOperand2().getValue();

				if(cmd_op.toString().equals("addi")){
					ALU_output = SOP1 + SOP2;
				}
				else if(cmd_op.toString().equals("subi")){
					ALU_output = SOP1 - SOP2;
				}
				else if(cmd_op.toString().equals("muli")){
					ALU_output = SOP1 * SOP2;
				}
				else if(cmd_op.toString().equals("divi")){
					ALU_output = SOP1 / SOP2;
					int r = SOP1 % SOP2;
					containingProcessor.getRegisterFile().setValue(31, r);
				}
				else if(cmd_op.toString().equals("andi")){
					ALU_output = SOP1 & SOP2;
				}
				else if(cmd_op.toString().equals("ori")){
					ALU_output = SOP1 | SOP2;
				}
				else if(cmd_op.toString().equals("xori")){
					ALU_output = SOP1 ^ SOP2;
				}
				else if(cmd_op.toString().equals("slti")){
					if(SOP1 < SOP2)
						ALU_output = 1;
					else
						ALU_output = 0;
				}
				else if(cmd_op.toString().equals("slli")){
					ALU_output = SOP1 << SOP2;
				}
				else if(cmd_op.toString().equals("srli")){
					ALU_output = SOP1 >>> SOP2;
				}
				else if(cmd_op.toString().equals("srai")){
					ALU_output = SOP1 >> SOP2;
				}
				else if(cmd_op.toString().equals("load")){
					ALU_output = SOP1 + SOP2;
				}
			}
			else if(cmd_op_opcode == 23)
			{
				int SOP1 = containingProcessor.getRegisterFile().getValue(
						cmd.getDestinationOperand().getValue());
				int SOP2 = cmd.getSourceOperand2().getValue();
				ALU_output = SOP1 + SOP2;
			}
			else if(cmd_op_opcode == 24)
			{
				OperandType OPERNDTYPE = cmd.getDestinationOperand().getOperandType();
				int immediate = 0;
				if (OPERNDTYPE == OperandType.Register)
				{
					immediate = containingProcessor.getRegisterFile().getValue(
							cmd.getDestinationOperand().getValue());
				}
				else
				{
					immediate = cmd.getDestinationOperand().getValue();
				}
				ALU_output = immediate + currentPC;
				EX_IF_Latch.setIS_enable(true, ALU_output);
			}
			else if(cmd_op_opcode < 29)
			{
				int SOP1 = containingProcessor.getRegisterFile().getValue(
						cmd.getSourceOperand1().getValue());
				int SOP2 = containingProcessor.getRegisterFile().getValue(
						cmd.getSourceOperand2().getValue());
				int immediate = cmd.getDestinationOperand().getValue();

				if(cmd_op.toString().equals("beq")){
					if(SOP1 == SOP2)
					{
						ALU_output = immediate + currentPC;
						EX_IF_Latch.setIS_enable(true, ALU_output);
					}
				}
				else if(cmd_op.toString().equals("bne")){
					if(SOP1 != SOP2)
					{
						ALU_output = immediate + currentPC;
						EX_IF_Latch.setIS_enable(true, ALU_output);
					}
				}
				else if(cmd_op.toString().equals("blt")){
					if(SOP1 < SOP2)
					{
						ALU_output = immediate + currentPC;
						EX_IF_Latch.setIS_enable(true, ALU_output);
					}
				}
				else if(cmd_op.toString().equals("bgt")){
					if(SOP1 > SOP2)
					{
						ALU_output = immediate + currentPC;
						EX_IF_Latch.setIS_enable(true, ALU_output);
					}
				}
			}
			EX_MA_Latch.setALU_result(ALU_output);
		}
		OF_EX_Latch.setEX_enable(false);
		EX_MA_Latch.setMA_enable(true);
	}
}
