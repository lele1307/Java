package actions;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Actions {

    private ArrayList<String> triggers = new ArrayList<String>();

    private ArrayList<String> subjects = new ArrayList<String>();

    private ArrayList<String> consumed = new ArrayList<String>();

    private ArrayList<String> produced = new ArrayList<String>();

    private String narration;

    public Actions(ArrayList<String> triggers,ArrayList<String> subjects,ArrayList<String> consumed,ArrayList<String> produced,String narration){
        this.triggers = triggers;
        this.subjects = subjects;
        this.consumed = consumed;
        this.produced = produced;
        this.narration = narration;
    }

    public ArrayList<String> getFixAction(String actionName){
        switch (actionName){
            case "triggers":
                return triggers;
            case "subjects":
                return subjects;
            case "consumed":
                return consumed;
            case "produced":
                return produced;
            default:
                return null;
        }
    }

    public String getNarration(){
        return narration;
    }

}
