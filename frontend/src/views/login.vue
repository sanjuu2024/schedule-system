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
                <el-button type="primary" @click="login" :disabled="!validated">
                    登录
                </el-button>
                <el-button type="info" @click="resetForm">重置</el-button>
            </div>
            <el-link type="primary" @click="router.push('/register')">
                去注册
            </el-link>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import type { LoginParams } from '@/api/type';
import { Lock, User } from '@element-plus/icons-vue';
import { reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user';
import { useRoute } from 'vue-router';
import { ElNotification } from 'element-plus';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

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

// 控制登录按钮是否可用
let validated = ref<boolean>(false);
let usernameValid = ref<boolean>(false);
let passwordValid = ref<boolean>(false);

// 校验规则
const usernamePattern = /^[a-zA-Z0-9_]{4,10}$/;
const passwordPattern = /^[a-zA-Z0-9_]{6,12}$/;
const rules = {
    username: [
        {
            required: true,
            message: '4-10位，只允许英文大小写、数字和下划线。',
            trigger: 'change',
            validator: (rule: any, value: string, callback: any) => {
                usernameValid.value = false;
                if (!usernamePattern.test(value)) {
                    callback(
                        new Error('4-10位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    usernameValid.value = true;
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
                passwordValid.value = false;
                if (!passwordPattern.test(value)) {
                    callback(
                        new Error('6-12位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    passwordValid.value = true;
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

watch(
    () => {
        (usernameValid.value, passwordValid.value);
    },
    () => {
        validated.value = !!(usernameValid.value && passwordValid.value); // 实时更新按钮状态，但不显示错误(显示错误让el-form封装的rules去干)
    },
    { deep: true, immediate: true },
);

// 登录
async function login() {
    try {
        await userStore.userLogin(loginForm);
        router.push({ path: (route.query.redirect as string) || '/schedule' });
        ElNotification({
            title: '登录成功',
            message: `欢迎您，${userStore.userInfo.username}！`,
            type: 'success',
        });
    } catch (error) {
        console.log(error);
    }
}
</script>

<style scoped lang="scss">
.box {
    display: flex;
    justify-content: center;
    align-items: center;
    padding-top: 100px;
    max-height: 100vh;
}

.el-card {
    width: 40vw;
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
