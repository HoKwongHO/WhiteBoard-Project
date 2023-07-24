package manager;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CreateWhiteBoard {
    static String address = "localhost";
    static int port = 4444;
    static String userName = "Manager";
    private JFrame jFrame;
    private JTextField managerName;
    public static managerPanel whiteBoard;
    public static ArrayList<String> allUsers = new ArrayList<>();
    public static ArrayList<Connection> allConnects = new ArrayList<>();

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
        EventQueue.invokeLater(()->{
            try {
               CreateWhiteBoard window =  new CreateWhiteBoard();
                window.jFrame.setVisible(true);
            }catch (Exception e){
                System.out.println("error: cannot open windows!");
            }
        });

        ServerSocket server = null;
        Connection c = null;
        try {
            server = new ServerSocket(port);
            Socket client;
            while(true) {
                try {
                    client = server.accept();
                    c = new Connection(client);
                    c.start();
                    allConnects.add(c);

                } catch (Exception e) {
                    break;
                }
            }
            try {
                server.close();
            } catch (Exception e) {
                System.out.println("error! unable to close server!");
            }
        }catch (Exception e){
            System.out.println("Fail to start server");
        }

    }

    public CreateWhiteBoard(){
        initialize();
    }
    public void initialize() {
        jFrame = new JFrame();
        Listener listener = new Listener();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(100, 100, 450, 300);


        managerName = new JTextField();
        managerName.setFont(new Font("Arial", Font.PLAIN, 12));
        managerName.setBounds(142, 114, 142, 29);
        jFrame.getContentPane().add(managerName);
        managerName.setText(userName);

        JButton loginBtn = new JButton("start white board");
        loginBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        loginBtn.addActionListener(e -> {
            if(e.getActionCommand().equals("start white board")){
                userName = managerName.getText();
                allUsers.add(userName);
                jFrame.dispose();
                try {
                    whiteBoard = new managerPanel(userName);
                    whiteBoard.setFrame(whiteBoard);
                }catch (Exception exception){
                    System.out.println("Error, cannot open white board!");
                }
            }
        });
        loginBtn.setBounds(130, 206, 167, 23);
        jFrame.getContentPane().add(loginBtn);

        jFrame.getContentPane().setLayout(null);
    }
}
