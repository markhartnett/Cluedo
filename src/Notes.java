/*
 *  Class that holds the info of all the cards that are to be used to be guessed
 *  and sets displays whether or not these cards are owned seen
 */
import javax.swing.*;
import java.awt.*;

public class Notes {
    private NoteItem[] notePlayers = new NoteItem[6];
    private NoteItem[] noteWeapons = new NoteItem[6];
    private NoteItem[] noteRooms = new NoteItem[9];
    private JFrame notes;
    
    //constructor sets all cards as a blank char as they have not been seen yet
    Notes(){
        notePlayers[0]=new NoteItem("Ross", "ROSS", ' ');
        notePlayers[1]=new NoteItem("Phoebe", "PHOEBE",' ');
        notePlayers[2]=new NoteItem("Joey", "JOEY",' ');
        notePlayers[3]=new NoteItem("Monica", "MONICA", ' ');
        notePlayers[4]=new NoteItem("Rachel", "RACHEL", ' ');
        notePlayers[5]=new NoteItem("Chandler", "CHANDLER", ' ');
        
        noteWeapons[0]=new NoteItem("Pistol", "PISTOL", ' ');
        noteWeapons[1]=new NoteItem("Wrench", "WRENCH", ' ');
        noteWeapons[2]=new NoteItem("Dagger", "DAGGER", ' ');
        noteWeapons[3]=new NoteItem("Candlestick", "CANDLESTICK", ' ');
        noteWeapons[4]=new NoteItem("Rope", "ROPE", ' ');
        noteWeapons[5]=new NoteItem("Pipe", "PIPE", ' ');
        
        noteRooms[0]=new NoteItem("Monica + Chandler's Kitchen", "MC_KITCHEN", ' ');
        noteRooms[1]=new NoteItem("Central Perk", "CENTRALPERK", ' ');
        noteRooms[2]=new NoteItem("Joey's Kitchen", "J_KITCHEN", ' ');
        noteRooms[3]=new NoteItem("Monica & Chandler's Living Room", "MC_LIVINGROOM", ' ');
        noteRooms[4]=new NoteItem("Rachel's Office", "R_OFFICE", ' ');
        noteRooms[5]=new NoteItem("Geller Household", "GELLERHOUSE", ' ');
        noteRooms[6]=new NoteItem("Allesandro's", "ALESANDROS", ' ');
        noteRooms[7]=new NoteItem("Phoebe's Apartment", "P_APARTMENT", ' ');
        noteRooms[8]=new NoteItem("Joey's Living Room", "J_LIVINGROOM", ' ');
    }
    
    //creates a jframe to display the notes
    public void showNotes(Player playerRequesting){
        //initialize jframe and set dimensions etc
        notes = new JFrame("Notes of " + playerRequesting.getPlayerName());
        notes.setSize(400, 500);
        notes.setLocation(1000,0);
        notes.setResizable(false);
        
        notes.setLayout(new GridLayout(31,2));
        
        //create header for the suspect cards
        notes.add(new JLabel("Suspects"));
        notes.add(new JLabel("  Found"));
        notes.add(new JLabel(""));
        notes.add(new JLabel(""));
        //add player name and whether or not they hae been checked off on the notes
        for(int i=0; i<6; i++){
            notes.add(new JLabel(notePlayers[i].getName()));
            notes.add(new JLabel(" " + notePlayers[i].getChecked() + "\n"));
        }
        notes.add(new JLabel(""));
        notes.add(new JLabel(""));
        
        //create header for the suspect weapon cards
        notes.add(new JLabel("Weapons"));
        notes.add(new JLabel("  Found"));
        notes.add(new JLabel(""));
        notes.add(new JLabel(""));
        //add weapon and whether or not they hae been checked off on the notes
        for(int i=0; i<6; i++){
            notes.add(new JLabel(noteWeapons[i].getName()));
            notes.add(new JLabel(" " + noteWeapons[i].getChecked() + "\n"));
        }
        notes.add(new JLabel(""));
        notes.add(new JLabel(""));
        
        //create header for the suspected room cards
        notes.add(new JLabel("Rooms"));
        notes.add(new JLabel("  Found"));
        notes.add(new JLabel(""));
        notes.add(new JLabel(""));
        //add rooms and whether or not they hae been checked off on the notes
        for(int i=0; i<9; i++){
            notes.add(new JLabel(noteRooms[i].getName()));
            notes.add(new JLabel(" " + noteRooms[i].getChecked() + "\n"));
        }
        
        notes.setVisible(true);
    }
    
    public NoteItem getNoteItem(String enumName) {
        for (int i = 0; i < notePlayers.length; i++){
            if (notePlayers[i].getEnumName().equals(enumName))
                return notePlayers[i];
        }
        for (int i = 0; i < noteRooms.length; i++){
            if (noteRooms[i].getEnumName().equals(enumName))
                return noteRooms[i];
        }
        for (int i = 0; i < noteWeapons.length; i++){
            if (noteWeapons[i].getEnumName().equals(enumName))
                return noteWeapons[i];
        }
        return null;
    }

    public void setPlayerChecked(String enumName) {
        for (int i = 0; i < notePlayers.length; i++) {
            if (notePlayers[i].getEnumName().equals(enumName))
                notePlayers[i].setSeen();
        }
    }

    public void setRoomChecked(String enumName) {
        for (int i = 0; i < noteRooms.length; i++) {
            if (noteRooms[i].getEnumName().equals(enumName))
                noteRooms[i].setSeen();
        }
    }

    public void setWeaponChecked(String enumName) {
        for (int i = 0; i < noteWeapons.length; i++){
            if (noteWeapons[i].getEnumName().equals(enumName))
                noteWeapons[i].setSeen();
        }
    }
}
