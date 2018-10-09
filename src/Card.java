/*
 * Class represents cards dealt to all players at the start of the game which contain characters/weapons/rooms
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Card {
    private String enumName;
    
    public Card(String enumName){
        this.enumName = enumName;
    }
    
    public String getEnumName(){
        return enumName;
    }
    
    public String getName(){
        switch (enumName){
            case "JOEY" : return "Joey";
            case "ROSS" : return "Ross";
            case "MONICA" : return "Monica";
            case "RACHEL" : return "Rachel";
            case "PHOEBE" : return "Phoebe";
            case "CHANDLER" : return "Chandler";
            case "MC_KITCHEN" : return "Monica + Chandlers Kitchen";
            case "MC_LIVINGROOM" : return "Monica + Chandlers Living Room";
            case "R_OFFICE" : return "Rachel's Office";
            case "CENTRALPERK" : return "Central Perk";
            case "GELLERHOUSE" : return "Geller Household";
            case "J_KITCHEN" : return "Joey's Kitchen";
            case "J_LIVINGROOM" : return "Joey's Living Room";
            case "P_APARTMENT" : return "Phoebe's Apartment";
            case "ALESANDROS" : return "Allesandros";
            case "ROPE" : return "Rope";
            case "DAGGER" : return "Dagger";
            case "WRENCH" : return "Wrench";
            case "PISTOL" : return "Pistol";
            case "CANDLESTICK" : return "Candlestick";
            case "PIPE" : return "Pipe";
            default: return enumName;
        }
    }
    
    @Override
    public String toString(){
        return enumName;
    }
}
