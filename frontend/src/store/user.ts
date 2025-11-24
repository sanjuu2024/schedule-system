import { defineStore } from 'pinia';
import { setToken, getToken, removeToken } from '@/utils/token';
import type { LoginParams, UserInfo } from '@/api/type';
import { reqLogin, reqLogout, reqUserInfo } from '@/api/user';
import router from '@/router';
import { ElMessage } from 'element-plus';

export const useUserStore = defineStore('user', {
    state: () => ({
        token: getToken(),
        userInfo: {
            uid: 0,
            username: '',
        } as UserInfo,
    }),
    actions: {
        async userLogin(data: LoginParams) {
            try {
                let res = await reqLogin(data);
                if (res.code === 200) {
                    this.token = res.data.token;
                    this.userInfo = res.data.user;
                    setToken(res.data.token);
                    ElMessage.success('登录成功');
                } else {
                    ElMessage.error('登录失败：' + res.message);
                }
            } catch (err) {
                throw new Error((err as Error).message);
            }
        },
        async getUserInfo() {
            try {
                let res = await reqUserInfo();
                if (res.code === 200) {
                    this.userInfo = res.data;
                } else {
                    throw new Error(res.message);
                }
            } catch (err) {
                console.log(err);
            }
        },
        async userLogout() {
            let res = await reqLogout();
            if (res.code === 200) {
                ElMessage.success('退出成功');
                removeToken();
                this.token = '';
                this.userInfo = { uid: 0, username: '' };
                router.push('/login');
            } else {
                ElMessage.error('退出失败：' + res.message);
                throw new Error(res.message);
            }
        },
    },
});
