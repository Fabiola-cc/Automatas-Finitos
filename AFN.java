import java.util.*;

public class AFN {
    String[] states;
    Set<Character> alphabet;
    String initialState;
    Set<String> acceptanceStates;
    Map<String, Map<Character, Set<String>>> transitions;

    public AFN(String[] states, Set<Character> alphabet, String initialState,
               Set<String> acceptanceStates, Map<String, Map<Character, Set<String>>> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.transitions = transitions;
    }
    
    public static class AFD {
        Set<Set<String>> states;
        Set<Character> alphabet;
        Set<String> initialState;
        Set<Set<String>> acceptanceStates;
        Map<Set<String>, Map<Character, Set<String>>> transitions;

        // Constructor
        public AFD(Set<Set<String>> states, Set<Character> alphabet, Set<String> initialState,
                   Set<Set<String>> acceptanceStates, Map<Set<String>, Map<Character, Set<String>>> transitions) {
            this.states = states;
            this.alphabet = alphabet;
            this.initialState = initialState;
            this.acceptanceStates = acceptanceStates;
            this.transitions = transitions;
        }
    }

    public static AFD convertAFNtoAFD(AFN afn) {
        Set<Set<String>> afdStates = new HashSet<>();
        Set<Set<String>> afdAcceptanceStates = new HashSet<>();
        Map<Set<String>, Map<Character, Set<String>>> afdTransitions = new HashMap<>();

        // Inicializar con el estado inicial del AFN
        Set<String> initialState = epsilonClosure(afn, new HashSet<>(Collections.singletonList(afn.initialState)));
        afdStates.add(initialState);
        Queue<Set<String>> queue = new LinkedList<>();
        queue.add(initialState);

        while (!queue.isEmpty()) {
            Set<String> currentState = queue.poll();

            for (char symbol : afn.alphabet) {
                Set<String> nextState = epsilonClosure(afn, move(afn, currentState, symbol));
                if (!nextState.isEmpty()) {
                    if (!afdStates.contains(nextState)) {
                        afdStates.add(nextState);
                        queue.add(nextState);
                    }
                    afdTransitions.computeIfAbsent(currentState, k -> new HashMap<>()).put(symbol, nextState);
                }
            }

            // Verificar si el estado actual es de aceptación
            if (!Collections.disjoint(currentState, afn.acceptanceStates)) {
                afdAcceptanceStates.add(currentState);
            }
        }

        return new AFD(afdStates, afn.alphabet, initialState, afdAcceptanceStates, afdTransitions);
    }

    private static Set<String> epsilonClosure(AFN afn, Set<String> states) {
        Stack<String> stack = new Stack<>();
        Set<String> closure = new HashSet<>(states);
        stack.addAll(states);

        while (!stack.isEmpty()) {
            String state = stack.pop();
            Set<String> epsilonTransitions = afn.transitions.getOrDefault(state, Collections.emptyMap())
                    .getOrDefault('\0', Collections.emptySet());
            for (String nextState : epsilonTransitions) {
                if (closure.add(nextState)) {
                    stack.push(nextState);
                }
            }
        }

        return closure;
    }

    private static Set<String> move(AFN afn, Set<String> states, char symbol) {
        Set<String> result = new HashSet<>();
        for (String state : states) {
            Set<String> destinations = afn.transitions.getOrDefault(state, Collections.emptyMap())
                    .getOrDefault(symbol, Collections.emptySet());
            result.addAll(destinations);
        }
        return result;
    }

     // Método para verificar si un estado existe
     public boolean hasState(String state) {
        return Arrays.asList(states).contains(state);
    }

    // Método para obtener todas las transiciones desde un estado con un símbolo dado
    public Set<String> getTransitions(String state, char symbol) {
        if (!hasState(state) || !alphabet.contains(symbol)) {
            return Collections.emptySet();
        }
        return transitions.getOrDefault(state, Collections.emptyMap())
                          .getOrDefault(symbol, Collections.emptySet());
    }
}