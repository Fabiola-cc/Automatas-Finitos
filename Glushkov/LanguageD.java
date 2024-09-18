package Glushkov;

import java.util.HashSet;
import java.util.Set;

public class LanguageD {
    public static Set<Integer> extractD(String regex, int operands) {
        Set<Integer> D = new HashSet<>();
        int operandID = operands; // Conocer cuales 'operandos' se han revisado

        String reversedRegex = new StringBuilder(regex).reverse().toString();

        boolean expectOperand = false; // Clave para saber si ya se añadió un caracter
        boolean starFlag = false; // Clave para saber si hay un operador *
        boolean parenthesis = false; // Clave para saber si hay un operador ()
        boolean usedOr = false; // Clave para saber si hay un operador |
        boolean shouldClose = false; // no hay razones notorias para continuar el ciclo
        for (char c : reversedRegex.toCharArray()) {
            switch (c) {
                case '(':
                    if (!starFlag) {
                        expectOperand = false;
                        shouldClose = true;
                    }
                    starFlag = false;
                    break;
                case ')':
                    parenthesis = true;
                    expectOperand = true;
                    shouldClose = false;
                    break;
                case '|':
                    expectOperand = true;
                    usedOr = true;
                    shouldClose = false;
                    break;
                case '+':
                    expectOperand = false;
                    shouldClose = false;
                    break;
                case '*':
                    if (!starFlag && !expectOperand && shouldClose) {
                        break;
                    }
                    expectOperand = true;
                    starFlag = true;
                    shouldClose = false;
                    break;
                default:
                    if (shouldClose) {
                        return D;
                    }
                    if (!expectOperand) { // para un caracter simple
                        D.add(operandID);
                        shouldClose = true;
                    } else if (expectOperand) { // Si hay una operación
                        D.add(operandID);
                        expectOperand = false;
                        if (!starFlag && !usedOr && !expectOperand) { // no hay *, ni | y acaba de usarse un operando
                            shouldClose = true;
                        }
                        if (starFlag && !parenthesis) { // si hay * y no ()
                            expectOperand = false;
                            starFlag = false;
                        }
                        if (usedOr && !starFlag) { // ya se aplicó | y *
                            usedOr = false;
                            shouldClose = true;
                        }
                    }
                    operandID--; // reduce el índice
                    break;
            }
        }
        return D;
    }

}
