package constants;

import java.util.HashMap;
import java.util.Map;

public class MinigameCategoryUIConstants {

    public static final String[] CATEGORY_NAMES = {
            "All",
            "4 Player",
            "1v3",
            "2v2",
            "Battle",
            "Duel",
            "Bowser",
            "DK",
            "Story",
            "Extra",
            "8 Player",
            "1 Player",
            "1 Player Bowser",
            "1 Player DK",
            "4 Player/Duel",
            "2v2/Duel",
            "Battle/Duel"
    };

    public static final Map<Integer, String> MINIGAME_CATEGORY_MAP = new HashMap<>() {{
        put(-1, "All");
        put(0, "4 Player");
        put(1, "1v3");
        put(2, "2v2");
        put(3, "Battle");
        put(4, "Duel");
        put(5, "Bowser");
        put(6, "DK");
        put(7, "Story");
        put(8, "Extra");
        put(9, "8 Player");
        put(10, "1 Player");
        put(11, "1 Player Bowser");
        put(12, "1 Player DK");
        put(13, "4 Player/Duel");
        put(14, "2v2/Duel");
        put(15, "Battle/Duel");
    }};
}
