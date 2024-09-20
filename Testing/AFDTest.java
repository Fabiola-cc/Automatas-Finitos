package Testing;

import org.junit.Test;

import Automatas.AFD;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class AFDTest {
    
    @Test
    public void TransitionBasicTest() {
        String[] alphabet = {"1"};

        String[] states = {
            "q0",
            "q1",
            "q2",
            "q3",
            "q4",
            "q5",
            "q6",
            "q7",
            "q8",
            "q9",
            "q10"
        };

        String[][] transitions = {
            {
                "q0",
                "1",
                "q1"
            },
            {
                "q1",
                "1",
                "q2"
            },
            {
                "q2",
                "1",
                "q3"
            },
            {
                "q3",
                "1",
                "q4"
            },
            {
                "q4",
                "1",
                "q5"
            },
            {
                "q5",
                "1",
                "q6"
            },
            {
                "q6",
                "1",
                "q7"
            },
            {
                "q7",
                "1",
                "q8"
            },
            {
                "q8",
                "1",
                "q9"
            },
            {
                "q9",
                "1",
                "q10"
            }
        };

        String[] acceptanceStates = {"q10"};

        String initialState = "q0";

        String inputString = "111";

        AFD afd = new AFD(states, alphabet, initialState, acceptanceStates, transitions);

        ArrayList<ArrayList<String>> derivationProcess = afd.derivation(afd.getInitialState(),
                inputString, afd.getTransitions());

        boolean Aceptation = afd.accepted(derivationProcess.get(derivationProcess.size() - 1).get(0), inputString,
        afd.getAcceptanceStates(), afd.getTransitions());
        
        assertEquals(Aceptation, false);
    }

    @Test
    public void TransitionComplexTest() {
        // Alfabeto con más de un símbolo
        String[] alphabet = {"0", "1"};

        // Definición de los estados
        String[] states = {
            "q0", "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10", "q11", "q12"
        };

        // Transiciones entre los estados
        String[][] transitions = {
            {"q0", "0", "q1"},
            {"q0", "1", "q2"},
            {"q1", "0", "q3"},
            {"q1", "1", "q4"},
            {"q2", "0", "q5"},
            {"q2", "1", "q6"},
            {"q3", "1", "q7"},
            {"q4", "0", "q8"},
            {"q5", "1", "q9"},
            {"q6", "0", "q10"},
            {"q7", "1", "q11"},
            {"q8", "0", "q12"},
            {"q9", "1", "q10"},
            {"q10", "0", "q11"},
            {"q11", "1", "q12"}
        };

        // Estados de aceptación
        String[] acceptanceStates = {"q12"};

        // Estado inicial
        String initialState = "q0";

        // Cadena de entrada
        String inputString = "11001";

        // Crear el AFD
        AFD afd = new AFD(states, alphabet, initialState, acceptanceStates, transitions);

        // Obtener el proceso de derivación para la cadena de entrada
        ArrayList<ArrayList<String>> derivationProcess = afd.derivation(afd.getInitialState(),
                inputString, afd.getTransitions());

        // Verificar si la cadena es aceptada por el autómata
        boolean Aceptation = afd.accepted(derivationProcess.get(derivationProcess.size() - 1).get(0), inputString,
                afd.getAcceptanceStates(), afd.getTransitions());

        // Comparar el resultado con el esperado (en este caso, se espera que sea aceptado)
        assertEquals(Aceptation, true);
    }

    @Test
    public void TransitionFourAlphabetTest() {
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

        // Obtener el proceso de derivación para la cadena de entrada
        ArrayList<ArrayList<String>> derivationProcess = afd.derivation(afd.getInitialState(),
                inputString, afd.getTransitions());

        // Verificar si la cadena es aceptada por el autómata
        boolean Aceptation = afd.accepted(derivationProcess.get(derivationProcess.size() - 1).get(0), inputString,
                afd.getAcceptanceStates(), afd.getTransitions());

        // Comparar el resultado con el esperado (se espera que sea aceptado)
        assertEquals(Aceptation, false);
    }    

    @Test
    public void MinimizationTest() {
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

        // Comparar el resultado con el esperado
        assertEquals(minimizeAFD.getStates().length, 2);
    }   
}
