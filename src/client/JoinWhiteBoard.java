package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JoinWhiteBoard {
    static String address = "localhost";
    static int port = 4444;
    static String userName = "Client";
    private JFrame cJFrame;
    private JTextField ClientName;
    public static ClientPanel whiteBoard;
    public static ArrayList<String> allUsers = new ArrayList<>();
    private static int clientNum;
    static Socket cs;
    static Connection connection;

    public static void main(String[] args){
        if(args.length == 3){
            try {
                address = args[0];
                port = Integer.parseInt(args[1]);
                userName = args[2];
            }catch (Exception e) {
                System.out.println("None valid Attribute, start as default");
            }
        }
        else{
            System.out.println("missing Attribute, start as default");
        }
        try{
            cs = new Socket(address, port);
        }catch (Exception e){
            System.out.println("Starting client error");
            System.exit(1);
        }
        connection = new Connection(cs);
        try {
           JoinWhiteBoard sc = new JoinWhiteBoard();
           sc.cJFrame.setVisible(true);
        }catch (Exception e){
            System.out.println("error: cannot open start client!");
        }
        connection.run();
    }

    public JoinWhiteBoard(){
        initialize();
    }
    public void initialize() {
        cJFrame = new JFrame();
        Listener listener = new Listener();
        cJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cJFrame.setBounds(100, 100, 450, 300);


        ClientName = new JTextField();
        ClientName.setFont(new Font("Arial", Font.PLAIN, 12));
        ClientName.setBounds(142, 114, 142, 29);
        cJFrame.getContentPane().add(ClientName);
        ClientName.setText(userName);

        JButton joinBtn = new JButton("Join");
        joinBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        joinBtn.setBounds(130, 206, 167, 23);
        joinBtn.addActionListener(e -> {
            if(e.getActionCommand().equals("Join")){
                userName = ClientName.getText();
                try {
                    connection.DO.writeUTF("permission#"+userName);
                } catch (IOException ex) {
                    System.out.println("Error: unable to send join request");
                }
//                WaitFrame wf = new WaitFrame();
//                wf.wFrame.setVisible(true);
                int time = 0;
                while(connection.status == 0 && time < 100){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        time++;
                    } catch (InterruptedException ex) {
                        System.out.println("time error");
                    }
                }
//                wf.wFrame.dispose();
                if(connection.status == 1) {
                    cJFrame.dispose();
                    try {
                        whiteBoard = new ClientPanel(connection, userName);
                        whiteBoard.setFrame(whiteBoard);
                    } catch (Exception exception) {
                        System.out.println("Error, cannot open white board!");
                    }
                }
                else{
                    if(connection.status == 2) {
                        JOptionPane.showMessageDialog(cJFrame, "name exist");
                    }
                    if(connection.status == 3) {
                        JOptionPane.showMessageDialog(cJFrame, "rejected");
                    }
                    try {
                        connection.DO.writeUTF("close#");
                        connection.DO.flush();
                        cs.close();
                        System.exit(1);
                    }catch (Exception e1){
                        System.out.println("can't close cs");
                    }
                    cJFrame.dispose();
                }
            }
            System.out.println("join");
        });
        cJFrame.getContentPane().add(joinBtn);
        cJFrame.getContentPane().setLayout(null);
    }
}
