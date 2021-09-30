<template>
	<a-breadcrumb class="header-breadcrumb">
		<a-breadcrumb-item>
			<router-link to="/home">
				<a-icon type="home" />
				<span>首页</span>
			</router-link>
		</a-breadcrumb-item>
		<template v-for="item of shouldShowRoutes">
			<a-breadcrumb-item :key="item.name">
				<router-link
					:to="item.path"
					v-if="item.meta.allowNavigate && item.name !== $route.name"
				>
					<a-icon :type="item.meta.icon" />
					<span>{{ item.meta.title }}</span>
				</router-link>
				<template v-else>
					<a-icon :type="item.meta.icon" />
					<span>{{ item.meta.title }}</span>
				</template>
			</a-breadcrumb-item>
		</template>
	</a-breadcrumb>
</template>

<script>
export default {
	name: 'Breadcrumb',
	computed: {
		shouldShowRoutes() {
			return this.$route.matched.filter((route) => {
				return route.meta ? !route.meta.hideInBread : false;
			});
		},
	},
	watch: {
		$route(oldRoute) {
			this.routes = oldRoute.matched;
		},
	},
};
</script>

<style lang="less" scoped>
.header-breadcrumb {
	display: inline;
	vertical-align: middle;
}
</style>
