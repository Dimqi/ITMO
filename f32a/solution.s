.data
input_addr:  .word 0x80
output_addr: .word 0x84

.text
_start:
    @p input_addr a! @
    check_n
    @p output_addr a! !
    halt

check_n:
    dup inv lit 1 + -if not_positive_n
    sum_n ;

not_positive_n:
    drop lit -1
    finish_counting ;

sum_n:
    dup lit 1 +
    dup lit 1 and
    if div_2
    over
div_2:
    2/
multiply:
    a!
    lit 0
    lit 31 >r
multiply_do:
    +*  
    next multiply_do

check_result:
    over drop
    inv a dup a! 
    lit 0x80000000 and 
    inv and inv
    a over
    if finish_counting

overflow:
    drop    
    lit 0xCCCCCCCC

finish_counting:
    ;



    
