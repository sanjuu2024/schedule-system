import type { RouteRecordRaw } from 'vue-router';

export const constRoutes = [
    {
        name: 'Layout',
        path: '/',
        component: () => import('@/layout/index.vue'),
        meta: {
            title: '首页',
        },
        children: [
            {
                name: 'Login',
                path: 'login',
                component: () => import('@/views/login.vue'),
                meta: {
                    title: '登录',
                },
            },
            {
                name: 'Register',
                path: 'register',
                component: () => import('@/views/register.vue'),
                meta: {
                    title: '注册',
                },
            },
            {
                name: 'Schedule',
                path: 'schedule',
                component: () => import('@/views/schedule.vue'),
                meta: {
                    title: '日程',
                },
            },
        ],
    },
    {
        name: '404',
        path: '/:pathMatch(.*)*',
        component: () => import('@/components/404.vue'),
        meta: {
            title: '404 Not Found',
        },
    },
] as RouteRecordRaw[];
