package uqac.dim.crypturmess.ui;

import android.util.Log;

import uqac.dim.crypturmess.R;

public class UserBGManager {
    public int getBackgroundByUserName(String username) {
        int charSeed = 0;
        for(int i = 0; i < username.length(); i++){
            charSeed = charSeed + username.charAt(i);
        }
        int seed = charSeed%6;
        switch (seed) {
            case 0:
                return R.drawable.user_bg1;
            case 1:
                return R.drawable.user_bg2;
            case 2:
                return R.drawable.user_bg3;
            case 3:
                return R.drawable.user_bg4;
            case 4:
                return R.drawable.user_bg5;
            case 5:
                return R.drawable.user_bg6;
        }
        return R.drawable.user_bg1;
    }
}
