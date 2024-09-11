<template>

    <v-data-table
        :headers="headers"
        :items="followModel"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'FollowModelView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "fromId", value: "fromId" },
                { text: "toId", value: "toId" },
            ],
            followModel : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/followModels'))

            temp.data._embedded.followModels.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.followModel = temp.data._embedded.followModels;
        },
        methods: {
        }
    }
</script>

