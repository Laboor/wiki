import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import http from '@http';
import store from '@store';
import api from '@config/api-config';
import { message } from 'ant-design-vue';
import NProgress from 'nprogress'; // Progress 进度条

Vue.use(VueRouter);

// 重写路由的push方法
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function(to) {
	return VueRouterPush.call(this, to).catch((err) => err);
};

// 路由重载
VueRouter.prototype.reload = function(routes) {
	this.matcher = createRouter(routes).matcher;
};

// 创建路由
function createRouter(routes) {
	return new VueRouter({
		mode: process.env.VUE_APP_ROUTER_MODE,
		base: process.env.BASE_URL,
		routes,
	});
}

const router = createRouter(routes);

// 初始路由标志
let isStartRoute = true;

// 路由守卫hook
router.beforeEach((to, from, next) => {
	// 开启进度条
	NProgress.start();
	// 修改页面title
	if (to.meta.title) {
		document.title = `${to.meta.title} — ${process.env.VUE_APP_BASE_TITLT}`;
	}

	// 初始导航
	if (from === VueRouter.START_LOCATION && isStartRoute) {
		isStartRoute = false;
		// 加载异步路由
		store.dispatch('getAsyncRoute').then(() => {
			if (to.matched[0].path === '*') {
				next({ path: to.fullPath });
			} else if (to.meta.loginAuth) {
				// 需要登录验证
				http
					.get(api.AUTH_VERIFICATION, {
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('token'),
						},
					})
					.then(() => {
						next();
					})
					.catch((error) => {
						if (error.response) {
							console.error('ResponseError', error.response);
						} else if (error.request) {
							console.error('RequestError', error.request);
						} else {
							console.error('UnknownError', error.message);
						}
						message.warning({
							content: '未登录账户，请先登录已继续浏览！',
							duration: 3,
						});
						router.redirectUrl = to.path; // 缓存跳转登录前的页面路径
						next({ name: 'login' }); // 跳转登录页
					});
			} else {
				next();
			}
		});
	} else {
		// 不为初始导航
		next();
	}
});

// 路由守卫hook
router.afterEach(() => {
	// 关闭进度条
	NProgress.done();
});

export default router;
