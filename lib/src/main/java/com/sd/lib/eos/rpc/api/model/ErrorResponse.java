package com.sd.lib.eos.rpc.api.model;

import java.util.List;

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
        private List<DetailsModel> details;

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

        public List<DetailsModel> getDetails()
        {
            return details;
        }

        public void setDetails(List<DetailsModel> details)
        {
            this.details = details;
        }
    }

    public static class DetailsModel
    {
        private String message;
        private String file;
        private String line_number;
        private String method;

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public String getFile()
        {
            return file;
        }

        public void setFile(String file)
        {
            this.file = file;
        }

        public String getLine_number()
        {
            return line_number;
        }

        public void setLine_number(String line_number)
        {
            this.line_number = line_number;
        }

        public String getMethod()
        {
            return method;
        }

        public void setMethod(String method)
        {
            this.method = method;
        }
    }
}
