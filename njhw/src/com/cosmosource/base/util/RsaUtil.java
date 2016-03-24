package com.cosmosource.base.util;

import java.math.BigInteger;

/**
 * @类描述:  RSA算法
 * 
 * @作者： tt
 */
public class RsaUtil
{

    private BigInteger e;
    private BigInteger d;
    private BigInteger n;
    private int nRounds;
    
    public RsaUtil()
    {
        nRounds = 1;
    }

    public void setE(BigInteger value)
    {
        e = value;
    }

    public void setE(String value)
    {
        e = new BigInteger(value, 16);
    }

    public void setD(BigInteger value)
    {
        d = value;
    }

    public void setD(String value)
    {
        d = new BigInteger(value, 16);
    }

    public void setN(BigInteger value)
    {
        n = value;
    }

    public void setN(String value)
    {
        n = new BigInteger(value, 16);
    }

    public void setRounds(int value)
    {
        nRounds = value;
    }

    public byte[] Encrypt(byte message[])
    {
        byte former[] = null;
        byte complement[] = null;
        byte cipher[] = null;
        BigInteger a = null;
        former = new byte[1 + message.length];
        former[0] = 0;
        for(int i = 0; i < message.length; i++)
            former[i + 1] = message[i];

        a = new BigInteger(former);
        a = a.modPow(e, n);
        complement = a.toByteArray();
        if(complement[0] != 0)
            return complement;
        cipher = new byte[complement.length - 1];
        for(int i = 0; i < cipher.length; i++)
            cipher[i] = complement[i + 1];

        return cipher;
    }

    public byte[] Decrypt(byte message[])
    {
        byte former[] = null;
        byte complement[] = null;
        byte cipher[] = null;
        BigInteger a = null;
        former = new byte[1 + message.length];
        former[0] = 0;
        for(int i = 0; i < message.length; i++)
            former[i + 1] = message[i];

        a = new BigInteger(former);
        a = a.modPow(d, n);
        complement = a.toByteArray();
        if(complement[0] != 0)
            return complement;
        cipher = new byte[complement.length - 1];
        for(int i = 0; i < cipher.length; i++)
            cipher[i] = complement[i + 1];

        return cipher;
    }
    
    public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		// for (int i = 0; i < b.length; i++)
		for (int i = 0; i < b.length; i++)// �̶�ֻҪ 8 λ
		{
			stmp = Integer.toHexString(b[i] & 0xFF);
			if (stmp.length() == 1)
				hs += "0" + stmp;
			else
				hs += stmp;
		}
		return hs.toUpperCase();

	}
    
    public static byte[] hex2byte(byte[] b) {

        if ((b.length % 2) != 0)

            throw new IllegalArgumentException("���Ȳ���ż��");

        byte[] b2 = new byte[b.length / 2];

        for (int n = 0; n < b.length; n += 2) {

            String item = new String(b, n, 2);

            b2[n / 2] = (byte) Integer.parseInt(item, 16);

        }

        return b2;
    }

}