import java.io.*;
import java.net.*;


class DBServer
{
    final static char EOT = 4;
    public static void main(String[] args)
    {
         new DBServer(8888);
    }

    public DBServer(int portNumber)
    {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            Socket socket = ss.accept();
            System.out.println("Server accepting");
            while(true) {
                acceptNextCommand(socket);
            }
        } catch (SocketException e){
            System.err.println(e);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private void acceptNextCommand(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in,out);
        } catch (SocketException e){
            System.err.println(e);
        }catch(IOException ioe) {
            System.err.println(ioe);
        }
    }
    private void processNextCommand (BufferedReader in, BufferedWriter out)
    {
        try {
            String line = in.readLine();
            System.out.println("line: "+line);
            String resultSignal ="\n"+EOT+"\n";
            out.write("Your commandLine111: "+ line + resultSignal);
            out.flush();
        }catch (SocketException e){
            System.err.println(e);
        }catch(IOException ioe) {
            System.err.println(ioe);
        }

    }
}