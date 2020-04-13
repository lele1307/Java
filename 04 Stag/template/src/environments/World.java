package environments;

import actions.Actions;
import entities.Locations;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import java.io.*;
import java.util.ArrayList;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


/**
 * @author dukehan
 */
public class World {
    private Parser dotParser;
    private JSONArray actionsJson = new JSONArray();
    private ArrayList<Actions> actions = new ArrayList<Actions>();
    private ArrayList<Locations>locations = new ArrayList<Locations>();
    private ArrayList<Paths>paths = new ArrayList<Paths>();
    private ArrayList<Player>players = new ArrayList<Player>();
    private String directions;
    private int triggerStatus;
    private int inputStatus;

    public World(String dotPath,String jsonPath){
        File dot = new File(dotPath);
        File json = new File(jsonPath);
        try {
            dotParser = new Parser();
            FileReader dotReader = new FileReader(dot);
            dotParser.parse(dotReader);
            parseDot();
            setPath();
        }catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }catch (com.alexmerz.graphviz.ParseException pe) {
            System.out.println(pe);
        }
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader jsonReader = new FileReader(json);
            JSONObject object = (JSONObject) jsonParser.parse(jsonReader);
            actionsJson = (JSONArray) object.get("actions");
            parseJson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.directions = "Nothing Feedback!";
    }

    public void parseDot(){
        String type;
        ArrayList<Graph> graphs = dotParser.getGraphs();
        ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
        for(Graph g : subGraphs){
            //locations  paths
            ArrayList<Graph> subGraphs1 = g.getSubgraphs();
            for (Graph g1 : subGraphs1){
                ArrayList<Node> nodesLoc = g1.getNodes(false);
                Node nLoc = nodesLoc.get(0);
                //cluster -> location_name
                Locations x = new Locations(nLoc.getId().getId(),nLoc.getAttribute("description"));
                x.setCluster(g1.getId().getId());
                ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
                for (Graph g2 : subGraphs2) {
                    //entitiesTypeName
                    type = g2.getId().getId();
                    ArrayList<Node> nodesEnt = g2.getNodes(false);
                    for (Node nEnt : nodesEnt) {
                        //something in entitiesType
                        x.setTypeArr(type,nEnt.getId().getId(),nEnt.getAttribute("description"));
                    }
                }
                locations.add(x);
            }
            ArrayList<Edge> edges = g.getEdges();
            for (Edge e : edges){
                //Path
                Paths y = new Paths(e.getSource().getNode().getId().getId(),e.getTarget().getNode().getId().getId());
                paths.add(y);
            }
        }
    }

    public void setPath(){
        for (int i=0; i<paths.size(); i++){
            for (int j=0; j<locations.size(); j++){
                if (locations.get(j).getName().equals(paths.get(i).getStart())){
                    locations.get(j).getPlaces().add(paths.get(i).getTarget());
                }
            }
        }
    }

    public void parseJson(){
        for (int i=0; i<actionsJson.size(); i++){
            ArrayList<String> triggers = new ArrayList<String>();
            ArrayList<String> subjects = new ArrayList<String>();
            ArrayList<String> consumed = new ArrayList<String>();
            ArrayList<String> produced = new ArrayList<String>();
            String narration;
            JSONObject actionType = new JSONObject();
            actionType = (JSONObject) actionsJson.get(i);
            setActionTypeArr(actionType.get("triggers"),triggers);
            setActionTypeArr(actionType.get("subjects"),subjects);
            setActionTypeArr(actionType.get("consumed"),consumed);
            setActionTypeArr(actionType.get("produced"),produced);
            narration = actionType.get("narration").toString();
            Actions act = new Actions(triggers,subjects,consumed,produced,narration);
            actions.add(act);
        }

    }

    public void setActionTypeArr(Object actionType,ArrayList<String> type){
        JSONArray container = new JSONArray();
        container = (JSONArray) actionType;
        for (int j=0; j<container.size(); j++){
            type.add(container.get(j).toString());
        }
    }

    public void setPlayers(Player player){
        if (players.size()!=0){
            for (int i=0; i<players.size();i++){
                if (player.getName().equals(players.get(i).getName())){
                    //System.out.println("currHAVE Player: "+player.getName());
                    return;
                }
            }
        }
        //System.out.println("currADD Player: "+player.getName());
        players.add(player);
    }

    public ArrayList<Actions> getActions(){
        return actions;
    }

    public ArrayList<Locations> getLocations() {
        return locations;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getDirections(){
        return directions;
    }

    public Locations getUnplaced(int index){
        return locations.get(index);
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(int triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public int getInputStatus() {
        return inputStatus;
    }

    public void setInputStatus(int inputStatus) {
        this.inputStatus = inputStatus;
    }
}
