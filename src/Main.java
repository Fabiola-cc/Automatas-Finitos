package src;

import Glushkov.GlushkovAFN;
import Graph.Grafo;
import ShutingYard.ShuntingYardRegex;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.SwingUtilities;

import Automatas.AFD;
import Automatas.AFN;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Scanner s = new Scanner(System.in);

        // System.out.println("Bienvenido");
        // System.out.println("Porfavor ingresa la regexp que deseas transformar: ");
        // System.out.println("Recuerda que puedes utilizar + * | y (), omite los puntos para concatenaciones");
        // String inputRegex = s.nextLine();

        // System.out.println("Genial! Ahora porfavor ingresa la cadena que deseas evaluar: ");
        // String inputString = s.nextLine();

        // // PASO 1 Regex a Postfix
        // String postfix = ShuntingYardRegex.infixToPostfix(inputRegex);

        // // PASO 2 Regex a AFN
        // Object[] initialAFN = GlushkovAFN.regexToDFA(inputRegex);

        // // Conversion of states
        // List<Character> stateList = (List<Character>) initialAFN[0];
        // String[] states = new String[stateList.size()];
        // for (int i = 0; i < stateList.size(); i++) {
        //     states[i] = stateList.get(i).toString();
        // }

        // // Convertion alphabet
        // Set<Character> alphabet = new HashSet<>((List<Character>) initialAFN[1]);

        // // Convertion final states
        // Set<String> acceptanceStates = new HashSet<>();
        // for (Integer finalState : (Set<Integer>) initialAFN[3]) {
        //     acceptanceStates.add(states[finalState + 1]);
        // }

        // // Conversion transition
        // String[][] transitionMatrix = (String[][]) initialAFN[2];
        // Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();

        // for (int i = 0; i < transitionMatrix.length; i++) {
        //     String state = states[i];
        //     transitions.putIfAbsent(state, new HashMap<>());

        //     for (int j = 0; j < transitionMatrix[i].length; j++) {
        //         if (transitionMatrix[i][j] != null) {
        //             char input = alphabet.toArray(new Character[0])[j];

        //             Set<String> destinationStates = new HashSet<>(Arrays.asList(transitionMatrix[i][j].split("")));
        //             transitions.get(state).put(input, destinationStates);
        //         }
        //     }
        // }

        // for (Object object : initialAFN) {
        //     System.out.println();
        //     System.out.println(object);
        // }

        // // Creates none determinist automata
        // AFN afn = new AFN(states, alphabet, states[0], acceptanceStates, transitions);
        // // Create determinist automata
        // AFD afd = AFN.convertAFNtoAFD(afn);
        // // Minimize AFD
        // AFD afdMinimize = afd.minimize();

        // ArrayList<ArrayList<String>> derivationProcess = afdMinimize.derivation(afdMinimize.getInitialState(),
        //         inputString, afdMinimize.getTransitions());

        // // Checks if a string is valid in automata
        // if (afdMinimize.accepted(derivationProcess.get(derivationProcess.size() - 1).get(0), inputString,
        //         afdMinimize.getAcceptanceStates(), afdMinimize.getTransitions())) {
        //     System.out.println("La cadena es aceptada");
        // } else {
        //     System.out.println("La cadena no es aceptada");
        // }

        // // Builds Map for JSON file
        // Map<String, Object> data = new HashMap<>();
        // data.put("Estados", afdMinimize.getStates());
        // data.put("Alfabeto", afdMinimize.getAlphabet());
        // data.put("Estado inicial", afdMinimize.getInitialState());
        // data.put("Estados de aceptación", afdMinimize.getAcceptanceStates());
        // data.put("Matriz de trancisión", afdMinimize.getTransitions());

        // // Use Gson to convert the Map to JSON
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // String jsonString = gson.toJson(data);

        // try (FileWriter file = new FileWriter("Automata.json")) {
        //     file.write(jsonString);
        //     System.out.println("Archivo JSON generado exitosamente.");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // SwingUtilities.invokeLater(() -> {
        //     Grafo frame = new Grafo(afdMinimize.getStates(), afdMinimize.getInitialState(),
        //             afdMinimize.getAcceptanceStates(), afdMinimize.getTransitions());
        //     frame.setVisible(true);

        // Alfabeto con 4 símbolos
        String[] alphabet = {"a", "b", "c", "d"};

        // Definición de los estados
        String[] states = {
            "q0", "q1", "q2", "q3", "q4", "q5", "q6"
        };

        // Definición de transiciones entre estados
        String[][] transitions = {
            {"q0", "a", "q1"},
            {"q0", "c", "q4"},
            {"q1", "b", "q2"},
            {"q2", "c", "q3"},
            {"q2", "a", "q2"},
            {"q2", "d", "q5"},
            {"q3", "d", "q4"},
            {"q4", "a", "q5"},
            {"q4", "b", "q0"},
            {"q5", "b", "q6"},
            {"q6", "c", "q0"},
            {"q6", "d", "q4"} // Transición opcional para volver a "q4"
        };

        // Definir los estados de aceptación
        String[] acceptanceStates = {"q4"};

        // Definir el estado inicial
        String initialState = "q0";

        // Cadena de entrada
        String inputString = "aabbcdd";

        // Crear el AFD
        AFD afd = new AFD(states, alphabet, initialState, acceptanceStates, transitions);

        AFD minimizeAFD = afd.minimize();
        // });
    }
}
