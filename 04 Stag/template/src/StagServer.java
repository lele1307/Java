import commands.Process;
import environments.*;
import java.io.*;
import java.net.*;

class StagServer
{
    public static void main(String[] args)
    {
        if(args.length != 2) {
            System.out.println("Usage: java StagServer <entity-file> <action-file>");
        } else {
            new StagServer(args[0], args[1], 8888);
        }
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            World world = new World(entityFilename,actionFilename);
            //map information
            /*
            System.out.println("================= Locations =================");
            System.out.println(Printout.printLocationsType(world.getLocations()));
            System.out.println("================= Path =================");
            Printout.printPathsType(world.getPaths());
            System.out.println("================= Actions =================");
            Printout.printActionsType(world.getActions());
            */
            while(true) {
                acceptNextConnection(ss,world);
            }
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss,World world)
    {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in,out,world);
            out.close();
            in.close();
            socket.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out,World world) throws IOException
    {

        String line = in.readLine();
        String playerName = separatePlayerName(line);
        Player player = new Player(playerName);
        world.setPlayers(player);

        String feedback;
        Process init = new Process();
        init.setProcess(line, world);
        feedback = init.interpreterCmd(world);
        out.write(feedback);
        //Printout.printPlayer(world.getPlayers());

    }

    private String separatePlayerName(String line){
        String[] newLine = line.split(" ");
        return newLine[0].substring(0,newLine[0].length()-1);
    }

}
