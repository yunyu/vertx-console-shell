package in.yunyul.vertx.console.shell;

import in.yunyul.vertx.console.base.ConsolePage;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.ShellServer;
import io.vertx.ext.shell.command.CommandResolver;
import io.vertx.ext.shell.term.HttpTermOptions;
import io.vertx.ext.shell.term.TermServer;
import io.vertx.ext.web.Router;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class ShellConsolePage implements ConsolePage {
    private List<CommandResolver> resolvers;

    public static ShellConsolePage create() {
        return new ShellConsolePage(null);
    }

    public static ShellConsolePage create(CommandResolver... resolvers) {
        return new ShellConsolePage(Arrays.asList(resolvers));
    }

    public static ShellConsolePage create(List<CommandResolver> resolvers) {
        return new ShellConsolePage(resolvers);
    }

    public ShellConsolePage(List<CommandResolver> resolvers) {
        this.resolvers = resolvers;
    }

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
        if (resolvers != null) {
            for (CommandResolver resolver : resolvers) {
                server.registerCommandResolver(resolver);
            }
        }
        server.listen();
    }

    @Override
    public String getLoaderFileName() {
        return "/js/shell.js";
    }
}
