<template v-if="batteryComposition">
  <SectionHeader title="2. Battery composition" />
  <!-- Composition of Electrolyte -->
  <div class="section-content">
    <div class="sub-section-container">
      <div class="sub-title-container">
        <span class="sub-title">Composition of Electrolyte</span>
      </div>
      <div
        v-if="batteryComposition.electrolyteComposition"
        class="list-container"
      >
        <ul>
          <span class="list-label" />
          <li
            v-for="electrolytes in batteryComposition.electrolyteComposition"
            :key="electrolytes"
            data-cy="electrolyte-composition"
          >
            {{ electrolytes }}
          </li>
        </ul>
      </div>
    </div>
    <!-- Composition of Anode -->
    <div class="sub-section-container">
      <div class="sub-title-container">
        <span class="sub-title"> Composition of Anode</span>
      </div>
      <Field
        label="Natural Graphite content"
        :value="
          batteryComposition.anodeContent.naturalGraphiteContent.value
        "
        :unit="
          batteryComposition.anodeContent.naturalGraphiteContent.unit
        "
      />
      <div
        v-if="batteryComposition.anodeContent.anodeComposition"
        class="list-container"
      >
        <ul>
          <span class="list-label">Recyclate Content Ni</span>
          <li
            v-for="electrolytes in batteryComposition.anodeContent
              .anodeComposition"
            :key="electrolytes"
          >
            {{ electrolytes }}
          </li>
        </ul>
      </div>
    </div>
    <!-- Composition of Cathode -->
    <div class="sub-section-container">
      <div class="sub-title-container">
        <span class="sub-title"> Composition of Cathode</span>
      </div>
      <Field
        label="Li content"
        :value="batteryComposition.cathodeComposition.liContent.value"
        :unit="batteryComposition.cathodeComposition.liContent.unit"
      />
      <Field
        label="Ni content"
        :value="batteryComposition.cathodeComposition.niContent.value"
        :unit="batteryComposition.cathodeComposition.niContent.unit"
      />
      <Field
        label="Co content"
        :value="batteryComposition.cathodeComposition.coContent.value"
        :unit="batteryComposition.cathodeComposition.coContent.unit"
      />
      <div
        v-if="batteryComposition.cathodeComposition.otherCathodeComposition"
        class="list-container"
      >
        <ul>
          <span class="list-label">Recyclate Content Ni</span>
          <li
            v-for="electrolytes in batteryComposition.cathodeComposition
              .otherCathodeComposition"
            :key="electrolytes"
          >
            {{ electrolytes }}
          </li>
        </ul>
      </div>
    </div>
    <!-- Part Numbers for Components TABLE -->
    <div class="sub-title-container">
      <span class="sub-title"> Part Numbers for Components</span>
    </div>
    <b-table
      borderless
      striped
      :items="items"
    />
    <!-- Critical Raw Materials -->
    <div class="sub-section-container">
      <div class="sub-title-container">
        <span class="sub-title">Critical Raw Materials</span>
      </div>
      <div
        v-if="batteryComposition.crm"
        class="list-container"
      >
        <ul>
          <span class="list-label">List of CRM</span>
          <li
            v-for="electrolytes in batteryComposition.crm"
            :key="electrolytes"
          >
            {{ electrolytes }}
          </li>
        </ul>
      </div>
    </div>
    <!-- Recycled content in active materials/battery model
 -->
    <div class="sub-section-container">
      <div class="sub-title-container">
        <span class="sub-title">Recycled content in active materials/battery model</span>
      </div>
      <Field
        label="Recyclate Content Ni"
        :value="batteryComposition.niRecyclateContent.value"
        :unit="batteryComposition.niRecyclateContent.unit"
      />
      <Field
        label="Recyclate Content Li"
        :value="batteryComposition.liRecyclateContent.value"
        :unit="batteryComposition.liRecyclateContent.unit"
      />
      <Field
        label="Recyclate Content Co"
        :value="batteryComposition.coRecyclateContent.value"
        :unit="batteryComposition.coRecyclateContent.unit"
      />
      <Field
        label="Recyclate Content Pb"
        :value="batteryComposition.pbRecyclateContent.value"
        :unit="batteryComposition.pbRecyclateContent.unit"
      />
    </div>
  </div>
</template>

<script>
import SectionHeader from "./SectionHeader.vue";
import SectionContent from "./SectionContent.vue";
import Field from "./Field.vue";

export default {
  name: "BatteryComposition",
  components: {
    Field,
    SectionHeader,
    SectionContent,
  },
  props: {
    sectionTitle: String,
    batteryComposition: {},
  },
  data() {
    return {
      items: [
        {
          part_name: "Battery modules",
          serial_number: null,
          phone_number: "+49 345879349",
          email: "john@spp01.de",
        },
        {
          part_name: "BMS",
          serial_number: "23494511/45",
          phone_number: "+49 345879349",
          email: "john@spp01.de",
        },
        {
          part_name: "Voltage cables",
          serial_number: "23494511/45",
          phone_number: "+49 345879349",
          email: "john@spp01.de",
        },
        {
          part_name: "Thermal control",
          serial_number: null,
          phone_number: "+49 345879349",
          email: "john@spp01.de",
        },
        {
          part_name: "Casing",
          serial_number: "23494511/45",
          phone_number: "+49 345879349",
          email: "john@spp01.de",
        },
      ],
    };
  },
  methods: {},
};
</script>

<style scoped>
.section-content {
  width: 100%;
  border: solid 1px #b3cb2d;
  border-radius: 0 0 4px 4px;
  background-color: #fff;
  margin-bottom: 50px;
}
.sub-section-container {
  display: flex;
  flex-wrap: wrap;
  border-bottom: solid 1px #edefe5;
}
.field-container {
  display: flex;
  flex-direction: column;
  width: 33%;
  min-height: 120px;
}
.sub-title {
  font-weight: bold;
  font-size: 20px;
  color: #c6cca3;
}
.sub-title-container {
  padding: 40px 40px 20px 40px;
  width: 100%;
}
.list-container {
  width: 33%;
  padding-left: 40px;
  padding-bottom: 40px;
}
.list-label {
  padding: 30px 0 10px 0;
  font-size: 12px;
  color: #777777;
}
ul {
  display: flex;
  flex-direction: column;
  padding: 0;
}
li {
  margin-left: 20px;
  font-weight: bold;
}
</style>
