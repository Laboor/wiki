<template>
	<div>
		<a-popover title="Title" trigger="click" :visible="show">
			<template slot="content">
				<p>Content</p>
				<p>Content</p>
			</template>
			<span >Hello World</span>
		</a-popover>

    <button @click="onClick" style="margin: 200px;">
      button
    </button>
	</div>
</template>

<script>
export default {
	data() {
		return {
			show: false,
		};
	},
	methods: {
		onClick() {
			this.show = !this.show;
		},
	},
};
</script>
