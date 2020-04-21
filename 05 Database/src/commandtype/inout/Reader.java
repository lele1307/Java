package commandtype.inout;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author dukehan
 */
public class Reader {
    private File csvTarget;
    private String line;
    private String[][] tableContent;
    private int rawIndex;
    private boolean exists;
    public Reader(String pathname){
         this.csvTarget = new File(pathname);
         if(csvTarget.exists()){
             this.rawIndex = getTableLines();
             this.line ="";
             this.tableContent = new String[rawIndex][];
             this.exists = true;
         }else{
             this.exists = false;
         }
    }

    public int getTableLines(){
        int rawLines = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvTarget));
            while ((line = br.readLine())!= null) {
                rawLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawLines;
    }

    public void readAllTable(){
        int rawLines = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvTarget));
            while ((line = br.readLine())!= null) {
                String[] raw = line.split("\t,");
                tableContent[rawLines] = raw;
                rawLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] readFirstLine(){
        String[] raw = new String[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvTarget));
            line = br.readLine();
            raw = line.split(",");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raw;
    }

    public void printTable(String[][] target){
        for (int i=0;i<target.length;i++){
            System.out.println(Arrays.toString(target[i]));
        }
    }

    public String[][] getTableContent(){
        return tableContent;
    }

    public String readColumn(String[] attributes) {
        ArrayList<Integer> attributesIndex = getAttributesIndex(attributes);
        String outLine = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvTarget));
            while ((line = br.readLine())!= null) {
                String[] cols = line.split(",");
                outLine = outLine.concat(rebuildStringArr(cols,attributesIndex)+"\n");
            }
        } catch (FileNotFoundException e) {
            // File object create catch error
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outLine;
    }

    public ArrayList<Integer> getAttributesIndex(String[] attributes){
        String[] tableCurrAttribute = readFirstLine();
        /*for (int i = 0; i<tableCurrAttribute.length; i++){
            System.out.println(tableCurrAttribute[i]);
        }*/
        ArrayList<Integer> attributesIndex = new ArrayList<Integer>();
        if (attributes.length==1&&attributes[0].equals("*")){
            for (int i=0;i<tableCurrAttribute.length;i++){
                attributesIndex.add(i);
            }
        } else {
            for (int i=0;i<tableCurrAttribute.length;i++){
                for (int j=0; j<attributes.length;j++){
                    if (tableCurrAttribute[i].equals(attributes[j])){
                        attributesIndex.add(i);
                    }
                }
            }
        }
        return attributesIndex;
    }

    public String rebuildStringArr(String[] cols,ArrayList<Integer> attributesIndex){
        String newStr = "";
        for (int i=0;i<attributesIndex.size();i++){
            newStr = newStr.concat(cols[attributesIndex.get(i)]+" ");
        }
        return newStr;
    }

    public boolean isExists() {
        return exists;
    }
}
