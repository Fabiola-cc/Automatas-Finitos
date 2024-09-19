package Testing;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;

import Glushkov.*;

public class AFNTest {

    @Test
    public void testRegex1() {
        String regex = "(a|bb)+";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(0);
        expectedD.add(2);

        Set<Integer> resultD = LanguageD.extractD(regex, 2);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("01");
        expectedF.add("12");
        expectedF.add("21");
        expectedF.add("20");
        expectedF.add("00");

        Set<String> resultF = LanguageF.extractF(regex, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex2() {
        String regex = "(ab|c)*d";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(2);
        expectedP.add(3);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(3);

        Set<Integer> resultD = LanguageD.extractD(regex, 2);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("12");
        expectedF.add("10");
        expectedF.add("01");
        expectedF.add("13");
        expectedF.add("23");
        expectedF.add("20");
        expectedF.add("22");

        Set<String> resultF = LanguageF.extractF(regex, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex3() {
        String regex = "1(1|0)";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(1);
        expectedD.add(2);

        Set<Integer> resultD = LanguageD.extractD(regex, 2);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("01");
        expectedF.add("02");

        Set<String> resultF = LanguageF.extractF(regex, 2);
        assertEquals(expectedF, resultF);
    }

}
