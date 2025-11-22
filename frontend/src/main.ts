import { createApp } from 'vue';
import App from './App.vue';
import '@/assets/styles/index.scss';
import router from '@/router/index.ts';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn'; // 导入中文语言包,这样设置后所有ElementPlus的所有组件都会显示为中文文本
import 'element-plus/dist/index.css';

const app = createApp(App);
app.use(router);
app.use(ElementPlus, {
    locale: zhCn,
});
app.mount('#app');
