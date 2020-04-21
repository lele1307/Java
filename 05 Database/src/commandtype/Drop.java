package commandtype;
import content.Name;
import process.Terminal;

import java.io.File;


/**
 * @author dukehan
 */
public class Drop {
    final static int CMDLEN = 3;
    final static int STRUCTURE = 1;
    private String fileName;
    private String structure;
    public Terminal cmdDrop(Terminal terminal, String[] command){
        this.fileName = command[2];
        this.structure = command[STRUCTURE].toUpperCase();
        if (parseCmd(command)){
            compileDrop(terminal);
            return terminal;
        }
        terminal.setOutput("Drop parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN && Name.parseName(fileName)){
            if ("DATABASE".equals(structure)|| "TABLE".equals(structure)){
                return true;
            }
        }
        return false;
    }

    public Terminal compileDrop(Terminal terminal){
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
            terminal.setOutput("OK Drop TABLE");
        }else{
            File[] files = directory.listFiles();
            // empty directory
            if (files.length == 0){
                directory.delete();
                terminal.setOutput("OK Drop DATABASE ");
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
                terminal.setOutput("OK Drop DATABASE");
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
            terminal.setOutput("ERROR: Please check database status!!");
        }
        return terminal;
    }

    public Terminal dropTable(Terminal terminal,String name){
        String pathname = terminal.delDatabaseTable(name);
        System.out.println("tablepath: |"+pathname+"|");
        if(pathname!=""){
            File target = new File(pathname);
            delAllFile(terminal,target);
        } else {
            terminal.setOutput("ERROR: Please check table status!!");
        }
        return terminal;
    }

}
