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

    // Método para construir el AFD desde una expresión regular
    public static void regexToDFA(String regex) {
        // Listado de símbolos
        List<Character> operandos = extractOperands(regex);
        int total_operandos = operandos.size();

        // Construir el árbol sintáctico y calcular P, D, F
        Set<Integer> P = extractP(regex); // índices de operandos iniciales
        Set<Integer> D = extractD(regex, total_operandos); // índices de operandos finales
        List<String> F[];

    }

    public static void main(String[] args) {
        // Ejemplo de expresión regular
        String regex = "(a|b)+ab(b|a)*"; // preguntar por caso (a+|b)*

        Set<Integer> P = extractP(regex);
        Set<Integer> D = extractD(regex, 5);
        System.out.println(P);
        System.out.println(D);
    }
}
