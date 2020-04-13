package commands;
import java.util.Arrays;

/**
 * @author dukehan
 */
public class CmdTrigger extends Command {
    static final String INVALID_SUBJECTS_LOCATION = "[-1, -1]";
    public boolean cmpTriggerCmd(int[][] subjectsLocation){
        for (int i=0; i<subjectsLocation.length; i++){
            if ((Arrays.toString(subjectsLocation[i]).equals(INVALID_SUBJECTS_LOCATION))){
                return false;
            }
        }
        return true;
    }

}
