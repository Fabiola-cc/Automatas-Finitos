package Glushkov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class LanguageF {

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
}
