<template>
    <div>
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
    img {
        margin: .5em;
    }
</style>