<template>
	<a-breadcrumb class="header-breadcrumb">
		<a-breadcrumb-item>
			<router-link to="/home">
				<a-icon type="home" />
				<span>首页</span>
			</router-link>
		</a-breadcrumb-item>
		<template v-if="!$route.meta.hideInBread">
			<template v-for="item of routes">
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
		</template>
	</a-breadcrumb>
</template>

<script>
export default {
	name: 'breadcrumb',
	data() {
		return {
			routes: this.$route.matched,
		};
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
