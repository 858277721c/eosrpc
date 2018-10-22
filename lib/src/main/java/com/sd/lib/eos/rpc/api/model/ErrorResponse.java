package com.sd.lib.eos.rpc.api.model;

public class ErrorResponse
{
    private int code;
    private String message;
    private ErrorModel error;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ErrorModel getError()
    {
        return error;
    }

    public void setError(ErrorModel error)
    {
        this.error = error;
    }

    public static class ErrorModel
    {
        private int code;
        private String name;
        private String what;

        public int getCode()
        {
            return code;
        }

        public void setCode(int code)
        {
            this.code = code;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getWhat()
        {
            return what;
        }

        public void setWhat(String what)
        {
            this.what = what;
        }
    }
}
