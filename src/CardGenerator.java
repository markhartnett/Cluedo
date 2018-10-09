import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Class contains static method to generate list of cards at the start of the game
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnettrrd
 */

public final class CardGenerator {
    /**
     * Method used to generate all cards used in the game from the character, weapon and room enums.
     *
     * @param list the list to store all cards
     */
    public static void generate(ArrayList<Card> list){
        int arraySize = CharacterNames.values().length + WeaponTypes.values().length + (RoomType.values().length - 3);
        String[] array = new String[arraySize];
        int counter = 0;

        for (CharacterNames tmp: CharacterNames.values()){
            array[counter] = tmp.toString();
            counter++;
        }
        for (WeaponTypes tmp: WeaponTypes.values()){
            array[counter] = tmp.toString();
            counter++;
        }
        for (RoomType tmp: RoomType.values()){
            if (tmp != RoomType.NO_ROOM && tmp != RoomType.CORRIDOR && tmp != RoomType.CELLAR) {
                array[counter] = tmp.toString();
                counter++;
            }
        }

        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = array[index];
            array[index] = array[i];
            array[i] = a;
        }

        for (int i = 0; i < array.length; i++){
            list.add(new Card(array[i]));
        }
    }
}
