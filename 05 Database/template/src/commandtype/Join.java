package commandtype;

import process.Terminal;

public class Join {
    final static int CMDLEN = 8;
    final static int ON = 4;
    final static int[] AND = {2,6};
    public Terminal cmdJoin(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Join parse OK");
            return terminal;
        }
        terminal.setOutput("Join parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            String and1 = command[AND[0]];
            String and2 = command[AND[1]];
            String on = command[ON];
            if ("AND".equals(and1) && "ON".equals(on) && "AND".equals(and2)){
                return true;
            }
        }
        return false;
    }
}
