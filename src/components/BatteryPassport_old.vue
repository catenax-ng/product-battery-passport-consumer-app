<template>
  <Header />
  <div class="container">
    <h5>Step # 1: Load contract offers from the battery provider</h5>

    <label class="label" for="Provider">Battery Provider:</label>

    <select
      class="select"
      id="selectProvider"
      v-model="selectedProvider"
      placeholder="Select Battery Provider"
    >
      <option value="" disabled selected>Select Battery Provider...</option>
      <option
        v-for="provider in listProviders"
        :value="provider.name"
        v-bind:key="provider.id"
      >
        {{ provider.name }}
      </option>
    </select>

    <button
      type="button"
      class="success-btn"
      :disabled="isDisabled"
      v-on:click="GetProviderInfo"
    >
      Load Contract Offers
    </button>
    <span
      class="snackbar"
      :class="contractInfo ? 'show' : ''"
      id="loadContracts"
    />

    <h5 class="center">Step # 2: Negotiate the edc contract</h5>

    <label class="label" for="contractOffer">Contract Offers:</label>
    <select
      required
      class="form-select select"
      id="selectOffer"
      v-model="selectedContract"
      placeholder="Select Offer"
      @change="setSelectedContract($event)"
    >
      <option value="" disabled selected>Select an Offer...</option>
      <option
        v-for="(offer, index) in provider.contractOffers"
        v-bind:key="index"
      >
        {{ offer }}
      </option>
    </select>
    <!-- <span id="selectedBatt"></span> -->

    <label class="label" for="Battery">Battery:</label>
    <select
      required
      class="form-select select"
      id="selectBattery"
      v-model="selectedBattery"
      placeholder="Select Battery"
      @change="setSelectedBattery($event)"
    >
      <option value="" disabled selected>Select Battery...</option>
      <option
        v-for="(battery, id) in provider.batteries"
        :value="battery.id"
        v-bind:key="id"
      >
        {{ battery.name }}
      </option>
    </select>

    <button
      type="button"
      class="btn btn-success center success-btn"
      :disabled="isDisabled"
      v-on:click="doNegotiation"
    >
      Start Negotiation
    </button>
    <span
      class="snackbar"
      :class="negotiationInfo ? 'show' : ''"
      id="negotiateContract"
    ></span>

    <!-- <div class="container" style="width:25%;">
      <label for="Battery">Battery:</label>
    <select required class="form-select" id="selectBattery" placeholder="Select Battery" @change="setSelectedBattery($event)">
      <option value="" disabled selected >Select Battery...</option>
      <option v-for="(battery, id) in provider.batteries" :value="battery.id"
              v-bind:key="id">{{ battery.name }}
      </option>
    </select>
  </div> -->
    <!-- <br />
    <div class="container" style="width:25%;">
      <label for="connectorURL">Connector URL:</label>
      <input type="text" class="form-control" v-model="this.provider.providerConnector" disabled id="txtConnectorURL" placeholder="Connector URL">
    </div> -->

    <h5 class="center">Step # 3: Get battery passport from the provider</h5>

    <button
      type="button"
      class="btn btn-success center success-btn"
      :disabled="isDisabled"
      v-on:click="initiateTransfer"
    >
      Get Battery Passport
    </button>

    <div v-if="isLoading" class="center" style="margin-top: -50px">
      <div style="width: 3rem; height: 3rem" role="status">
        <span
          ><img src="../assets/loading.gif" height="200" width="250"
        /></span>
      </div>

      <div class="h5" style="margin: 75px 0px 0px 10px">
        {{ currentStatus }}
      </div>
    </div>

    <div v-if="isPassportVisible" class="margin-top">
      <span>
        {{ productPassport }}
      </span>
    </div>
    <!-- <div v-if="isPassportVisible" class="container margin-top">
    <table v-bind="productPassport" class="table table-bordered table-striped" style="border:1px ghostwhite; text-align:left;">
      <thead>
        <th colspan="6" class="h3 table-heading">Battery Passport</th>
      </thead>
      <tbody> -->
    <!-- <tr v-for="(value, key) in this.productPassport" v-bind:key="key">
          <th scope="col">{{key}}</th>
          <td v-if="typeof value == 'object'">
            <table class="table table-bordered table-striped"><tr scope="row" v-for="(k,val) in value" v-bind:key="k"><th>{{val}}</th>
            <td>{{k}}</td></tr></table></td>
          <td v-else>{{value}}</td>
        </tr> -->
    <!-- <template v-for="(value1, key1) in this.productPassport" v-bind:key="key1">
    <tr scope="row" v-if="typeof value1 != 'object'">
      <th scope="col">{{key1}}</th>
      <td>{{value1}}</td> 
    </tr>
      <template v-if="typeof value1 == 'object'">
        <tr>
          <th scope="col">{{key1}}</th>
          <td></td>
        </tr>

        <template v-for="(value2,key2) in value1" v-bind:key="key2">
        <tr scope="row" v-if="typeof value2 != 'object'">
          <th style="padding-left: 50px;">{{key2}}</th>
          <td>
            {{value2}} 
          </td>
        </tr>
        <template v-if="typeof value2 == 'object'">
          <tr>
          <th scope="col" style="padding-left: 75px;">{{key2}}</th>
          <td></td>
        </tr>
          <tr v-for="(value3,key3) in value2" v-bind:key="key3">
            <th style="padding-left: 100px;">{{key3}}</th>
          <td>
            {{value3}} 
          </td>
          </tr>
          </template>
        </template>
      </template>
  </template>
  </tbody>
    </table>
  </div> -->
  </div>
