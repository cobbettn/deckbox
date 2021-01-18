<template>
  <div id="app">
    <Header user="Planeswalker"/>
    <router-view></router-view>    
  </div>
</template>

<script>
import Header from './components/header/Header.vue'

export default {
  name: 'App',
  components: {
    Header,
  },
  data() {
    return {}
  }
}
</script>

<style>
* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }
  body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
    background: #272727;
    color: #747474;
  }

</style>
