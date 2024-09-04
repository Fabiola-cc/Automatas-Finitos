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

    

}