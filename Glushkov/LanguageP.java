package Glushkov;

import java.util.HashSet;
import java.util.Set;

public class LanguageP {
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

}
