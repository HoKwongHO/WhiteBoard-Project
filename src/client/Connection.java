package client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    public Socket cs;
    public DataInputStream DI = null;
    public  DataOutputStream DO = null;
    public boolean getKicked = false;
    public int status;
    String exitUser;
    String[] curUsers;
    public void run(){
        try{
            while(true){
                String message = DI.readUTF();
                String[] request = message.split("#");
                switch (request[0]) {
                    case "draw":
                        if (!request[1].equals("null")) {
                            ClientPanel.listener.addRecord(request[1]);
                            ClientPanel.DB.repaint();
                        }
                        break;
                    case "chat":
                        if(!request[1].equals(ClientPanel.chat)) {
                            ClientPanel.chatBoard.append(request[1]);
                        }
                        break;
                    case "userlist":
                        JoinWhiteBoard.whiteBoard.userList.setListData(request[1].split("!"));
                        break;
                    case "clientexit":
                        exitUser = request[1].split("@")[0];
                        curUsers = request[1].split("@")[1].split("!");
                        JOptionPane.showMessageDialog(JoinWhiteBoard.whiteBoard.jFrame, "user " + exitUser + " leaves");
                        JoinWhiteBoard.whiteBoard.userList.setListData(curUsers);
                        break;
                    case "kicked":
                        getKicked = true;
                        JOptionPane.showMessageDialog(JoinWhiteBoard.whiteBoard.jFrame, "you have been kicked");
                        System.exit(1);
                    case "denied":
                        if(request[1].equals("exname")) {
                            status = 2;
                        }
                        else{
                            status = 3;
                        }
                        break;
                    case "accessed":
                        System.out.println("Accessed");
                        status = 1;
                        break;
                    case "otherkicked":
                        exitUser = request[1].split("@")[0];
                        curUsers = request[1].split("@")[1].split("!");
                        JOptionPane.showMessageDialog(JoinWhiteBoard.whiteBoard.jFrame, "user " + exitUser + " has been kicked");
                        JoinWhiteBoard.whiteBoard.userList.setListData(curUsers);
                        break;
                    case "new":
                        ClientPanel.DB.removeAll();
                        ClientPanel.DB.updateUI();
                        ClientPanel.listener.clearRecords();
                        break;
                }

            }
        }catch (Exception e){
            System.out.println("launch error");
        }
    }
    Connection(Socket cs){
        getKicked = false;
        status = 0;
        try{
            this.cs = cs;
            DO = new DataOutputStream(this.cs.getOutputStream());
            DI = new DataInputStream(this.cs.getInputStream());

        }catch (IOException e){
            JOptionPane.showMessageDialog(JoinWhiteBoard.whiteBoard.jFrame, "connection fail");
        }catch (Exception e1){
            System.exit(0);
        }
    }
}
