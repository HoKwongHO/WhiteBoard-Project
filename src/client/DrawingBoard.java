package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingBoard extends JPanel {
    static ArrayList<String> records = new ArrayList<>();
    String[] commands;
    int t, startX, startY, endX, endY, width, height;
    Color color;
    String[] rgb;
    String text;
    public static void setList(ArrayList<String> newRecords){
        records = newRecords;
    }

    public void paint(Graphics graph){
        super.paint(graph);
        draw((Graphics2D)graph, this.records);
    }

    public void draw(Graphics2D graph, ArrayList<String> records){
        try {
            for (String record : records){
                commands = record.split(";");
                t = Integer.parseInt(commands[1]);
                rgb = commands[2].split(" ");
                startX = Integer.parseInt(commands[3]);
                startY = Integer.parseInt(commands[4]);
                endX = Integer.parseInt(commands[5]);
                endY = Integer.parseInt(commands[6].split("@")[0]);
                graph.setStroke(new BasicStroke(t));
                color = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
                graph.setColor(color);
                if(commands.length > 7){
                    text = commands[7];
                }
                switch (commands[0]){
                    case "Line":
                        graph.drawLine(startX, startY, endX, endY);
                        break;
                    case "Circle":
                        int d = Math.min(Math.abs(endX - startX), Math.abs(endY - startY));
                        graph.drawOval(Math.min(startX, endX), Math.min(startY, endY), d, d);
                        break;
                    case "Oval":
                        width = Math.abs(endX - startX);
                        height = Math.abs(endY - startY);
                        graph.drawOval(Math.min(startX, endX), Math.min(startY, endY), width, height);
                        break;
                    case "Rectangle":
                        width = Math.abs(endX - startX);
                        height = Math.abs(endY - startY);
                        graph.drawRect(Math.min(startX, endX), Math.min(startY, endY), width, height);
                        break;
                    case "Text":
                        Font f = new Font("Arial", Font.PLAIN, t+11);
                        graph.setFont(f);
                        graph.drawString(text, endX, endY);
                        break;
                }
            }
        }catch (Exception e){
            System.out.println("Fail to paint");
        }
    }
}
