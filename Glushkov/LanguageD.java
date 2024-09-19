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
        boolean waitOR = false;
        int regexPos = 0;
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
                    if (!starFlag && !expectOperand && shouldClose) {
                        break;
                    }
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
                    expectOperand = true;
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
                        if (!waitOR) {
                            return D;
                        }
                        operandID--;
                        break;
                    }
                    if (!expectOperand) { // para un caracter simple
                        D.add(operandID);
                        if (isNextOperatorOr(reversedRegex, regexPos)) {
                            waitOR = true;
                            operandID--; // reduce el índice
                            break;
                        }
                        shouldClose = true;
                    } else if (expectOperand) { // Si hay una operación
                        D.add(operandID);
                        waitOR = false;
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
                        if (isNextOperatorOr(reversedRegex, regexPos)) {
                            waitOR = true;
                        }
                    }
                    operandID--; // reduce el índice
                    break;
            }
            regexPos++;
        }
        return D;
    }

    private static boolean isNextOperatorOr(String regex, int startIndex) {
        // Recorre desde el índice dado hasta el final de la cadena
        for (int i = startIndex; i < regex.length(); i++) {
            char currentChar = regex.charAt(i);

            // Si encuentras un operador |, devuelve true
            if (currentChar == '|') {
                return true;
            }

            // Si encuentras un carácter que no es operando (letras o números), termina la
            // búsqueda
            if (!Character.isLetterOrDigit(currentChar)) {
                return false;
            }
        }

        return false;
    }

}
