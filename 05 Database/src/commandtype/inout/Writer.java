package commandtype.inout;
import java.io.*;
/**
 * @author dukehan
 */
public class Writer {
    private File csv;
    public Writer(String pathname){
        this.csv = new File(pathname);
    }

    public boolean writeOneLineInFile(String data) {
        try {
            /* CSV file create */
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            /* Create new Row */
            bw.write(data);
            bw.newLine();
            bw.close();
            return true;
        } catch (FileNotFoundException e) {
            // File object create catch error
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter close catch error
            e.printStackTrace();
        }
        return false;
    }

    public String buildStrLine(String[] line){
        String newStr = "";
        for (int i=0; i<line.length;i++){
            newStr = newStr.concat(line[i]);
            if (i==line.length-1){
                break;
            }
            newStr = newStr.concat("\t,");
        }
        return newStr;
    }

    public void emptyFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
            /* Create new Row */
            bw.write("");
            bw.close();
        } catch (FileNotFoundException e) {
            // File object create catch error
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter close catch error
            e.printStackTrace();
        }
    }

    public boolean writeTableInFile(String[][]table){
        for (int i=0; i<table.length;i++){
            if(!writeOneLineInFile(buildStrLine(table[i]))){
                return false;
            }
        }
        return true;
    }

}
