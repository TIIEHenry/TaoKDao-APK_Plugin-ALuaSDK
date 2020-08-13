package taokdao.plugins.lua.androluasdk;

import androidx.annotation.Keep;

@Keep
public class PluginConstant {
    public static String Project_Template_ID = PluginConstant.class.getPackage().getName();

    public static class FileBuilder {
        public static final String ANDROLUA_RUNNER = "taokdao.builder.lua.runner";
    }

    public static class ProjectBuilder {
        public static final String ANDROLUA_RUNNER = "taokdao.builder.lua.runner";
    }
}
