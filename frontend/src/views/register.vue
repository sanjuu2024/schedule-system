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
import { reqCheckUsername, reqRegister } from '@/api/register';
import type { RegisterParams, ResponseData } from '@/api/type';
import { Lock, User } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
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
                if (!usernamePattern.test(value)) {
                    callback(
                        new Error('4-10位，只允许英文大小写、数字和下划线。'),
                    );
                } else {
                    callback();
                }
            },
        },
        {
            trigger: 'blur',
            async validator(rule: any, value: string, callback: any) {
                if (!usernamePattern.test(value)) return callback();
                try {
                    let res: ResponseData = await reqCheckUsername(value);
                    if (res.code !== 200) {
                        return callback(new Error(res.message));
                    }
                    callback();
                } catch (err) {
                    ElMessage.error(err as string);
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
                if (!passwordPattern.test(value)) {
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
    registerForm.username = registerForm.password = '';
    theRegisterForm.value.resetFields();
}

// 静默校验(只检查格式,禁用确认提交按钮而不显示错误信息)(显示错误让el-form封装的rules去干)
function silentValidation() {
    const { username, password } = registerForm;

    // 检查每个字段是否符合格式要求
    const usernameValid = usernamePattern.test(username);
    const passwordValid = passwordPattern.test(password);

    // 只要有一个不通过就禁用按钮
    validated.value = !!(usernameValid && passwordValid);
}

watch(
    () => registerForm,
    () => {
        silentValidation(); // 实时更新按钮状态，但不显示错误(显示错误让el-form封装的rules去干)
    },
    { deep: true, immediate: true },
);

// 注册
async function register() {
    try {
        let res = await reqRegister(registerForm);
        if (res.code === 200) {
            ElMessage.success('注册成功！即将跳转至登录页面。');
            router.push('/login');
        } else {
            ElMessage.error('注册失败！' + res.message);
        }
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
