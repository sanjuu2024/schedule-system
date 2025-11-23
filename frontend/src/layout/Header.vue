<template>
    <div class="box">
        <h1>欢迎来到日程管理系统</h1>
        <div class="nav">
            <el-button
                :icon="User"
                type="warning"
                @click="router.push('/register')"
            >
                注册
            </el-button>
            <el-button
                :icon="UserFilled"
                type="success"
                @click="router.push('/login')"
            >
                登录
            </el-button>
            <el-button :icon="SwitchButton" type="danger" @click="logout">
                退出
            </el-button>
            <el-button
                :icon="Calendar"
                type="primary"
                @click="router.push('/schedule')"
            >
                查看日程
            </el-button>
        </div>
        <hr />
    </div>
</template>

<script setup lang="ts">
import {
    Calendar,
    SwitchButton,
    User,
    UserFilled,
} from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user';

const router = useRouter();
const userStore = useUserStore();

async function logout() {
    try {
        await userStore.userLogout();
    } catch (error) {
        console.error('退出失败：', error);
    }
}
</script>

<style scoped lang="scss">
.box {
    text-align: center;
}

h1 {
    font-size: 45px;
    font-weight: bold;
    margin: 20px 0;
}

.el-button {
    margin: 10px;
}
</style>
