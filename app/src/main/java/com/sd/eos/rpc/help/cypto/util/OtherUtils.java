package com.sd.eos.rpc.help.cypto.util;

public class OtherUtils
{
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}
