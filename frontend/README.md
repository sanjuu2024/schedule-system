# 📱 日程管理系统 - 前端

基于 Vue3 + TypeScript + Element Plus 构建的现代化前端应用。



## 🎯 技术栈

- **框架**: Vue 3.5.x (Composition API)
- **语言**: TypeScript 5.x
- **UI 库**: Element Plus 2.11.x
- **状态管理**: Pinia 3.x
- **路由**: Vue Router 4.x
- **HTTP 客户端**: Axios 1.x
- **构建工具**: Vite 6.x
- **代码规范**: ESLint + Prettier + Stylelint
- **Git Hook**: Husky + Commitlint



## 📦 项目结构

```
frontend/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API 接口
│   │   ├── schedule.ts    # 日程接口
│   │   ├── user.ts        # 用户接口
│   │   └── type.ts        # 类型定义
│   ├── assets/            # 资源文件
│   │   ├── images/        # 图片
│   │   └── styles/        # 全局样式
│   ├── components/        # 公共组件
│   ├── enums/             # 枚举常量
│   │   └── ErrorCode.ts   # 错误码枚举
│   ├── layout/            # 布局组件
│   │   ├── Header.vue     # 头部
│   │   └── index.vue      # 布局容器
│   ├── router/            # 路由配置
│   │   ├── index.ts       # 路由实例
│   │   └── routes.ts      # 路由表
│   ├── store/             # 状态管理
│   │   ├── index.ts       # Pinia 入口
│   │   └── user.ts        # 用户模块
│   ├── utils/             # 工具函数
│   │   ├── request.ts     # Axios 封装
│   │   ├── token.ts       # Token 管理
│   │   └── debounce.ts    # 防抖函数
│   ├── views/             # 页面组件
│   │   ├── login.vue      # 登录页
│   │   ├── register.vue   # 注册页
│   │   └── schedule.vue   # 日程管理页
│   ├── App.vue            # 根组件
│   ├── main.ts            # 入口文件
│   └── permission.ts      # 路由守卫
├── .eslintrc.js           # ESLint 配置
├── .prettierrc            # Prettier 配置
├── stylelintrc.cjs        # Stylelint 配置
├── tsconfig.json          # TypeScript 配置
├── vite.config.ts         # Vite 配置
└── package.json           # 依赖配置
```



## 🚀 快速开始

### 安装依赖

推荐使用 pnpm：

```bash
# 如果没有 pnpm，先安装
npm install -g pnpm

# 安装项目依赖
pnpm install
```

### 开发模式

```bash
pnpm run dev
```

自动打开浏览器访问 `http://localhost:5173`（具体端口视情况而定）

### 生产构建

```bash
pnpm run build
```

构建产物在 `dist` 目录

### 预览构建

```bash
pnpm run preview
```

### 代码检查

```bash
# ESLint 检查
pnpm run lint

# ESLint 自动修复
pnpm run fix

# Stylelint 检查
pnpm run lint:style

# Stylelint 自动修复
pnpm run lint:style-fix

# Prettier 格式化
pnpm run format
```



## 🔧 配置说明

### 环境变量

创建 `.env.development` 和 `.env.production` 文件：

```bash
# 开发环境
VITE_API_BASE_URL=http://localhost:8080/schedule

# 生产环境
VITE_API_BASE_URL=https://api.example.com
```

### 代理配置

修改 `vite.config.ts` 中的 proxy 配置：

```typescript
server: {
  proxy: {
    '/schedule': {
      target: 'http://localhost:8080',
      changeOrigin: true,
    }
  }
}
```



## 📝 核心功能

### 1. 用户认证
- 用户注册（表单验证、用户名唯一性检查）
- 用户登录（JWT Token 认证）
- 用户退出（清除 Token 和用户信息）
- 路由守卫（未登录自动跳转登录页）

### 2. 日程管理
- 日程列表（分页查询、加载状态）
- 添加日程（表单验证、自动聚焦）
- 编辑日程（数据回填、实时验证）
- 删除日程（二次确认弹窗）
- 状态切换（快速标记完成/未完成）

### 3. 状态管理
- 用户信息全局共享（Pinia）
- Token 持久化（localStorage）
- 响应式数据更新

### 4. 错误处理
- 统一的错误码体系
- 友好的错误提示
- Token 过期自动跳转登录



## 🎨 代码规范

### TypeScript

- 使用严格模式
- 接口优先于类型别名
- 明确的类型注解

### Vue 组件

- 使用 Composition API
- 单文件组件（SFC）
- `<script setup>` 语法糖
- Props 类型定义

### 样式

- 使用 SCSS 预处理器
- BEM 命名规范（推荐）
- 组件样式 scoped

### Git Commit

遵循 Conventional Commits：

```
feat: 新功能
fix: 修复 Bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
perf: 性能优化
test: 测试相关
chore: 构建/工具链相关
```



## 🔌 API 对接

### 请求拦截器

```typescript
// 自动添加 Token
request.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers.token = token
  }
  return config
})
```

### 响应拦截器

```typescript
// 统一错误处理
request.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      // Token 过期，跳转登录
      router.push('/login')
    }
    return Promise.reject(error)
  }
)
```



## 📊 性能优化

- ✅ 路由懒加载
- ✅ 组件按需引入
- ✅ 图片懒加载
- ✅ 防抖/节流
- ✅ 请求缓存



## 📚 参考文档

- [Vue 3 官方文档](https://cn.vuejs.org/)
- [Element Plus 文档](https://element-plus.org/zh-CN/)
- [Pinia 文档](https://pinia.vuejs.org/zh/)
- [Vue Router 文档](https://router.vuejs.org/zh/)
- [Vite 文档](https://cn.vitejs.dev/)
- [TypeScript 文档](https://www.typescriptlang.org/zh/)
