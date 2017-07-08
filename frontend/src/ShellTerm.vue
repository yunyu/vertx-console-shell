<template>
    <div class="terminal-wrapper" ref="terminal" v-resize:throttle="onResize">
    </div>
</template>

<style lang="scss">
@import '../node_modules/xterm/src/xterm.css';

.terminal-wrapper {
    width: 100%;
    height: 100%;
    background-color: #000;
    overflow: hidden;

    .terminal {
        font-family: Menlo, Monaco, Consolas, monospace;
    }

    /* Chrome and Safari scrollbar styling
    workaround for https://github.com/sourcelair/xterm.js/issues/762 */
    .xterm-viewport {
        &::-webkit-scrollbar {
            width: 6px;
            height: 6px;
            background-color: transparent;
        }
        &::-webkit-scrollbar-thumb {
            background: #222;
            &:hover,
            &:active {
                background: #444;
            }
        }
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
    beforeMount() {
        document.body.style.background = '#000';
    },
    mounted() {
        this.$nextTick(() => {
            this.term = new VertxTerm(window.location.pathname + '/shellproxy', {}, this.$refs.terminal);
        });
    },
    beforeDestroy() {
        this.term.close();
        document.body.style.background = '';
    },
    methods: {
        onResize(e) {
            this.term.refit();
        }
    }
}
</script>