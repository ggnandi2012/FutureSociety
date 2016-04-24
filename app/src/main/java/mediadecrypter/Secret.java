
package mediadecrypter;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.Config;
/**
 * Created by gournandi on 4/05/16.
 */

public class Secret {

   // public static String KEY = "LUAF978893ILVVHDFILILVAFIVIG397897F9QEFVYVIE2LIGFILGG7G77RG7GRGYVADFJHVCVXJHVHJJVJVCVJSFDVALVVSFVLISDLIVFSLIDFLIYWE77287O4GO84VYVF";

    private static String TAG = "Secret";

    private static String folder;

   /* private static byte[] iv = {

            (byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,

            (byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
    };*/

    public static String getDecryptedFilePath(String path, int filetype, String folder) {
        String outputFilePath = "";
        ArrayList<String> encryptedFile = new ArrayList<String>();
        File outputFile = null;
        FileInputStream fin = null;
        FileOutputStream fos = null;
        CipherOutputStream cos = null;
        String returnPath = "";
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("Blowfish");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey key = keyGen.generateKey();
        if (filetype == Media.TYPE_SLIDESHOW) {
            File[] filenames = FileUtils.getAllFilesIn(path, false);
            if (filenames != null) {
                for (int i = 0; i < filenames.length; i++) {
                    if (!filenames[i].getAbsolutePath().contains(File.separator + ".")) {
                        encryptedFile.add(filenames[i].getAbsolutePath());
                    }
                }
            }else{
                return returnPath;
            }
        } else {
            encryptedFile.add(/*
                               * Environment.getExternalStorageDirectory()
                               * .getAbsolutePath() + File.separator +
                               */path);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < encryptedFile.size(); i++) {
            try {
                String fileExtension = encryptedFile.get(i).substring(
                        encryptedFile.get(i).lastIndexOf("."));
                /*String filePath = Environment.getDataDirectory().getAbsolutePath() + File.separator
                        + "data" + File.separator + "edu.fs.com.futuresociety" + File.separator
                        + "temp";

                File directory = new File(filePath);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                filePath = filePath + File.separator + "temp" + i + fileExtension;*/





                File encryptFile = new File(encryptedFile.get(i));

                 outputFilePath = "/mnt/sdcard/Ft_Lesson"//Environment.getExternalStorageDirectory().getAbsolutePath()
                        /*+ File.separator + folder*/;
                outputFile = new File(outputFilePath);
                if (!outputFile.exists()) {
                    outputFile.mkdir();
                }

                outputFilePath = outputFilePath + File.separator + "temp_" + i + fileExtension;

                enCrypt("10241024102410241024102410241024",new FileInputStream("/mnt/sdcard/Ft_Lesson/math_lesson1.mp4"),new FileOutputStream("/mnt/sdcard/Ft_Lesson/math_lesson1_encrypted.mp4"));

                deCrypt("10241024102410241024102410241024",new FileInputStream("/mnt/sdcard/Ft_Lesson/math_lesson1_encrypted.mp4"), new FileOutputStream(outputFilePath));




            } catch (Exception e) {
                if (Config.DEBUG) {
                    Log.d(TAG, "Unable to decrypt the file");
                    e.printStackTrace();
                }
            }


        }

            returnPath = outputFile.getAbsolutePath();


        return outputFilePath;
    }


    private static Cipher encipher;
    private static Cipher decipher;
    private static CipherInputStream cis;
    private static CipherOutputStream cos;
    private static FileInputStream fis;
    private static byte[] ivbytes = new byte[]{(byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e',                                                                                          (byte)'f', (byte)'g', (byte)'h', (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m',     (byte)'n', (byte)'o', (byte)'p'};
    private static IvParameterSpec iv = new IvParameterSpec(ivbytes);

    public static boolean enCrypt(String key, InputStream is, OutputStream os)
    {
        try {
            byte[] encoded = new BigInteger(key, 16).toByteArray();
            SecretKey seckey = new SecretKeySpec(encoded, "AES");
            encipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            encipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
            cis = new CipherInputStream(is, encipher);
            copyByte(cis, os);
            return true;
        }
        catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deCrypt(String key, InputStream is, OutputStream os)
    {
        try {
            byte[] encoded = new BigInteger(key, 16).toByteArray();
            SecretKey seckey = new SecretKeySpec(encoded, "AES");
            encipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            encipher.init(Cipher.DECRYPT_MODE, seckey, iv);
            cos = new CipherOutputStream(os, encipher);
            copyByte(is, cos);
            //cos.close();
            return true;
        }
        catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void copyByte(InputStream is, OutputStream os) throws IOException
    {
        byte[] buf = new byte[8192];
        int numbytes;
        while((numbytes = is.read(buf)) != -1)
        {
            os.write(buf, 0, numbytes);
            os.flush();
        }
        os.close();
        is.close();
    }


    public static void deleteDecryptedFiles(String folder) {
        String outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + folder;
        File outputFile = new File(outputFilePath);
        File files[] = null;
        if (outputFile.isDirectory())
            files = outputFile.listFiles();
        if (files == null)
            return;
        for (File tempFile : files) {
            tempFile.delete();
            if (Config.DEBUG)
                Log.i(TAG, tempFile.getName() + "  file is deleted");
        }
    }
}
