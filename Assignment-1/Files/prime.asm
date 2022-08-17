	.data
a:
	13
	.text
main:
	load %x0, $a, %x3
	addi %x0, 1, %x4
	beq %x3, %x4, endl
	blt %x3, %x4, endl
	addi %x4, 1, %x4
loop:
	beq %x4, %x3, prime
	add %x0, %x3, %x5
	div %x5, %x4, %x5
	mul %x5, %x4, %x5
	sub %x5, %x3, %x6
	beq %x6, %x0, done
	addi %x4, 1, %x4
	subi %x5, %x5, %x5
	jmp loop
prime:
	addi %x0, 1, %x10
	end
done:
	subi %x0, 1, %x10
	end
endl:
	subi %x0, 1, %x10
	end