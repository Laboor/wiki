import Vue from 'vue';
import Vuex from 'vuex';
import router from '@router';
import http from '@http';
import api from '@config/api';
import { CREATE_MAIN_MENU } from './mutation-types';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		userInfo: {
			id: '',
			name: '',
			role: '',
			authorityLevel: 0,
			token: '',
		},
		mainMenu: [],
	},
	mutations: {
		[CREATE_MAIN_MENU](state) {
			let routes = router.getRoutes();
      // 过滤根路由
			let menuRoutes = routes.filter((item) => {
				return item.parent;
			});

			let toBeDel = [];
			menuRoutes.forEach((route, index) => {
				if (route.parent) {
					menuRoutes.forEach((parentRoute) => {
						if (parentRoute.path === route.parent.path) {
							parentRoute.children = []; // 触发BUG
							parentRoute.children.push(route);
							toBeDel.push(menuRoutes[index]);
						}
					});
				}
			});
			state.mainMenu = menuRoutes.filter((route) => {
				return route && !toBeDel.includes(route);
			});
      console.log(state.mainMenu);
		},
	},
	actions: {
		getAsyncRoute({ commit }) {
			return http
				.get(api.MENU)
				.then((res) => {
					res.data.routes.forEach((routeConfig) => {
						let componentName = routeConfig.component;
						routeConfig.component = () => import(`@views/${componentName}`);
						let parent = routeConfig.parent;
						delete routeConfig.parent;
						router.addRoute(parent, routeConfig);
					});
					return res;
				})
				.finally(() => {
					commit(CREATE_MAIN_MENU); // 生成主菜单
				});
		},
		updateUserInfo({ dispatch, state }, userId) {
			return http
				.get(api.USER_INFO, {
					params: {
						userId: userId,
					},
				})
				.then((res) => {
					state.userInfo = res.userInfo;
					dispatch('getAsyncRoute', res.userInfo.role);
					return res;
				});
		},
	},
	modules: {},
});
