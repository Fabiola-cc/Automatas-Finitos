
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
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

    public Arista(Node origin, Node destiny) {
        this.origin = origin;
        this.destiny = destiny;
    }
}

class GraphPanel extends JPanel {
    List<Node> nodes = new ArrayList<>();
    List<Arista> aristas = new ArrayList<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addArista(Node origin, Node destiny) {
        aristas.add(new Arista(origin, destiny));
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
    
        //Draw arrow
        drawArrow(g2d, 10, 100, 60, 100);

        //Draw aristas
        for (Arista arista : aristas) {
            g2d.drawLine(arista.origin.x, arista.origin.y, arista.destiny.x, arista.destiny.y);
        }

        //Draw nodes
        for (Node node : nodes) {
            drawNode(g2d, node);
        }
    }
}

public class Grafo extends JFrame {
    public Grafo() {
        GraphPanel panel = new GraphPanel();

        // Create nodes
        Node nodeA = new Node(100,100, 50,"A", Color.BLACK);
        Node subNodeA = new Node(100,100, 40,"A", Color.BLACK);
        Node nodeB = new Node(200,300, 50, "B", Color.BLACK);
        Node nodeC = new Node(300,100, 5, "C", Color.BLACK);
        
        //Add nodes into panel
        panel.addNode(nodeA);
        panel.addNode(subNodeA);
        panel.addNode(nodeB);
        panel.addNode(nodeC);

        // Create aristas
        panel.addArista(nodeA, nodeB);
        panel.addArista(nodeB, nodeC);
        panel.addArista(nodeA, nodeC);

        //Configure window
        this.add(panel);
        this.setTitle("Finite Determinist Automata");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
