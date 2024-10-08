package Glushkov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class LanguageF {

    // Función para obtener el lenguaje F (parejas de números)
    public static Set<String> extractF(String regex, int total_operandos) {
        Set<String> languageF = new HashSet<>();
        Stack<List<Integer>> stack = new Stack<>();
        List<Integer> currentOperands = new ArrayList<>();
        List<Integer> previousOperands = null;
        boolean flag = false; // If recently closed parenthesis
        boolean newparenthesis = false; // If recently opened parenthesis
        int multiple_parenthesis = 0;
        boolean starFlag = false; // If recently used *
        boolean lastoperand = false; // If lastly was an operand
        int operandstack = 0;
        int lateroperandstack = 0;
        int operand = 0;
        List<Integer> orOperands = new ArrayList<>();
        boolean usedOr = false; // Clave para saber si hay un operador |
        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            switch (c) {
                case '(':
                    // Guardar el estado actual de operandos
                    stack.push(new ArrayList<>(currentOperands));
                    if (regex.charAt(i + 2) == '|') {
                        currentOperands.clear();
                    }
                    lastoperand = false;
                    multiple_parenthesis++;
                    newparenthesis = true;
                    break;
                case ')':
                    // Combinar con los operandos dentro de paréntesis
                    previousOperands = stack.pop();
                    combinePairs(languageF, previousOperands, currentOperands);
                    flag = true;
                    lastoperand = false;
                    multiple_parenthesis--;
                    newparenthesis = false;
                    break;
                case '+':
                case '*':
                    newparenthesis = false;
                    // Añadir las transiciones debido a *
                    if (!currentOperands.isEmpty()) {
                        int lastOperand = currentOperands.get(currentOperands.size() - 1);
                        if (usedOr && flag) {
                            if (operandstack > 1) {
                                int firstOperand = orOperands.get(orOperands.size() - 1) - (operandstack - 1);
                                int first_lastOperand = orOperands.get(orOperands.size() - 1);
                                int last_firstoperand = lastOperand - (lateroperandstack - 1);
                                languageF.add(lastOperand + "" + firstOperand);
                                languageF.add(first_lastOperand + "" + firstOperand);
                                languageF.add(first_lastOperand + "" + last_firstoperand);
                                if (lastOperand < total_operandos) {
                                    languageF.add(first_lastOperand + "" + (lastOperand + 1));
                                    languageF.add(lastOperand + "" + (lastOperand + 1));
                                }
                                if (lateroperandstack == 1) {
                                    languageF.add(lastOperand + "" + lastOperand);
                                }
                            }
                            if (operandstack == 1 && lateroperandstack == 1) {
                                int firstOperand = (lastOperand - 1);
                                languageF.add(lastOperand + "" + lastOperand);
                                languageF.add(lastOperand + "" + firstOperand);
                                languageF.add(firstOperand + "" + lastOperand);
                                languageF.add(firstOperand + "" + firstOperand);
                                if (lastOperand < total_operandos) {
                                    languageF.add(firstOperand + "" + (lastOperand + 1));
                                    languageF.add(lastOperand + "" + (lastOperand + 1));
                                }
                            }
                            if (lateroperandstack > 1) {
                                int firstOperand = orOperands.get(orOperands.size() - 1) - (operandstack - 1);
                                int last_firstoperand = lastOperand - (lateroperandstack - 1);
                                languageF.add(lastOperand + "" + last_firstoperand);
                                languageF.add(lastOperand + "" + firstOperand);
                                if (lastOperand < total_operandos) {
                                    languageF.add(firstOperand + "" + (lastOperand + 1));
                                    languageF.add(lastOperand + "" + (lastOperand + 1));
                                }
                                if (operandstack == 1) {
                                    languageF.add(firstOperand + "" + firstOperand);
                                    languageF.add(firstOperand + "" + last_firstoperand);
                                }
                            }
                            usedOr = false;
                            operandstack = 0;
                            lateroperandstack = 0;
                        } else {
                            languageF.add(lastOperand + "" + lastOperand);
                            if (lastOperand < total_operandos) {
                                char idk = regex.charAt(i + 1);
                                boolean next_not_or = idk != '|';
                                if (next_not_or) {
                                    languageF.add(lastOperand + "" + (lastOperand + 1));
                                }
                            }
                        }
                    }
                    starFlag = true;
                    lastoperand = false;
                    break;
                case '|':
                    newparenthesis = false;
                    // Guardar los operandos de un lado del | y vaciar para los siguientes
                    orOperands.addAll(currentOperands);
                    if (!newparenthesis) {
                        operandstack++;
                    }
                    lastoperand = false;
                    usedOr = true;
                    break;
                default:
                    if (Character.isLetterOrDigit(c)) {
                        boolean check_concat = (lastoperand || newparenthesis);
                        if (!currentOperands.isEmpty() && check_concat) {
                            int lastOperand = currentOperands.get(currentOperands.size() - 1);
                            languageF.add(lastOperand + "" + operand);
                        }
                        if (flag || starFlag) {
                            currentOperands.clear();
                            flag = false;
                            starFlag = false;
                        }
                        if (lastoperand) {
                            if (!usedOr && !newparenthesis) {
                                operandstack++;
                            }
                            currentOperands.clear();
                        }
                        if (usedOr) {
                            lateroperandstack++;
                        }
                        currentOperands.add(operand);
                        operand++;
                    }
                    lastoperand = true;
                    newparenthesis = false;
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
