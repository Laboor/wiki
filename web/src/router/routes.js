import Layout from '@components/Layout';
import ParentView from '@components/ParentView';

/* meta 配置参数说明
 ** title--浏览器标签
 ** icon--面包屑及菜单栏图标
 ** allowNavigate--允许点击面包屑导航
 ** hideInBread--在面包屑中隐藏
 ** hideInMenu--在菜单中隐藏
 ** isSubMenu--是否多级菜单
 ** loginAuth--开启登录验证
 */

// 生产模式路由配置
let routes = [
	{
		path: '/',
		name: 'root',
		component: Layout,
		meta: {
			hideInBread: true,
			hideInMenu: true,
      loginAuth: true
		},
		redirect: 'home', // 重定向到首页
		children: [
			{
				path: 'home',
				name: 'home',
				component: () =>
					import(/* webpackChunkName: "Home" */ '@views/Home.vue'),
				meta: {
					title: '首页',
					icon: 'home',
					allowNavigate: true,
					hideInBread: true,
					hideInMenu: true,
				},
			},
			{
				path: 'test',
				name: 'test',
				component: ParentView,
				meta: {
					title: '测试',
					icon: 'home',
					isSubMenu: true,
				},
				children: [
					{
						path: 'login',
						name: 'login',
						component: ParentView,
						meta: {
							title: '登录',
							icon: 'home',
							isSubMenu: true,
						},
						children: [
							{
								path: 'undone',
								name: 'undone',
								component: () =>
									import(/* webpackChunkName: "Undone" */ '@views/Undone.vue'),
								meta: {
									title: '未完成',
									icon: 'home',
                  loginAuth: true,
								},
							},
						],
					},
				],
			},
		],
	},
	{
		path: '*',
		name: 'page-not-found',
		component: () =>
			import(
				/* webpackChunkName: "PageNotFound" */ '../views/PageNotFound.vue'
			),
		meta: {
			hideInMenu: true,
		},
	},
];

// 开发模式路由配置
if (process.env.NODE_ENV !== 'production') {
	let devRoutes = [
		{
			path: '/login',
			name: 'login',
			component: () =>
				import(/* webpackChunkName: "Login" */ '@views/Login.vue'),
			meta: {
				title: '登录',
				hideInMenu: true,
			},
		},
		{
			path: '/test',
			name: 'test',
			component: () => import(/* webpackChunkName: "Test" */ '@views/Test.vue'),
			meta: {
				title: '测试',
				hideInMenu: true,
			},
		},
	];
	routes = routes.concat(devRoutes);
}

export default routes;
