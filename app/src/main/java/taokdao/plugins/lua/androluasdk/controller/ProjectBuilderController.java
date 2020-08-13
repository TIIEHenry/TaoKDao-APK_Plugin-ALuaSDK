package taokdao.plugins.lua.androluasdk.controller;

import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.project.build.ProjectBuilderPool;
import taokdao.plugins.lua.androluasdk.builder.projectbuilders.ALuaProjectBuilder;

public class ProjectBuilderController  extends BaseDynamicPluginEntrance {
    private ALuaProjectBuilder aLuaProjectBuilder;


    public void onCreate(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        aLuaProjectBuilder = new ALuaProjectBuilder(iMainContext);
        ProjectBuilderPool.getInstance().add(aLuaProjectBuilder);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        ProjectBuilderPool.getInstance().remove(aLuaProjectBuilder);
    }
}
