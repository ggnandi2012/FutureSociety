
package mediadecrypter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import util.Config;

/**
 * Created by gournandi on 4/05/16.
 */

public class DecryptAndViewControl {

    private final static String TAG = "DecryptAndViewControl";

    private final static String DIALOG_MESSAGE = "Processing media...";

    private final static String DIALOG_TITLE = "Please wait...";

    private final static String NO_DECRYPTED_MEDIA = "Could not decrypt media files";

    private final static int TYPE_DECRYPTION_DONE = 100;

    private final static int TYPE_DECRYPTION_FAILED = 200;

    public static final int MEDIA_PLAY_REQUEST_CODE = 2207;

    private Activity contextActivity;

    private String encryptedFilePath;

    private String decryptedFile;

    private int fileType;

    private ProgressDialog dialog;

    private DecryptThread thread;

    private boolean launchPlayer = true;

    private DecryptionListener decryptionListener;

    private String folder;

    public DecryptAndViewControl(Activity activity) {
        this.contextActivity = activity;
    }

    public void setFileUrl(String url, int fileType, String folder) {
        this.encryptedFilePath = url;
        this.fileType = fileType;
        this.folder = folder;
    }

    public void setLaunchPlayer(boolean value) {
        this.launchPlayer = value;
    }

    public void setDecryptionListener(DecryptionListener listener) {
        this.decryptionListener = listener;
    }

    public void decryptAndView() {
        showProgressDialog();
        thread = new DecryptThread();
        thread.start();
    }

    public static void deleteDecryptedFiles(String folder) {
        Secret.deleteDecryptedFiles(folder);
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(contextActivity);
        dialog.setMessage(DIALOG_MESSAGE);
        dialog.setTitle(DIALOG_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TYPE_DECRYPTION_DONE) {
                processDecryptedFile();
            }
            if (msg.what == TYPE_DECRYPTION_FAILED) {
                dialog.dismiss();
                Toast.makeText(contextActivity, NO_DECRYPTED_MEDIA, Toast.LENGTH_LONG).show();
                if (decryptionListener != null) {
                    decryptionListener.onError("Decryption failed");
                }
            }
        }

    };

    private void processDecryptedFile() {
        dialog.dismiss();
        if (launchPlayer) {
            /*if (fileType == Media.TYPE_SLIDESHOW) {
                Intent mediaIntent = new Intent(contextActivity, ViewSlideshowActivity.class);
                ArrayList<String> slideshowImages = new ArrayList<String>();
                File decryptedfiledir = new File(decryptedFile);
                if (decryptedfiledir.isDirectory()) {
                    File[] filenames = decryptedfiledir.listFiles();
                    for (int i = 0; i < filenames.length; i++) {
                        if (Config.DEBUG)
                            Log.i(getClass().getName(), "================================ "
                                    + filenames[i].getName());
                        if (!filenames[i].getName().startsWith(".")
                                && !filenames[i].getName().toLowerCase().contains("thumbs.db")) {
                            slideshowImages.add(filenames[i].getAbsolutePath());
                        }
                    }
                    if (slideshowImages.size() > 0) {
                        mediaIntent.putExtra("DECRYPTED_MEDIA", slideshowImages);
                        contextActivity.startActivity(mediaIntent);
                    } else {
                        Toast.makeText(
                                contextActivity,
                                contextActivity.getResources().getString(
                                        R.string.lbl_nothing_to_display), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else
                    Toast.makeText(
                            contextActivity,
                            contextActivity.getResources().getString(
                                    R.string.lbl_file_content_problem), Toast.LENGTH_SHORT).show();

            } else*/ if (fileType == Media.TYPE_VIDEO) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(decryptedFile), "video/*");

                contextActivity.startActivityForResult(intent, MEDIA_PLAY_REQUEST_CODE);

            } else {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + decryptedFile), "image/*");

                contextActivity.startActivityForResult(intent, MEDIA_PLAY_REQUEST_CODE);
            }
        } else {
            if (decryptionListener != null) {
                decryptionListener.onDecryptionComplete(decryptedFile);
            }
        }

    }

    public String getDecryptedFilePath() {
        return decryptedFile;
    }

    private class DecryptThread extends Thread {

        @Override
        public void run() {
            if (Config.DEBUG) {
                Log.d(TAG, "Input: " + encryptedFilePath);
            }
            decryptedFile =Secret.getDecryptedFilePath(encryptedFilePath,fileType,folder);

            if (Config.DEBUG) {
                Log.d(TAG, "Output: " + decryptedFile);
            }
            if (decryptedFile != null) {
                handler.sendEmptyMessage(TYPE_DECRYPTION_DONE);
            } else {
                handler.sendEmptyMessage(TYPE_DECRYPTION_FAILED);

            }

        };
    }
}
