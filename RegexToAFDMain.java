import Glushkov.GlushkovAFN;
import ShuntingYard.ShuntingYardRegex;
import java.util.*;

public class RegexToAFDMain {
    public static void main(String[] args) {
        // Paso 1: Definir la expresión regular
        String regex = "(a|b)*abb";
        System.out.println("Expresión regular: " + regex);

        // Paso 2: Convertir la expresión regular a notación postfix
        String postfix = ShuntingYardRegex.infixToPostfix(regex);
        System.out.println("Expresión en notación postfix: " + postfix);

        // Paso 3: Construir el AFN utilizando el algoritmo de Glushkov
        Object[] afnComponents = GlushkovAFN.regexToDFA(regex);
        
        // Extraer componentes del AFN
        List<Character> states = (List<Character>) afnComponents[0];
        List<Character> alphabet = (List<Character>) afnComponents[1];
        String[][] transitions = (String[][]) afnComponents[2];
        Set<Integer> finalStates = (Set<Integer>) afnComponents[3];

        // Crear el AFN
        AFN afn = createAFN(states, alphabet, transitions, finalStates);
        System.out.println("AFN construido.");
        printAFNInfo(afn);

        // Paso 4: Convertir el AFN a AFD
        AFN.AFD afd = AFN.convertAFNtoAFD(afn);
        
        System.out.println("AFD construido.");

        // Imprimir información sobre el AFD resultante
        printAFDInfo(afd);
    }

    private static AFN createAFN(List<Character> states, List<Character> alphabet, 
        String[][] transitions, Set<Integer> finalStates) {
        String[] afnStates = new String[states.size()];
        Set<Character> afnAlphabet = new HashSet<>(alphabet);
        String initialState = "S";
        Set<String> afnAcceptanceStates = new HashSet<>();
        Map<String, Map<Character, Set<String>>> afnTransitions = new HashMap<>();

        // Convertir estados
        for (int i = 0; i < states.size(); i++) {
            afnStates[i] = String.valueOf(i);
            if (finalStates.contains(i)) {
                afnAcceptanceStates.add(String.valueOf(i));
            }
        }

        // Convertir transiciones
        for (int i = 0; i < transitions.length; i++) {
            for (int j = 0; j < transitions[i].length; j++) {
                if (transitions[i][j] != null && !transitions[i][j].isEmpty()) {
                    String fromState = i == 0 ? "S" : String.valueOf(i - 1);
                    char symbol = alphabet.get(j);
                    String[] toStates = transitions[i][j].split("");

                    afnTransitions.computeIfAbsent(fromState, k -> new HashMap<>())
                                  .computeIfAbsent(symbol, k -> new HashSet<>())
                                  .addAll(Arrays.asList(toStates));
                }
            }
        }

        return new AFN(afnStates, afnAlphabet, initialState, afnAcceptanceStates, afnTransitions);
    }

    private static void printAFDInfo(AFN.AFD afd) {
        System.out.println("\nInformación del AFD resultante:");
        System.out.println("Número de estados: " + afd.states.size());
        System.out.println("Alfabeto: " + afd.alphabet);
        System.out.println("Estado inicial: " + afd.initialState);
        System.out.println("Estados de aceptación: " + afd.acceptanceStates);
        System.out.println("\nTransiciones:");
        for (Map.Entry<Set<String>, Map<Character, Set<String>>> entry : afd.transitions.entrySet()) {
            System.out.println("Desde " + entry.getKey() + ":");
            for (Map.Entry<Character, Set<String>> transition : entry.getValue().entrySet()) {
                System.out.println("  Con '" + transition.getKey() + "' va a " + transition.getValue());
            }
        }
    }

    private static void printAFNInfo(AFN afn) {
        System.out.println("\nInformación del AFN resultante:");
        System.out.println("Número de estados: " + afn.states.length);
        System.out.println("Alfabeto: " + afn.alphabet);
        System.out.println("Estado inicial: " + afn.initialState);
        System.out.println("Estados de aceptación: " + afn.acceptanceStates);
        System.out.println("\nTransiciones:");
        for (String state : afn.states) {
            System.out.println("Desde " + state + ":");
            Map<Character, Set<String>> stateTransitions = afn.transitions.get(state);
            if (stateTransitions != null) {
                for (Map.Entry<Character, Set<String>> transition : stateTransitions.entrySet()) {
                    System.out.println("  Con '" + transition.getKey() + "' va a " + transition.getValue());
                }
            } else {
                System.out.println("  No hay transiciones definidas para este estado.");
            }
        }
    }
}