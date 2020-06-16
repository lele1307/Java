package commandtype;

import process.Terminal;

/**
 * @author dukehan
 */
public abstract class CommonHandler {
    /**
     * first parse Command, then execute Command
     * @return Terminal information Change
     */
    public abstract Terminal runCommand(Terminal terminal, String[] command);
    /**
     * Parse Command
     * @return Parse result
     */
    public abstract boolean parseCommand(String[] command);
    /**
     * Execute Command
     * @return executed result reset Terminal.setOut
     */
    public abstract Terminal executeCommand(Terminal terminal,String[] command);

}
