<template>
    <div class="card">
        <img :src="card.image_uris.small"/>
    </div>
</template>

<script>
export default {
    name: "Card",
    props: ['card']
}
</script>

<style>

</style>