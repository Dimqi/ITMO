package model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CheckAreaTest {

    @ParameterizedTest(name = "Проверка CheckArea")
    @CsvSource({
        "-1, -1,  4, true", 
        "-3, -5,  4, false",
        " 0,  0,  4, true",
        " 2,  0,  4, true", 
        " 5,  5,  4, false",
    })
    void testCheckHit(BigDecimal x, BigDecimal y, BigDecimal r, boolean expected) {
        assertEquals(expected, CheckArea.checkHit(x, y, r), "ошибка" + expected);
    }
}