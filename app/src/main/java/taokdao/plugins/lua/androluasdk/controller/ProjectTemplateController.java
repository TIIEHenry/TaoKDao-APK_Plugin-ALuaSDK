package taokdao.plugins.lua.androluasdk.controller;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import taokdao.api.data.bean.Properties;
import taokdao.api.main.IMainContext;
import taokdao.api.main.action.MainAction;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.project.template.ProjectTemplatePool;
import taokdao.api.project.template.wrapped.ProjectTemplate;
import taokdao.plugins.lua.androluasdk.PluginConstant;
import taokdao.plugins.lua.androluasdk.R;
import taokdao.plugins.setup.io.Filej;
import tiiehenry.android.ui.dialogs.api.callback.InputCallback;
import tiiehenry.android.ui.dialogs.api.strategy.Dialogs;
import tiiehenry.android.ui.dialogs.api.strategy.input.IInputDialog;

public class ProjectTemplateController extends BaseDynamicPluginEntrance {
    private ProjectTemplate projectTemplate;

    public void onInit(@NonNull final IMainContext iMainContext, @NonNull final PluginManifest pluginManifest) {
        projectTemplate = new ProjectTemplate(
                new Properties(PluginConstant.ProjectTemplate.ALUA_RUNNER, "AndroLua+ Project", "project for AndroLua+"),
                ContextCompat.getDrawable(pluginContext, R.mipmap.ic_launcher),
                file -> showCreateDialog(iMainContext, file, pluginManifest.pluginDir), null);
        ProjectTemplatePool.getInstance().add(projectTemplate);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        ProjectTemplatePool.getInstance().remove(projectTemplate);
    }

    private void showCreateDialog(@NonNull final IMainContext main, final File currentDir, final File pluginDir) {
        Dialogs.global.asInput()
                .title("请输入项目名")
                .input("请输入项目名",
                        "demoProject",
                        new InputCallback() {
                            @Override
                            public void onInput(@NonNull IInputDialog dialog, CharSequence input) {
                                if (input.equals("")) {
                                    dialog.setInputError("项目名不能为空");
                                    return;
                                }
                                Filej newDir = new Filej(currentDir, input.toString());
                                if (newDir.exists()) {
                                    dialog.setInputError("项目名已存在");
                                    return;
                                }
                                new Filej(newDir, "lua").mkdirs();
                                if (!newDir.exists()) {
                                    dialog.setInputError("新建项目文件夹失败");
                                    return;
                                }
                                try {
                                    new Filej(pluginDir.toString() + "/project").copyTo(newDir, true);
                                    File config = new File(newDir, "project.json");
                                    File mainFile = new File(newDir, "lua/main.lua");
                                    main.getProjectManager().openProject(config);
                                    main.getFileOpenManager().requestOpen(mainFile.getAbsolutePath());
                                    main.getFileOpenManager().requestOpen(config.getAbsolutePath());
                                    MainAction.onFileCreated.runObservers(main);
                                    main.getExplorerWindow().hideWindow();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    dialog.setInputError(e.getMessage());
                                }
                            }
                        })
                .negativeText()
                .positiveText()
                .show();
    }

}
