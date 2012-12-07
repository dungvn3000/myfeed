package gumi.builders;

import org.junit.Test;

import static java.lang.reflect.Modifier.FINAL;
import static java.lang.reflect.Modifier.PRIVATE;
import static org.junit.Assert.assertEquals;

/**
 * Testing that I don't accidentally break the publicly available API.
 */
public class PublicInterfaceTest {

    private void testPrivateFinal(final String fieldName) throws Exception {
        assertEquals(UrlBuilder.class.getDeclaredField(fieldName).getModifiers(), PRIVATE + FINAL);
    }
    
    @Test
    public void testFieldModifiers() throws Exception {
        testPrivateFinal("inputEncoding");
        testPrivateFinal("outputEncoding");
    }
    
}
