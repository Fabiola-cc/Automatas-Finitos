
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Nodes in Grafo
class Node {
    int x, y, radius;
    String label;
    Color borderColor;

    public Node(int x, int y, int radius, String label, Color borderColor) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.label = label;
        this.borderColor = borderColor;
    }
}

//Aristas in Grafo
class Arista {
    Node origin, destiny;
    String label;

    public Arista(Node origin, Node destiny, String label) {
        this.origin = origin;
        this.destiny = destiny;
        this.label = label;
    }
}

class GraphPanel extends JPanel {
    List<Node> nodes = new ArrayList<>();
    List<Arista> aristas = new ArrayList<>();
    Node initialNode; // Nodo inicial

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addArista(Node origin, Node destiny, String label) {
        aristas.add(new Arista(origin, destiny, label));
    }

    // Método para establecer el nodo inicial
    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    private void drawNode(Graphics2D g2d, Node node) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(node.x - node.radius, node.y - node.radius, node.radius * 2, node.radius * 2);

        g2d.setColor(node.borderColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(node.x - node.radius, node.y - node.radius, node.radius * 2, node.radius * 2);

        g2d.setColor(Color.BLACK);
        g2d.drawString(node.label, node.x - 7, node.y + 5);
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 20;
        int arrowWidth = 10;

        int xBase = x2 - (int) (arrowLength * Math.cos(angle));
        int yBase = y2 - (int) (arrowLength * Math.sin(angle));

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(x2, y2);
        arrowHead.addPoint(xBase + (int) (arrowWidth * Math.sin(angle)), yBase - (int) (arrowWidth * Math.cos(angle)));
        arrowHead.addPoint(xBase + (int) (arrowWidth * Math.sin(angle)), yBase + (int) (arrowWidth * Math.cos(angle)));

        g2d.fillPolygon(arrowHead);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Dibuja la flecha al lado del nodo inicial
        if (initialNode != null) {
            int arrowX1 = initialNode.x - initialNode.radius - 30; // Ajusta la posición de la flecha
            int arrowY1 = initialNode.y;
            int arrowX2 = initialNode.x - initialNode.radius;
            int arrowY2 = initialNode.y;
            drawArrow(g2d, arrowX1, arrowY1, arrowX2, arrowY2);
        }

        // Dibuja aristas
        for (Arista arista : aristas) {
            g2d.drawLine(arista.origin.x, arista.origin.y, arista.destiny.x, arista.destiny.y);
            g2d.drawString(arista.label, (arista.origin.x + arista.destiny.x + 10) / 2, (arista.origin.y + arista.destiny.y) / 2);
        }

        // Dibuja nodos
        for (Node node : nodes) {
            drawNode(g2d, node);
        }
    }
}


public class Grafo extends JFrame {
    public Grafo(String[] states, String initial_state, String[] acceptance_states, String[][] transitions) {
        GraphPanel panel = new GraphPanel();

        HashMap<String, Node> nodeStates = new HashMap<>();
        Node initialNode = null; // Referencia al nodo inicial

        int centerX = 600;
        int centerY = 500;
        int radius = 400;

        int numStates = states.length;

        for (int i = 0; i < numStates; i++) {
            String q = states[i];

            double angle = 2 * Math.PI * (i+2) / numStates;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));

            Node node = new Node(x, y, 40, q, Color.BLACK);
            panel.addNode(node);
            nodeStates.put(node.label, node);
            if (Arrays.asList(acceptance_states).contains(node.label)) {
                Node subNode = new Node(x, y, 30, q, Color.BLACK);
                panel.addNode(subNode);
            }

            // Identificar el nodo con el estado inicial
            if (q.equals(initial_state)) {
                initialNode = node; // Guarda el nodo inicial
            }
        }

        for (String[] transition : transitions) {
            panel.addArista(nodeStates.get(transition[0]), nodeStates.get(transition[2]), transition[1]);
        }

        // Configura el nodo inicial en el panel para que dibuje la flecha
        panel.setInitialNode(initialNode);

        // Configura la ventana
        this.add(panel);
        this.setTitle("Finite Determinist Automata");
        this.setSize(1200, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
