package constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class MinigameCategoryConstants {

    public static final int FOUR_PLAYER_MINIGAME = 0;
    public static final int FOUR_PLAYER_MIC_MINIGAME = 1;
    public static final int FOUR_PLAYER_DUEL_MINIGAME = 2; //literally only for the shared 4 Player/Duel minigames in 8
    public static final int ONE_V_THREE_MINIGAME = 3;
    public static final int ONE_V_THREE_MIC_MINIGAME = 4;
    public static final int TWO_V_TWO_MINIGAME = 5;
    public static final int TWO_V_TWO_DUEL_MINIGAME = 6; //literally only for the shared 2v2/Duel minigames in 8
    public static final int BATTLE_MINIGAME = 7;
    public static final int BATTLE_DUEL_MINIGAME = 8; //literally only for the shared Battle/Duel minigames in 8
    public static final int ITEM_MINIGAME = 9; //literally only for the shared Battle/Duel minigames in 8
    public static final int DUEL_MINIGAME = 10;
    public static final int BOWSER_MINIGAME = 11;
    public static final int ONE_PLAYER_BOWSER_MINIGAME = 12;
    public static final int DK_MINIGAME = 13;
    public static final int ONE_PLAYER_DK_MINIGAME = 14;
    public static final int ONE_PLAYER_MINIGAME = 15;
    public static final int EIGHT_PLAYER_MINIGAME = 16;
    public static final int GAME_GUY_MINIGAME = 17; //literally only for the Game Guy minigames in 3

    public static final Map<Integer, String> MINIGAME_CATEGORY_MAP = new LinkedHashMap<>() {{
        put(-1, "All");
        put(FOUR_PLAYER_MINIGAME, "4 Player");
        put(FOUR_PLAYER_MIC_MINIGAME, "4 Player Mic");
        put(FOUR_PLAYER_DUEL_MINIGAME, "4 Player/Duel");
        put(ONE_V_THREE_MINIGAME, "1v3");
        put(ONE_V_THREE_MIC_MINIGAME, "1v3 Mic");
        put(TWO_V_TWO_MINIGAME, "2v2");
        put(TWO_V_TWO_DUEL_MINIGAME, "2v2/Duel");
        put(BATTLE_MINIGAME, "Battle");
        put(BATTLE_DUEL_MINIGAME, "Battle/Duel");
        put(ITEM_MINIGAME, "Item");
        put(DUEL_MINIGAME, "Duel");
        put(BOWSER_MINIGAME, "Bowser");
        put(ONE_PLAYER_BOWSER_MINIGAME, "1 Player Bowser");
        put(DK_MINIGAME, "DK");
        put(ONE_PLAYER_DK_MINIGAME, "1 Player DK");
        put(ONE_PLAYER_MINIGAME, "1 Player");
        put(EIGHT_PLAYER_MINIGAME, "8 Player");
        put(GAME_GUY_MINIGAME, "Game Guy");
    }};

    public static final String[] MINIGAME_CATEGORY_NAMES = MINIGAME_CATEGORY_MAP.values().toArray(new String[0]);
}