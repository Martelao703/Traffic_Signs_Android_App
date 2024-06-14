package com.example.its_app;


import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagelistIndexer {
    private Map<Long, Integer> pictogramMap;

    public ImagelistIndexer(List<Drawable> signalImageList, List<SignalCode> signalCodes) {
        pictogramMap = new HashMap<>();
        // Populate the map with pictogram category codes and corresponding drawable resources
        pictogramMap.put(0L, R.drawable.image_not_found);
        pictogramMap.put(116L, R.drawable.image_180px_vienna_convention_road_sign_b1_v1);
        pictogramMap.put(815L, R.drawable.image_180px_vienna_convention_road_sign_e12aa_v1);
        pictogramMap.put(557L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_30);
        pictogramMap.put(558L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_40);
        pictogramMap.put(559L, R.drawable.image_180px_vienna_convention_road_sign_c14_v1_50);
        // Add more mappings as required
    }

    public int getDrawableId(long pictogramCategoryCode) {
        return pictogramMap.getOrDefault(pictogramCategoryCode, R.drawable.image_not_found); // Fallback image
    }
}