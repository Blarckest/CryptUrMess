package uqac.dim.crypturmess.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import uqac.dim.crypturmess.R;

public class UserBGManager {
    public GradientDrawable getBackgroundByUserName(String username) {
        GradientDrawable bgShape = new GradientDrawable();
        bgShape.setShape(GradientDrawable.OVAL);
        bgShape.setOrientation(GradientDrawable.Orientation.TL_BR);
        //Log.i("HEX", username + " : "+ username.hashCode());
        long hash = (long) Math.pow(username.hashCode(), 2);
        hash = hash%879879717;
        hash = (long) Math.pow(hash,2);
        String hashHex = Long.toHexString(hash);
        while (hashHex.length()<9){
            hashHex = Long.toHexString((long) Math.pow(Long.parseLong(hashHex), 2));
        }

        Log.i("HEX", username + " : "+ hashHex);

        int colors[] = {Color.parseColor("#"+ hashHex.subSequence(0,6)),Color.parseColor("#"+hashHex.subSequence(hashHex.length()-7,hashHex.length()-1))};

        bgShape.setColors(colors);

        return bgShape;
    }
}
