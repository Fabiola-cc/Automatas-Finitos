
public class Main {
    public static void main(String[] args) {
        String[] states = {"q0", "q1", "q2", "q3", "q4", "q5"};
        String[] alphabet = {"0", "1"};

        String initial_state = "q0";

        String[] acceptance_states = {"q3", "q4", "q5"};

        String[][] transitions = {
            {"q0", "0", "q1"},
            {"q0", "1", "q2"},
            {"q1", "0", "q0"},
            {"q1", "1", "q3"},
            {"q2", "0", "q4"},
            {"q2", "1", "q5"},
            {"q3", "0", "q4"},
            {"q3", "1", "q5"},
            {"q4", "0", "q4"},
            {"q4", "1", "q5"},
            {"q5", "0", "q5"},
            {"q5", "1", "q5"}
        };

        AFD afd = new AFD(states, alphabet, initial_state, acceptance_states, transitions);
        // AFD afdMinimizado = afd.minimize();
        afd.minimize();

        // System.out.println("Estados del AFD minimizado: " + Arrays.toString(afdMinimizado.states));
        // System.out.println("Estados de aceptación del AFD minimizado: " + Arrays.toString(afdMinimizado.acceptance_states));
        // System.out.println("Transiciones del AFD minimizado:");
        // for (String[] trans : afdMinimizado.transitions) {
        //     if (trans != null) {
        //         System.out.println(Arrays.toString(trans));
        //     }
        // }
    }
}
