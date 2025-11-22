<template>
    <div class="box">
        <h1>404 Not Found</h1>
        <img
            src="../assets/images/onigiri.jpg"
            alt="404 Not Found"
            width="300px"
        />
        <a href="/">返回首页</a>
    </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.box {
    h1 {
        font-size: 50px;
        font-weight: bold;
    }
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    height: 80vh;
}
</style>
