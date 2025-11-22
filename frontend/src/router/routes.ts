import type { RouteRecordRaw } from 'vue-router';

export const constRoutes = [
    {
        name: 'Layout',
        path: '/',
        component: () => import('@/layout/index.vue'),
        meta: {
            title: '首页',
            icon: 'HomeFilled',
        },
        children: [
            {
                name: 'Login',
                path: 'login',
                component: () => import('@/views/login.vue'),
                meta: {
                    title: '登录',
                    icon: 'User',
                },
            },
            {
                name: 'Signup',
                path: 'signup',
                component: () => import('@/views/signup.vue'),
                meta: {
                    title: '注册',
                    icon: 'UserPlus',
                },
            },
            {
                name: 'Schedule',
                path: 'schedule',
                component: () => import('@/views/schedule.vue'),
                meta: {
                    title: '日程',
                    icon: 'Calendar',
                },
            },
        ],
    },
] as RouteRecordRaw[];
