package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Open {
    DrawingBoard DB;
    String fileDir;
    public JFrame openF;
    private managerPanel whiteBoard;
    Listener listener;
    public Open(managerPanel whiteBoard, Listener listener, DrawingBoard DB){
        this.whiteBoard = whiteBoard;
        this.listener = listener;
        this.DB = DB;
        initialize();
    }

    public void initialize(){
        openF = new JFrame();
        openF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        openF.setBounds(100, 100, 450, 300);
        openF.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("File Name: ");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 100, 90, 15);
        openF.getContentPane().add(lblNewLabel);

        JTextField textField = new JTextField();
        textField.setBounds(70, 97, 350, 21);
        openF.getContentPane().add(textField);
        textField.setColumns(10);

        JButton PDFBtn = new JButton("Open");
        PDFBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileDir = "./" + textField.getText();
                openFile();
                openF.dispose();
            }
        });
        PDFBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        PDFBtn.setBounds(163, 186, 93, 23);
        openF.getContentPane().add(PDFBtn);

        JLabel lblNewLabel_1 = new JLabel("Open File");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(23, 22, 74, 15);
        openF.getContentPane().add(lblNewLabel_1);

    }
    public void openFile(){
        Scanner scanner = null;
        try {
            try {
                scanner = new Scanner(new FileInputStream(fileDir));
            }catch (Exception e1){
                System.out.println("can't create scanner");
            }
            listener.clearRecords();
            while(scanner.hasNextLine()){
                listener.addRecord(scanner.nextLine());
            }
            Connection.boardCast("new#");
            Connection.boardCastDraw(listener.getRecords());
            DB.repaint();
            JOptionPane.showMessageDialog(whiteBoard, "Opened file");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(whiteBoard, "opening error");
        }
    }
}
