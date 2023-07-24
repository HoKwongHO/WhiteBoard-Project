package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Save {
    Listener listener;
    String fileName;
    public JFrame saveF;
    private managerPanel whiteBoard;
    public Save(managerPanel whiteBoard, Listener listener){
        this.whiteBoard = whiteBoard;
        this.listener = listener;
        initialize();
    }

    public void initialize(){
        saveF = new JFrame();
        saveF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        saveF.setBounds(100, 100, 450, 300);
        saveF.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("File Name: ");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 100, 90, 15);
        saveF.getContentPane().add(lblNewLabel);

        JTextField textField = new JTextField();
        textField.setBounds(70, 97, 350, 21);
        saveF.getContentPane().add(textField);
        textField.setColumns(10);

        JButton PDFBtn = new JButton("Save");
        PDFBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileName = "./" + textField.getText();
                saveFile();
                saveF.dispose();
            }
        });
        PDFBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        PDFBtn.setBounds(163, 186, 93, 23);
        saveF.getContentPane().add(PDFBtn);

        JLabel lblNewLabel_1 = new JLabel("Save File");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(23, 22, 74, 15);
        saveF.getContentPane().add(lblNewLabel_1);

    }
    public void saveFile(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(fileName));
            for(String record : listener.getRecords()){
                pw.println(record);
            }
            pw.flush();
            pw.close();
        JOptionPane.showMessageDialog(whiteBoard, "Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(whiteBoard, "saving error");
        }
    }
}
