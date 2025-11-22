import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';
const pathResolve = (dir: string) => path.resolve(__dirname, dir);

// https://vite.dev/config/
export default defineConfig(({mode}) => {
	let env = loadEnv(mode, process.cwd());
	return {
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
		server: {
			proxy: {
				// 即VITE_APP_BASE_API
				// 开发环境下关键字(关键路径?)为/api,
				// 生产环境下关键字为http://127.0.0.1:10086(直接上后端地址)(实际生产环境不用这么麻烦因为前后端同个地址同个端口),
				// 测试环境下关键字为/test-api,
				// 所以不用一个个写如：'/api': {}，可以直接：
				[env.VITE_APP_BASE_API]: {
					// 🍰获取数据的服务器地址
					target: env.VITE_SERVE, // 🔺写成server了...然后卡大半天怀疑人生...
					// 🍰是否需要代理跨域
					changeOrigin: true,
					// 🍰路径重写(🔺注意不是`^/${...}`！！！VITE_APP_BASE_API自带`/`来着！！！)
					rewrite: (path) =>
						path.replace(
							new RegExp(`^${env.VITE_APP_BASE_API}`),
							'',
						)
				},
			},
		},
	}
});
