
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishu
 */
public class Seeder3 {
     public Seeder3() throws IOException
    {
        ServerSocket server=new ServerSocket(12345);
        while(true)
        { Socket socket;
            socket = server.accept();
            System.out.println("\n\n\nConnection established :"+socket);
        ClientThread cthread= new ClientThread(socket);
       cthread.start();
     
        
        }
    }
    public static void main(String args[])throws IOException
    {new Seeder3();}
    
    class ClientThread extends Thread{
      Socket socket;  
     ClientThread(Socket s)
   {this.socket=s;}
     public void run(){
         try{
        DataInputStream in=new DataInputStream(socket.getInputStream());
        
       String filename=in.readUTF();
        int part=in.readInt();
        String filedest="F:\\seeder2\\"+filename;
        System.out.println("\nClient wants the file "+ filedest);
        File transferFile1;
        transferFile1 = new File(filedest);
        
        RandomAccessFile transferFile=new RandomAccessFile(transferFile1,"r");
        transferFile.seek((part*transferFile.length())/2);
       // System.out.println("Sending part "+part +" of file "+filename);
        
        byte []bytearray =new byte[(int)(transferFile.length()/2)];
        transferFile.read(bytearray, 0, bytearray.length);
       // FileInputStream fis=new FileInputStream(transferFile);
       // BufferedInputStream bis= new BufferedInputStream(fis);
       // bis.read(bytearray,0,bytearray.length);
        
        OutputStream os= socket.getOutputStream();
        os.write((int)(transferFile.length()));os.flush();
       // System.out.println("Sending file");
          System.out.println("\nSending part "+(part+1) +" of file "+filename);
        
        
        os.write(bytearray,0,bytearray.length);
        os.flush();
        socket.close();
        System.out.println("\nTransfer completed");
         }
         catch(IOException ex)
                 {System.out.println(ex.getMessage());}
        
    }}
}
