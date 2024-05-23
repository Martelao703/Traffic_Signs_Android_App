package OBUSDK.JsonData;

import java.io.Serializable;

public class PictogramCategoryCodeType implements Serializable {
    public int nature;
    public int serialNumber;

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object right) {
        if (right == null) {
            return false;
        }

        if (this == right) {
            return true;
        }

        if (getClass() != right.getClass()) {
            return false;
        }

        PictogramCategoryCodeType pictogramCategoryCodeType = (PictogramCategoryCodeType) right;
        return nature == pictogramCategoryCodeType.getNature() && serialNumber == pictogramCategoryCodeType.getSerialNumber();
    }
}
