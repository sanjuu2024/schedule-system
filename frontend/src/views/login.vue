<template>
    <div class="box">
        <el-card>
            <h2>登录</h2>
            <el-form
                label-width="auto"
                label-position="top"
                :model="loginForm"
                ref="theLoginForm"
                :rules="rules"
            >
                <el-form-item prop="username" label="用户名">
                    <el-input
                        v-model="loginForm.username"
                        placeholder="请输入用户名"
                        :prefix-icon="User"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input
                        type="password"
                        v-model="loginForm.password"
                        placeholder="请输入密码"
                        :prefix-icon="Lock"
                        show-password
                    ></el-input>
                </el-form-item>
            </el-form>
            <div class="footer">
                <el-button type="primary">登录</el-button>
                <el-button type="info" @click="resetForm">重置</el-button>
            </div>
            <el-link type="primary" @click="router.push('/signup')">
                去注册
            </el-link>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import type { LoginParams } from '@/api/type';
import { Lock, User } from '@element-plus/icons-vue';
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

defineOptions({
    name: 'Login',
});

// 表单数据
let loginForm = reactive<LoginParams>({
    username: '',
    password: '',
});

// 表单实例
let theLoginForm = ref();

// 校验规则
const rules = {
    username: [
        {
            required: true,
            message: '4-10位，只允许英文大小写、数字和下划线。',
            trigger: 'change',
            validator: (rule: any, value: string, callback: any) => {
                const reg = /^[a-zA-Z0-9_]{4,10}$/;
                if (!reg.test(value)) {
                    callback(
                        new Error('4-10位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    callback();
                }
            },
        },
    ],
    password: [
        {
            required: true,
            message: '6-12位，只允许英文大小写、数字和下划线。',
            trigger: 'change',
            validator: (rule: any, value: string, callback: any) => {
                const reg = /^[a-zA-Z0-9_]{6,12}$/;
                if (!reg.test(value)) {
                    callback(
                        new Error('6-12位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    callback();
                }
            },
        },
    ],
};

function resetForm() {
    loginForm.username = loginForm.password = '';
    theLoginForm.value.resetFields();
}
</script>

<style scoped lang="scss">
.box {
    display: flex;
    justify-content: center;
    align-items: center;
}

.el-card {
    width: 60vw;
}

.el-button {
    float: right;
    margin: 10px;
}

.footer::after {
    content: '';
    display: block;
    clear: both;
}

h2 {
    text-align: center;
    font-size: 30px;
    margin: 30px 0;
}
</style>
