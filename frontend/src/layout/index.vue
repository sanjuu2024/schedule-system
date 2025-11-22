<template>
    <Header class="header"></Header>
    <router-view></router-view>
</template>

<script setup lang="ts">
import Header from './Header.vue';
defineOptions({
    name: 'Header',
});
</script>

<style scoped lang="scss">
.header {
    height: 200px;
    padding: 50px;
}
</style>
