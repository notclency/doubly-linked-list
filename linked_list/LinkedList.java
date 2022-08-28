import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinkedList{

    static int player_count[] = {0};
    
    static int size = 4;
    static Node[] teamNodes = new Node[size];
    String[] teamNames = {"TEAM CANDA: ", "TEAM FINLAND: ", "TEAM USA: ", "INJURY RESERVE: "};

    /* 
     Converts a teams char to its integer position 
    */
    public int teamCharToInt(char team) {
        
        if(team == 'C'){
            return 0;
        }else if(team == 'F'){
            return 1;
        }else if(team == 'U'){
            return 2;
        }

        return 3;
    }

    /*
     Converts a teams int to its char 
    */
    private char teamIntToInt(int newTeam) {
        if(newTeam == 0){
            return 'C';
        }else if(newTeam == 1){
            return 'F';
        }else if(newTeam == 2){
            return 'U';
        }

        return 'I';
    }

    /*
     searches for node in the linked list using its position number on the list 
    */
    public Node search(int position) {
        
        Node node;
        int searchIndex = 1;
        boolean found = false;

        if(position >= 1 && position <= player_count[0]){
            for(int i = 0; i < size; i++){
                node = teamNodes[i];
                while(node != null && !found){
                    if(searchIndex == position){
                        found = true;
                        return node;
                    }else{
                        node = node.nextNode;
                        searchIndex ++;
                    }
                }
            }
        }
        

        return null;
    }

    /*
     linkes a node in the linked list in it's appropiate team
    */
    public void link(Node playerNode, int teamInt) {

        if(teamNodes[teamInt] == null){
            teamNodes[teamInt] = playerNode;
        }else{
            Node head = teamNodes[teamInt];
            Node trans;
            
            while(true){
                trans = head.nextNode;
                if(trans == null){
                    head.nextNode = playerNode;
                    head.nextNode.prevNode = head;
                    return;
                }
                head = head.nextNode;
                
            }
        }
    }

    /*
     deltes a node in the linked list
    */
    public void delete(Node playerNode){
        
        unlink(playerNode);
        delete(playerNode);
    }

    /*
     unlinks a node from the linked list, setting is nextNode and prevNode to null. !doesnt delete the node! 
    */
    public Node unlink(Node playerNode) {
        
        int team  = teamCharToInt(playerNode.toPlayer.team);


        if(playerNode.prevNode == null && playerNode.nextNode != null){ // first & not alone
            teamNodes[team] = playerNode.nextNode;
            playerNode.nextNode.prevNode = teamNodes[team];
            playerNode.nextNode = null;
            playerNode.prevNode = null;

        }else if(playerNode.prevNode != null && playerNode.nextNode != null){  // middle & not alone
            playerNode.nextNode.prevNode = playerNode.prevNode;
            playerNode.prevNode.nextNode = playerNode.nextNode;

            playerNode.nextNode = null;
            playerNode.nextNode = null;
        }else if(playerNode.prevNode == null && playerNode.nextNode == null){ // first & alone
            teamNodes[team] = null;
            playerNode.nextNode = null;
            playerNode.prevNode = null;
        }else if(playerNode.prevNode != null && playerNode.nextNode == null){ // last
            playerNode.prevNode.nextNode = null;

            playerNode.nextNode = null;
            playerNode.nextNode = null;
        }

        return playerNode;
    }

    /*
     moves a node from one teams linked list to another 
    */
    public void move(Node playerNode, int newTeam){
        unlink(playerNode);
        playerNode.toPlayer.team = teamIntToInt(newTeam);
        link(playerNode, newTeam);
    }
 
    /*
     reads a file and its data.
    */
    public void read_file(Node[] teamNodes, int[] player_count) throws FileNotFoundException{
        File file = new File("C:\\Users\\PC\\Desktop\\proj\\myGit\\Linked-List\\doubly-linked-list\\linked_list\\team.txt");
        Scanner readFile = new Scanner(file);
        load(teamNodes, readFile);
    }

    /* 
     builds a linked list from data read from a file 
    */
    public void load(Node[] teamNodes, Scanner file) {
        
        char team;
        char position;
        int goals;
        int assists;
        int goals_ga;
        String name;

        int teamInt;

        while(file.hasNextLine()){
            team = file.next().charAt(0);
            position = file.next().charAt(0);
            goals = file.nextInt();
            assists = file.nextInt();
            goals_ga = file.nextInt();
            name = file.nextLine();

            Node node = new Node();
            player player = new player(team, position, goals, assists, goals_ga, name);
            node.toPlayer = player;
            teamInt = teamCharToInt(team);
            player_count[0]++;
            link(node, teamInt);
            
        }
    }

    /* 
     displays and data in the linked list in an organised manner. 
    */
    public void print_players(Node[] teamNodes) {

        int count = 1;

        for(int i = 0; i < size; i++){
            Node trans = teamNodes[i];
            System.out.println("\n" + teamNames[i] + "\n");
            while(trans != null){  
                System.out.print(count);    
                System.out.println(trans);
                trans = trans.nextNode;
                count ++;

            }
        }
        System.out.println("\n");
    }

    public static void main(String[] args) throws FileNotFoundException {
        LinkedList mylist = new LinkedList();

        mylist.read_file(teamNodes, player_count);                   // reads and builds the doubly linked list
        mylist.move(mylist.search(1), 1);
        mylist.print_players(teamNodes);

    }

    /* 
     A player node. 
    */
    class Node{
        player toPlayer;
        Node nextNode;
        Node prevNode;

        Node(){
            this.toPlayer = null;
            this.nextNode = null;
            this.nextNode = null;
        }

        public String toString(){
            
            if(this.toPlayer.position == 'g'){
                return ")" + this.toPlayer.name + "  Goals " + this.toPlayer.goals + "  Assists " + this.toPlayer.assists + "  goals against " + this.toPlayer.goals_ga;
            }else{
                return ")" + this.toPlayer.name + "  Goals " + this.toPlayer.goals + "  Assists " + this.toPlayer.assists;
            }
        }
    }

    /* 
     A class that holds data for individual players. 
    */
    class player{
        
        char team;
        char position;
        int goals;
        int assists;
        int goals_ga;
        String name;

        player(char team, char position, int goals, int assists, int goals_ga, String name){
            this.team = team;
            this.position = position;
            this.goals = goals;
            this.assists = assists;
            this.goals_ga = goals_ga;
            this.name = name;
        }
    }
}