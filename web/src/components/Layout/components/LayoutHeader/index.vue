<template>
	<a-layout-header class="layout-header">
		<breadcrumb class="header-breadcrumb"/>
		<right-content />
	</a-layout-header>
</template>

<script>
import Breadcrumb from './components/Breadcrumb';
import RightContent from './components/RightContent';

export default {
	name: 'LayoutHeader',
	components: {
		Breadcrumb,
		RightContent,
	},
	data() {
		return {};
	},
};
</script>

<style lang="less" scoped>
.layout-header {
	background: #fff;
	padding: 0;

	.header-breadcrumb {
		padding: 0 24px;
	}
}
</style>
