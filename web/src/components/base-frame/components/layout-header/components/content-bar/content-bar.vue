<template>
	<div class="header-right">
		<!-- 用户信息下拉框 -->
		<a-dropdown>
			<span class="user-info">
				<a-avatar class="user-avatar" size="large" icon="user" />
				<span class="user-name">Admin User</span>
			</span>
			<a-menu slot="overlay">
				<a-menu-item>
					<a-icon type="user" />
					<span>个人中心</span>
				</a-menu-item>
				<a-menu-item>
					<a-icon type="setting" />
					<span>个人设置</span>
				</a-menu-item>
				<a-menu-item @click="logout">
					<a-icon type="logout" />
					<span>退出登录</span>
				</a-menu-item>
			</a-menu>
		</a-dropdown>
	</div>
</template>

<script>
export default {
	name: 'content-bar',
	data() {
		return {};
	},
	methods: {
		logout() {
			localStorage.setItem('token', '');
			this.$router.push('/login');
		},
	},
};
</script>

<style lang="less" scoped>
.header-right {
	height: 100%;
	margin-right: 20px;
	float: right;

	.user-info {
		display: inline-block;
		padding-left: 10px;
		padding-right: 10px;
		margin-left: 5px;
		margin-right: 5px;
		transition: background 0.3s;
		&:hover {
			background: rgb(231, 231, 231);
		}
		.user-name {
			margin-left: 10px;
		}
	}
}
</style>
