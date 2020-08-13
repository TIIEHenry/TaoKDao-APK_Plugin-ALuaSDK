package taokdao.plugins.lua.androluasdk.controller;

import androidx.annotation.NonNull;

import taokdao.api.file.build.FileBuilderPool;
import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.project.build.ProjectBuilderPool;
import taokdao.plugins.lua.androluasdk.builder.filebuilders.ALuaFileBuilder;
import taokdao.plugins.lua.androluasdk.builder.projectbuilders.ALuaProjectBuilder;

public class FileBuilderController  extends BaseDynamicPluginEntrance {
    private ALuaFileBuilder aLuaFileBuilder;


    public void onCreate(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        aLuaFileBuilder = new ALuaFileBuilder();
        FileBuilderPool.getInstance().add(aLuaFileBuilder);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        FileBuilderPool.getInstance().remove(aLuaFileBuilder);
    }
}
