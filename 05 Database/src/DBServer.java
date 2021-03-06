import process.Command;
import process.Terminal;

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
            Terminal terminal = new Terminal();
            while(true) {
                acceptNextCommand(socket,terminal);
            }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private void acceptNextCommand(Socket socket,Terminal terminal) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in,out,terminal);
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private void processNextCommand (BufferedReader in, BufferedWriter out,Terminal terminal)
    {
        try {
            String line = in.readLine();
            if (line!=null){
                String resultSignal ="\n"+EOT+"\n";
                Command commandline = new Command(line,terminal);
                String result = terminal.getOutput();
                out.write( result + resultSignal);
                out.flush();
            }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}