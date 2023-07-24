package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class Listener implements ActionListener, MouseListener, MouseMotionListener {
    Graphics2D graph;
    JFrame jFrame;
    Object type = "Line";
    static Color color = Color.BLACK;
    String rgb = "255 255 255";
    String record, text, curC;
    ArrayList<String> records = new ArrayList<>();
    int startX, startY, endX, endY, width, height;
    int t = 1;
    Connection connection;
    public Listener(){

    }
    public Listener(JFrame jFrame, Connection connection){

        this.jFrame = jFrame;
        this.connection = connection;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Color")){
            final JFrame colorPanel = new JFrame("Color Panel");
            colorPanel.setSize(300, 300);
            colorPanel.setLocationRelativeTo(null);
            colorPanel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            Color curColor = JColorChooser.showDialog(colorPanel, "Color", Color.BLACK);
            if (curColor != null) {
                color = curColor;
            }
        }
        else{
            this.type = e.getActionCommand();
            Cursor cur = new Cursor(Cursor.CROSSHAIR_CURSOR);
            jFrame.setCursor(cur);
        }

    }

    public void setGraph(Graphics2D graph){
        this.graph = graph;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        if(!graph.getColor().equals(color)){
            graph.setColor(color);
            curC = getRgb(color);

        }
        if(type.equals("Eraser")){
            curC = getRgb(color);
            color = Color.WHITE;
            graph.setColor(color);
            rgb = getRgb(color);
            graph.setStroke(new BasicStroke(t));
            graph.drawLine(startX, startY, startX, startY);
            records.add("Line;"+t+";"+rgb+";"+startX+";"+startY+";"+startX+";"+startY+"@");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        switch (type.toString()){
            case "Line":
                rgb = getRgb(color);
                graph.setStroke(new BasicStroke(t));
                graph.drawLine(startX, startY, endX, endY);
                record = "Line;"+t+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+"@";
                records.add(record);
                break;
            case "Circle":
                rgb = getRgb(color);
                graph.setStroke(new BasicStroke(t));
//                int d = (int) Math.round(Math.sqrt(Math.pow(Math.abs(startX-endX), 2) + Math.pow(Math.abs(startY-endY), 2)));
                int d = Math.min(Math.abs(startX-endX), Math.abs(startY-endY));
                graph.drawOval(Math.min(startX, endX), Math.min(startY, endY), d, d);
                record = "Circle;"+t+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+"@";
                records.add(record);
                break;
            case "Oval":
                rgb = getRgb(color);
                graph.setStroke(new BasicStroke(t));
                width = Math.abs(endX - startX);
                height = Math.abs(endY - startY);
                graph.drawOval(Math.min(startX, endX), Math.min(startY, endY), width, height);
                record ="Oval;"+t+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+"@";
                records.add(record);
                break;
            case "Rectangle":
                rgb = getRgb(color);
                graph.setStroke(new BasicStroke(t));
                width = Math.abs(endX - startX);
                height = Math.abs(endY - startY);
                graph.drawRect(Math.min(startX, endX), Math.min(startY, endY), width, height);
                record = "Rectangle;"+t+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+"@";
                records.add(record);
                break;
            case "Text":
                rgb = getRgb(color);
                text = JOptionPane.showInputDialog("Enter text here");
                if(text != null){
                    Font f = new Font("Arial", Font.PLAIN, t+11);
                    graph.setFont(f);
                    graph.drawString(text, endX, endY);
                    record = "Text;"+t+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+";"+text+";"+"@";
                    records.add(record);
                }
                break;
            case "Eraser":
                color = setRGB(curC);
                graph.setColor(color);
                break;
        }
        jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        try {
            connection.DO.writeUTF("draw#"+record);
        } catch (IOException ex) {
            System.out.println("Error: unable to draw on client");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        if(type.equals("Eraser")){
            color = Color.WHITE;
            graph.setColor(color);
            rgb = getRgb(color);
            graph.setStroke(new BasicStroke(10));
            graph.drawLine(startX,startY,endX,endY);
            record = "Line;"+10+";"+rgb+";"+startX+";"+startY+";"+endX+";"+endY+"@";
            records.add(record);
            startX = endX;
            startY = endY;
            try {
                connection.DO.writeUTF("draw#"+record);
            } catch (IOException ex) {
                System.out.println("Error: unable to draw on client");
            }
        }
    }

    public ArrayList<String> getRecords(){
        return records;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private String getRgb(Color color){
        return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }

    private Color setRGB(String RGB){
        String[] colorInd = RGB.split(" ");
        return new Color(Integer.parseInt(colorInd[0]), Integer.parseInt(colorInd[1]), Integer.parseInt(colorInd[2]));
    }

    void clearRecords(){
        records.clear();
    }

    public void addRecord(String record){
        records.add(record);
    }

    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}