package com.sd.eos.rpc.model;

public class AccountModel
{
    private final String account;
    private final String privateKey;
    private final String publicKey;

    public AccountModel(String account, String privateKey, String publicKey)
    {
        this.account = account;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPrivateKey()
    {
        return privateKey;
    }

    public String getPublicKey()
    {
        return publicKey;
    }

    public String getAccount()
    {
        return account;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AccountModel))
        {
            return false;
        }

        final AccountModel other = (AccountModel) obj;
        return account.equals(other.account);
    }
}
