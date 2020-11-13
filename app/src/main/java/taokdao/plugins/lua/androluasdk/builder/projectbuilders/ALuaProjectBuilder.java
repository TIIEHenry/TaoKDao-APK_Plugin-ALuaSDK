package taokdao.plugins.lua.androluasdk.builder.projectbuilders;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

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
import taokdao.plugins.lua.androluasdk.PluginConstant;

public class ALuaProjectBuilder implements IProjectBuilder {
    private final IMainContext mainContext;

    private String pkg = "com.androlua";
    private String cls = "com.androlua.LuaActivity";

    private List<IBuildOption<Project>> buildOptionList = new ArrayList<>();
    private BuildOption<Project> runWithAndroLuaPro = new BuildOption<>(
            new Properties(PluginConstant.ProjectBuilder.ALUA_RUNNER_RUN_WITH_ANDROLUAPRO, "runWithAndroLua+"),
            new IBuildCallback<Project>() {
                @Override
                public boolean onBuild(@NonNull IMainContext main, @NonNull Project config, @NonNull IBuildOption<Project> option) {
                    File file = new File(config.projectDir, "lua/main.lua");

                    boolean handled = tryStartActivity(mainContext, pkg,cls, file);
                    if (handled)
                        return true;
                    handled = tryStartActivity(mainContext, "com.androlua","com.androlua.LuaActivity", file);
                    if (handled)
                        return true;
                    handled = tryStartActivity(mainContext, "com.androlub","com.androlua.LuaActivity", file);
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

    private boolean tryStartActivity(@NonNull IMainContext iMainContext, String packageName, String className, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName component = new ComponentName(packageName, className);
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
        return PluginConstant.ProjectBuilder.ALUA_RUNNER;
    }

    @Override
    public void loadParameters(@NonNull List<String> parameters) {
        List<Pair<String, String>> parameterList = analyzeParameters(parameters);
        for (Pair<String, String> pair : parameterList) {
            Log.e("ALuaProjectBuilder", "loadParameters: " + pair);
            String cla = pair.first;
            if (cla.equals("-pkg")) {
                pkg = pair.second;
            } else if (cla.equals("-cls")) {
                cls = pair.second;
            }
        }
    }

    private List<Pair<String, String>> analyzeParameters(@NonNull List<String> parameters) {
        List<Pair<String, String>> pairList = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            String parameter = parameters.get(i);
            if (i + 1 < parameters.size()) {
                if (parameter.startsWith("-")) {
                    String parameter2 = parameters.get(i + 1);
                    Pair<String, String> pair;
                    if (parameter2.startsWith("-")) {
                        pair = new Pair<>(parameter, "");
                    } else {
                        pair = new Pair<>(parameter, parameter2);
                        i++;
                    }
                    pairList.add(pair);
                }
            } else {
                Pair<String, String> pair = new Pair<>(parameter, "");
                pairList.add(pair);
            }
        }
        return pairList;
    }
}
