<template>
	<a-layout-sider
		class="layout-sider"
		width="250"
	>
		<div class="logo">
			<img
				src="../../../../assets\logo\gzrc-logo-lg.png"
				alt="logo"
				style="width:100%;"
				class="logo-img"
			/>
		</div>

		<a-menu
			class="layout-sider-menu"
			theme="dark"
			mode="inline"
			:default-selected-keys="['1']"
		>
			<template v-for="item of router">
				<a-menu-item
					:key="item.name"
					v-if="!item.meta.hideInMenu && item.meta.isSubMenu === false"
				>
					<router-link :to="item.path">
						<a-icon :type="item.meta.icon" />
						<span>{{ item.meta.title }}</span>
					</router-link>
				</a-menu-item>

				<a-sub-menu
					:key="item.name"
					v-if="!item.meta.hideInMenu && item.meta.isSubMenu === true"
				>
					<span slot="title">
						<a-icon :type="item.meta.icon" />
						<span>{{ item.meta.title }}</span>
					</span>
					<template v-for="item of item.children">
						<a-menu-item :key="item.name" v-if="!item.meta.hideInMenu">
							<router-link :to="item.path">
								<span>{{ item.meta.title }}</span>
							</router-link>
						</a-menu-item>
					</template>
				</a-sub-menu>
			</template>
		</a-menu>
	</a-layout-sider>
</template>

<script>
export default {
	name: 'layout-sider',
	data() {
		return {
			router: this.$router.options.routes,
		};
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