</template>

<script type="text/jsx">
import axios from 'axios';
//import 'bootstrap/dist/css/bootstrap.min.css'
import Header from '@/components/Header.vue'

let listBatteryProviders = require('../assets/providers.json');

export default {
  name: 'batteryPassport',
  created(){
  },
  components: {
Header
  },
  mounted(){

    let user = localStorage.getItem("user-info");
        if (!user){
          if (this.$route.query.provider != undefined && this.$route.query.battery != undefined){
            let provider = this.$route.query.provider
            let battery =  this.$route.query.battery
            let QRCodeAccessInfo = {"provider": provider.toUpperCase(), "battery": battery}
            localStorage.setItem("QRCode-info", JSON.stringify(QRCodeAccessInfo))
          }
          else
            this.$router.push({name:'Login'});
        }
        else{
          let user = localStorage.getItem("user-info")
          let role = JSON.parse(user).role;
console.log("CurrentUser: ",user, role);
          // check query params for QR code scanning
          this.selectedProvider = this.$route.query.provider
          this.selectedBattery = this.$route.query.battery
          this.selectedContract = this.$route.query.battery + '_' + role.toLowerCase()

          if (this.$route.query.provider === undefined && this.$route.query.battery === undefined) {
            // do manual selection of fields
            console.log('INFO: provider and battery are not defined')
            this.resetFields()
          }
          else if (this.$route.query.provider === '' && this.$route.query.battery === '')
            alert('Battery provider and battery name are required...!')
          else{
            // Get BatteryData
            this.GetBatteryDataUsingQRCode()

          }
        }
  },
  data() {
    return {
      listProviders: listBatteryProviders,
      provider: {},
      productPassport:'',
      selectedProvider:'',
      selectedBattery:'',
      selectedContract:'',
      currentStatus: '',
      uuid: '',
      contractId:'',
      isLoading:'',
      isDisabled: false,
      isPassportVisible: false,
      contractInfo: false,
      negotiationInfo: false,
      errors: [],
       username: '',
      role: '',

    }
  },
  methods:{

    GetProviderInfo() {
       let user = localStorage.getItem("user-info")
       let role = JSON.parse(user).role
      axios.get('/consumer/data/contractnegotiations/provider/metadata/' + this.selectedProvider, {
        headers: {
            'X-Api-Key': 'password'
          }
      })
      .then(response => {
          this.provider = response.data
          this.getContractOfferByLoggedInRole()
          if (this.provider != '' )
          {
            //this.GetDigitalTwinObjectById()
              //this.GetSubmodelData()
            document.getElementById('loadContracts').innerHTML='Contract offers loaded successfully..'
            //this.GetDigitalTwinId()
            
          }
          else{
            document.getElementById('loadContracts').innerHTML='No contract offers'
            this.resetFields()
          };
           this.contractInfo = !this.contractInfo;
           setTimeout(() => {
             this.contractInfo = false;
        }, 2000);

      })

      .catch(e => {
        this.errors.push(e)
        document.getElementById('loadContracts').innerHTML='Something went wrong!..'
      })
    },
    getContractOfferByLoggedInRole: function(){
      let user = localStorage.getItem("user-info")
      let role = JSON.parse(user).role
      const offer = this.provider.contractOffers.filter( h => h.includes(role.toLowerCase()) );
      this.provider.contractOffers = offer

      // to handle filling the battery provider dropdown here because this.provider is loaded before provider dropdown and get emplty value.
      this.selectedProvider = this.provider.name

    //data validation - TODO
     /* if ((this.$route.query.provider != undefined || this.$route.query.provider != '')  && (this.$route.query.battery != undefined || this.$route.query.battery != '')){
        // Query Params for the QR code functionality
        let batteryProviderQP = this.$route.query.provider
        let batteryQP = this.$route.query.battery
        // Dorpdown values
        let batteryProvider = this.provider.name
        let battery = this.provider.batteries[0].name
       alert(this.$route.query.provider)
      }
      else
        alert('xyx')*/
    },
    resetFields: function () {

      // TODO
      this.selectedProvider = ''

      this.selectedBattery = ''
      this.selectedContract = ''
      //alert('Hello')


    },
    setSelectedBattery(event){
      this.selectedBattery = event.target.value
    },
    setSelectedContract(event){
      this.selectedContract = event.target.value
    },
    GetDigitalTwinId: function(){
      const res =  axios.get("http://localhost:4245/lookup/shells?assetIds=%5B%7B%0A%09%22key%22%3A%20%22batteryId%22%2C%0A%09%22value%22%3A%20%22334593247%22%0A%7D%2C%7B%0A%20%20%20%20%20%20%20%20%20%20%20%20%22key%22%3A%20%22passportNumber%22%2C%0A%20%20%20%20%20%20%20%20%20%20%20%20%22value%22%3A%20%2212345%22%0A%7D%5D");
      const data = res.json();
      console.log(data);
      return data;
    },
    GetDigitalTwinObjectById: function(){
      //const res =  axios.get("http://localhost:4243/registry/shell-descriptors/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120001"); // Without AAS Proxy
      //Calling with AAS Proxy
      const res =  axios.get("http://localhost:4245/registry/shell-descriptors/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120001");
      const data = res.json();
      alert(data)
      console.log(data);
      return data;
    },
    GetSubmodelData: function(){
      //const res =  axios.get("http://localhost:8193/api/service/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120001-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97919/submodel?provider-connector-url=http://provider-control-plane:8282"); // Without AAS Proxy
      //Calling with AAS Proxy
      //const res =  axios.get("http://localhost:4245/shells/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120001/aas/urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97919/submodel?content=value&extent=withBlobValue",{
        const res =  axios.get("http://localhost:8193/api/service/urn:uuid:365e6fbe-bb34-11ec-8422-0242ac120001-urn:uuid:61125dc3-5e6f-4f4b-838d-447432b97919/submodel?provider-connector-url=http://provider-control-plane:8282", {
        auth: {
            username: 'someuser',
            password: 'somepassword'
          }
      });
      const data = res.json();
      alert(data)
      console.log(data);
      return data;
    },


    async doNegotiation(){

      var result = await this.negotiateContract()
      this.uuid = result.id
      this.isLoading = true
      this.currentStatus = "Negotiating contract"
      // Check agreement status //
      // Status: INITIAL, REQUESTED, CONFIRMED
      // first call to get the initial status
      var data = await this.getAgreementId(this.uuid)
      if (data == "rejected")
        return;
      else
        this.contractId = data.contractAgreementId

      // Check the agreement status until it is of status CONFIRMED
      while(data.state != "CONFIRMED"){
        data = await this.getAgreementId(this.uuid)
        this.currentStatus = "Agreement state: " + data.state
        console.log(data.state + '_' + data.contractAgreementId)
        this.contractId = data.contractAgreementId
      }
      this.isLoading = false
      document.getElementById('negotiateContract').innerHTML='Finished with uuid:  ' + this.uuid
    },
    async initiateTransfer(){


      this.isLoading = true
      // //const destinationPath = 'C:/Users/muhammadsaud.khan/Documents/Workspace/catenax-edc-mp/DataSpaceConnector/samples/04.0-file-transfer/data'
      // const destinationPath = '/app/samples/04.0-file-transfer/data' // set different path for containers

      // // Initiate transfer request //
      // let asset = ''
      // let user = localStorage.getItem("user-info")
      // let role = JSON.parse(user).role
      // if (role.toLowerCase() == "dismantler")
      //   asset = "test-document_dismantler"
      // else if (role.toLowerCase() == "oem")
      //   asset = "test-document_oem"
      // else if (role.toLowerCase() == "recycler")
      //   asset = "test-document_recycler"
      // else if (role.toLowerCase() == "Battery Producer")
      //   asset = "test-document_battery_producer"
      // var res = await this.getProductPassport(asset, destinationPath, this.contractId)
      // if (res == null)
      //   this.currentStatus = "Something went wrong in finalizing product process"
      // else {
      //   this.currentStatus = "Finalizing product passport"

      //   // Display the product passport //
      //   var productPass = await this.displayProductPassport(asset+ '.json')
      //   if (productPass == ''){
      //     setTimeout(this.displayProductPassport(asset+ '.json'), 60000);
      //   }

      //   this.productPassport = productPass
      //   this.isPassportVisible = true;
      //   this.isLoading = false;
      //   this.isDisabled = false;
        // Clear QRCode-info from localStorage
        localStorage.removeItem("QRCode-info");
      //}
      this.$router.replace({ name: "Passport", params:{  contractId: this.contractId }, query:{ provider: this.selectedProvider, battery: this.selectedBattery } });
    },
    async GetBatteryDataUsingQRCode(){

      // load provider dropdown to fill the relevant dropdown values i.e., contract offer and battery
       await this.GetProviderInfo()
       // provider drondown is not auto fill because this.selectedProvider is loaded before the provider dropdown.


      // check if the selected provider has contract offer.

      // negotiate contract
      await this.doNegotiation()

      // get battery passport
      await this.initiateTransfer()
    },
    negotiateContract(){

      // TODO dynamic contractoffer file name
      let contractOffer = require('../assets/registry/contractoffers/' + this.selectedContract.toLowerCase());
      return new Promise(resolve => {

      axios.post('/consumer/data/contractnegotiations', contractOffer,{
        headers: {
            'X-Api-Key': 'password'
          }
      } )
        .then((response) => {
          resolve(response.data);
        })
        .catch((e) => {
          this.errors.push(e)
          resolve('rejected');
        });
    });
    },
    getAgreementId(uuid){
      return new Promise(resolve => {

       //axios.get('/provider/data/contractnegotiations/' + uuid, {
       axios.get('/consumer/data/contractnegotiations/' + uuid, {
          headers: {
            'X-Api-Key': 'password',
            "accept": "application/json"
          }})
        .then((response) => {
          console.log('check_state : ' + response.data.state)
          console.log('Agreement Id: ' + response.data.contractAgreementId)
          this.currentStatus = 'Agreement state: ' + response.data.state
          resolve(response.data);
        })
        .catch((e) => {
          this.errors.push(e)
          alert(e)
          resolve('rejected');
        });
    })
    },
  getProductPassport(asset, destinationPath, contractId){

    return new Promise(resolve => {

      var jsonData = {
        "protocol": "ids-multipart",
        "assetId": asset,
        "contractId": contractId,
        "dataDestination": {
          "properties": {
            "path": destinationPath + '/' + asset + '.json',
            "keyName": "keyName",
            "type": "File"
          }
        },
        "transferType": {
          "contentType": "application/octet-stream",
          "isFinite": true
        },
        "managedResources": false,
        "connectorAddress": "http://edc-provider:8282/api/v1/ids/data",
        "connectorId": "consumer"
    }

    // "connectorAddress": "http://edc-provider:8282/api/v1/ids/data",

      axios.post('/consumer/data/transferprocess', jsonData, {
        //axios.post('/data/transferprocess', jsonData, {
        headers: {
            'X-Api-Key': 'password'
          }
      })
        .then((response) => {
          console.log(response.data)
          resolve(response.data);
        })
        .catch((e) => {
          this.errors.push(e)
          resolve('rejected');
        });
    })

  },
  displayProductPassport(filename){

     return new Promise(resolve => {

      //axios.get('/consumer/data/contractnegotiations/passport/display/' + filename, {
      axios.get('/consumer/data/contractnegotiations/passport/display/' + filename, {
        headers: {
            'X-Api-Key': 'password'
          }
      })
        .then((response) => {
          console.log(response.data)
          resolve(response.data);
        })
        .catch((e) => {
          this.errors.push(e)
          resolve('rejected');
        });
    })
  }
}
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;

  width: 22%;
  margin: 0 39% 0 39%;
}
.success-btn {
  width: 340px;
  height: 48px;
  margin: 12px 0 60px 0;
  background: #b3cb2d;
  color: white;
  font-size: 16px;
  font-weight: bolder;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.label {
  padding: 12px 0 12px 0;
  font-weight: bold;
}
.select {
  width: 340px;
  height: 48px;
  border: solid 1px #b3cb2c;
  border-radius: 4px;
}
/* The snackbar - position it at the top and in the middle of the screen */
.snackbar {
  visibility: hidden; /* Hidden by default. Visible on click */
  min-width: 250px; /* Set a default minimum width */
  margin-left: -125px; /* Divide value of min-width by 2 */
  background-color: #b3cb2d; /* Black background color */
  color: #fff; /* White text color */
  text-align: center; /* Centered text */
  border-radius: 4px; /* Rounded borders */
  padding: 16px; /* Padding */
  position: fixed; /* Sit on top of the screen */
  z-index: 1; /* Add a z-index if needed */
  left: 50%; /* Center the snackbar */
  top: 30px; /* 30px from the top */
  font-weight: bold;
}

/* Show the snackbar when clicking on a button (class added with JavaScript) */
.snackbar.show {
  visibility: visible; /* Show the snackbar */
  /* Add animation: Take 0.5 seconds to fade in and out the snackbar.
  However, delay the fade out process for 2.5 seconds */
  -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
  animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

/* Animations to fade the snackbar in and out */
@-webkit-keyframes fadein {
  from {
    top: 0;
    opacity: 0;
  }
  to {
    top: 30px;
    opacity: 1;
  }
}

@keyframes fadein {
  from {
    top: 0;
    opacity: 0;
  }
  to {
    top: 30px;
    opacity: 1;
  }
}

@-webkit-keyframes fadeout {
  from {
    top: 30px;
    opacity: 1;
  }
  to {
    top: 0;
    opacity: 0;
  }
}

@keyframes fadeout {
  from {
    top: 30px;
    opacity: 1;
  }
  to {
    top: 0;
    opacity: 0;
  }
}
</style>
