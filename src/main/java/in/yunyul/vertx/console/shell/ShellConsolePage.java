package in.yunyul.vertx.console.shell;

import in.yunyul.vertx.console.base.ConsolePage;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.ShellServer;
import io.vertx.ext.shell.command.CommandRegistry;
import io.vertx.ext.shell.command.CommandResolver;
import io.vertx.ext.shell.spi.CommandResolverFactory;
import io.vertx.ext.shell.term.HttpTermOptions;
import io.vertx.ext.shell.term.TermServer;
import io.vertx.ext.web.Router;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class ShellConsolePage implements ConsolePage {
    /**
     * Creates the shell console page
     *
     * @return the console page
     */
    public static ShellConsolePage create() {
        return new ShellConsolePage();
    }

    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        CommandRegistry registry = CommandRegistry.getShared(vertx);

        ShellServer server = ShellServer.create(vertx);
        TermServer termServer = TermServer.createHttpTermServer(vertx, router,
                new HttpTermOptions()
                        .setSockJSPath(basePath + "/shellproxy/*")
                        .setVertsShellJsResource(null) // don't serve any static files
                        .setTermJsResource(null)
                        .setShellHtmlResource(null)
                        .setAuthOptions(null) // use registry auth
        );

        // Adapted from ShellServiceImpl
        List<CommandResolverFactory> factories = lookupResolverFactories();
        // When providers are registered we start the server
        AtomicInteger count = new AtomicInteger(factories.size());
        List<CommandResolver> resolvers = new ArrayList<>();
        resolvers.add(registry);
        for (CommandResolverFactory factory : factories) {
            factory.resolver(vertx, ar -> {
                if (ar.succeeded()) {
                    resolvers.add(ar.result());
                }
                if (count.decrementAndGet() == 0) {
                    server.registerTermServer(termServer);
                    resolvers.forEach(server::registerCommandResolver);
                    server.listen();
                }
            });
        }
    }

    private List<CommandResolverFactory> lookupResolverFactories() {
        // Lookup providers
        ServiceLoader<CommandResolverFactory> loader = ServiceLoader.load(CommandResolverFactory.class);
        Iterator<CommandResolverFactory> it = loader.iterator();
        List<CommandResolverFactory> factories = new ArrayList<>();
        factories.add((vertx, handler) -> handler.handle(Future.succeededFuture(CommandResolver.baseCommands(vertx))));
        while (true) {
            try {
                if (it.hasNext()) {
                    CommandResolverFactory factory = it.next();
                    factories.add(factory);
                } else {
                    break;
                }
            } catch (Exception e) {
            }
        }
        return factories;
    }

    @Override
    public String getLoaderFileName() {
        return "/js/shell.js";
    }
}
