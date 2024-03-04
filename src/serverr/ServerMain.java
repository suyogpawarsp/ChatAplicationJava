/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverr;

/**
 *
 * @author Admin
 */
public class ServerMain
{
     public static void main(String[] args)
    {
       Server s=new Server();    //it will invoke the gui part
       s.waitingForClient();     //it will wait for the client
       s.setIoStream();          //it will set the streams through which we will transefer the data
     
    }
}
