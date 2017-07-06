<template>
    <div class="terminal-wrapper">
        <div ref="terminal"></div>
    </div>
</template>

<style lang="scss">
.terminal-wrapper {
    .terminal {
        font-family: Menlo, Monaco, Consolas, monospace;
    }

    $term-color-black: #030303;
    $term-color-fg: #b0b8c1;

    /* TERM.JS STYLES */
    .fg {
        color: $term-color-fg;
    }

    div[style*=";color: rgb(240, 240, 240);"] {
        color: $term-color-fg !important;
    }

    .bg {
        background-color: $term-color-black;
    }

    div[style*="background-color: rgb(0, 0, 0);"] {
        background-color: $term-color-black !important;
    }

    .terminal-cursor {
        color: $term-color-black;
        background-color: $term-color-fg;
    }

     ::-moz-selection {
        color: $term-color-black;
        background: #b4d5ff;
    }

     ::selection {
        color: $term-color-black;
        background: #b4d5ff;
    }
}
</style>

<script>
import VertxTerm from './vertx-shell.js';

export default {
    name: 'Shell',
    mounted() {
        this.$nextTick(() => {
            new VertxTerm(window.location.pathname + '/shellproxy', {
                cols: 80,
                rows: 24
            }, this.$refs.terminal);
        });
    }
}
</script>