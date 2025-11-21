import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';
const pathResolve = (dir: string) => path.resolve(__dirname, dir);

// https://vite.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': pathResolve('./src')
        }
    },
	css: {
		preprocessorOptions: {
			scss: {
				// 使用 @use 代替 @import (推荐的现代方式)
				// as * 表示不使用命名空间，可以直接访问变量
				additionalData: `@use "@/assets/styles/variables.scss" as *;`,
			},
			// 如果用到了less和stylus可以如下配置：
			// less: {
			// 	javascriptEnabled: true,
			// 	additionalData: `@use "@/assets/styles/variables.less" as *;`,
			// },
			// stylus: {
			// 	additionalData: `@use "@/assets/styles/variables.styl" as *;`,
			// },
		},
	},
});
