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
import LayoutSider from './components/LayoutSider';
import LayoutHeader from './components/LayoutHeader';
import LayoutContent from './components/LayoutContent';
import LayoutFooter from './components/LayoutFooter';

export default {
  name: 'Layout',
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
