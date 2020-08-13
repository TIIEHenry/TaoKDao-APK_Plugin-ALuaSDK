package taokdao.plugins.lua.androluasdk.builder.filebuilders;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import taokdao.api.builder.IBuildOption;
import taokdao.api.builder.wrapped.BuildOption;
import taokdao.api.data.bean.Properties;
import taokdao.api.file.build.IFileBuilder;
import taokdao.plugins.lua.androluasdk.PluginConstant;

public class ALuaFileBuilder implements IFileBuilder {
    BuildOption<File> runWithAndroLua = new BuildOption<File>(
            new Properties(PluginConstant.FileBuilder.ANDROLUA_RUNNER + ".run", "runWithAndroLua+")
            , (main, config, option) -> {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.androlua", "com.androlua.LuaActivity"));
            intent.setData(Uri.fromFile(config));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            main.send("AndroLua+ not installed");
            return false;
        }
    });

    @NonNull
    @Override
    public String id() {
        return PluginConstant.FileBuilder.ANDROLUA_RUNNER;
    }

    @NonNull
    @Override
    public String getLabel() {
        return "AndroLua+ Runner";
    }

    List<String> suffixes = new ArrayList<>();
    List<IBuildOption<File>> options = new ArrayList<>();

    public ALuaFileBuilder() {
        suffixes.add("lua");
        suffixes.add("aly");
        options.add(runWithAndroLua);
    }


    @NonNull
    @Override
    public List<String> getSuffixes() {
        return suffixes;
    }


    @NonNull
    @Override
    public List<IBuildOption<File>> getBuildOptionList() {
        return options;
    }

}
