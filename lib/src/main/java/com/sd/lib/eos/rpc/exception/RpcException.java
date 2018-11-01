package com.sd.lib.eos.rpc.exception;

public class RpcException extends Exception
{
    public RpcException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public String toString()
    {
        String result = super.toString();

        final Throwable throwable = getCause();
        if (throwable != null)
            result = result + " " + throwable.toString();

        return result;
    }
}
