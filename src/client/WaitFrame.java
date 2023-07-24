package client;
import manager.managerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class WaitFrame{
    JFrame wFrame = new JFrame();
    public WaitFrame(){
        initialize();
    }
    void initialize(){
        wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wFrame.setBounds(100, 100, 450, 300);
        wFrame.getContentPane().setLayout(null);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        closeBtn.setBounds(163, 186, 93, 23);
        wFrame.getContentPane().add(closeBtn);

        JLabel lblNewLabel_1 = new JLabel("Wait for host to agree...");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(108, 96, 231, 15);
        wFrame.getContentPane().add(lblNewLabel_1);
    }
}
