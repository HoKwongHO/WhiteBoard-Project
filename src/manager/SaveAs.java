package manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class SaveAs {
    DrawingBoard DB;
    Listener listener;
    public JFrame SaveAsF;
    private managerPanel whiteBoard;
    public SaveAs(managerPanel whiteBoard, Listener listener, DrawingBoard DB){
        this.whiteBoard = whiteBoard;
        this.listener = listener;
        this.DB = DB;
        initialize();

    }

    public void initialize(){
        BufferedImage BI = new BufferedImage(620, 420, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = BI.createGraphics();
        graph.setColor(Color.white);
        graph.fillRect(0,0, 620, 420);
        DB.draw(graph, listener.getRecords());
        SaveAsF = new JFrame();
        SaveAsF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SaveAsF.setBounds(100, 100, 450, 300);
        SaveAsF.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("File Name: ");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNewLabel.setBounds(30, 58, 74, 15);
        SaveAsF.getContentPane().add(lblNewLabel);

        JTextField textField = new JTextField();
        textField.setBounds(77, 97, 351, 21);
        SaveAsF.getContentPane().add(textField);
        textField.setColumns(10);

        JButton PDFBtn = new JButton(".png");
        PDFBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = "./"+textField.getText();
                try {
                    ImageIO.write(BI, "PNG",new File(fileName+".png"));
                }catch (Exception e1){
                    System.out.println("can't save png");
                }
                SaveAsF.dispose();
                JOptionPane.showMessageDialog(whiteBoard, "Saved");
            }
        });
        PDFBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        PDFBtn.setBounds(128, 142, 93, 23);
        SaveAsF.getContentPane().add(PDFBtn);

        JLabel lblNewLabel_1 = new JLabel("Save As:");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(30, 144, 74, 15);
        SaveAsF.getContentPane().add(lblNewLabel_1);

        JButton JPGBtn = new JButton(".jpg");
        JPGBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = "./"+textField.getText();
                try {
                    ImageIO.write(BI, "JPEG",new File(fileName+".jpg"));
                }catch (Exception e1){
                    System.out.println("can't save jpg");
                }
                SaveAsF.dispose();
                JOptionPane.showMessageDialog(whiteBoard, "Saved");
            }
        });
        JPGBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        JPGBtn.setBounds(279, 142, 93, 23);
        SaveAsF.getContentPane().add(JPGBtn);


    }

    public void SaveJPG(String name){

    }

}
