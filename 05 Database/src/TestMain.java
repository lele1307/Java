import process.Command;
import process.Terminal;

import java.util.Scanner;
/**
 * @author dukehan
 */
public class TestMain implements PrintOut {
    public static void main(String[] args)
    {
        System.out.println("TestMain test start!!!!");
        Terminal terminal = new Terminal();
        System.out.println(terminal.getOutput());
        //stimulate cmd input
        String cmd;
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            cmd = scan.nextLine();
            System.out.println("SQL:> "+cmd);
            System.out.println("================= Command =================");
            Command commandline = new Command(cmd,terminal);
            System.out.println("ProcessResult: "+terminal.getOutput());
            System.out.println("--------------------------------------------");
            PrintOut.printCurrent(terminal);
            PrintOut.printAllDb(terminal);
            System.out.println("================= Command End ==============");
        }
        scan.close();

    }
}
