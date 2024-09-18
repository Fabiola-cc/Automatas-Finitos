package ShutingYard;

import java.util.*;

public class ShuntingYardRegex {
    // Define la precedencia de los operadores
    private static int precedence(char operator) {
        switch (operator) {
            case '*':
                return 3; // Mayor precedencia
            case '.':
                return 2; // Concatenación implícita
            case '|':
                return 1; // Alternancia
            default:
                return 0; // Otros caracteres (no operadores)
        }
    }

    // Función para convertir de infix a postfix utilizando el algoritmo de Shunting
    // Yard
    public static String infixToPostfix(String regex) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : regex.toCharArray()) {
            switch (token) {
                case '(':
                    operators.push(token);
                    break;
                case ')':
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.append(operators.pop());
                    }
                    operators.pop(); // Descartar el '('
                    break;
                case '|':
                case '.':
                    // Operadores binarios (|, .): manejar la precedencia
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)
                            && operators.peek() != '(') {
                        output.append(operators.pop());
                    }
                    operators.push(token);
                    break;
                case '*':
                    // Operador unario (*): tiene la máxima precedencia
                    output.append(token); // Añadir directamente el * después de su operando
                    break;
                default:
                    output.append(token); // Si es un operando (carácter), añadirlo al output
            }
        }

        // Vaciar la pila de operadores
        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.toString();
    }
}
