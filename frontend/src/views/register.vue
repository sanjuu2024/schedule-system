<template>
    <div class="box">
        <el-card>
            <h2>注册</h2>
            <el-form
                label-width="auto"
                label-position="top"
                :model="registerForm"
                ref="theRegisterForm"
                :rules="rules"
            >
                <el-form-item prop="username" label="用户名">
                    <el-input
                        v-model="registerForm.username"
                        placeholder="请输入用户名"
                        :prefix-icon="User"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input
                        type="password"
                        v-model="registerForm.password"
                        placeholder="请输入密码"
                        :prefix-icon="Lock"
                        show-password
                    ></el-input>
                </el-form-item>
            </el-form>
            <div class="footer">
                <el-button
                    type="primary"
                    @click="register"
                    :disabled="!validated"
                >
                    注册
                </el-button>
                <el-button type="info" @click="resetForm">重置</el-button>
            </div>
            <el-link type="primary" @click="router.push('/login')">
                去登录
            </el-link>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { reqCheckUsername, reqRegister } from '@/api/user';
import type { RegisterParams, ResponseData } from '@/api/type';
import { Lock, User } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { debounce } from '@/utils/debounce';

const router = useRouter();

defineOptions({
    name: 'Register',
});

// 表单数据
let registerForm = reactive<RegisterParams>({
    username: '',
    password: '',
});

// 表单实例
let theRegisterForm = ref();

// 控制注册按钮是否可用
let validated = ref<boolean>(false);

// 用户名是否可用
let usernameAvailable = ref<boolean>(false);

// 密码是否合法
let passwordValid = ref<boolean>(false);

// 校验规则
const usernamePattern = /^[a-zA-Z0-9_]{4,10}$/;
const passwordPattern = /^[a-zA-Z0-9_]{6,12}$/;

// 检查用户名是否重复（不防抖，用于 validator）
async function checkUsername(value: string): Promise<boolean> {
    try {
        let res: ResponseData = await reqCheckUsername(value);
        return res.code === 200 && !res.data; // true 表示可用
    } catch (err) {
        return true; // 网络错误不阻止
    }
}

// 防抖检查用户名（用于按钮状态控制）
const checkUsernameDebounced = debounce(async (value: string) => {
    if (!usernamePattern.test(value)) {
        usernameAvailable.value = false;
        return;
    }
    theRegisterForm.value.validateField('username');
}, 500);

// 表单校验规则
const rules = {
    username: [
        {
            required: true,
            trigger: 'change',
            validator: (_rule: any, value: string, callback: any) => {
                usernameAvailable.value = false;
                if (!usernamePattern.test(value)) {
                    callback(
                        new Error('4-10位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    callback(); // 格式正确就通过
                }
            },
        },
        {
            required: true,
            trigger: 'blur',
            validator: async (_rule: any, value: string, callback: any) => {
                if (!usernamePattern.test(value)) {
                    callback(
                        new Error('4-10位，只允许英文大小写、数字和下划线。'),
                    );
                }
                // 失去焦点时检查重复（不防抖，立即检查）
                const available = await checkUsername(value);
                if (!available) {
                    usernameAvailable.value = false;
                    return callback(new Error('用户名已存在！'));
                }
                usernameAvailable.value = true;
                callback();
            },
        },
    ],
    password: [
        {
            required: true,
            message: '6-12位，只允许英文大小写、数字和下划线。',
            trigger: 'change',
            validator: (_rule: any, value: string, callback: any) => {
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
    registerForm.username = registerForm.password = '';
    theRegisterForm.value.resetFields();
    usernameAvailable.value = false;
    passwordValid.value = false;
}

// 监听用户名变化（防抖更新按钮状态）
watch(
    () => registerForm.username,
    (newVal) => {
        checkUsernameDebounced(newVal);
    },
);

// 监听并更改按钮状态
watch(
    () => [usernameAvailable.value, passwordValid.value],
    () => {
        validated.value = usernameAvailable.value && passwordValid.value;
    },
);

// 注册
async function register() {
    try {
        await reqRegister(registerForm);
        ElMessage.success('注册成功！即将跳转至登录页面。');
        router.push('/login');
    } catch (err) {
        console.log(err);
    }
}
</script>

<style scoped lang="scss">
.box {
    display: flex;
    justify-content: center;
    align-items: center;
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
