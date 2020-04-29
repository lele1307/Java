package content;
/**
 * @author dukehan
 */
public interface Name {

    public static boolean parseName(String name){
        String regex = "^[a-z0-9A-Z_]+$";
        return name.matches(regex);
    }

    public static boolean parseNameList(String[] nameList){
        for (int i=0;i<nameList.length;i++){
            if (!Name.parseName(nameList[i])) {
                return false;
            }
        }
        return true;
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            count++;
        }
        return count;
    }
}
