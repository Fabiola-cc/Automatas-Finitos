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
        String regex = "(a|b)*ab";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);
        expectedP.add(2);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(3);

        Set<Integer> resultD = LanguageD.extractD(regex, 3);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("10");
        expectedF.add("11");
        expectedF.add("12");
        expectedF.add("02");
        expectedF.add("23");

        Set<String> resultF = LanguageF.extractF(regex, 3);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex5() {// a*ba*
        String regex = "a*ba*";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(1);
        expectedD.add(2);

        Set<Integer> resultD = LanguageD.extractD(regex, 2);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("12");
        expectedF.add("22");

        Set<String> resultF = LanguageF.extractF(regex, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex6() {// (ab|ba)+
        String regex = "(ab|ba)+";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(2);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(1);
        expectedD.add(3);

        Set<Integer> resultD = LanguageD.extractD(regex, 3);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("01");
        expectedF.add("12");
        expectedF.add("10");
        expectedF.add("23");
        expectedF.add("32");
        expectedF.add("30");

        Set<String> resultF = LanguageF.extractF(regex, 2);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex7() {// b*a*
        String regex = "b*a*";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(0);
        expectedD.add(1);

        Set<Integer> resultD = LanguageD.extractD(regex, 1);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("11");

        Set<String> resultF = LanguageF.extractF(regex, 1);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex8() {// (a*|c+)*
        String regex = "(a*|c+)*";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(0);
        expectedD.add(1);

        Set<Integer> resultD = LanguageD.extractD(regex, 1);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("01");
        expectedF.add("11");
        expectedF.add("10");

        Set<String> resultF = LanguageF.extractF(regex, 1);
        assertEquals(expectedF, resultF);
    }

    @Test
    public void testRegex9() {// (a|cb)*bb(cc*|a)
        String regex = "(a|cb)*bb(cc*|a)";
        Set<Integer> expectedP = new HashSet<>();
        expectedP.add(0);
        expectedP.add(1);
        expectedP.add(3);

        Set<Integer> resultP = LanguageP.extractP(regex);
        assertEquals(expectedP, resultP);

        Set<Integer> expectedD = new HashSet<>();
        expectedD.add(7);
        expectedD.add(6);
        expectedD.add(5);

        Set<Integer> resultD = LanguageD.extractD(regex, 7);
        assertEquals(expectedD, resultD);

        Set<String> expectedF = new HashSet<>();
        expectedF.add("00");
        expectedF.add("12");
        expectedF.add("01");
        expectedF.add("03");
        expectedF.add("20");
        expectedF.add("21");
        expectedF.add("23");
        expectedF.add("34");
        expectedF.add("45");
        expectedF.add("56");
        expectedF.add("66");
        expectedF.add("47");

        Set<String> resultF = LanguageF.extractF(regex, 7);
        assertEquals(expectedF, resultF);
    }

}