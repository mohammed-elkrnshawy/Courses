package s.panorama.graduationproject.Classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

import s.panorama.graduationproject.R;

import static android.content.Context.MODE_PRIVATE;

public class SharedUtils {

    public static String getLocalization(final Context context) {
        return context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE).getString("language", "en");
    }

    public static Dialog ShowWaiting(Context context,Dialog progressDialog) {
        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.view_waiting);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressDialog.setCancelable(true);
        return progressDialog;
    }

}
