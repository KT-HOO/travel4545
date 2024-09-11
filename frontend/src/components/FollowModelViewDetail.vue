<template>

    <v-card outlined>
        <v-card-title>
            FollowModel # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="FromId" v-model="item.fromId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="ToId" v-model="item.toId" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'FollowModelViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/followModels/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

