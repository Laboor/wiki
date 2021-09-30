<template>
  <div class="undone">
    <img
      class="undone-img"
      align="center"
      alt="开发中"
      src="@assets/img/building.png"
    />
    <h1 class="undone-title">
      功能开发中，敬请期待...
    </h1>
  </div>
</template>

<script>
export default {
  name: 'undone',
  destroyed() {
    console.log(`${this.$options.name}被销毁`);
  },
};
</script>

<style lang="less" scoped>
.undone {
  position: relative;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .undone-title {
    font-size: 20px;
    font-weight: bold;
  }
  .undone-img {
    width: 30%;
  }
}
</style>
