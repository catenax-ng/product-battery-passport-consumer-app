<template>
  <div>
    <div class="header-container">
      <div class="logo-container">
        <router-link to="/">
          <img :src="CatenaLogo" alt="logo" class="logo" />
        </router-link>
      </div>
      <v-container>
        <v-tabs v-model="tab">
          <v-tab value="one">History page</v-tab>
          <v-tab value="two">QR code scanner</v-tab>
        </v-tabs>
      </v-container>
      <div class="right-manu-wrapper">
        <div class="right-menu-container">
          <span>
            <span @mouseover="profileHover = true">
              <img :src="Profile" alt="profile" class="buttons" />
            </span>
            <div
              v-if="profileHover"
              class="profile-menu"
              @mouseleave="profileHover = false"
            >
              <div class="menu-btn">
                <img :src="Profile" alt="profile" class="menu-profile" />
                <!--TODO: Profile page onClick-->
                <span class="profile-text">
                  {{ username }}
                  <p>{{ role }}</p>
                </span>
              </div>
              <div class="menu-btn">
                <span class="profile-text" @click="logout">Sign out</span>
              </div>
            </div>
          </span>
        </div>
      </div>
    </div>
    <v-container>
      <v-window v-model="tab">
        <v-main>
          <v-window-item value="one">
            <BatteryPassport />
          </v-window-item>
          <v-window-item value="two">
            <div class="ghost"></div>
            <QRScanner />
          </v-window-item>
        </v-main>
      </v-window>
    </v-container>
    <div v-if="batteryId" class="id-container">
      <div class="id-wrapper">
        <h1>
          BatteryID:
          {{
            batteryId.batteryIdentification.batteryIDDMCCode
              ? batteryId.batteryIdentification.batteryIDDMCCode
              : "—"
          }}
        </h1>
      </div>
      <div
        v-if="
          batteryId.batteryIdentification.batteryIDDMCCode ==
          'X123456789012X12345678901234566'
        "
        class="code-container"
      >
        <img
          :src="X123456789012X12345678901234566"
          alt="profile"
          class="code"
          width="170"
          height="170"
        />
      </div>
      <div
        v-else-if="
          batteryId.batteryIdentification.batteryIDDMCCode == 'NCR186850B'
        "
        class="code-container"
      >
        <img
          :src="NCR186850B"
          alt="profile"
          class="code"
          width="170"
          height="170"
        />
      </div>
      <div
        v-if="batteryId.batteryIdentification.batteryIDDMCCode == 'IMR18650V1'"
        class="code-container"
      >
        <img
          :src="IMR18650V1"
          alt="profile"
          class="code"
          width="170"
          height="170"
        />
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import CatenaLogoType from "../assets/logotype.png";
import CatenaLogo from "../assets/Catena-X_Logo_mit_Zusatz_2021.svg";
import Profile from "../assets/profile.svg";
import Notifications from "../assets/notifications.svg";
import Settings from "../assets/settings.svg";
import QRScannerIcon from "../assets/qr-icon.svg";
import QrCode from "../assets/BMW_test-battery-1.svg";
import IMR18650V1 from "../assets/IMR18650V1.svg";
import X123456789012X12345678901234566 from "../assets/X123456789012X12345678901234566.svg";
import NCR186850B from "../assets/NCR186850B.svg";
import QRScanner from "../views/QRScanner.vue";
import BatteryPassport from "../components/BatteryPassport.vue";
import Footer from "../components/Footer.vue";
import Logout from "../assets/logout.png";
import { inject } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {
    QRScanner,
    BatteryPassport,
    Footer,
  },
  props: {
    batteryId: {
      type: Object,
      default: null,
    },
  },

  setup() {
    return {
      CatenaLogoType,
      CatenaLogo,
      Profile,
      Notifications,
      QRScannerIcon,
      Settings,
      QrCode,
      IMR18650V1,
      X123456789012X12345678901234566,
      NCR186850B,
      Logout,
    };
  },

  data() {
    return {
      profileHover: false,
      hamburgerMenu: false,
      profileMenu: false,
      username: "",
      role: "",
      auth: inject("authentication"),
      tab: null,
    };
  },
  mounted() {
    if (this.auth.isUserAuthenticated) {
      this.username = this.auth.getUserName();
      this.role = this.auth.getRole();
    }
  },
  methods: {
    logout() {
      this.auth.logout();
    },
    scanQRCode() {
      this.$router.push({ name: "ScanPassport" });
    },
  },
};
</script>

