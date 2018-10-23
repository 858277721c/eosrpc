package com.sd.lib.eos.rpc.helper.chain;


import com.sd.lib.eos.rpc.helper.cypto.util.HexUtils;
import com.sd.lib.eos.rpc.helper.types.EosType;
import com.sd.lib.eos.rpc.helper.types.TypeAccountName;
import com.sd.lib.eos.rpc.helper.types.TypeActionName;
import com.sd.lib.eos.rpc.helper.types.TypePermissionLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class Action implements EosType.Packer {
    private TypeAccountName account;

    private TypeActionName name;

    private List<TypePermissionLevel> authorization = null;

    private String data;

    public Action(String account, String name, TypePermissionLevel authorization, String data){
        this.account = new TypeAccountName(account);
        this.name = new TypeActionName(name);
        this.authorization = new ArrayList<>();
        if ( null != authorization ) {
            this.authorization.add(authorization);
        }
    }

    public Action(String account, String name) {
        this (account, name, null, null);
    }

    public Action(){
        this ( null, null, null, null);
    }

    public String getAccount() {
        return account.toString();
    }

    public void setAccount(String account) {
        this.account = new TypeAccountName(account);
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new TypeActionName(name);
    }

    public List<TypePermissionLevel> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<TypePermissionLevel> authorization) {
        this.authorization = authorization;
    }

    public void setAuthorization(TypePermissionLevel[] authorization) {
        this.authorization.addAll( Arrays.asList( authorization) );
    }

    public void setAuthorization(String[] accountWithPermLevel) {
        if ( null == accountWithPermLevel){
            return;
        }

        for ( String permissionStr : accountWithPermLevel ) {
            String[] split = permissionStr.split("@", 2);
            authorization.add( new TypePermissionLevel(split[0], split[1]) );
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void pack(EosType.Writer writer) {
        account.pack(writer);
        name.pack(writer);

        writer.putCollection( authorization );

        if ( null != data ) {
            byte[] dataAsBytes = HexUtils.toBytes( data );
            writer.putVariableUInt(dataAsBytes.length);
            writer.putBytes( dataAsBytes );
        }
        else {
            writer.putVariableUInt(0);
        }
    }
}