import ShellTerm from './ShellTerm.vue';

if (!window.vertxConsoleRoutes) window.vertxConsoleRoutes = [];
window.vertxConsoleRoutes.push({
    path: '/shell',
    component: ShellTerm
});
