package taokdao.plugins.lua.androluasdk.builder.projectbuilders;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import taokdao.api.builder.IBuildOption;
import taokdao.api.builder.callback.IBuildCallback;
import taokdao.api.builder.wrapped.BuildOption;
import taokdao.api.data.bean.Properties;
import taokdao.api.main.IMainContext;
import taokdao.api.project.bean.Project;
import taokdao.api.project.build.IProjectBuilder;

public class ALuaProjectBuilder implements IProjectBuilder {
    private final IMainContext mainContext;

    private List<IBuildOption<Project>> buildOptionList = new ArrayList<>();
    private BuildOption<Project> runWithAndroLuaPro = new BuildOption<>(
            new Properties(ALuaProjectBuilder.class.getPackage().getName(), "runWithAndroLua+"),
            new IBuildCallback<Project>() {
                @Override
                public boolean onBuild(@NonNull IMainContext main, @NonNull Project config, @NonNull IBuildOption<Project> option) {
                    File file = new File(config.projectDir, "lua/main.lua");

                    boolean handled = tryStartActivity(mainContext, "com.androlua", file);
                    if (handled)
                        return true;
                    handled = tryStartActivity(mainContext, "com.androlub", file);
                    if (handled)
                        return true;
                    mainContext.send("Please Install AndroLua+");
                    return false;
                }
            });

    public ALuaProjectBuilder(IMainContext mainContext) {
        this.mainContext = mainContext;
        buildOptionList.add(runWithAndroLuaPro);
    }

    private boolean tryStartActivity(@NonNull IMainContext iMainContext, String packageName, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName component = new ComponentName(packageName, "com.androlua.LuaActivity");
            intent.setComponent(component);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromFile(file));
            iMainContext.startActivity(intent);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @NonNull
    @Override
    public List<IBuildOption<Project>> getBuildOptionList() {
        return buildOptionList;
    }

    @NonNull
    @Override
    public String id() {
        return "taokdao.plugins.lua.androluasdk";
    }
}
