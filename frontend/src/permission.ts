import router from '@/router';
import { getToken } from './utils/token';
import { useUserStore } from './store/user';
import { ElMessage } from 'element-plus';

// 全局前置路由守卫
router.beforeEach(async (to: any, from: any, next: any) => {
    const token = getToken();
    if (token) {
        if (to.path === '/login') {
            next({ path: '/' });
        } else {
            const userStore = useUserStore();
            if (!userStore.userInfo.id) {
                try {
                    await userStore.getUserInfo();
                } catch (err) {
                    // 有可能是token过期，但是已经在axios响应拦截器中处理过了；这里就只是返回登录页即可。
                    alert(err + '\n即将跳转到登录页面。');
                    next('/login');
                }
            }
            next();
        }
    } else {
        if (to.path === '/login' || to.path === '/register') {
            next();
        } else {
            ElMessage.error('请先登录！');
            next({ path: '/login', query: { redirect: to.fullPath } });
        }
    }
});

// 全局后置路由守卫
router.afterEach((to: any, from: any) => {
    document.title = to.meta.title || '日程管理系统';
});
