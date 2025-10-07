package constants;

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
    public static final int DUEL_MINIGAME = 9;
    public static final int BOWSER_MINIGAME = 10;
    public static final int ONE_PLAYER_BOWSER_MINIGAME = 11;
    public static final int DK_MINIGAME = 12;
    public static final int ONE_PLAYER_DK_MINIGAME = 13;
    public static final int ONE_PLAYER_MINIGAME = 14;
    public static final int EIGHT_PLAYER_MINIGAME = 15;

    public static final Map<Integer, String> MINIGAME_CATEGORY_MAP = Map.ofEntries(
            Map.entry(-1, "All"),
            Map.entry(FOUR_PLAYER_MINIGAME, "4 Player"),
            Map.entry(FOUR_PLAYER_MIC_MINIGAME, "4 Player Mic"),
            Map.entry(FOUR_PLAYER_DUEL_MINIGAME, "4 Player/Duel"),
            Map.entry(ONE_V_THREE_MINIGAME, "1v3"),
            Map.entry(ONE_V_THREE_MIC_MINIGAME, "1v3 Mic"),
            Map.entry(TWO_V_TWO_MINIGAME, "2v2"),
            Map.entry(TWO_V_TWO_DUEL_MINIGAME, "2v2/Duel"),
            Map.entry(BATTLE_MINIGAME, "Battle"),
            Map.entry(BATTLE_DUEL_MINIGAME, "Battle/Duel"),
            Map.entry(DUEL_MINIGAME, "Duel"),
            Map.entry(BOWSER_MINIGAME, "Bowser"),
            Map.entry(ONE_PLAYER_BOWSER_MINIGAME, "1 Player Bowser"),
            Map.entry(DK_MINIGAME, "DK"),
            Map.entry(ONE_PLAYER_DK_MINIGAME, "1 Player DK"),
            Map.entry(ONE_PLAYER_MINIGAME, "1 Player"),
            Map.entry(EIGHT_PLAYER_MINIGAME, "8 Player")
    );

    public static final String[] MINIGAME_CATEGORY_NAMES = MINIGAME_CATEGORY_MAP.values().toArray(new String[0]);
}