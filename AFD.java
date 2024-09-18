import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class AFD {

    String[] states;
    String[] alphabet;
    String initial_state;
    String[] acceptance_states;
    String[][] transitions;

    public AFD(String[] states, String[] alphabet, String initial_state, String[] acceptance_states, String[][] transitions ) {
        this.states = states;
        this.alphabet = alphabet;
        this.initial_state  = initial_state;
        this.acceptance_states = acceptance_states;
        this.transitions = transitions;
    }

    public String[] getStates() {
        return this.states;
    }

    public String[] getAlphabet() {
        return this.alphabet;
    }

    public String getInitialState() {
        return this.initial_state;
    }

    public String[] getAcceptanceStates() {
        return this.acceptance_states;
    }

    public String[][] getTransitions() {
        return this.transitions;
    }



    /*
     * q --> state
     * a --> alphabet element
     * d --> list of transitions
     * @return transition's value
    */
    private String transition(String q, char a, String[][] d) {
        String transited_state = "";
        for (String[] d1 : d) {
            if (d1[0].equals(q) && d1[1].equals(a)) {
                transited_state = d1[2];
            }
        }
        return transited_state;
    }

    /*
     * q --> state
     * w --> string
     * d --> list of transitions
     * @return final state of string
     */
    private String final_state(String q, String w, String[][] d) {
        char value = w.charAt(w.length() - 1);

        return transition(String.valueOf(q), value, d);
    }

    /*
     * q --> state
     * w --> string
     * d --> list of transitions
     * @return secuence of states
     */
    public ArrayList<ArrayList<String>> derivation(String q, String w, String[][] d) {
        String state = q;
        ArrayList<ArrayList<String>> transitions = new ArrayList<>();

        for (int i = 0; i < w.length(); i++) {
            if (w.charAt(i) != ' ') {
                ArrayList<String> actualT = new ArrayList<>();
                actualT.add(state);
                actualT.add(String.valueOf(w.charAt(i)));

                state = transition(state, w.charAt(i), d);
                actualT.add(state);

                transitions.add(actualT);
            }
            
        }

        return transitions;
    }

    /*
     * q --> state
     * w --> string
     * F --> Acceptance state
     * d --> list of transitions
     */
    public Boolean accepted(String q, String w, String[] F, String[][] d){
        return Arrays.asList(F).contains(final_state(q, w, d));
    }

    public AFD minimize() {
        //Convert transitions into HashMap<String, HashMap<String, String>>
        HashMap<String, HashMap<String, String>> transitionMap = new HashMap<>();

        for (String[] transition : transitions) {
            String stateFrom = transition[0];
            String symbol = transition[1];
            String stateTo = transition[2];

            transitionMap.putIfAbsent(symbol, new HashMap<>());

            transitionMap.get(symbol).put(stateFrom, stateTo);
        }
        
        // Converts states and acceptance states into sets
        Set<String> F = new HashSet<>(Arrays.asList(acceptance_states));
        Set<String> Q = new HashSet<>(Arrays.asList(states));
    
        Set<String> P = new HashSet<>();
        P = difference(Q, F);

        Set<String[]> combinations = new HashSet<>();

        //Doing cartesian product
        for (String i: P) {
            for (String j : F) { 
                String[] combination = {i , j};
                combinations.add(combination);
            }
        }

        
        Set<String[]> newCombinations = new HashSet<>();

        Iterator<String[]> iterator = combinations.iterator();
        while (iterator.hasNext()) {
            String[] combination = iterator.next();

            for (String alphabet : transitionMap.keySet()) {
                HashMap<String, String> alphabetTransition = transitionMap.get(alphabet);

                ArrayList<String> firstValues = new ArrayList<>();
                ArrayList<String> secondValues = new ArrayList<>();

                if (alphabetTransition.values().contains(combination[0]) && alphabetTransition.values().contains(combination[1])) {
                    for (String key : alphabetTransition.keySet()) {
                        if (alphabetTransition.get(key).equals(combination[0]) && Arrays.asList(acceptance_states).contains(key) == false) {
                            firstValues.add(key);
                        }
                        if (alphabetTransition.get(key).equals(combination[1]) && Arrays.asList(acceptance_states).contains(key) == false) {
                            secondValues.add(key);
                        }
                    }
                }

                if (firstValues.size() > 0 && secondValues.size() > 0) {
                    for (String i : firstValues) {
                        for (String j : secondValues) {
                            String[] newCombination = {i, j};
                            newCombinations.add(newCombination);
                        }
                    }
                }
            }
        }

        // Add all new combinations to the original set
        combinations.addAll(newCombinations);

        Set<String[]> matrix_combinations = new HashSet<>();

        for (String q1 : states) {
            boolean same_state = true;
            
            for (String q2 : states) {

                if (same_state && q1.equals(q2)) {
                    same_state = false;
                } else if (!same_state && !q1.equals(q2)) {
                    String[] matrix_combination = {q1, q2};
                    matrix_combinations.add(matrix_combination);
                }
            }
        }

        
        Set<List<String>> combinationsList = new HashSet<>();
        Set<List<String>> combinationsMatrixList = new HashSet<>();
        
        for (String[] arr : combinations) {
            combinationsList.add(Arrays.asList(arr));
        }
        for (String[] arr : matrix_combinations) {
            combinationsMatrixList.add(Arrays.asList(arr));
        }
        
        Set<List<String>> states_to_combine = differenceList(combinationsMatrixList, combinationsList);

        Set<String> combinedStates = new HashSet<>();

        for (List<String> q : states_to_combine) {
            String combined = String.join("", q);
            combinedStates.add(combined);
        }

        Set<String> combinedStates2 = new HashSet<>();
        int counter = combinedStates.size();
        while (combinedStates.size() > 1 && counter > 1) {
            Set<String> toRemove = new HashSet<>();
            Set<String> toAdd = new HashSet<>();

            for (String x : combinedStates) {
                String state = x;
                for (String y : combinedStates) {
                    if (!x.equals(y) && (x.contains(y.substring(0, 1)) || x.contains(y.substring(2, 3)))) {
                        state = state + y;
                        toRemove.add(y); // Marcar para eliminar después
                    }
                }
                toAdd.add(state); // Marcar para agregar después
                if (!state.equals(x)) {
                    break; // Salir si se modificó
                }
            }

            // Modificar el conjunto fuera del bucle de iteración
            combinedStates.removeAll(toRemove);
            combinedStates2.addAll(toAdd);

            counter--;
        }

        //Define new states
        ArrayList<String> newStates = new ArrayList<>();
        if (combinedStates2.size() > 0) {
            for (String state1 : combinedStates2) {
                for (String state2 : states) {
                    if (!state1.contains(state2)) {
                        newStates.add(state2);
                    }
                }
                newStates.add(state1);
            }
        } else {
            newStates = new ArrayList<>(Arrays.asList(states));
        }

        //Define new initial state
        String newInitialState = "";
        for (String x : newStates) {
            if (x.contains(initial_state)){
                newInitialState = x;
                break;
            }
        }

        //Define new acceptances states
        ArrayList<String> newAcceptanceStates = new ArrayList<>();
        for (String x : newStates) {
            for (String y : acceptance_states) {
                if (x.contains(y)){
                    newAcceptanceStates.add(x);
                    break;
                }
            }
        }

        //Define transition function
        ArrayList<String[]> newTransitions = new ArrayList<>();
        for (String newState : newStates) {
            int totalAlphabet = alphabet.length;
            for (String[] oldTransition : transitions) {
                if (newState.contains(oldTransition[0])) {
                    for (String realState : combinedStates2) {
                        if (realState.contains(oldTransition[2])) {
                            oldTransition[2] = realState;
                        }
                    }
                    String[] newTransition = {newState, oldTransition[1], oldTransition[2]};
                    newTransitions.add(newTransition);
                    totalAlphabet--;
                    if (totalAlphabet < 1) {
                        break;
                    }
                }
            }
        }

        // Convert ArrayList<String> to String[]
        String[] newStatesArray = newStates.toArray(new String[0]);

        // Convert ArrayList<String> to String[]
        String[] newAcceptanceStatesArray = newAcceptanceStates.toArray(new String[0]);

        // Convert ArrayList<String[]> to String[][]
        String[][] newTransitionsArray = newTransitions.toArray(new String[newTransitions.size()][]);

        return new AFD(newStatesArray, alphabet, newInitialState, newAcceptanceStatesArray, newTransitionsArray);
    }
    

    private Set<String> difference(Set<String> Q, Set<String> F) {
        Set<String> difference = new HashSet<>(Q);
        difference.removeAll(F);
        return difference;
    }

    private Set<List<String>> differenceList(Set<List<String>> Q, Set<List<String>> F) {
        Set<List<String>> normalizedQ = new HashSet<>();
        Set<List<String>> normalizedF = new HashSet<>();
        
        // Normaliza las listas de ambos sets
        for (List<String> qList : Q) {
            List<String> sortedList = new ArrayList<>(qList);
            Collections.sort(sortedList);
            normalizedQ.add(sortedList);
        }
        
        for (List<String> fList : F) {
            List<String> sortedList = new ArrayList<>(fList);
            Collections.sort(sortedList);
            normalizedF.add(sortedList);
        }
        
        // Realiza la diferencia con las listas normalizadas
        normalizedQ.removeAll(normalizedF);
        
        return normalizedQ;
    }
    
}