package com.sd.eos.rpc.eos4j.ecc;

import com.sd.eos.rpc.eos4j.ecc.utils.ByteUtils;
import com.sd.eos.rpc.eos4j.ecc.utils.EException;
import com.sd.eos.rpc.eos4j.ecc.utils.Hex;
import com.sd.eos.rpc.eos4j.ecc.utils.Sha;
import com.sd.eos.rpc.help.cypto.digest.Ripemd160;
import com.sd.eos.rpc.help.cypto.util.Base58;

import java.math.BigInteger;

/**
 * Ecc
 *
 * @author espritblock http://eblock.io
 *
 */
public class EccTool
{

    /**
     * seedPrivate
     *
     * @param seed
     * @return
     */
    public static String seedPrivate(String seed) {
        if (seed == null || seed.length() == 0) {
            throw new EException("args_empty", "args is empty");
        }
        byte[] a = { (byte) 0x80 };
        byte[] b = new BigInteger(Sha.SHA256(seed)).toByteArray();
        byte[] private_key = ByteUtils.concat(a, b);
        byte[] checksum = Sha.SHA256(private_key);
        checksum = Sha.SHA256(checksum);
        byte[] check = ByteUtils.copy(checksum, 0, 4);
        byte[] pk = ByteUtils.concat(private_key, check);
        return Base58.encode(pk);
    }

    /**
     * privateKey
     *
     * @param pk
     * @return
     */
    private static BigInteger privateKey(String pk) {
        byte[] private_wif = Base58.decode(pk);
        byte version = (byte) 0x80;
        if (private_wif[0] != version) {
            throw new EException("version_error", "Expected version " + 0x80 + ", instead got " + version);
        }
        byte[] private_key = ByteUtils.copy(private_wif, 0, private_wif.length - 4);
        byte[] new_checksum = Sha.SHA256(private_key);
        new_checksum = Sha.SHA256(new_checksum);
        new_checksum = ByteUtils.copy(new_checksum, 0, 4);
        byte[] last_private_key = ByteUtils.copy(private_key, 1, private_key.length - 1);
        BigInteger d = new BigInteger(Hex.bytesToHexString(last_private_key), 16);
        return d;
    }

    /**
     * privateToPublic
     *
     * @param pk
     * @return
     */
    public static String privateToPublic(String pk) {
        if (pk == null || pk.length() == 0) {
            throw new EException("args_empty", "args is empty");
        }
        // private key
        BigInteger d = privateKey(pk);
        // publick key
        Point ep = new Secp256k().G().multiply(d);
        byte[] pub_buf = ep.getEncoded();
        byte[] csum = Ripemd160.from(pub_buf).bytes();
        csum = ByteUtils.copy(csum, 0, 4);
        byte[] addy = ByteUtils.concat(pub_buf, csum);
        StringBuffer bf = new StringBuffer("EOS");
        bf.append(Base58.encode(addy));
        return bf.toString();
    }
}
