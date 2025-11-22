import { createRouter, createWebHistory } from 'vue-router';
import { constRoutes } from './routes';
export default createRouter({
    history: createWebHistory(),
    routes: constRoutes,
    scrollBehavior() {
        return {
            left: 0,
            top: 0,
        };
    },
});
