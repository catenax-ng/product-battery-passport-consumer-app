<template>
  <div class="field-container">
    <span class="field-label">{{ label }}</span>
    <span
      v-if="value"
      class="field-value"
    >{{ value }} {{ unit }}</span>
    <span v-else-if="length" />
    <span
      v-else-if="tempRangeMin"
      class="field-value"
    >
      {{ tempRangeMin }} {{ tempUnit }}—{{ tempRangeMax }} {{ tempUnit }}</span>

    <span v-else-if="day" />
    <span
      v-else
      class="field-value"
    >—</span>
    <!-- This block of code is for section 1 General information Importer information, STREET goes as a value and than the rest below -->
    <span
      v-if="city"
      class="field-value"
    >{{ city ? city : "—" }} </span>
    <span
      v-if="postal"
      class="field-value"
    >{{ postal ? postal : "—" }} </span>
    <span
      v-if="country"
      class="field-value"
    >{{ country ? country : "—" }}
    </span>
    <!-- This block of code is for section 4 Parameters of the battery where we have tests and temp ranges -->
    <span
      v-if="tempMin"
      class="test"
    >Temp ranges:
      <b>{{ tempMin }} {{ tempUnit }} — {{ tempMax }} {{ tempUnit }}</b>
    </span>
    <span
      v-if="test"
      class="test"
    >Reference test: <b>{{ test }}</b>
    </span>

    <!-- This block of code is for section 1 General information. Dimensions of the battery -->
    <span
      v-if="length"
      class="field-value"
    >L: {{ length }}{{ unit }}, H: {{ height }}{{ unit }}, W: {{
      width
    }}{{ unit }}</span>
    <!-- This block of code is for dates -->
    <span
      v-if="day"
      class="field-value"
    >{{ day }}/{{ month }}/{{ year }}</span>
  </div>
</template>

<script>
export default {
  name: "FieldComponent",
  props: {
    label: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    value: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    unit: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    test: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    city: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    postal: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    country: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    length: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    height: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    width: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    day: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    month: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    year: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    tempMin: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    tempMax: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    tempUnit: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    tempRangeMin: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
    tempRangeMax: {
      type: [String, Number, null],
      required: false,
      default: ""
    },
  },
};
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

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

.test {
  font-size: 12px;
  padding: 4px 0 4px 40px;
}

.field-container {
  display: flex;
  flex-direction: column;
  width: 33%;
  min-height: 120px;
}

.field-label {
  padding: 30px 0 10px 40px;
  font-size: 12px;
  color: #777777;
}

.field-value {
  padding-left: 40px;
  font-size: 14px;
  line-height: 20px;
  font-weight: bold;
}
</style>
