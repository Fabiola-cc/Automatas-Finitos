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
    private String transition(char q, char a, String[][] d) {
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
    private String final_state(char q, String w, String[][] d) {

    }

    /*
     * q --> state
     * w --> string
     * d --> list of transitions
     * @return secuence of states
     */
    public String derivation(String q, String w, String[][] d) {

    }

    /*
     * q --> state
     * w --> string
     * F --> Acceptance state
     * d --> list of transitions
     */
    public Boolean accepted(String q, String w, String[] F, String[][] d){

    }

}