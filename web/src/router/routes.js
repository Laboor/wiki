import BaseFrame from '../components/base-frame/base-frame.vue';


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
    component: BaseFrame,
    meta: {
      hideInBread: true,
      hideInMenu: true,
    },
    children: [
      {
        path: '/home',
        name: 'home',
        component: () =>
          import(/* webpackChunkName: "undone" */ '../views/undone/undone.vue'),
        meta: {
          title: '首页',
          icon: 'home',
          allowNavigate: true,
          hideInBread: true,
          hideInMenu: true,
        },
      },
    ],
  },
  {
    path: '*',
    name: 'page-not-found',
    component: () =>
      import(
        /* webpackChunkName: "page-not-found" */ '../views/page-not-found/page-not-found.vue'
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
        import(/* webpackChunkName: "login" */ '../views/login/login.vue'),
      meta: {
        title: '登录',
        hideInMenu: true,
      },
    },
    {
      path: '/test',
      name: 'test',
      component: () =>
        import(/* webpackChunkName: "test" */ '../views/test.vue'),
      meta: {
        title: '测试',
        hideInMenu: true,
        // loginAuth: true,
      },
    },
  ];
  routes = routes.concat(devRoutes);
}

export default routes;
