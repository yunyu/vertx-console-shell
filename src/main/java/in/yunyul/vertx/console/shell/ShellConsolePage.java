package in.yunyul.vertx.console.shell;

import in.yunyul.vertx.console.base.ConsolePage;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.ShellServer;
import io.vertx.ext.shell.command.CommandResolver;
import io.vertx.ext.shell.term.HttpTermOptions;
import io.vertx.ext.shell.term.TermServer;
import io.vertx.ext.web.Router;

@SuppressWarnings("unused")
public class ShellConsolePage implements ConsolePage {
    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        ShellServer server = ShellServer.create(vertx);
        TermServer termServer = TermServer.createHttpTermServer(vertx, router,
                new HttpTermOptions()
                        .setSockJSPath(basePath + "/shellproxy/*")
                        .setVertsShellJsResource(null) // don't serve any static files
                        .setTermJsResource(null)
                        .setShellHtmlResource(null)
                        .setAuthOptions(null) // use registry auth
        );
        server.registerTermServer(termServer);
        server.registerCommandResolver(CommandResolver.baseCommands(vertx));
        server.listen();
    }

    @Override
    public String getLoaderFileName() {
        return "/js/shell.js";
    }
}
