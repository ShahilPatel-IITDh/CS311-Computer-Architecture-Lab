.data
a:
	2
	26
	29
	20
	10
	158
	50
	60
	89
n:
	9
	.text
main:
	load %x0, $n, %x3
	add %x0, %x0, %x4
	add %x0, %x0, %x5
check:
	sub %x5, %x5, %x5
	sub %x3, %x4, %x6
	subi %x6, 1, %x6
	beq %x4, %x3, terminate
	addi %x4, 1, %x4
	jmp loop
loop:
	beq %x5, %x6, check
	load %x5, $a, %x7
	addi %x5, 1, %9
	load %x9, $a, %x8
	blt %x7, %x8, interchange
	jmp lin
interchange:
	add %x0, %x8, %x10
	sub %x8, %x8, %x8
	add %x0, %x7, %x8
	sub %x7, %x7, %x7
	add %x0, %x10, %x7
	store %x7, $a, %x5
	store %x8, $a, %x9
	jmp lin
lin:
	addi %x5, 1, %x5
	sub %x7, %x7, %x7
	sub %x8, %x8, %x8
	sub %x9, %x9, %x9
	sub %x10, %x10, %x10
	jmp loop
terminate:
	end