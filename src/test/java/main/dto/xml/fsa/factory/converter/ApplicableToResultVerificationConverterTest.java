package main.dto.xml.fsa.factory.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicableToResultVerificationConverterTest {

    @Test
    @DisplayName("Test Convert if applicable true")
    public void testConvertIfApplicableTrue(){
        boolean applicable = true;
        int actual = ApplicableToResultVerificationConverter.convert(applicable);
        assertEquals(1,actual);
    }

    @Test
    @DisplayName("Test Convert if applicable false")
    public void testConvertIfApplicableFalse(){
        boolean applicable = false;
        int actual = ApplicableToResultVerificationConverter.convert(applicable);
        assertEquals(2,actual);
    }
}
