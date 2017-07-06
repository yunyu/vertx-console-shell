<template>
    <div>
        Shell
    </div>
</template>

<script>
import VertxTerm from './vertx-shell.js';

export default {
    name: 'Shell',
    mounted() {
        this.$nextTick(() => {
            new VertxTerm(window.location.pathname + '/shellproxy', {
                cols: 80,
                rows: 24
            });
        });
    }
}
</script>