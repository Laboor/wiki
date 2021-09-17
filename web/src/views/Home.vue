<template>
	<div>
		<a-popover title="Title" trigger="click" :visible="show">
			<template slot="content">
				<p>Content</p>
				<p>Content</p>
			</template>
			<span>Hello World</span>
		</a-popover>

		<button @click="onClick" style="margin: 200px;">
			button
		</button>
	</div>
</template>

<script>
export default {
	name: 'home',
	data() {
		return {
			show: false,
		};
	},
	methods: {
		onClick() {
			this.$store.dispatch('getAsyncRoute');
		},
	},
};
</script>
