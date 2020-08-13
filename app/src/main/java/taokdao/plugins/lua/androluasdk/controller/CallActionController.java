package taokdao.plugins.lua.androluasdk.controller;

import android.content.Intent;

import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;

public class CallActionController {


    public void onCall(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
        boolean handled = tryStartActivity(iMainContext, "com.androlua");
        if (handled)
            return;
        handled = tryStartActivity(iMainContext, "com.androlub");
        if (handled)
            return;
        iMainContext.send("Please Install AndroLua+");
    }

    private boolean tryStartActivity(@NonNull IMainContext iMainContext, String packageName) {
        try {
            Intent intent = iMainContext.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) {
                return false;
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            iMainContext.startActivity(intent);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

}
