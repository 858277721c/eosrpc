package com.sd.lib.eos.rpc.exception;

public class RpcException extends Exception
{
    public RpcException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage()
    {
        final Throwable throwable = getCause();
        if (throwable != null)
            return throwable.getMessage();

        return super.getLocalizedMessage();
    }

    @Override
    public String toString()
    {
        final Throwable throwable = getCause();
        return throwable == null ? super.toString() : super.toString() + " " + throwable.toString();
    }
}
