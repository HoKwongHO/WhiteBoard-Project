package manager;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Connection extends Thread {
    public Socket s;
    public String cName;
    public DataInputStream DI;
    public DataOutputStream DO;
    public boolean getKicked = false;

    public Connection(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            DI = new DataInputStream(s.getInputStream());
            DO = new DataOutputStream(s.getOutputStream());
            String cMessage;
            label:
            while ((cMessage = DI.readUTF()) != null){
                String[] cInfo = cMessage.split("#");
                switch (cInfo[0]){
                    case "initial":
                        ArrayList records = CreateWhiteBoard.whiteBoard.listener.getRecords();
                        try{
                            boardCastDraw(records);
                        } catch (Exception e) {
                            System.out.println("initializing client error!");
                        }
                        String userList = "userlist#";
                        for (String user : CreateWhiteBoard.allUsers){
                            userList += ("!" + user);
                        }
                        String[] StrUserList = userList.split("#");
                        String[] allUsers = StrUserList[1].split("!");
                        CreateWhiteBoard.whiteBoard.userList.setListData(allUsers);
                        boardCast(userList);
                        break;
                    case "permission":
                        cName = cInfo[1];
                        if (CreateWhiteBoard.allUsers.contains(cName)){
                            try {
                                DO.writeUTF("denied#exname");
                                DO.flush();
                            }catch (Exception e){
                                System.out.println("returning Denied error");
                            }
                        }
                        else {
                            int ans = requireLink(cName);
                            if(ans == JOptionPane.YES_OPTION){
                                if (CreateWhiteBoard.allUsers.contains(cName)) {
                                    try {
                                        DO.writeUTF("denied#exname");
                                        DO.flush();
                                        CreateWhiteBoard.allConnects.remove(this);
                                        s.close();
                                    }
                                    catch (Exception e){
                                        System.out.println("Connection close error");
                                    }
                                }
                                else{
                                    CreateWhiteBoard.allUsers.add(cName);
                                    System.out.println("In accessed");
                                    DO.writeUTF("accessed#");
                                    DO.flush();
                                }
                            }
                            else{
                                DO.writeUTF("denied#rejected");
                                DO.flush();
                                CreateWhiteBoard.allConnects.remove(this);
                                s.close();
                            }
                        }
                        break;
                    case "draw":
                        boardCast(cMessage);
                        boardDraw(cInfo[1]);
                        break;
                    case "close":
                        s.close();
                        break label;
                    case "chat":
                        boardCast(cMessage);
                        managerPanel.sendMessage(cInfo[1]);
                        break;
                    case "new":
                        managerPanel.DB.removeAll();
                        managerPanel.DB.updateUI();
                        managerPanel.listener.clearRecords();
                }
            }
        } catch (SocketException e) {
            System.out.println("manager connection interrupted");
            if(!this.getKicked){
                clientExit();
            }
        }
        catch (Exception e) {
            System.out.println("connection error!");
        }
    }

    public static int requireLink(String name){
        return CreateWhiteBoard.whiteBoard.agreeLink(name);
    }

    public static void boardCast(String message) throws IOException {
        for(Connection connect : CreateWhiteBoard.allConnects){
            connect.DO.writeUTF(message);
            connect.DO.flush();
        }
    }
    public static void boardCastDraw(ArrayList<String> records) throws IOException {
        for(String record : records) {
            for (Connection connect : CreateWhiteBoard.allConnects) {
                connect.DO.writeUTF("draw#"+record);
                connect.DO.flush();
            }
        }
    }

    public static void boardDraw(String record){
        managerPanel.listener.addRecord(record);
        managerPanel.DB.repaint();
    }

    public void clientExit(){
        CreateWhiteBoard.allUsers.remove(cName);
        CreateWhiteBoard.allConnects.remove(this);
        String allUser = "";
        String[] allUserList;
        for(String user : CreateWhiteBoard.allUsers){
            allUser += (user + "!");
        }
        allUserList = allUser.split("!");
        for(Connection connect : CreateWhiteBoard.allConnects){
            try{
                connect.DO.writeUTF("clientexit#" + cName + "@" + allUser);
            }catch (Exception e){
                System.out.println("client exit error");
            }
        }
        JOptionPane.showMessageDialog(CreateWhiteBoard.whiteBoard.jFrame, "user " + cName + " leaves");
        CreateWhiteBoard.whiteBoard.userList.setListData(allUserList);
    }
}