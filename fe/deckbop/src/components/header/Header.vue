<template>
    <div class="header">
        <h2>DeckBop</h2>
        <div>
            <h2>
                Welcome {{ user }} 
                <button>logout</button>
            </h2>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Header',
    props: {
        user: String
    }
}
</script>

<style scoped>

    .header{
        padding: .7em;
        background: #747474;
        color: #272727;
        display: flex;
        justify-content: space-between;
    }
</style>