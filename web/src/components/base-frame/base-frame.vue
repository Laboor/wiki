<template>
  <a-layout class="main-page">
    <layout-sider />
    <a-layout>
      <layout-header />
      <layout-content class="layout-content">
        <router-view></router-view>
      </layout-content>
      <layout-footer />
    </a-layout>
  </a-layout>
</template>

<script>
import LayoutSider from './components/layout-sider/layout-sider.vue';
import LayoutHeader from './components/layout-header/layout-header.vue';
import LayoutContent from './components/layout-content/layout-content.vue';
import LayoutFooter from './components/layout-footer/layout-footer.vue';

export default {
  name: 'base-frame',
  components: {
    LayoutHeader,
    LayoutSider,
    LayoutContent,
    LayoutFooter,
  },
  data() {
    return {};
  },
};
</script>

<style lang="less" scope>
.main-page {
  height: 100%;

  .layout-content {
    margin: 15px;
  }
}
</style>
