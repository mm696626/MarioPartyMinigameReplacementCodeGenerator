package constants;

import java.util.HashMap;
import java.util.Map;

public class MinigameCategoryUIConstants {

    public static final String[] CATEGORY_NAMES = {
            "All",
            "4 Player",
            "4 Player Mic",
            "4 Player/Duel",
            "1v3",
            "1v3 Mic",
            "2v2",
            "2v2/Duel",
            "Battle",
            "Battle/Duel",
            "Duel",
            "Bowser",
            "1 Player Bowser",
            "DK",
            "1 Player DK",
            "1 Player",
            "8 Player"
    };

    public static final Map<Integer, String> MINIGAME_CATEGORY_MAP = new HashMap<>() {{
        put(-1, "All");
        put(0, "4 Player");
        put(1, "4 Player Mic");
        put(2, "4 Player/Duel");
        put(3, "1v3");
        put(4, "1v3 Mic");
        put(5, "2v2");
        put(6, "2v2/Duel");
        put(7, "Battle");
        put(8, "Battle/Duel");
        put(9, "Duel");
        put(10, "Bowser");
        put(11, "1 Player Bowser");
        put(12, "DK");
        put(13, "1 Player DK");
        put(14, "1 Player");
        put(15, "8 Player");
    }};
}
