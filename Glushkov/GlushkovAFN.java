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

    public static Set<Integer> extractP(String regex) {
        Set<Integer> P = new HashSet<>();
        int operandID = 0; // Conocer cuantos 'operandos' se han revisado
        boolean other = false; // Clave para saber si ya se añadió un caracter
        boolean flag = false; // Clave para saber si hay un paréntesis abierto
        for (char c : regex.toCharArray()) {
            switch (c) {
                case '(':
                    flag = true;
                    if (other) {
                        return P;
                    }
                    break;
                case '+':
                    return P;
                case ')':
                    flag = false;
                    break;
                case '*':
                case '|':
                    other = false;
                    break;
                default:
                    if (other) {
                        if (!flag) {
                            return P;
                        }
                        operandID++;
                        break;
                    }
                    other = true;
                    P.add(operandID);
                    operandID++;
                    break;
            }
        }
        return P;
    }

    public static Set<Integer> extractD(String regex, int operands) {
        Set<Integer> D = new HashSet<>();
        int operandID = operands; // Conocer cuales 'operandos' se han revisado

        String reversedRegex = new StringBuilder(regex).reverse().toString();

        boolean next = false; // Clave para saber si ya se añadió un caracter
        boolean flag = false; // Clave para saber si hay un operador *
        boolean parenthesis = false; // Clave para saber si hay un operador ()
        for (char c : reversedRegex.toCharArray()) {
            switch (c) {
                case '(':
                    if (!flag) {
                        next = false; // return D????
                    }
                    flag = false;
                    break;
                case ')':
                    parenthesis = true;
                    break;
                case '|':
                    next = true;
                    break;
                case '+':
                    next = false;
                    break;
                case '*':
                    next = true;
                    flag = true;
                    break;
                default:
                    if (!next) { // Si no hay una operación | o *
                        D.add(operandID);
                        return D; // guarda el dato y termina el ciclo
                    } else if (next) { // Si hay una operación
                        D.add(operandID);
                        operandID--; // guarda el dato y reduce el índice
                        if (flag && !parenthesis) { // si hay * y ()
                            next = true;
                            flag = false;
                        } else if (!flag) { // si no hay más *
                            return D;
                        }
                    }
                    break;
            }
        }
        return D;
    }

    // Función para obtener el lenguaje F (parejas de números)
    public static Set<String> extractF(String regex) {
        Set<String> languageF = new HashSet<>();
        Stack<List<Integer>> stack = new Stack<>();
        List<Integer> currentOperands = new ArrayList<>();
        List<Integer> previousOperands = null;
        boolean flag = false; // If recently closed parenthesis
        boolean lastoperand = false; // If lastly was an operand
        int operand = 0;

        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            switch (c) {
                case '(':
                    // Guardar el estado actual de operandos
                    stack.push(new ArrayList<>(currentOperands));
                    currentOperands.clear();
                    lastoperand = false;
                    break;
                case ')':
                    // Combinar con los operandos dentro de paréntesis
                    previousOperands = stack.pop();
                    combinePairs(languageF, previousOperands, currentOperands);
                    flag = true;
                    lastoperand = false;
                    break;
                case '*':
                    // Añadir las transiciones debido a *
                    if (!currentOperands.isEmpty()) {
                        int lastOperand = currentOperands.get(currentOperands.size() - 1);
                        languageF.add(lastOperand + "" + lastOperand);
                    }
                    lastoperand = false;
                    break;
                case '+':
                    // Añadir las transiciones debido a +
                    if (!currentOperands.isEmpty()) {
                        int lastOperand = currentOperands.get(currentOperands.size() - 1);
                        languageF.add(lastOperand + "" + lastOperand);
                    }
                    lastoperand = false;
                    break;
                case '|':
                    // Guardar los operandos de un lado del | y vaciar para los siguientes
                    stack.push(new ArrayList<>(currentOperands));
                    lastoperand = false;
                    break;
                default:
                    if (Character.isLetterOrDigit(c)) {
                        if (!currentOperands.isEmpty()) {
                            for (int lastOperand : currentOperands) {
                                languageF.add(lastOperand + "" + operand);
                            }
                        }
                        if (flag) {
                            currentOperands.clear();
                            flag = false;
                        }
                        if (lastoperand) {
                            currentOperands.clear();
                        }
                        currentOperands.add(operand);
                        operand++;
                    }
                    lastoperand = true;
                    break;
            }
        }

        // Combinar al final si todavía hay operandos en el stack
        if (!stack.isEmpty()) {
            previousOperands = stack.pop();
            combinePairs(languageF, previousOperands, currentOperands);
        }

        return languageF;
    }

    private static void combinePairs(Set<String> languageF, List<Integer> first, List<Integer> second) {
        for (int f : first) {
            for (int s : second) {
                languageF.add(f + "" + s);
            }
        }
    }

    // Método para construir el AFD desde una expresión regular
    public static Object[] regexToDFA(String regex) {
        // Listado de símbolos
        List<Character> operandos = extractOperands(regex);
        int total_operandos = operandos.size();

        // Construir el árbol sintáctico y calcular P, D, F
        Set<Integer> P = extractP(regex); // índices de operandos iniciales
        Set<Integer> D = extractD(regex, total_operandos - 1); // índices de operandos finales
        Set<String> F = extractF(regex);

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
}
