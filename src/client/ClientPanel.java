package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientPanel extends JFrame {
    public JFrame jFrame;
    int curX, curY;
    static client.DrawingBoard DB;
    static client.Listener listener;
    static client.ClientPanel ClientP;
    Connection connection;
    static String chat;
    public ClientPanel(Connection connection, String userName){
        this.connection = connection;
        initialize(userName);
    }
    JList userList;
    static JTextArea chatBoard;
    public int agreeLink(String name){
        int result = JOptionPane.showConfirmDialog(null, name + "want to connet to server", "comfirm", JOptionPane.INFORMATION_MESSAGE);
        return result;
    }
    public void initialize(String userName){
        jFrame = new JFrame();
        listener = new Listener(jFrame, connection);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(100, 100, 1200, 600);
        jFrame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Client white board: "+userName);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel.setBounds(45, 61, 526, 49);
        jFrame.getContentPane().add(lblNewLabel);

        JTextArea chatInput = new JTextArea();
        chatInput.setBounds(885, 424, 293, 71);
        jFrame.getContentPane().add(chatInput);

        userList = new JList<Object>();
        jFrame.getContentPane().add(userList);
        String managerN = userName;
        String[] allUser = {managerN};
        userList.setListData(allUser);
        JScrollPane scrollList = new JScrollPane(userList);
        scrollList.setBounds(29, 380, 178, 113);
        scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jFrame.getContentPane().add(scrollList);


        chatBoard = new JTextArea();
        chatBoard.setEnabled(false);
        chatBoard.setDisabledTextColor(Color.black);
        chatBoard.setBounds(885, 18, 293, 394);
        jFrame.getContentPane().add(chatBoard);

        JButton lineBtn = new JButton("Line");
        lineBtn.setActionCommand("Line");
        lineBtn.setFont(new Font("Arial", Font.BOLD, 12));
        lineBtn.setBounds(29, 120, 93, 23);
        lineBtn.addActionListener(listener);
        jFrame.getContentPane().add(lineBtn);

        JButton circleBtn = new JButton("Circle");
        circleBtn.setActionCommand("Circle");
        circleBtn.setFont(new Font("Arial", Font.BOLD, 12));
        circleBtn.setBounds(29, 165, 93, 23);
        circleBtn.addActionListener(listener);
        jFrame.getContentPane().add(circleBtn);

        JButton ovalBtn = new JButton("Oval");
        ovalBtn.setActionCommand("Oval");
        ovalBtn.setFont(new Font("Arial", Font.BOLD, 12));
        ovalBtn.setBounds(29, 212, 93, 23);
        ovalBtn.addActionListener(listener);
        jFrame.getContentPane().add(ovalBtn);

        JButton rectBtn = new JButton("Rectangle");
        rectBtn.setActionCommand("Rectangle");
        rectBtn.setFont(new Font("Arial", Font.BOLD, 12));
        rectBtn.setBounds(29, 255, 93, 23);
        rectBtn.addActionListener(listener);
        jFrame.getContentPane().add(rectBtn);

        JButton textBtn = new JButton("Text");
        textBtn.setActionCommand("Text");
        textBtn.setFont(new Font("Arial", Font.BOLD, 12));
        textBtn.setBounds(29, 302, 93, 23);
        textBtn.addActionListener(listener);
        jFrame.getContentPane().add(textBtn);

        JButton eraserBtn = new JButton("Eraser");
        eraserBtn.setActionCommand("Eraser");
        eraserBtn.setFont(new Font("Arial", Font.BOLD, 12));
        eraserBtn.setBounds(29, 347, 93, 23);
        eraserBtn.addActionListener(listener);
        jFrame.getContentPane().add(eraserBtn);

        JButton colorBtn = new JButton("Color");
        colorBtn.setActionCommand("Color");
        colorBtn.setFont(new Font("Arial", Font.BOLD, 12));
        colorBtn.setBounds(138, 347, 69, 23);
        colorBtn.addActionListener(listener);
        jFrame.getContentPane().add(colorBtn);

        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chat = userName+": "+chatInput.getText()+ "\n";
                chatBoard.append(chat);
                chatInput.setText("");
                try {
                    connection.DO.writeUTF("chat#"+chat);
                } catch (IOException ex) {
                    System.out.println("fail to send chat");
                }
            }
        });
        sendBtn.setFont(new Font("Arial", Font.BOLD, 12));
        sendBtn.setBounds(1085, 505, 93, 23);
        jFrame.getContentPane().add(sendBtn);

//      the white board
        DB = new DrawingBoard();
        DB.setBorder(null);
        DB.setBounds(255, 105, 620, 420);
        DB.setBackground(Color.WHITE);
        DB.setList(listener.getRecords());
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        DB.setLayout(null);
        jFrame.getContentPane().add(DB);
        DB.addMouseMotionListener(listener);
        DB.addMouseListener(listener);
        listener.setGraph((Graphics2D) DB.getGraphics());
        try {
            connection.DO.writeUTF("initial#");
        } catch (IOException e) {
            System.out.println("initialize error");
        }
    }
    void setFrame(JFrame jFrame){
        this.jFrame = jFrame;
    }

}
