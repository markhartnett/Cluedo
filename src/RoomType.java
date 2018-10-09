
/*
 16310943 James Byrne
 16314763 Jakub Gajewski
 16305706 Mark Hartnett
 */

// enum for the type of room a tile is of
public enum RoomType {
    MC_KITCHEN, MC_LIVINGROOM, R_OFFICE, CENTRALPERK, CELLAR, GELLERHOUSE, J_KITCHEN, J_LIVINGROOM, P_APARTMENT, ALESANDROS, CORRIDOR, NO_ROOM;

    public static RoomType getValue(String str) {
        switch (str.trim().toLowerCase()) {
            case "monica and chandlers livingroom (mc livingroom)":
            case "monica & chandlers kitchen":
            case "monica + chandlers kitchen":
            case "monica and chandlers kitchen":
            case "monica & chandler's kitchen":
            case "monica + chandler's kitchen":
            case "monica and chandler's kitchen":
            case "mc kitchen":
            case "mc_kitchen":
                return RoomType.MC_KITCHEN;
            case "monica and chandlers kitchen (mc kitchen)":
            case "monica & chandlers living room":
            case "monica + chandlers living room":
            case "monica and chandlers living room":
            case "monica & chandler's living room":
            case "monica + chandler's living room":
            case "monica and chandler's living room":
            case "monica & chandlers livingroom":
            case "monica + chandlers livingroom":
            case "monica and chandlers livingroom":
            case "monica & chandler's livingroom":
            case "monica + chandler's livingroom":
            case "monica and chandler's livingroom":
            case "mc livingroom":
            case "mc_livingroom":
                return RoomType.MC_LIVINGROOM;
            case "rachel's office":
            case "rachels office":
            case "r office":
            case "r_office":
                return RoomType.R_OFFICE;
            case "central perk":
            case "centralperk":
                return RoomType.CENTRALPERK;
            case "the geller household":
            case "geller household":
            case "gellerhouse":
                return RoomType.GELLERHOUSE;
            case "joey's kitchen":
            case "joeys kitchen":
            case "j kitchen":
            case "j_kitchen":
                return RoomType.J_KITCHEN;
            case "joey's living room":
            case "joeys living room":
            case "joey's livingroom":
            case "joeys livingroom":
            case "j livingroom":
            case "j_livingroom":
                return RoomType.J_LIVINGROOM;
            case "phoebe's apartment":
            case "phoebes apartment":
            case "p apartment":
            case "p_apartment":
                return RoomType.P_APARTMENT;
            case "allesandros":
            case "alesandros":
            case "allesandro's":
            case "alesandro's":
                return RoomType.ALESANDROS;
            default:
                return null;
        }
    }
}

