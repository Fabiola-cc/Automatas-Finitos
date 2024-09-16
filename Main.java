
import javax.swing.SwingUtilities;

import Glushkov.GlushkovAFN;
import ShuntingYard.ShuntingYardRegex;

//Java program to read input using DataInputStream class
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Bienvenido");
        System.out.println("Porfavor ingresa la regexp que deseas transformar: ");
        System.out.println("Recuerda que puedes utilizar + * | y (), omite los puntos para concatenaciones");
        String inputRegex = s.nextLine();

        System.out.println("Genial! Ahora porfavor ingresa la cadena que deseas evaluar: ");
        String inputString = s.nextLine();

        // PASO 1 Regex a Postfic
        String postfix = ShuntingYardRegex.infixToPostfix(inputRegex);

        // PASO 2 Regex a AFN
        Object[] AFN = GlushkovAFN.regexToDFA(inputRegex);
        // for (Object object : AFN) {
        // System.out.println();
        // System.out.println(object);
        // }

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
        // AFD afdMinimizado = afd.minimize();

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

        SwingUtilities.invokeLater(() -> {
            Grafo frame = new Grafo();
            frame.setVisible(true);
        });
    }
}
