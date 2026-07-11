package com.samar.urlshortener;
import com.samar.urlshortener.service.Base62Encoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class Base62EncoderTest {
    private final Base62Encoder encoder = new Base62Encoder();
    @Test void encodeProducesSevenCharacterCode() { assertEquals(7, encoder.encode(12345L).length()); }
    @Test void encodeAndDecodeAreInverses() { long orig = 987654321L; assertEquals(orig, encoder.decode(encoder.encode(orig))); }
    @Test void zeroEncodesToPaddedCode() { assertEquals("0000000", encoder.encode(0L)); }
}
