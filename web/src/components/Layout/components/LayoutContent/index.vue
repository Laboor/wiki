<template>
	<a-layout-content class="layout-content">
		<slot></slot>
	</a-layout-content>
</template>

<script>
export default {
	name: 'LayoutContent',
};
</script>

<style lang="less" scoped>
.layout-content {
	background: #fff;
	min-height: 280px;
}
</style>
