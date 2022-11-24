<template>
  <Spinner v-if="loading" class="spinner-container" />
  <div v-else>
    <!-- <Header :battery-id="uuid" /> -->
    <div class="pass-container">
      <div v-for="section in data" :key="section">
        <SectionHeader :title="section.label" @click="toggle = !toggle" />
        <div class="section-content">
          <div v-for="content in section.content" :key="content">
            <div v-if="content.type === 'string'">
              <div v-for="subContent in content.content" :key="subContent">
                <Field :value="subContent" :label="content.label" />
              </div>
            </div>
            <div v-if="content.type === 'address'">
              <div v-for="subContent in content.content" :key="subContent">
                <Field
                  class="longer"
                  label="Address"
                  :city="subContent.street"
                  :country="subContent.country"
                  :postal="subContent.zip"
                  :value="subContent.name"
                />
                <Field label="Contact phone number" :value="subContent.phone" />
                <Field label="Email" :value="subContent.email" />
              </div>
            </div>
            <div v-if="content.type === 'material'">
              <!-- <AttributeField
                :attributes-list="content.content"
                :label="label"
              /> -->
              <br />
              <h2>{{ content.label }}</h2>

              <div v-for="subContent in content.content" :key="subContent">
                <div>{{ subContent.matierialPercentageMassFraction }}</div>
                <div>{{ subContent.matierialWeight }}</div>
                <div>{{ subContent.materialName }}</div>
                <span v-if="subContent.uuid">
                  <button @click="onClick">{{ subContent.uuid }}</button>
                </span>
              </div>
            </div>
            <div v-if="content.type === 'dimensions'">
              <div v-for="subContent in content.content" :key="subContent">
                <!-- <span>{{ subContent.length.value }}</span>
                <span>{{ subContent.length.unit }}</span> -->
                <Field
                  :label="content.label"
                  :length="subContent.length.value"
                  :unit="subContent.length.unit"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- <GeneralInformation
        section-title="General information"
        :general-information="data"
      />
      <CellChemistry
        section-title="Cell chemistry"
        :cell-chemistry="data.cellChemistry"
      />
      <ElectrochemicalProperties
        section-title="State of Health"
        :electrochemical-properties="data.electrochemicalProperties"
      />
      <BatteryComposition
        section-title="Parameters of The Battery"
        :battery-composition="data.composition"
      />
      <StateOfBattery
        section-title="State of Battery"
        :state-of-battery="data"
      />

      <Documents section-title="Documents" :documents="data.document" />
    </div>
    <Footer /> -->
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import GeneralInformation from "@/components/GeneralInformation.vue";
import CellChemistry from "@/components/CellChemistry.vue";
import ElectrochemicalProperties from "@/components/ElectrochemicalProperties.vue";
import BatteryComposition from "@/components/BatteryComposition.vue";
import StateOfBattery from "@/components/StateOfBattery.vue";
import Documents from "@/components/Documents.vue";
import Spinner from "@/components/Spinner.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { API_KEY } from "@/services/service.const";
import apiWrapper from "@/services/wrapper";
import AAS from "@/services/aasServices";
import { inject } from "vue";
import materialPassportExample00 from "../assets/MOCK/materialPassportExample00.json";
import SectionHeader from "../components/SectionHeader.vue";
import Field from "../components/Field.vue";
import AttributeField from "../components/AttributeField.vue";

export default {
  name: "PassportView",
  components: {
    // Header,
    // GeneralInformation,
    // CellChemistry,
    // StateOfBattery,
    // ElectrochemicalProperties,
    // BatteryComposition,
    // Documents,
    // Footer,
    SectionHeader,
    AttributeField,
    Spinner,
    Field,
  },

  data() {
    return {
      auth: inject("authentication"),
      data: null,
      loading: true,
      errors: [],
      passId: this.$route.params.id,
      MATERIAL_URL: process.env.VUE_APP_MATERIAL_URL,
    };
  },
  async created() {
    //this.loading = false;
    //let assetIds = this.$route.params.assetIds;
    // this.data = await this.getPassport(this.passId);
    this.data = materialPassportExample00[this.passId];
    this.loading = false;
  },
  methods: {
    onClick() {
      this.data =
        materialPassportExample00["c4daf9fe-be3c-46c8-aef4-82791cb5cdbc"];
      this.$router.push({
        path: `/c4daf9fe-be3c-46c8-aef4-82791cb5cdbc`,
      });
    },

    async getPassport(assetId) {
      let assetIdJson = [{ key: "Battery_ID_DMC_Code", value: assetId }];
      let aas = new AAS();
      let wrapper = new apiWrapper();
      let accessToken = await this.auth.getAuthTokenForTechnicalUser();
      let AASRequestHeader = {
        Authorization: "Bearer " + accessToken,
      };

      const shellId = await aas.getAasShellId(
        JSON.stringify(assetIdJson),
        AASRequestHeader
      );
      const shellDescriptor = await aas.getShellDescriptor(
        shellId[0],
        AASRequestHeader
      );
      const subModel = await aas.getSubmodelDescriptor(
        shellDescriptor,
        AASRequestHeader
      );
      if (subModel.endpoints.length > 0) {
        let providerConnector = {
          connectorAddress:
            subModel.endpoints[0].protocolInformation.endpointAddress,
          idShort: subModel.idShort,
        };
        let APIWrapperRequestHeader = {
          "x-api-key": API_KEY,
        };

        //let assetId = JSON.parse(assetIds)[1].value; // Two elements in json array [batteryIDDMCode, assetId], get the last element and it wll always be the asset id i.e., [1]
        console.info("Selected asset Id: " + assetId);
        const response = await wrapper.performEDCDataTransfer(
          assetId,
          providerConnector,
          APIWrapperRequestHeader
        );
        return response;
      } else
        alert(
          "There is no connector endpoint defined in submodel.. Could not proceed further!"
        );
    },
  },
};
</script>

<style>
.pass-container {
  width: 76%;
  margin: 0 12% 0 12%;
}

.spinner-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.spinner {
  margin: auto;

  width: 8vh;

  animation: rotate 3s infinite;
}

.section-content {
  width: 100%;
  border: solid 1px #b3cb2d;
  border-radius: 0 0 4px 4px;
  background-color: #fff;
  margin-bottom: 50px;
}

@keyframes rotate {
  100% {
    transform: rotate(360deg);
  }
}

@media (max-width: 750px) {
  .pass-container {
    width: 100%;
    margin: 0;
  }
}
</style>
