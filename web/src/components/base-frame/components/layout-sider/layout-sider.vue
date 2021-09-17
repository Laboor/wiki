<template>
	<a-layout-sider class="layout-sider" width="250">
		<div class="logo">
			<img
				src="@assets/logo/gzrc-logo-lg.png"
				alt="logo"
				style="width:100%;"
				class="logo-img"
			/>
		</div>
		<recursive-menu />
	</a-layout-sider>
</template>

<script>
import recursiveMenu from './compontents/recursive-menu.vue';

export default {
	name: 'layout-sider',
	components: {
		'recursive-menu': recursiveMenu,
	},
};
</script>

<style lang="less" scoped>
.layout-sider {
	.logo {
		height: 43px;
		margin: 16px;
		border-radius: 10px;

		.logo-img {
			border-radius: 5px;
			height: 100%;
		}
		.logo-font {
			color: aliceblue;
		}
	}
}
</style>
