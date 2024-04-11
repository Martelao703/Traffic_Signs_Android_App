package OBUSDK;

import java.nio.ByteBuffer;
import java.util.Date;

public class SafeByteConverter {
    public int countryCodeToInt32(byte[] byteArray) {
        byte[] temp = { byteArray[1], byteArray[0], 0, 0 };
        int value = ByteBuffer.wrap(temp).getInt();
        return (value >> (7 - 1)) & ((1 << 10) - 1);
    }

    public int languageToInt32(byte[] byteArray) {
        byte[] temp = { byteArray[1], byteArray[0], 0, 0 };
        int value = ByteBuffer.wrap(temp).getInt();
        return (value >> (7 - 1)) & ((1 << 10) - 1);
    }

    public Date toIVIDateTime(Long date) {
        // TODO - Init with ivi date time
        if (date != null) {
            return new Date(1980, 1, 1); // This will not compile, Date constructor deprecated
        } else {
            return new Date();
        }
    }
}
