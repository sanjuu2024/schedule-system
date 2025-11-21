# 个人 Vue3 + vite 开发模板



### 🍰 使用指南

- 复制模板内所有文件到目标文件夹。
- （在目标文件夹根目录下）执行：

>    过程中如果出现warning，跟着指令运行pnpm approve-builds就行（空格选择enter安装）

```cmd
pnpm i
```

-   如果是全栈项目推荐根目录和前端项目的包统一管理，在根目录下添加`pnpm-workspace.yaml`，写入：（并且在根目录中执行 `pnpm i`。）

```json
packages:
  - "frontend"
```



### ⚒️ 命令

-   `pnpm run lint`：运行 ESLint 检查 `src` 目录下的所有代码。
-   `pnpm run fix`：在上一条命令的基础上自动修复eslint可修复的问题。
-   `pnpm run lint:style`：检查项目 `src` 目录下的样式文件，匹配以下类型：
    -   `.css`
    -   `.scss`
    -   `.vue` 文件中的 `<style>` 标签内容
-   `pnpm run lint:style-fix`：在上一条命令的基础上自动修复stylelint可修复的问题。
-   `format`：使用 Prettier 自动格式化 src 下的代码和文档文件（js, ts, vue, html, css, scss, md）。



### 🍕 注

-   已配置 `@` 为 `src/`。
-   已安装 `axios` 和 `husky`。



### 🍔 可选

#### 1. 关于husky

鉴于可能是全栈项目，就没直接在模板中配置husky。

不管是哪种类型，前端项目相关的请按照以下配置：（**以下操作都在项目根目录执行**）

-   如果没有package.json请先执行：

```cmd
pnpm init
```

-   执行：

```cmd
pnpm add -D husky @commitlint/config-conventional @commitlint/cli commitizen cz-conventional-changelog
git init
pnpm husky install   # 初始化.husky/文件夹
```

-   创建 `commitlint.config.js`：

```js
module.exports = {
    extends: ['@commitlint/config-conventional'],
};
```

-   在 `.husky/` 文件夹下创建 `commit-msg`，写入：

```
#!/usr/bin/env sh
. "$(dirname -- "$0")/_/husky.sh"

npx --no -- commitlint --edit ${1}
```

-   把 `.husky/pre-commit` 修改为：

```cmd
#!/usr/bin/env sh
. "$(dirname -- "$0")/_/husky.sh"

# cd frontend   # 如果是在全站项目中才需要写这句
pnpm run format
git add .
```

-   `package.json`中添加：

```cmd
"config": {
  "commitizen": {
    "path": "./node_modules/cz-conventional-changelog"
  }
}
```

**之后执行`git add .`和`git cz`即可。**

**注意不要把`.husky/`放入`.gitignore`。**



#### 2. 关于element-plus

如果项目用到了element-plus，请如下配置：

-   执行：

```cmd
pnpm install element-plus @element-plus/icons-vue
```

-   `main.ts`

```ts
import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn';   // 导入中文语言包,这样设置后所有ElementPlus的所有组件都会显示为中文文本

const app = createApp(App);
app.use(ElementPlus,{
    locale:zhCn
});
app.mount('#app');
```
