package com.example.its_app;

import java.util.HashMap;
import java.util.Map;

public class ImageListManager {
    private static ImageListManager instance; // Singleton instance
    private Map<Long, Integer> pictogramMap;

    private ImageListManager() {
        pictogramMap = new HashMap<>();
        pictogramMap.put(0L, R.drawable.image_not_found);
        pictogramMap.put(116L, R.drawable.image_180px_vienna_convention_road_sign_b1_v1);
        pictogramMap.put(815L, R.drawable.image_180px_vienna_convention_road_sign_e12aa_v1);
        pictogramMap.put(557L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_30);
        pictogramMap.put(558L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_40);
        pictogramMap.put(559L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_50);
        pictogramMap.put(268L, R.drawable.image_180px_vienna_convention_road_sign_aa_7a_v1);
    }

    public static synchronized ImageListManager getInstance() {
        if (instance == null) {
            instance = new ImageListManager();
        }
        return instance;
    }

    public int getDrawableId(long pictogramCategoryCode) {
        return pictogramMap.getOrDefault(pictogramCategoryCode, R.drawable.image_not_found);
    }
}