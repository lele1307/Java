package commandtype;
import content.Name;
import process.Terminal;

import java.io.File;


/**
 * @author dukehan
 */
public class Drop extends CommonHandler {
    static final int CMDLEN = 3;
    static final int STRUCTURE = 1;
    private String fileName;
    private String structure;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            executeCommand(terminal,command);
            return terminal;
        }
        terminal.setOutput("ERROR: Drop parse fail!!");
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length!=CMDLEN){
            return false;
        }
        this.fileName = command[2];
        this.structure = command[STRUCTURE].toUpperCase();
        if(Name.parseName(fileName)){
            if ("DATABASE".equals(structure)|| "TABLE".equals(structure)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Terminal executeCommand(Terminal terminal, String[] command) {
        if (structure.equals("DATABASE")){
            dropDatabase(terminal,fileName);
        }
        else if (structure.equals("TABLE")){
            dropTable(terminal,fileName);
        }
        return terminal;
    }

    public Terminal delAllFile(Terminal terminal,File directory){
        if (!directory.isDirectory()){
            directory.delete();
            terminal.setOutput("OK");
        }else{
            File[] files = directory.listFiles();
            // empty directory
            if (files.length == 0){
                directory.delete();
                terminal.setOutput("OK");
            } else {
                // del son files and directories
                for (File file : files){
                    if (file.isDirectory()){
                        delAllFile(terminal,file);
                    } else {
                        file.delete();
                    }
                }
                // del itself
                directory.delete();
                terminal.setOutput("OK");
            }
        }
        return terminal;
    }

    public Terminal dropDatabase(Terminal terminal,String name){
        String pathname = terminal.delDatabase(name);
        if(pathname!=""){
            File target = new File(pathname);
            delAllFile(terminal,target);
        } else {
            terminal.setOutput("OK");
        }
        return terminal;
    }

    public Terminal dropTable(Terminal terminal,String name){
        String pathname = terminal.delDatabaseTable(name);
        if(pathname!=""){
            File target = new File(pathname);
            delAllFile(terminal,target);
        } else {
            terminal.setOutput("OK");
        }
        return terminal;
    }

}
