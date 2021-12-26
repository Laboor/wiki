export default {
	routes: [
		{
			path: '/test/login/foo',
			name: 'foo',
      parent: 'login',
			component: 'Foo.vue',
			meta: {
				title: '愚人',
				icon: 'home',
			},
		},
	],
};
