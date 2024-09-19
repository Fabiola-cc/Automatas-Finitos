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

        Set<Integer> resultD = LanguageD.extractD(regex, 3);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("12");
        expectedF.add("10");
        expectedF.add("01");
        expectedF.add("13");
        expectedF.add("23");
        expectedF.add("20");
        expectedF.add("22");

        Set<String> resultF = LanguageF.extractF(regex, 3);
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

    @Test
    public void testRegex4() {
        String regex1 = "(a|b)*ab";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);
        expectedP.add(2);

        Set<Integer> resultP = LanguageP.extractP(regex1);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(3);

        Set<Integer> resultD = LanguageD.extractD(regex1, 3);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("10");
        expectedF.add("11");
        expectedF.add("12");
        expectedF.add("02");
        expectedF.add("23");

        Set<String> resultF = LanguageF.extractF(regex1, 3);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex5() {// Prueba 2: a*ba*
        String regex2 = "a*ba*";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex2);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(1);
        expectedD.add(2);

        Set<Integer> resultD = LanguageD.extractD(regex2, 2);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("12");
        expectedF.add("22");

        Set<String> resultF = LanguageF.extractF(regex2, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex6() {// Prueba 3: (ab|ba)+
        String regex3 = "(ab|ba)+";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(2);

        Set<Integer> resultP = LanguageP.extractP(regex3);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(1);
        expectedD.add(3);

        Set<Integer> resultD = LanguageD.extractD(regex3, 3);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("01");
        expectedF.add("12");
        expectedF.add("10");
        expectedF.add("23");
        expectedF.add("32");
        expectedF.add("30");

        Set<String> resultF = LanguageF.extractF(regex3, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex7() {// Prueba 4: b*a*
        String regex4 = "b*a*";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex4);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(0);
        expectedD.add(1);

        Set<Integer> resultD = LanguageD.extractD(regex4, 1);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("11");

        Set<String> resultF = LanguageF.extractF(regex4, 1);
        assertEquals(expectedF, resultF);
    }
}
