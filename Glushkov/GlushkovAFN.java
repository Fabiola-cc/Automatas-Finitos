package Glushkov;

import java.util.*;

public class GlushkovAFN {

    public static List<Character> extractOperands(String regex) {
        List<Character> operands = new ArrayList<>();

        for (char c : regex.toCharArray()) {
            // Verifica que el carácter no sea un operador o paréntesis
            if (c != '*' && c != '.' && c != '|' && c != '+' && c != '(' && c != ')') {
                operands.add(c); // Añadir al ArrayList
            }
        }

        return operands;
    }

    // Método para construir el AFD desde una expresión regular
    public static Object[] regexToDFA(String regex) {
        // Listado de símbolos
        List<Character> operandos = extractOperands(regex);
        int total_operandos = operandos.size();

        // Construir el árbol sintáctico y calcular P, D, F
        Set<Integer> P = LanguageP.extractP(regex); // índices de operandos iniciales
        Set<Integer> D = LanguageD.extractD(regex, total_operandos - 1); // índices de operandos finales
        Set<String> F = LanguageF.extractF(regex);

        // Listado de estados
        List<Character> states = new ArrayList<>();
        states.add('s');
        for (int i = 0; i < total_operandos; i++) {
            states.add((char) ('0' + i));
        }

        // Listado de entradas
        Set<Character> inputs = new HashSet<>(operandos);
        List<Character> inputs_array = new ArrayList<>(inputs);

        // Matriz de transiciones
        String[][] transitions = new String[states.size()][inputs.size()];
        // Transiciones iniciales
        for (Integer state : P) {
            char input = operandos.get(state);
            int input_position = inputs_array.indexOf(input);
            if (transitions[0][input_position] == null) {
                transitions[0][input_position] = ""; // Inicializa con cadena vacía si es null
            }
            transitions[0][input_position] += state.toString();
        }

        // Transiciones intermedias
        for (String pair : F) {
            int position = Character.getNumericValue(pair.charAt(0));
            Integer state = Character.getNumericValue(pair.charAt(1));
            char input = operandos.get(state);
            int input_position = inputs_array.indexOf(input);
            if (transitions[position + 1][input_position] == null) {
                transitions[position + 1][input_position] = ""; // Inicializa con cadena vacía si es null
            }
            transitions[position + 1][input_position] += state.toString();
        }

        // Impresión de matriz
        System.out.print("state\t");
        for (char input : inputs_array) {
            System.out.print(input + "\t");
        }

        int actualState = 0;
        System.out.print("\nS\t");
        for (String string : transitions[0]) {
            System.out.print(string + "\t");
        }

        for (int i = 1; i < transitions.length; i++) {
            System.out.print("\n" + actualState + "\t");
            for (String string : transitions[i]) {
                System.out.print(string + "\t");
            }
            actualState++;
        }

        System.out.println("\n\nAll states:\t" + states);
        System.out.println("final states:\t" + D);
        System.out.println("inputs:\t" + inputs_array);

        // RETORNAR estados, entradas, matriz de transiciones, estados finales
        return new Object[] { states, inputs_array, transitions, D };
    }

    public static void main(String[] args) {
        String inputRegex = "(1(1|0))";
        regexToDFA(inputRegex);
    }
}
