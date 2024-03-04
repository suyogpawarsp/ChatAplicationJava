package serverr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server
{
    private JFrame serverframe;
    private JTextArea ta;
    private JScrollPane scrollpane;
    private JTextField tf;
    
    private ServerSocket serversocket;
    private Socket socket;
    
    private InetAddress inet_address;
    
    private DataInputStream dis;
    private DataOutputStream dos;
    //-------------Thread Creation--------------
    Thread thread=new Thread()
            {
                public void run()
                {
                    while(true)
                    {
                        readMessage();
                    }
                }
            };
    //--------------------------------------------
    
   Server()
   {
       serverframe=new JFrame("Server");
       serverframe.setSize(500,500);
       serverframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       ta=new JTextArea();
       ta.setEditable(false);
       Font f=new Font("Arial",1,16);
       ta.setFont(f);
       scrollpane =new JScrollPane(ta);  
       serverframe.add(scrollpane);
       
       tf=new JTextField();
       tf.addActionListener(new ActionListener()
       {

           @Override
           public void actionPerformed(ActionEvent ae) {
             
              sendMessage(tf.getText());
              ta.append(tf.getText()+"\n");
              tf.setText("");
           }
       });
       tf.setEditable(false);
       serverframe.add(tf, BorderLayout.SOUTH);
       
       serverframe.setVisible(true);
   }
   
   public void waitingForClient()
   {
       try
       {
           String ipaddress=getIpAddress();
           serversocket=new ServerSocket(1111);
           ta.setText("To connect with server please provide IP Address : "+ipaddress);
           socket=serversocket.accept();
           ta.setText("Client connected\n");
           ta.append("-----------------------------------------\n");
           tf.setEditable(true);
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
   }
   public String getIpAddress()
   {
        String ip_address ="";
       try
       {
           
           inet_address=InetAddress.getLocalHost();
           ip_address=inet_address.getHostAddress();
           
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
       return ip_address;
   }
   void setIoStream()
   {
       try
       {
       dis=new DataInputStream(socket.getInputStream());
       dos=new DataOutputStream(socket.getOutputStream());
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
       thread.start();
   }
   public void sendMessage(String meassage)
   {
      try
      {
            dos.writeUTF(meassage);
            dos.flush();  
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
   }
   
   public void readMessage()
   {
      try
      {
           String message=dis.readUTF();
           showMessage("Client : "+message);
           
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
       
   }
   
   public void showMessage(String message)
   {
       ta.append(message+"\n");
   }
}