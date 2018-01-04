package com.paxsz.dev;

public class SerialDev {
    public static int state = -1;
    public native int OpenDev(String device, byte[] cmd, int len);

    static
    {
        System.loadLibrary("SerialDev");
    }
}
