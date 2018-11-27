package com.sd.lib.eos.rpc.api.model;

import java.util.List;

public class ErrorResponse
{
    private int code;
    private String message;
    private Error error;

    public String getErrorInformation()
    {
        final StringBuilder sb = new StringBuilder();

        if (error != null)
        {
            sb.append(error.getCode()).append("\n");

            final List<Error.Details> details = error.getDetails();
            if (details != null && !details.isEmpty())
            {
                final Error.Details item = details.get(0);
                sb.append(item.message).append("\n");
            }
        } else
        {
            sb.append(code).append(" ").append(message).append("\n");
        }

        return sb.toString();
    }

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

    public Error getError()
    {
        return error;
    }

    public void setError(Error error)
    {
        this.error = error;
    }

    public static class Error
    {
        private int code;
        private String name;
        private String what;
        private List<Details> details;

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

        public List<Details> getDetails()
        {
            return details;
        }

        public void setDetails(List<Details> details)
        {
            this.details = details;
        }

        public static class Details
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
}
