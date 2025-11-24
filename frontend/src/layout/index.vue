<template>
    <Header class="header"></Header>
    <div class="container">
        <router-view></router-view>
    </div>
</template>

<script setup lang="ts">
import Header from './Header.vue';
defineOptions({
    name: 'Header',
});
</script>

<style scoped lang="scss">
.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: auto;
    padding: 30px 50px;
    z-index: 999;
    background-color: #fff;
    // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.container {
    padding-top: 200px;
}
</style>
