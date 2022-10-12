	.data
a:
	10 
    .text
main:
    add %x0, %x0, %x10
    load %x0, $a, %x4
    addi %x10, 2, %x5
    subi %x5, 1, %x6
    subi %x6, 1, %x7
    div %x4, %x5, %x8
    beq %x31, %x6, Odd
    beq %x31, %x7, Even
    end
Odd:
    add %x31, %x0, %x10
    end
Even:
    subi %x31, 1, %x10
    end
