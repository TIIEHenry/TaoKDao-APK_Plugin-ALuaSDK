package taokdao.plugins.lua.androluasdk.controller;

import androidx.annotation.NonNull;

import taokdao.api.file.build.FileBuilderPool;
import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.project.build.ProjectBuilderPool;
import taokdao.plugins.lua.androluasdk.builder.filebuilders.ALuaFileBuilder;
import taokdao.plugins.lua.androluasdk.builder.projectbuilders.ALuaProjectBuilder;

public class FileBuilderController {
    private ALuaFileBuilder aLuaFileBuilder;


    public void onCreate(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
        aLuaFileBuilder = new ALuaFileBuilder();
        FileBuilderPool.getInstance().add(aLuaFileBuilder);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
        FileBuilderPool.getInstance().remove(aLuaFileBuilder);
    }
}
