<template>
  <div class="tabs-nav-container" ref="tabs-nav-container" @contextmenu.prevent>
    <span class="left-btn" @click="handleLeftBtn" ref="left-btn">
      <a-icon class="left-btn-icon" type="left" />
    </span>
    <div class="tabs-scroll-bar" ref="tabs-scroll-bar">
      <transition-group name="tabs-list" tag="div">
        <a-dropdown
          :trigger="['contextmenu']"
          v-for="tab in tabList"
          :key="tab.routePath"
        >
          <div class="tab" @click="handleClickTab($event, tab)">
            <span
              class="tab-dot"
              :class="[tab.isCurrent ? 'tab-dot-active' : 'tab-dot-inactive']"
            ></span>
            <span class="tab-text">{{ tab.title }}</span>
            <a-icon
              v-if="tab.closable"
              type="close"
              class="tab-close-icon"
              @click.stop="handleCloseTab($event, tab)"
            />
          </div>
          <a-menu slot="overlay">
            <a-menu-item
              v-for="item in contextMenuList"
              :key="item.text"
              @click="item.handleClickItem($event, tab)"
            >
              {{ item.text }}
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </transition-group>
    </div>
    <span class="right-btn" @click="handleRightBtn" ref="right-btn">
      <a-icon class="right-btn-icon" type="right" />
    </span>
  </div>
</template>

<script>
export default {
  name: 'TabsNav',
  props: {
    tabList: {
      type: Array,
      required: true,
      defualt: function() {
        return [
          {
            title: 'new_tab',
            routePath: '',
            closable: true,
            isCurrent: false,
          },
        ];
      },
    },
    contextMenuList: {
      type: Array,
      required: false,
    },
  },
  data() {
    return {
      currentTranslateValue: 0,
      translateStepValue: 300,
      container: null,
      tabsBar: null,
      innerBtn: null,
    };
  },
  methods: {
    handleClickTab(e, tab) {
      this.$emit('click-tab', e, tab);
    },
    handleCloseTab(e, tab) {
      this.$emit('close-tab', e, tab);
    },
    handleContextMenu(e, tab) {
      this.$emit('context-menu', e, tab);
    },
    handleLeftBtn() {
      if (this.currentTranslateValue > 0) return;
      if (Math.abs(this.currentTranslateValue) >= this.translateStepValue) {
        this.currentTranslateValue += this.translateStepValue;
        this.tabsBar.style.transform = `translateX(${this.currentTranslateValue}px)`;
      } else {
        this.currentTranslateValue = 0;
        this.tabsBar.style.transform = `translateX(0px)`;
      }
    },
    handleRightBtn() {
      let containerRightCoord = this.container.getBoundingClientRect().right;
      let tabsBarRightCoord = this.tabsBar.getBoundingClientRect().right;
      let diffRightCoord =
        containerRightCoord - tabsBarRightCoord - this.innerBtn.offsetWidth;
      if (diffRightCoord > 0) return;
      if (Math.abs(diffRightCoord) > this.translateStepValue) {
        this.currentTranslateValue -= this.translateStepValue;
        this.tabsBar.style.transform = `translateX(${this.currentTranslateValue}px)`;
      } else {
        this.currentTranslateValue += diffRightCoord;
        this.tabsBar.style.transform = `translateX(${this.currentTranslateValue}px)`;
      }
    },
  },
  mounted() {
    this.container = this.$refs['tabs-nav-container'];
    this.tabsBar = this.$refs['tabs-scroll-bar'];
    this.innerBtn = this.$refs['right-btn'];
  },
};
</script>

<style lang="less" scoped>
@tabs-nav-height: 30px;
@tab-gutter: 7px;
@inner-btn-width: 25px;

.tabs-list-leave-active {
  position: absolute;
}
.tabs-list-enter,
.tabs-list-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

.tabs-nav-container {
  display: inline-block;
  position: relative;
  height: @tabs-nav-height;
  width: 100%;

  .inner-btn() {
    position: relative;
    width: @inner-btn-width + @tab-gutter;
    user-select: none;
    z-index: 1;
  }

  .btn-icon() {
    display: flex;
    justify-content: center;
    align-items: Center;
    background-color: rgb(190, 190, 190);
    width: @inner-btn-width;
    border-radius: 3px;
    height: @tabs-nav-height;
    cursor: pointer;
  }

  .left-btn {
    .inner-btn();
    float: left;

    .left-btn-icon {
      .btn-icon();
      float: left;
    }
  }

  .right-btn {
    .inner-btn();
    float: right;

    .right-btn-icon {
      .btn-icon();
      float: right;
    }
  }

  .tabs-scroll-bar {
    display: inline-block;
    position: absolute;
    white-space: nowrap;
    transition: transform 0.3s;
    z-index: 0;

    .tab {
      display: inline-block;
      height: 100%;
      padding: 0 10px;
      background-color: white;
      border-radius: 3px;
      cursor: pointer;
      box-shadow: 1px 1px 2px #d4d4d4;
      user-select: none;
      transition: all 0.3s;

      &:not(:first-child) {
        margin: 0 0 0 @tab-gutter;
      }

      &:hover {
        background-color: rgba(255, 255, 255, 0.63);
      }

      .tab-dot {
        display: inline-block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
      }

      .tab-dot-active {
        background-color: #2d8cf0;
      }

      .tab-dot-inactive {
        background-color: rgb(204, 204, 204);
      }

      .tab-text {
        font-size: 14px;
        text-align: center;
        line-height: @tabs-nav-height;
        margin: 0 10px;
      }

      .tab-close-icon {
        font-size: 12px;
        color: rgb(161, 160, 160);

        &:hover {
          color: red;
        }
      }
    }
  }
}
</style>
