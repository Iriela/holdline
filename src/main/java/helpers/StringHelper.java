/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author iriel
 */
public class StringHelper {
    public static String removeId(String x){
        return x.replace("id_","");
    }
    
    public static String normalize(String string){
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
    
    public static String hash(String string) throws Exception{
        byte[] bytes = string.getBytes();
        try{
            MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
            algorithm.reset();
            algorithm.update(bytes);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(bytes);
            BigInteger number = new BigInteger(1, messageDigest);
            String hash = number.toString(16);
            return hash;
        }
        catch(NoSuchAlgorithmException exception){
            throw new Error("Error while trying to hash string", exception);
        }
    }
    
    public static String replaceQuotes(String query) {
        return query.replace("'", "''");
    }
}
