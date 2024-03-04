package client;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client
{
      private JFrame clientframe;
    private JTextArea ta;
    private JScrollPane scrollpane;
    private JTextField tf;
    private Socket socket;
    
    private DataInputStream dis;
    private DataOutputStream dos;
    
    String ipaddress;
    
    //-------------------Thread created------------------
        
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
    
    //---------------------Thread ended ------------------------
    
    Client()
    {
        ipaddress=JOptionPane.showInputDialog("Enter IP Address ");
        
        if( ipaddress!=null )
        {
            if(!ipaddress.equals(""))
            {
                connectToServer();
                
            clientframe=new JFrame("Client");
            clientframe.setSize(500,500);
            clientframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ta=new JTextArea();
            ta.setEditable(false);
             Font f=new Font("Arial",1,16);
             ta.setFont(f);
            scrollpane =new JScrollPane(ta);  
            clientframe.add(scrollpane);

            tf=new JTextField();
            tf.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        sendMessage(tf.getText());
                        ta.append(tf.getText()+"\n");
                        tf.setText("");
                    }
                });
            clientframe.add(tf, BorderLayout.SOUTH);

            clientframe.setVisible(true);
            }
           
        }
    }
    
    void connectToServer()
    {
        try
        {
            socket=new Socket(ipaddress,1111);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     void setIoStream()
   {
       thread.start();
       try
       {
       dis=new DataInputStream(socket.getInputStream());
       dos=new DataOutputStream(socket.getOutputStream());
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
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
           showMessage("Server : "+message);
           
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
       
   }
       public void showMessage(String message)
   {
       ta.append(message+"\n");
       chatSound();
   }
       public void chatSound()
       {
            try
            {
               
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
       }
}
