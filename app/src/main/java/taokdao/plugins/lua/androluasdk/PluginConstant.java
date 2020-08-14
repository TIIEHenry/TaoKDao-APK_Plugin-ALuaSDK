package taokdao.plugins.lua.androluasdk;

import androidx.annotation.Keep;

@Keep
public class PluginConstant {
    public static String Project_Template_ID = PluginConstant.class.getPackage().getName();

    public static class FileBuilder {
        public static final String ALUA_RUNNER = "taokdao.builder.alua.runner";
    }

    public static class ProjectBuilder {
        public static final String ALUA_RUNNER = "taokdao.project.builder.alua.runner";
        public static final String ALUA_RUNNER_RUN_WITH_ANDROLUAPRO = ALUA_RUNNER + ".run.androlua";
    }

    public static class ProjectTemplate {
        public static final String ALUA_RUNNER = "taokdao.project.template.alua.runner";
    }
}
