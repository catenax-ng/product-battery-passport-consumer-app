import { REDIRECT_URI, INIT_OPTIONS, SERVER_URL, CLIENT_CREDENTIALS, IDP_URL } from "@/services/service.const";
import Keycloak from 'keycloak-js';
import axios from "axios";


export default class authentication {

    keycloak;
    constructor(){
      this.keycloak = new Keycloak(INIT_OPTIONS);
    }
    keycloakInit(app){
      this.keycloak.init({ onLoad: INIT_OPTIONS.onLoad }).then((auth) => {
        if (!auth) {
          window.location.reload();
        }
        else {
          app.mount('#app');
        }
        //Token Refresh
        setInterval(() => {
          this.updateToken(60);
        }, 60000);
        
      }).catch((e) => {
        console.log(e);
        console.log('Login Failure');
      });
    }
    getAccessToken() {
      return this.keycloak.token;
    }

    getRefreshedToken() {
      return this.keycloak.refreshToken;
    }

    updateToken(minimumValidity) {
      this.keycloak.updateToken(minimumValidity).then((refreshed) => {
        if (refreshed) {
          console.info('Token refreshed' + refreshed);
        } else {
          console.warn('Token not refreshed, valid for '
                    + Math.round( this.keycloak.tokenParsed.exp + this.keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
        }
      }).catch(() => {
        console.error('Failed to refresh token');
      });
    }

    isUserAuthenticated() {
      return this.keycloak.authenticated;
    }
    getClientId() {
      return this.keycloak.clientId;
    }
    decodeAccessToken() {
      return JSON.parse(window.atob( this.keycloak.token.split(".")[1]));
    }
    getUserName() {
      return this.decodeAccessToken().preferred_username;
    }
    getRole() {
      let clientRoles = '';
      if (this.getUrl().includes(SERVER_URL))
        clientRoles = this.decodeAccessToken().companyRole; // prod mode
      else
        clientRoles = this.keycloak.resourceAccess[this.getClientId()].roles; // develop mode 
      return clientRoles.length == 1 ? clientRoles[0] : clientRoles;
    }
    logout() {
      var logoutOptions = { redirectUri: REDIRECT_URI };

      this.keycloak.logout(logoutOptions).then((success) => {
        console.log("--> log: logout success ", success);
      }).catch((error) => {
        console.log("--> log: logout error ", error);
      });

    }
    getUrl() {
      return window.location.href;
    }

    /***** Technical User Authentication *****/

    getAuthTokenForTechnicalUser() {   

      const params = new URLSearchParams({

        grant_type: CLIENT_CREDENTIALS.grant_type,
        client_id: CLIENT_CREDENTIALS.client_id,
        client_secret: CLIENT_CREDENTIALS.client_secret,
        scope: CLIENT_CREDENTIALS.scope
      });
        
      return new Promise((resolve) => {
        axios({

          method: 'post',
          url: IDP_URL + 'realms/CX-Central/protocol/openid-connect/token',
          data: params.toString(),
          config: {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
          }

        }).then(response => {

          console.log(response.data);
          resolve(response.data.access_token);

        }).catch(error => {

          console.error(error);
          resolve("rejected");

        });
      });
    }
}
