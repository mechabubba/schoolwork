import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FourSquareTest {
    @Test
    void testCase1() {
        FourSquare square = new FourSquare("EXAMPLE", "KEYWORD");
        assertEquals(square.encode("HELP ME OBIWAN KENOBI"), "FYGMKYHOBXMFKKKIMD"); // encode
        assertEquals(square.decode("FYGMKYHOBXMFKKKIMD"), "HELPMEOBIWANKENOBI"); // decode
    }

    @Test
    void testCase2() {
        FourSquare square = new FourSquare("LOREM", "IPSUM");
        assertEquals(square.encode("dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"), "MJKGSNDRRFETMHIRRMUUURNPEDTASSDJFPJBSREMMJEESTKHERRKUFTBIUDUDUTKURPJOIHTMMTUHKHTRKOAGUOFFR"); // encode
        assertEquals(square.decode("MJKGSNDRRFETMHIRRMUUURNPEDTASSDJFPJBSREMMJEESTKHERRKUFTBIUDUDUTKURPJOIHTMMTUHKHTRKOAGUOFFR"), "DOLORSITAMETCONSECTETURADIPISCINGELITSEDDOEIUSMODTEMPORINCIDIDUNTUTLABOREETDOLOREMAGNAALIU"); // decode
    }

    @Test
    void testCase3() {
        FourSquare square = new FourSquare("STAR", "TREK");
        assertEquals(square.encode("TO BOLDLY GO WHERE NO ONE HAS GONE BEFORE"), "ULBIKRKWGIXCTURMLMLKCENDKMTASGIU"); // encode
        assertEquals(square.decode("ULBIKRKWGIXCTURMLMLKCENDKMTASGIU"), "TOBOLDLYGOWHERENOONEHASGONEBEFOR"); // decode
    }

    @Test
    void testCase4() {
        FourSquare square = new FourSquare("LORD", "RINGS");
        assertEquals(square.encode("ONE RING TO RULE THEM ALL ONE RING TO FIND THEM ONE RING TO BRING THEM ALL AND IN THE DARKNESS BIND THEM"), "KLOUFKFOIUPLDUGNHNIHKLOUFKFOHEFKDTGNMJMGTBIDUKOOFKFOGNHNIHDFDDKTGNLGNHMGSPDBKGSDRL"); // encode
        assertEquals(square.decode("KLOUFKFOIUPLDUGNHNIHKLOUFKFOHEFKDTGNMJMGTBIDUKOOFKFOGNHNIHDFDDKTGNLGNHMGSPDBKGSDRL"), "ONERINGTORULETHEMALLONERINGTOFINDTHEMONERINGTOBRINGTHEMALLANDINTHEDARKNESSBINDTHEM"); // decode
    }

    @Test
    void testCase5() {
        FourSquare square = new FourSquare("HARRY", "POTTER");
        assertEquals(square.encode("Yerr a wizard Harry"), "ZEOMAVGYALRDALSW"); // encode
        assertEquals(square.decode("ZEOMAVGYALRDALSW"), "YERRAWIZARDHARRY"); // decode
    }

    @Test
    void testChangeKeyword() {
        FourSquare square = new FourSquare("HARRY", "POTTER");
        assertEquals(square.encode("Yerr a wizard Harry"), "ZEOMAVGYALRDALSW"); // encode
        assertEquals(square.decode("ZEOMAVGYALRDALSW"), "YERRAWIZARDHARRY"); // decode
        square.setKeyword1("STAR");
        square.setKeyword2("TREK");
        assertEquals(square.encode("TO BOLDLY GO WHERE NO ONE HAS GONE BEFORE"), "ULBIKRKWGIXCTURMLMLKCENDKMTASGIU"); // encode
        assertEquals(square.decode("ULBIKRKWGIXCTURMLMLKCENDKMTASGIU"), "TOBOLDLYGOWHERENOONEHASGONEBEFOR"); // decode
    }
}
