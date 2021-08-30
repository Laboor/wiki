import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import http from '../http';
import apiConfig from '../config/api-config';
import { message } from 'ant-design-vue';

Vue.use(VueRouter);

// 重写路由的push方法
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
	return VueRouterPush.call(this, to).catch((err) => err);
};

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes,
});

// 路由变化hook
router.beforeEach((to, from, next) => {
	// 修改页面title
	if (to.meta.title) {
		document.title = `${to.meta.title} — ${process.env.VUE_APP_BASE_TITLT}`;
	}

	// 登录验证
	if (to.meta.loginAuth) {
		http
			.get(apiConfig.authVerification, {
				headers: { Authorization: 'Bearer ' + localStorage.getItem('token') },
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

export default router;
