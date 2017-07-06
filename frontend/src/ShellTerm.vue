<template>
    <div class="terminal-wrapper" ref="terminal" v-resize:throttle="onResize">
    </div>
</template>

<style lang="scss">
@import '../node_modules/xterm/src/xterm.css';

.terminal-wrapper {
    width: 100%;
    height: 100%;

    .terminal {
        font-family: Menlo, Monaco, Consolas, monospace;
    }
}
</style>

<script>
import VertxTerm from './vertx-shell.js';
import resize from 'vue-resize-directive';

export default {
    name: 'Shell',
    directives: {
        resize,
    },
    mounted() {
        this.$nextTick(() => {
            this.term = new VertxTerm(window.location.pathname + '/shellproxy', {}, this.$refs.terminal);
        });
    },
    beforeDestroy() {
        this.term.close();
    },
    methods: {
        onResize(e) {
            this.term.refit();
        }
    }
}
</script>