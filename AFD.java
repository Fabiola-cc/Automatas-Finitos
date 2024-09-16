import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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
        // Converts states and acceptance states into sets
        Set<String> F = new HashSet<>(Arrays.asList(acceptance_states));
        Set<String> Q = new HashSet<>(Arrays.asList(states));
    
        Set<Set<String>> P = new HashSet<>();
        P.add(F); // Adds acceptance states
        P.add(difference(Q, F));
    
        Queue<Set<String>> W = new LinkedList<>(P);
    
        while (!W.isEmpty()) {
            Set<String> A = W.poll();
            for (String a : alphabet) {
                // Find all states that go to state A with actual symbol
                Set<String> X = new HashSet<>();
                for (String q : states) {
                    if (A.contains(transition(q, a.charAt(0), transitions))) {
                        X.add(q);
                    }
                }
    
                // Adjusting partitions
                Set<Set<String>> newP = new HashSet<>();
                for (Set<String> Y : P) {
                    Set<String> intersection = new HashSet<>(Y);
                    intersection.retainAll(X);
                    Set<String> difference = new HashSet<>(Y);
                    difference.removeAll(X);
    
                    if (!intersection.isEmpty() && !difference.isEmpty()) {
                        newP.add(intersection);
                        newP.add(difference);
                        if (W.contains(Y)) {
                            W.remove(Y);
                            W.add(intersection);
                            W.add(difference);
                        } else {
                            W.add(intersection.size() <= difference.size() ? intersection : difference);
                        }
                    } else {
                        newP.add(Y);
                    }
                }
                P = newP;
            }
        }
    
        // Create new minimized AFD
        Map<String, String> representativeState = new HashMap<>();
        int newTotalStates = 0;
    
        for (Set<String> block : P) {
            String representative = block.iterator().next();
            for (String state : block) {
                representativeState.put(state, representative);
            }
            newTotalStates++;
        }
    
        // Create transitions for new AFD
        String[] newStates = new String[newTotalStates];
        String[] newFinalStates = new String[F.size()];
        ArrayList<String[]> transitionList = new ArrayList<>();
    
        int index = 0;
        int finalIndex = 0;
        for (String q : representativeState.keySet()) {
            newStates[index] = representativeState.get(q);
            if (F.contains(q)) {
                newFinalStates[finalIndex++] = representativeState.get(q);
            }
            for (String a : alphabet) {
                String trans = transition(q, a.charAt(0), transitions);
                if (!trans.isEmpty()) {
                    transitionList.add(new String[] { representativeState.get(q), a, representativeState.get(trans) });
                }
            }
            index++;
        }
    
        String[][] newTransitions = new String[transitionList.size()][3];
        transitionList.toArray(newTransitions);
    
        return new AFD(newStates, alphabet, representativeState.get(initial_state), newFinalStates, newTransitions);
    }
    

    private Set<String> difference(Set<String> Q, Set<String> F) {
        Set<String> difference = new HashSet<>(Q);
        difference.removeAll(F);
        return difference;
    }

    public void createGraph(String[] states, String[][] transitions) {

        // mxGraph graph = new mxGraph();
    }

}