
import java.util.ArrayList;
import java.util.Arrays;

public class AFD {

    String[] states;
    String[] alphabet;
    String intial_state;
    String[] acceptance_states;
    String[][] transitions;

    public AFD(String[] states, String[] alphabet, String intial_state, String[] acceptance_states, String[][] transitions ) {
        this.states = states;
        this.alphabet = alphabet;
        this.intial_state  = intial_state;
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

}