<style scoped>
.tooltip {
  position: relative;
  display: inline-block;
}

/* .tooltip .tooltiptext {
  visibility: hidden;
  width: max-content;
  background-color: #3d3d3d;
  box-shadow: 3px 4px 6px 0px #bebebe;
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  text-align: center;
  border-radius: 6px;
  padding: 7px 15px;


  position: absolute;
  z-index: 1;
  top: 65px;
  transform: translate(-60%, -50%);
}

.tooltip:hover .tooltiptext {
  visibility: visible;
} 
*/

h1 {
  font-weight: bold;
}

.ghost {
  height: 54vh;
}

.header-container {
  display: flex;
  width: 100%;
  margin: 30px 12% 0 0;
  padding: 0 4% 20px;
  border-bottom: 2px solid lightgray;
}

.logo-container {
  display: relative;
  width: 20%;
  padding-top: 10px;
}

.logo {
  position: absolute;
  height: 40px;
  left: 40px;
}

.logo-type {
  height: 49px;
}

.code-container {
  width: 40%;
  display: flex;
  justify-content: flex-end;
}

.id-wrapper {
  width: 60%;
}

.right-manu-wrapper {
  width: 20%;
  display: flex;
  justify-content: flex-end;
}

.code {
  padding: 0;
  margin: 0;
}

.id-container {
  display: flex;
  align-items: center;
  width: 76%;
  margin: 2% 12% 6% 12%;
  padding: 20px 0;
}

.buttons {
  margin: 15px 0 0 30px;
  cursor: pointer;
}

.profile-container {
  position: relative;
}

.profile-menu {
  position: absolute;
  min-width: 342px;
  border: solid 1px #ffa600;
  right: 0;
  background-color: white;
  cursor: pointer;
}

.menu-btn {
  display: flex;
  border-top: solid 1px #ffa600;
  padding: 16px;
  align-items: center;
}

.menu-btn:first-child {
  border-top: none;
}

.menu-btn:hover {
  background-color: #f8f9fa;
}

.profile-text {
  padding: 0 16px 0 12px;
  font-size: 18px;
  font-weight: bold;
}

p {
  font-size: 14px;
  font-weight: 500;
}

.menu-profile {
  padding: 16px;
}

.toggle-button {
  position: absolute;
  top: 0.75rem;
  right: 1rem;
  display: none;
  flex-direction: column;
  justify-content: space-between;
  width: 30px;
  height: 21px;
  z-index: 1;
}

.toggle-button .bar {
  height: 4px;
  width: 100%;
  background-color: #b3cb2c;
  border-radius: 10px;
}

@media (max-width: 750px) {
  .right-manu-wrapper {
    display: none;
  }

  .toggle-button {
    display: flex;
    margin-right: 36px;
  }

  .code-container {
    display: none;
  }

  .logo {
    height: 45px;
    left: 0;
  }

  .logo-type {
    display: none;
  }

  .header-container {
    width: 100%;
    margin: 30px;
  }

  .id-container {
    margin: 45px 0 0 30px;
    padding: 20px 0;
  }

  h1 {
    font-size: 25px;
    line-height: 36px;
  }

  .hamburger-menu {
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    width: 100%;
    background-color: #b3cb2c;
    height: auto;
    min-height: 270px;
    padding: 80px 0 0 0;
    z-index: 1;
  }

  .links {
    margin: 12px;
    font-weight: bold;
  }

  h3 {
    color: white;
  }

  .toggle-button .white-bar {
    background-color: white;
  }

  .toggle-button-color {
    background-color: white;
  }

  .profile-menu-mobile {
    display: flex;
    flex-direction: column;
    background-color: white;
    width: 100%;
    justify-content: center;
    align-items: center;
    border: solid 1px #b3cb2c;
  }

  .mobile-menu-links {
    text-align: center;
    font-weight: bold;
    font-size: 16px;
    border-bottom: solid 1px #b3cb2c;
    width: 100%;
    min-height: 60px;
    padding: 16px 0 0 0px;
  }
}
</style>
