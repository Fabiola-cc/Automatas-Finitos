import Automatas.AFD;
import Automatas.AFN;
import Glushkov.GlushkovAFN;
import ShutingYard.ShuntingYardRegex;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RegexTester {

    public static void runAllTests() {
        runTest("Simple Regex test 1", "(a|b)*ab",
                Arrays.asList("ab", "aab", "baab", "bab", "aaab"),
                Arrays.asList("a", "b", "aa", "abb", "baba"));

        runTest("Simple Regex test 2", "a*ba*",
                Arrays.asList("b", "ab", "aab", "baa", "aaaabaaaa"),
                Arrays.asList("a", "bb", "bbaa", "baab", "abab"));

        runTest("Simple Regex test 3", "(ab|ba)+",
                Arrays.asList("ab", "ba", "abab", "baba", "ababab"),
                Arrays.asList("a", "b", "aa", "bb", "aabb"));

        runTest("Simple Regex test 4", "b*a*",
                Arrays.asList("a", "b", "aa", "bb", "ba"),
                Arrays.asList("ab", "baab", "abab", "aabbb"));

        runTest("Simple Regex test 5", "(a|bb)+",
                Arrays.asList("a", "bb", "bba", "abba"),
                Arrays.asList("b", "aaa", "bbbb", "baa", "babab"));

        runTest("Medium Regex test 1", "if\\((a|x|t)+\\)\\{y\\}(else\\{n\\})?",
                Arrays.asList("if(a){y}else{n}", "if(atx){y}"),
                Arrays.asList("if(){y}else{n}", "if(a){y}else{", "if(t){a}"));

        runTest("Medium Regex test 2", "\"?(w)+\"?((\\.|;|,|\\*|\\|)*)?",
                Arrays.asList("\"wwwww\"", "\"ww\";.,|*"),
                Arrays.asList(".", "w\".\""));
    }

    private static void runTest(String testName, String regex, List<String> acceptTests, List<String> rejectTests) {
        System.out.println(testName);
        System.out.println("Regex: " + regex);
        
        for (String test : acceptTests) {
            testString(regex, test, true);
        }
        for (String test : rejectTests) {
            testString(regex, test, false);
        }
        System.out.println();
    }

    private static void testString(String regex, String test, boolean expectedAccept) {
        // PASO 1 Regex a Postfix
        String postfix = ShuntingYardRegex.infixToPostfix(regex);

        // PASO 2 Regex a AFN
        Object[] initialAFN = GlushkovAFN.regexToDFA(regex);

        // Conversion of states
        List<Character> stateList = (List<Character>) initialAFN[0];
        String[] states = new String[stateList.size()];
        for (int i = 0; i < stateList.size(); i++) {
            states[i] = stateList.get(i).toString();
        }

        // Conversion alphabet
        Set<Character> alphabet = new HashSet<>((List<Character>) initialAFN[1]);

        // Conversion final states
        Set<String> acceptanceStates = new HashSet<>();
        for (Integer finalState : (Set<Integer>) initialAFN[3]) {
            acceptanceStates.add(states[finalState + 1]);
        }

        // Conversion transition
        String[][] transitionMatrix = (String[][]) initialAFN[2];
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();

        for (int i = 0; i < transitionMatrix.length; i++) {
            String state = states[i];
            transitions.putIfAbsent(state, new HashMap<>());
            
            for (int j = 0; j < transitionMatrix[i].length; j++) {
                if (transitionMatrix[i][j] != null) {
                    char input = alphabet.toArray(new Character[0])[j];
                    
                    Set<String> destinationStates = new HashSet<>(Arrays.asList(transitionMatrix[i][j].split("")));
                    transitions.get(state).put(input, destinationStates);
                }
            }
        }

        AFN afn = new AFN(states, alphabet, states[0], acceptanceStates, transitions);
        AFD afd = AFN.convertAFNtoAFD(afn);
        AFD afdMinimize = afd.minimize();

        boolean accepted = afdMinimize.accepted(afdMinimize.getInitialState(), test, afdMinimize.getAcceptanceStates(), afdMinimize.getTransitions());
        
        System.out.println("Test string: " + test);
        System.out.println("Expected: " + (expectedAccept ? "Accept" : "Reject"));
        System.out.println("Result: " + (accepted ? "Accept" : "Reject"));
        System.out.println("Test " + (accepted == expectedAccept ? "PASSED" : "FAILED"));
        System.out.println();
    }

    public static void main(String[] args) {
        runAllTests();
    }
}