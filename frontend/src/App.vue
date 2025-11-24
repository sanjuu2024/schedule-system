<template>
    <div id="app-container">
        <router-view></router-view>
    </div>
</template>

<script setup lang="ts">
defineOptions({
    name: 'App',
});
</script>

<style scoped>
#app-container {
    min-height: 100vh;
}
</style>
