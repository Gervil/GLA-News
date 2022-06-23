package cl.ione.globallogic.Utilities;

import android.app.AlertDialog;
import android.content.Context;

import cl.ione.globallogic.R;

public class Messages {

    public static void showAlert(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.alert_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.btn_ok), (dialog, which) ->
                        dialog.dismiss())
                .create()
                .show();
    }
}
