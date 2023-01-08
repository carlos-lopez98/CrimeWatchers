import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the CrimeService.
 */
export default class CrimeClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'addCrime', 'getCrimeByType','getCrimeByBorough', 'getClosedCaseById', 'addClosedCrime', 'getAllCrimes'];
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
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the grocery item for the given product name.
     * @param name
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getGroceryItem(name, errorCallback) {
        try {
            const response = await this.client.get(`/grocery-item/${name}`);
            return response.data;
        } catch (error) {
            this.handleError("getGroceryItem", error, errorCallback)
        }
    }

    async getAllGroceryItems(errorCallback) {
        try {
            const response = await this.client.get(`/grocery-item/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllGroceryItems", error, errorCallback)
        }
    }
    async createGroceryItem(productName, department, price, expirationDate, type, inStock, quantity, discount, errorCallback) {
        try {
            const response = await this.client.post(`/grocery-item`, {
                groceryProductName: productName,
                groceryProductDepartment: department,
                groceryProductPrice: price,
                groceryExpirationDate: expirationDate,
                groceryType: type,
                inStock: inStock,
                quantityAvailable : quantity,
                discount: discount
            });
            return response.data;
        } catch (error) {
            this.handleError("createGroceryItem", error, errorCallback);
        }
    }

    /*     async updateItem(productName, errorCallback) {
                try {
                    const response = await this.client.post(`/groceryitem/${name}`){
                        department: department,
                        price: price,
                        expirationDate: expirationDate,
                        type: type,
                        inStock: inStock,
                        quantity: quantity,
                        discount: discount,
                        id: id
                    });
                    return response.data;
                } catch (error) {
                    this.handleError("updateItem", error, errorCallback);
                }
            }*/

    async deleteGroceryItem(name, errorCallback) {
        try {
            const response = await this.client.delete(`/grocery-item/${name}`);
            return response.data;
        } catch (error) {
            this.handleError("deleteGroceryItem", error, errorCallback)
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
