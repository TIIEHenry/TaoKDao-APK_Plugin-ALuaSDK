package taokdao.plugins.lua.androluasdk.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import taokdao.api.data.bean.Properties;
import taokdao.api.main.IMainContext;
import taokdao.api.main.action.MainAction;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.template.project.ProjectTemplatePool;
import taokdao.api.template.project.wrapped.ProjectTemplate;
import taokdao.plugins.lua.androluasdk.AConstant;
import taokdao.plugins.lua.androluasdk.R;
import tiiehenry.android.ui.dialogs.api.callback.InputCallback;
import tiiehenry.android.ui.dialogs.api.strategy.Dialogs;
import tiiehenry.android.ui.dialogs.api.strategy.input.IInputDialog;
import tiiehenry.io.Filej;

public class ProjectTemplateController {
    private ProjectTemplate projectTemplate;
    private Context pluginContext;

    public void onAttach(Context pluginContext) {
        this.pluginContext = pluginContext;
    }

    public void onInit(@NonNull final IMainContext iMainContext, final PluginManifest pluginManifest) {
        projectTemplate = new ProjectTemplate(
                new Properties(AConstant.Project_Template_ID, "AndroLua+ Project", "project for AndroLua+"),
                pluginContext.getDrawable(R.mipmap.ic_launcher),
                file -> showCreateDialog(iMainContext, file, pluginManifest.pluginDir), null);
        ProjectTemplatePool.getInstance().add(projectTemplate);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
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
