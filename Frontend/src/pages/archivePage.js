import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CrimeClient from "../api/CrimeClient";
import closedCrimes from "../util/closedCrimes";

/**
 * Logic needed for the view playlist page of the website.
 */
class ArchivePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample', 'renderAllClosedCrimes', 'onGetAll'], this);

        window.addEventListener("load", this.onGetAll);

        this.dataStore = new DataStore();

        //ClosedCrimes state
        this.closedCrimes = new closedCrimes();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {

        this.client = new CrimeClient();

        window.addEventListener("load", this.onGetAll);
        this.closedCrimes.addChangeListener(this.renderAllClosedCrimes);

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const example = this.dataStore.get("example");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div>
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    async renderAllClosedCrimes() {
        let resultArea = document.getElementById("result_Area");

        const allClosedCrimes = this.closedCrimes.get("allClosedCrimes");

        if (allClosedCrimes) {

            let myHtml = "";

            for (let closedCrime of allClosedCrimes) {

                myHtml += `
                <div class="closed_case_container">
                <div class="title_holder_archivePage">
                    <div class="title_archivePage">
                        <h1>Case Id: <span class="title_name"> ${closedCrime.caseId}</span> </h1>
                    </div>
                    <div class="title_archivePage">
                        <h1>Location:<span class="title_name"> ${closedCrime.borough}</span> </h1>
                    </div>
                    <div class="title_archivePage">
                        <h1>Date Closed:<span class="title_name"> ${closedCrime.dateClosed}</span> </h1>
                    </div>
                    <div class="title_archivePage">
                        <h1>Status:<span class="title_name"> ${closedCrime.status}</span> </h1>
                    </div>
                </div>
                <div class="description_title_archivePage">
                    <h1>Description:</h1>
                </div>
                <div class="description_text_archivePage">
                    <p>${closedCrime.description}</p>
                </div>
            </div>
                `


            }

            myHtml += "";
            resultArea.innerHTML = myHtml;
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetAll(event) {

        event.preventDefault();

        let result = await this.client.getAllClosedCrimes(this.errorHandler);

        this.closedCrimes.set("allClosedCrimes", result);

        if (result) {
            this.showMessageTrending(`You got ${result.length} closed cases in your area`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }

    }

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;

        //This is the data being stored in our current state
        this.dataStore.set("example", null);

        let result = await this.client.getExample(id, this.errorHandler);

        this.dataStore.set("example", result);


        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let name = document.getElementById("create-name-field").value;

        const createdExample = await this.client.createExample(name, this.errorHandler);
        this.dataStore.set("example", createdExample);

        if (createdExample) {
            this.showMessage(`Created ${createdExample.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const archivePage = new ArchivePage();
    archivePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
