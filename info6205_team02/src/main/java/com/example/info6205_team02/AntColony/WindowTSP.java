package com.example.info6205_team02.AntColony;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Draws the nodes to the screen, as well as a path.
 */
public class WindowTSP extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1200 / 16*9;
    private static final int OFFSET = 40;
    private static final int CITY_SIZE = 6;

    private Panel panel;
    private int[] nodes;

    private double[][] grap;
    private double maxX, maxY;
    private double scaleX, scaleY;

    /**
     * Construct WindowTSP and draw the nodes to the screen.
     *
     * @param nodes the nodes to draw to the screen
     * @param graph
     */
    public WindowTSP(int[] nodes, double[][] graph) {
        this.nodes = nodes;
        this.grap = graph;
        setScale(nodes, graph);
        panel = createPanel();
        setWindowProperties();
    }

    /**
     * Draw a path through the city.
     *
     * @param chromosome the Chromosome containing the path
     * @param graph
     */
    public void draw (int[] chromosome, double[][] graph) {
        this.nodes = chromosome;
        this.grap = graph;
        panel.repaint();
    }

    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }

    private void setWindowProperties () {
        int sWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
        int sHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
        int x = sWidth - (WIDTH / 2);
        int y = sHeight - (HEIGHT / 2);
        setLocation(x, y);
        setResizable(false);
        pack();
        setTitle("Traveling Salesman Problem - Ant Colony");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Sets the scale for the drawing so that all the nodes
     * are drawn inside the window.
     */
    private void setScale (int[] nodes, double[][] graph) {
        for (int i=0;i<graph.length;i++) {
            if (graph[i][0] > maxX) {
                maxX = graph[i][0];
            }
            if (graph[i][1] > maxY) {
                maxY = graph[i][1];
            }
        }
        scaleX = ((double)maxX) / ((double)WIDTH- OFFSET);
        scaleY = ((double)maxY) / ((double)HEIGHT- OFFSET);
    }

    /**
     * All the drawing is done here.
     */
    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paintTravelingSalesman((Graphics2D)graphics);
        }

        private void paintTravelingSalesman (Graphics2D graphics) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintCityNames(graphics);
            paintChromosome(graphics);
            paintCities(graphics);
        }

        private void paintChromosome (Graphics2D graphics) {

            graphics.setColor(Color.darkGray);
            double[][] array = grap;
            int[] array2=nodes;

            for (int i = 1; i < array2.length; i++) {
                int x1 = (int)(array[array2[i-1]][0] / scaleX + OFFSET / 2);
                int y1 = (int)(array[array2[i-1]][1] / scaleY + OFFSET / 2);
                int x2 = (int)(array[array2[i]][0] / scaleX + OFFSET / 2);
                int y2 = (int)(array[array2[i]][1] / scaleY + OFFSET / 2);
                graphics.drawLine(x1, y1, x2, y2);
            }

        }

        private void paintCities (Graphics2D graphics) {
            graphics.setColor(Color.darkGray);
            double[][] array = grap;
            int[] array2=nodes;
            for (int i = 0; i < array2.length; i++) {
                int x = (int)((array[array2[i]][0]) / scaleX - CITY_SIZE/2 + OFFSET / 2);
                int y = (int)((array[array2[i]][1]) / scaleY - CITY_SIZE/2 + OFFSET / 2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
            }
        }

        private void paintCityNames (Graphics2D graphics) {
            graphics.setColor(new Color(200, 200, 200));
            ArrayList<String> idList= new ArrayList<String>();
            try {
                idList = idMap("src/main/java/com/example/info6205_team02/Input/info6205.spring2023.teamproject.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            double[][] array = grap;
            int[] array2=nodes;
            for (int i = 0; i < nodes.length; i++) {
                int x = (int)((array[array2[i]][0]) / scaleX - CITY_SIZE/2 + OFFSET/2);
                int y = (int)((array[array2[i]][1]) / scaleY - CITY_SIZE/2 + OFFSET/2);
                graphics.fillOval(x, y, CITY_SIZE, CITY_SIZE);
                int fontOffset = getFontMetrics(graphics.getFont()).stringWidth("city "+array2[i])/2-2;
                graphics.drawString(String.valueOf(idList.get(array2[i])), x-fontOffset, y-3);
            }

        }

        }

        private ArrayList<String> idMap(String path) throws IOException {
            ArrayList<String> idMapList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("crimeID")) continue;
                String[] arr = line.split(",");
                String name = arr[0].substring(arr[0].length()-5);
                idMapList.add(name);
            }
            return idMapList;
        }
    }
