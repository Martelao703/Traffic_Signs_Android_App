package OBUSDK.PerEncDel;

public class A5 {
    protected byte[] a1001;
    protected int a1002;

    public A5(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Error");
        }
        a1002 = length;
        a1001 = new byte[(length + 7) / 8];
    }

    public A5(byte[] one, int two) {
        int num = (two + 7) / 8;
        a1002 = two;
        a1001 = new byte[num];
        System.arraycopy(one, 0, a1001, 0, two / 8);
        if (two % 8 > 0) {
            a1001[num - 1] = (byte)(one[num - 1] & (255 << (8 - two % 8)));
        }
    }

    public byte[] getA501() {
        return a1001;
    }

    public void setA501(byte[] value) {
        a1001 = value;
        a1002 = value.length * 8;
    }

    public int getA502() {
        return a1002;
    }

    public void setA502(int value) {
        a1002 = value;
        a1001 = new byte[(value + 7) / 8];
    }
}
