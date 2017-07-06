package in.yunyul.vertx.console.shell;

import in.yunyul.vertx.console.base.ConsolePage;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.term.HttpTermOptions;
import io.vertx.ext.shell.term.TermServer;
import io.vertx.ext.web.Router;

@SuppressWarnings("unused")
public class ShellConsolePage implements ConsolePage {
    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        TermServer server = TermServer.createHttpTermServer(vertx, router,
                new HttpTermOptions()
                        .setSockJSPath(basePath + "/shellproxy/*")
                        .setVertsShellJsResource(null) // don't serve any static files
                        .setTermJsResource(null)
                        .setShellHtmlResource(null)
                        .setAuthOptions(null) // use registry auth
        );
        server.termHandler(term -> term.stdinHandler(term::write));
        server.listen();
    }

    @Override
    public String getLoaderFileName() {
        return "/js/shell.js";
    }
}
