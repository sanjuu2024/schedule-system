<template>
    <div class="box">
        <el-card>
            <h2>注册</h2>
            <el-form
                label-width="auto"
                label-position="top"
                :model="signUpForm"
                ref="thesignUpForm"
                :rules="rules"
            >
                <el-form-item prop="username" label="用户名">
                    <el-input
                        v-model="signUpForm.username"
                        placeholder="请输入用户名"
                        :prefix-icon="User"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input
                        type="password"
                        v-model="signUpForm.password"
                        placeholder="请输入密码"
                        :prefix-icon="Lock"
                        show-password
                    ></el-input>
                </el-form-item>
                <el-form-item prop="email" label="邮箱">
                    <el-input
                        type="email"
                        v-model="signUpForm.email"
                        placeholder="请输入邮箱"
                    ></el-input>
                </el-form-item>
            </el-form>
            <div class="footer">
                <el-button type="primary" @click="">注册</el-button>
                <el-button type="info" @click="resetForm">重置</el-button>
            </div>
            <el-link type="primary" @click="router.push('/login')">
                去登录
            </el-link>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { reqCheckUsername } from '@/api/signup';
import type { SignupParams } from '@/api/type';
import { Lock, User } from '@element-plus/icons-vue';
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

defineOptions({
    name: 'Signup',
});

// 表单数据
let signUpForm = reactive<SignupParams>({
    username: '',
    password: '',
    email: '',
});

// 表单实例
let thesignUpForm = ref();

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
                    reqCheckUsername(value).then((res) => {
                        if (res.existing) {
                            callback(new Error('用户名已存在。'));
                        }
                    });
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
    email: [
        {
            required: true,
            message: '请输入正确的邮箱地址。',
            trigger: 'change',
            validator: (rule: any, value: string, callback: any) => {
                const reg = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
                if (!reg.test(value)) {
                    callback(new Error('请输入正确的邮箱地址。'));
                } else {
                    callback();
                }
            },
        },
    ],
};

function resetForm() {
    signUpForm.username = signUpForm.password = '';
    thesignUpForm.value.resetFields();
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
