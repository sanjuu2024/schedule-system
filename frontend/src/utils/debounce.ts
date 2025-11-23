// 防抖函数
// 在事件被触发n秒后再执行回调，如果在这n秒内又被触发，则重新计时
// 使用场景：搜索框输入、表单验证
export function debounce<T extends (...args: any[]) => any>(
    fn: T,
    delay: number = 300,
): (...args: Parameters<T>) => void {
    let timer: ReturnType<typeof setTimeout> | null = null;

    return function (this: any, ...args: Parameters<T>) {
        // 清除之前的定时器
        if (timer) {
            clearTimeout(timer);
        }

        // 设置新的定时器
        timer = setTimeout(() => {
            fn.apply(this, args);
            timer = null;
        }, delay);
    };
}

// 节流函数
// 规定在一个单位时间内，只能触发一次函数，如果这个单位时间内触发多次函数，只有一次生效
// 使用场景：滚动事件、resize事件、按钮点击
export function throttle<T extends (...args: any[]) => any>(
    fn: T,
    delay: number = 300,
): (...args: Parameters<T>) => void {
    let lastTime = 0;

    return function (this: any, ...args: Parameters<T>) {
        const now = Date.now();

        // 如果距离上次执行时间超过delay，则执行
        if (now - lastTime >= delay) {
            fn.apply(this, args);
            lastTime = now;
        }
    };
}
