package taokdao.plugins.lua.androluasdk;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.plugin.entrance.IDynamicPluginEntrance;
import taokdao.plugins.lua.androluasdk.controller.CallActionController;
import taokdao.plugins.lua.androluasdk.controller.FileBuilderController;
import taokdao.plugins.lua.androluasdk.controller.ProjectBuilderController;
import taokdao.plugins.lua.androluasdk.controller.ProjectTemplateController;

@Keep
public class DynamicEntrance extends BaseDynamicPluginEntrance {
    private FileBuilderController fileBuilderController = new FileBuilderController();
    private ProjectBuilderController projectBuilderController = new ProjectBuilderController();
    private ProjectTemplateController projectTemplateController = new ProjectTemplateController();
    private CallActionController callActionController = new CallActionController();

    @Override
    public void onAttach(@NonNull Context pluginContext) {
        projectTemplateController.onAttach(pluginContext);
    }

    @Override
    public void onUpGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDownGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onCreate(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        fileBuilderController.onCreate(iMainContext, pluginManifest);
        projectBuilderController.onCreate(iMainContext, pluginManifest);
    }

    @Override
    public void onInit(@NonNull final IMainContext iMainContext, @NonNull final PluginManifest pluginManifest) {
        projectTemplateController.onInit(iMainContext, pluginManifest);
    }

    @Override
    public void onCall(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        callActionController.onCall(iMainContext, pluginManifest);
    }

    @Override
    public void onResume(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onPause(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        projectTemplateController.onDestroy(iMainContext, pluginManifest);
        projectBuilderController.onDestroy(iMainContext, pluginManifest);
        fileBuilderController.onDestroy(iMainContext, pluginManifest);
    }


}