package mediadecrypter;

/**
 * Created by gournandi on 4/05/16.
 */

public interface DecryptionListener {
    public void onDecryptionComplete(String file);
    public void onError(String error);
}
