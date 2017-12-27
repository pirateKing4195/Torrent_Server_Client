
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishu
 */
public class TorrentServer {
    public static Map<String,ArrayList<Integer>> idmap=new HashMap<String,ArrayList<Integer>>();
    
    public TorrentServer() throws IOException
    {   

        ArrayList<Integer> ad = new ArrayList<Integer>();
        idmap.put("a.txt",ad);
        idmap.get("a.txt").add(12345);
        idmap.get("a.txt").add(12346);
        ArrayList<Integer> bd = new ArrayList<Integer>();
        
        bd.add(12345);bd.add(12347);
        idmap.put("b.txt",ad);
      
       /*  for (Entry<String, ArrayList<Integer>> entry : idmap.entrySet()) {
        System.out.print(entry.getKey()+" | ");
        for(int fruitNo : entry.getValue()){
            System.out.print(fruitNo+" ");
        }
        System.out.println();
    }*/
        
        ServerSocket server=new ServerSocket(13665);
        while(true)
   {
       Socket client=server.accept();
       ClientThread cthread= new ClientThread(client);
       cthread.start();
       
       
   }
         }  
    public static void main(String args[])throws IOException{
    new TorrentServer();
    }
        
        
       
      class ClientThread extends Thread{
   Socket client;
   
   ClientThread(Socket s)
   {this.client=s;}
   public void run()
   {
    DataOutputStream out = null;
       DataInputStream in=null;
       try {
           
           in = new DataInputStream(client.getInputStream());
           out = new DataOutputStream(client.getOutputStream());
           String filename="a.txt";
          // String filename=in.readUTF();
           System.out.println("\n\nClient wants the file "+filename+"\n");
           if(idmap.containsKey(filename))
           {    ArrayList<Integer> p=(ArrayList<Integer>)idmap.get(filename);
           System.out.print("File is present with hosts : ");
           for (int i=0;i<p.size();i++)
           {System.out.printf("%d ,", p.get(i));
           out.writeInt(p.get(i));}
           
           out.writeInt(-1);
          System.out.println("");
           out.flush();
           
           }else
           {out.writeInt(-1);
           System.out.println("File is not available");}
             
       } catch (IOException ex) {
           System.out.println(ex.getMessage());
       } 
       
         
         
         
         finally {
          
                              try {
                                 out.close() ;in.close(); client.close();}
                              catch (IOException ex) {
                                System.out.println(ex.getMessage()); }}
   }
      
      
      } 

        
    }

    


