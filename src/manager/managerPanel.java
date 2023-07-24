package manager;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class managerPanel extends JFrame{
    public JFrame jFrame;
    int curX, curY;
    static DrawingBoard DB;
    static Listener listener;
    static managerPanel managerP;
    public managerPanel(String userName){
        initialize(userName);
    }
    JList userList;
    static JTextArea chatBoard;
    public int agreeLink(String name){
        int result = JOptionPane.showConfirmDialog(null, name + " wants to share your whiteboard", "comfirm", JOptionPane.INFORMATION_MESSAGE);
        return result;
    }
    public void initialize(String userName){
        jFrame = new JFrame();
        listener = new Listener(jFrame);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(100, 100, 1200, 600);
        jFrame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Manger white board: "+userName);
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


        JButton newBtn = new JButton("New");
        newBtn.addActionListener(e->{
            DB.removeAll();
            DB.updateUI();
            listener.clearRecords();
            try {
                Connection.boardCast("new#");
            } catch (IOException ex) {
                System.out.println("Fail to do new");
            }
        });
        newBtn.setFont(new Font("Arial", Font.BOLD, 12));
        newBtn.setBounds(29, 18, 93, 23);
        jFrame.getContentPane().add(newBtn);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e->{
            Save saveFrame = new Save(managerP, listener);
            saveFrame.saveF.setVisible(true);
        });
        saveBtn.setFont(new Font("Arial", Font.BOLD, 12));
        saveBtn.setBounds(132, 18, 93, 23);
        jFrame.getContentPane().add(saveBtn);

        JButton saveAsBtn = new JButton("Save As");
        saveAsBtn.addActionListener(e->{
            SaveAs saveAsFrame = new SaveAs(managerP, listener, DB);
            saveAsFrame.SaveAsF.setVisible(true);
        });
        saveAsBtn.setFont(new Font("Arial", Font.BOLD, 12));
        saveAsBtn.setBounds(235, 18, 93, 23);
        jFrame.getContentPane().add(saveAsBtn);

        JButton openBtn = new JButton("Open");
        openBtn.addActionListener(e->{
            Open openFrame = new Open(managerP, listener, DB);
            openFrame.openF.setVisible(true);
        });
        openBtn.setFont(new Font("Arial", Font.BOLD, 12));
        openBtn.setBounds(338, 18, 93, 23);
        jFrame.getContentPane().add(openBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e->{
            System.exit(1);
        });
        exitBtn.setFont(new Font("Arial", Font.BOLD, 12));
        exitBtn.setBounds(441, 18, 93, 23);
        jFrame.getContentPane().add(exitBtn);

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

        JButton kickBtn = new JButton("Kick");
        kickBtn.addActionListener(e->{
            String kicking = userList.getSelectedValue().toString();
            if(kicking.equals(userName)){
                JOptionPane.showMessageDialog(CreateWhiteBoard.whiteBoard.jFrame, "can't kick yourself!");
            }
            else{
                for(Connection connection : CreateWhiteBoard.allConnects){
                    if (connection.cName.equals(kicking)){
                        try {
                            connection.DO.writeUTF("kicked#");
                            connection.DO.flush();
                            CreateWhiteBoard.allConnects.remove(connection);
                            CreateWhiteBoard.allUsers.remove(connection.cName);
                            JOptionPane.showMessageDialog(CreateWhiteBoard.whiteBoard.jFrame, connection.cName + " has been kicked out");
                            connection.s.close();
                        } catch (IOException ex) {
                            System.out.println("fail to kick");
                        }
                    }
                }
            }
        });
        kickBtn.setFont(new Font("Arial", Font.BOLD, 12));
        kickBtn.setBounds(29, 505, 93, 23);
        jFrame.getContentPane().add(kickBtn);

        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  String chat = userName+": "+chatInput.getText();
                  chatBoard.append(chat + "\n");
                  chatInput.setText("");
                  try {
                      Connection.boardCast("chat#"+chat + "\n");
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
    }
    void setFrame(JFrame jFrame){
        this.jFrame = jFrame;
    }

    static void sendMessage(String message){
        chatBoard.append(message);
    }
}
