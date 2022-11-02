    .data
a:
    20
b: 
    25
c:
    10
    .text

main:
    load %x0, $a %x3
    load %x0, $b %x4
    load %x0, $c %x5
    add %x0, %x0 %x10
    add %x0, %x0 %x6
    blt %x3, %x4 lowa
    jmp lowb

lowa:
    add %x3, %x0, %x6
    jmp continue

lowb:
    add %x4, %x0, %x6
    jmp continue

continue:
    blt %x6, %x5 endf
    add %x5, %x0, %x6
    jmp endf

endf:
    add %x6, %x0, %x10
    end



