public class NoteItem {
    String name;
    String enumName;
    char checked;

    //default constructor
    NoteItem(){
        name = "";
        enumName = "";
        checked = ' ';
    }

    //object constructor
    NoteItem(String name, String enumName, char checked){
        this.name = name;
        this.enumName = enumName;
        this.checked = checked;
    }

    //accessor methods
    public String getName() {
        return name;
    }

    public String getEnumName() {
        return enumName;
    }

    public char getChecked() {
        return checked;
    }

    //set the noteItem checked with a specified character
    private void setChecked(char checked) {
        this.checked = checked;
    }

    //if the player owns a specific card then the card is marked with an X
    public void setOwned(){
        setChecked('X');
    }

    //cards made seen to all
    public void setShared(){
        setChecked('A');
    }

    //if a player has seen a specific card this card is marked with an V
    public void setSeen(){
        setChecked('V');
    }


}
