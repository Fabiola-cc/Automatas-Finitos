import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


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
    

    public static AFD convertAFNtoAFD(AFN afn) {
        Set<Set<String>> afdStates = new HashSet<>();
        Set<String> afdAcceptanceStates = new HashSet<>();
        List<String[]> afdTransitions = new ArrayList<>();

        // Inicializar con el estado inicial del AFN
        Set<String> initialState = epsilonClosure(afn, new HashSet<>(Collections.singletonList(afn.initialState)));
        afdStates.add(initialState);
        Queue<Set<String>> queue = new LinkedList<>();
        queue.add(initialState);

        Map<Set<String>, String> stateMapping = new HashMap<>();
        AtomicInteger stateCounter = new AtomicInteger(0);

        while (!queue.isEmpty()) {
            Set<String> currentState = queue.poll();
            String currentStateName = stateMapping.computeIfAbsent(currentState, 
                k -> "q" + stateCounter.getAndIncrement());

            for (char symbol : afn.alphabet) {
                Set<String> nextState = epsilonClosure(afn, move(afn, currentState, symbol));
                if (!nextState.isEmpty()) {
                    String nextStateName = stateMapping.computeIfAbsent(nextState, 
                        k -> "q" + stateCounter.getAndIncrement());
                    if (!afdStates.contains(nextState)) {
                        afdStates.add(nextState);
                        queue.add(nextState);
                    }
                    afdTransitions.add(new String[]{currentStateName, String.valueOf(symbol), nextStateName});
                }
            }

            // Verificar si el estado actual es de aceptación
            if (!Collections.disjoint(currentState, afn.acceptanceStates)) {
                afdAcceptanceStates.add(currentStateName);
            }
        }

        String[] states = stateMapping.values().toArray(new String[0]);
        String[] alphabet = afn.alphabet.stream().map(String::valueOf).toArray(String[]::new);
        String initialStateName = stateMapping.get(initialState);
        String[] acceptanceStates = afdAcceptanceStates.toArray(new String[0]);
        String[][] transitions = afdTransitions.toArray(new String[afdTransitions.size()][]);

        return new AFD(states, alphabet, initialStateName, acceptanceStates, transitions);
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