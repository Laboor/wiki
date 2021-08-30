<template>
	<a-result status="404" title="404" sub-title="对不起，您浏览的页面不存在！">
		<template #extra>
			<router-link to="/home">
				<a-button type="primary">
					返回主页
				</a-button>
			</router-link>
			<router-view></router-view>
		</template>
	</a-result>
</template>
<script>
export default {
	data() {
		return {};
	},
};
</script>
