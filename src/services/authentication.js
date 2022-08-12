import { REDIRECT_URI, INIT_OPTIONS } from "@/services/service.const"
import Keycloak from 'keycloak-js'


export default class authentication {

    keycloak;
    constructor() {
        this.keycloak = new Keycloak(INIT_OPTIONS)
    }
    keycloakInit(app) {
        this.keycloak.init({ onLoad: INIT_OPTIONS.onLoad }).then((auth) => {
            if (!auth) {
                window.location.reload();
            }
            else {
                app.mount('#app')
            }
            //Token Refresh
            setInterval(() => {
                this.updateToken(60)
            }, 60000)

        }).catch((e) => {
            console.log(e)
            console.log('Login Failure')
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
                    + Math.round(this.keycloak.tokenParsed.exp + this.keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
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
        return JSON.parse(window.atob(this.keycloak.token.split(".")[1]));
    }
    getUserName() {
        return this.decodeAccessToken().preferred_username;
    }
    getRole() {
        // Since, the attributes differs in dev and prod keycloak instance, please uncomment these lines for development only
        let clientId = this.getClientId();
        let clientRoles = this.decodeAccessToken().resource_access[clientId].roles;

        // please uncomment this line for production only
        //let clientRoles = this.decodeAccessToken().companyRole;                          

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
}


