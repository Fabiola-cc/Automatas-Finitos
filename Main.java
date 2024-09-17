
import Glushkov.GlushkovAFN;
import ShuntingYard.ShuntingYardRegex;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Bienvenido");
        System.out.println("Porfavor ingresa la regexp que deseas transformar: ");
        System.out.println("Recuerda que puedes utilizar + * | y (), omite los puntos para concatenaciones");
        String inputRegex = s.nextLine();

        System.out.println("Genial! Ahora porfavor ingresa la cadena que deseas evaluar: ");
        String inputString = s.nextLine();

        // PASO 1 Regex a Postfix
        String postfix = ShuntingYardRegex.infixToPostfix(inputRegex);

        // PASO 2 Regex a AFN
        Object[] initialAFN = GlushkovAFN.regexToDFA(inputRegex);

        //Conversion of states
        List<Character> stateList = (List<Character>) initialAFN[0];
        String[] states = new String[stateList.size()];
        for (int i = 0; i < stateList.size(); i++) {
            states[i] = stateList.get(i).toString();
        }

        // Convertion alphabet
        Set<Character> alphabet = new HashSet<>((List<Character>) initialAFN[1]);

        //Convertion final states
        Set<String> acceptanceStates = new HashSet<>();
        for (Integer finalState : (Set<Integer>) initialAFN[3]) {
            acceptanceStates.add(states[finalState + 1]);
        }

        //Conversion transition
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

        AFN AFN = new AFN(states, alphabet, states[0], acceptanceStates, transitions);

        AFD AFD = AFN.convertAFNtoAFD(AFN);

        


        for (Object object : initialAFN) {
        System.out.println();
        System.out.println(object);
        }

        // String[] states = {"q0", "q1", "q2", "q3", "q4", "q5"};
        // String[] alphabet = {"0", "1"};

        // String initial_state = "q0";

        // String[] acceptance_states = {"q3", "q4", "q5"};

        // String[][] transitions = {
        // {"q0", "0", "q1"},
        // {"q0", "1", "q2"},
        // {"q1", "0", "q0"},
        // {"q1", "1", "q3"},
        // {"q2", "0", "q4"},
        // {"q2", "1", "q5"},
        // {"q3", "0", "q4"},
        // {"q3", "1", "q5"},
        // {"q4", "0", "q4"},
        // {"q4", "1", "q5"},
        // {"q5", "0", "q5"},
        // {"q5", "1", "q5"}
        // };

        // AFD afd = new AFD(states, alphabet, initial_state, acceptance_states,
        // transitions);
        // AFD afdMinimize = afd.minimize();

        // System.out.println("Estados del AFD minimizado: " +
        // Arrays.toString(afdMinimizado.states));
        // System.out.println("Estados de aceptación del AFD minimizado: " +
        // Arrays.toString(afdMinimizado.acceptance_states));
        // System.out.println("Transiciones del AFD minimizado:");
        // for (String[] trans : afdMinimizado.transitions) {
        // if (trans != null) {
        // System.out.println(Arrays.toString(trans));
        // }
        // }

        // SwingUtilities.invokeLater(() -> {
        //     Grafo frame = new Grafo(afdMinimize.getStates(), afdMinimize.getInitialState(), afdMinimize.getAcceptanceStates(), afdMinimize.getTransitions());
        //     frame.setVisible(true);
        // });
    }
}
