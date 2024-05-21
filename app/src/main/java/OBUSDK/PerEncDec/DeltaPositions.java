package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DeltaPositions {
    @SerializedName("DeltaPosition")
    private List<DeltaPosition> deltaPosition;

    public List<DeltaPosition> getDeltaPosition() {
        return deltaPosition;
    }

    public void setDeltaPosition(List<DeltaPosition> deltaPosition) {
        this.deltaPosition = deltaPosition;
    }
}
