import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the CrimeController APIs
 */
export default class CrimeClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'addCrime', 'getCrimeByBorough', 'addClosedCrime', 'getAllCrimes'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    // /**
    //  * Gets the crime by the given crime type.
    //  * @param crimeType
    //  * @param errorCallback (Optional) A function to execute if the call fails.
    //  * @returns crime by type
    //  */
    // async getCrimeByType(crimeType, errorCallback) {
    //     try {
    //         const response = await this.client.get(`/crimes/active/${crimeType}`);
    //         return response.data;
    //     } catch (error) {
    //         this.handleError("getCrimeByType", error, errorCallback)
    //     }
    // }

    /**
     * Gets the crime by the given borough.
     * @param borough
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns crime by borough
     */
    async getCrimeByBorough(borough, errorCallback) {
        try {
            const response = await this.client.get(`/crimes/active/borough/${borough}`);
            return response.data;
        } catch (error) {
            this.handleError("getCrimeByBorough", error, errorCallback)
        }
    }

    /**
     * Gets all crimes.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns all crimes
     */
    async getAllCrimes(errorCallback) {
        try {
            const response = await this.client.get(`/crimes/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllCrimes", error, errorCallback)
        }
    }

    /**
     * Creates a new crime given the caseId, borough, and state
     * @param caseId
     * @param borough
     * @param state
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns a new crime
     */
    async addCrime(caseId, borough, state, errorCallback) {
        try {
            const response = await this.client.post(`/crimes`, {
                caseId: caseId,
                borough: borough,
                state: state,
            });
            return response.data;
        } catch (error) {
            this.handleError("addCrime", error, errorCallback);
        }
    }

    /**
     * Creates a closed crime given the caseId, borough, and state
     * @param caseId
     * @param borough
     * @param state
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns a closed crime
     */
    async addClosedCrime(caseId, borough, state, errorCallback) {
        try {
            const response = await this.client.post(`/crimes/closed`, {
                caseId: caseId,
                borough: borough,
                state: state,
            });
            return response.data;
        } catch (error) {
            this.handleError("addClosedCrime", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
