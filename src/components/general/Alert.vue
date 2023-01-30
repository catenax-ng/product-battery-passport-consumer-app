<template>
    <v-alert
        closable
        icon="mdi-vuetify"
        :title=title
        :text=description
        :type=type
        >
    </v-alert>
</template>

<script>
export default {
 name: "Alert",
 props: {
    title: {
      type: String,
      default: "Alert",
    },
    description: {
      type: String,
      default: "Alert Description",
    },
    type: {
      type: String,
      default: "info",
    }
 }  
}
</script>

<style>

</style>