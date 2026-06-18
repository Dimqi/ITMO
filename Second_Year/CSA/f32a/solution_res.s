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
    lit 1 over
    sum_n ;

not_positive_n:
    lit -1 over
    finish_counting ;

sum_n:
    dup a!
    +
    a lit -1 +
    
    dup if finish_counting
    sum_n ;
    ;

finish_counting:
    drop
    ;

