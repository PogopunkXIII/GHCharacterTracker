package ghcharactertracker.com.ghct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CharClassUnitTest {
    CharClass testChar;
    int[] expVals = {0, 45, 95, 150, 210, 275, 345, 420, 500};
    int[] fastHealthVals = {0, 10, 12, 14, 16, 18, 20, 22, 24, 26};

    @Before
    public void setup() {
        testChar = new CharClass(ClassName.Brute);
    }

    @Test
    public void BruteTest() {
        assertEquals(testChar.getClassName(), ClassName.Brute);

        int[] bruteHealthValues = CharClass.getHealthValues(testChar.getClassName());

        //for(int i = 0; i < bruteHealthValues.length; i++) {
        //    checkHealthValues(bruteHealthValues[i], fastHealthVals[i]);
        //}
    }
}