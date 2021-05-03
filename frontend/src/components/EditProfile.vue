<template>
  <v-card>
    <v-container fluid>
      <v-row>
        <v-col cols="4">
          <v-list flat max-width="300">
            <v-subheader>Editar perfil</v-subheader>
            <v-list-item-group v-model="selectedItem" color="primary">
              <v-list-item v-for="(item, i) in items" :key="i">
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title v-text="item.text"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-col>
        <v-divider vertical></v-divider>
        <v-col cols="8">
          <keep-alive>
            <v-component :is="currentTab"></v-component>
          </keep-alive>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import EditPicProfile from "@/components/EditPicProfile.vue";
import EditPersonalData from "@/components/EditPersonalData.vue";
import EditPassword from "@/components/EditPassword.vue";
export default {
  name: "EditProfile",
  components: {
    EditPicProfile,
    EditPersonalData,
    EditPassword,
  },
  data: () => ({
    selectedItem: 0,
    items: [
      {
        text: "Foto de perfil",
        icon: "mdi-file-image",
        componentName: "EditPicProfile",
      },
      {
        text: "Editar datos",
        icon: "mdi-account",
        componentName: "EditPersonalData",
      },
      {
        text: "Cambiar contraseña",
        icon: "mdi-account-key",
        componentName: "EditPassword",
      },
    ],
  }),

  computed: {
    currentTab() {
      return this.items[this.selectedItem].componentName;
    },
  },
};
</script>

<style></style>
