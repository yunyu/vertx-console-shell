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
    /* TERM.JS STYLES */
    .fg {
        color: #b0b8c1;
    }

    div[style*=";color: rgb(240, 240, 240);"] {
        color: #b0b8c1 !important;
    }

    .bg {
        background-color: $term-color-black;
    }

    div[style*="background-color: rgb(0, 0, 0);"] {
        background-color: $term-color-black !important;
    }

    .terminal-cursor {
        color: $term-color-black;
        background-color: #b0b8c1;
    }

     ::-moz-selection {
        color: $term-color-black;
        background: #b4d5ff;
    }

     ::selection {
        color: $term-color-black;
        background: #b4d5ff;
    }
    /* FG COLORS */
    span[style*=";color:#2e3436;"] {
        color: #363737 !important;
    }

    span[style*=";color:#cc0000;"] {
        color: #bb0000 !important;
    }

    span[style*=";color:#4e9a06;"] {
        color: #00bb00 !important;
    }

    span[style*=";color:#c4a000;"] {
        color: #bbbb00 !important;
    }

    span[style*=";color:#3465a4;"] {
        color: #0000bb !important;
    }

    span[style*=";color:#75507b;"] {
        color: #bb00bb !important;
    }

    span[style*=";color:#06989a;"] {
        color: #00bbbb !important;
    }

    span[style*=";color:#d3d7cf;"] {
        color: #bac2cc !important;
    }
    /* FG BRIGHT COLORS */
    span[style*=";color:#555753;"] {
        color: #636666 !important;
    }

    span[style*=";color:#ef2929;"] {
        color: #ff5555 !important;
    }

    span[style*=";color:#8ae234;"] {
        color: #55ff55 !important;
    }

    span[style*=";color:#fce94f;"] {
        color: #ffff55 !important;
    }

    span[style*=";color:#729fcf;"] {
        color: #5555ff !important;
    }

    span[style*=";color:#ad7fa8;"] {
        color: #ff55ff !important;
    }

    span[style*=";color:#34e2e2;"] {
        color: #55ffff !important;
    }

    span[style*=";color:#eeeeec;"] {
        color: #d8e2f0 !important;
    }
    /* BG COLORS */
    span[style*="background-color:#2e3436;"] {
        background-color: #363737 !important;
    }

    span[style*="background-color:#cc0000;"] {
        background-color: #bb0000 !important;
    }

    span[style*="background-color:#4e9a06;"] {
        background-color: #00bb00 !important;
    }

    span[style*="background-color:#c4a000;"] {
        background-color: #bbbb00 !important;
    }

    span[style*="background-color:#3465a4;"] {
        background-color: #0000bb !important;
    }

    span[style*="background-color:#75507b;"] {
        background-color: #bb00bb !important;
    }

    span[style*="background-color:#06989a;"] {
        background-color: #00bbbb !important;
    }

    span[style*="background-color:#d3d7cf;"] {
        background-color: #bac2cc !important;
    }
    /* BG BRIGHT COLORS */
    span[style*="background-color:#555753;"] {
        background-color: #636666 !important;
    }

    span[style*="background-color:#ef2929;"] {
        background-color: #ff5555 !important;
    }

    span[style*="background-color:#8ae234;"] {
        background-color: #55ff55 !important;
    }

    span[style*="background-color:#fce94f;"] {
        background-color: #ffff55 !important;
    }

    span[style*="background-color:#729fcf;"] {
        background-color: #5555ff !important;
    }

    span[style*="background-color:#ad7fa8;"] {
        background-color: #ff55ff !important;
    }

    span[style*="background-color:#34e2e2;"] {
        background-color: #55ffff !important;
    }

    span[style*="background-color:#eeeeec;"] {
        background-color: #d8e2f0 !important;
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