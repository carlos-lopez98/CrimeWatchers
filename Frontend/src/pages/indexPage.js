import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CrimeClient from "../api/CrimeClient";
import TrendingData from "../util/TrendingData";


/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetByBorough', 'onCreate', 'renderExample', 'renderTrendingSection', 'onGetAll'], this);
        this.dataStore = new DataStore();
        this.TrendingData = new TrendingData();
    }


    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('search_form').addEventListener('submit', this.onGetByBorough);

        document.getElementById('create-form').addEventListener('submit', this.onCreate);

        window.addEventListener("load", this.onGetAll);
        this.client = new CrimeClient();

        this.dataStore.addChangeListener(this.renderExample)
        this.TrendingData.addChangeListener(this.renderTrendingSection)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {

        let resultArea = document.getElementById("left_area__Result");

        const crimes = this.dataStore.get("borough_crime_list");

        if (crimes) {
            let myHtml = "";
            for (let crime of crimes) {
                myHtml += `
                <div class="resultArea__innerText" >
                    <div class="innerText">

                        <div class="caseId_label">
                            <h1>Case Id:<span class="resultArea_innerText_headers"> ${crime.caseId}</span></h1>
                        </div>

                        <div class="caseId_label2">
                        <h1>Location: <span class="resultArea_innerText_headers">${crime.borough}</span></h1>
                        </div>

                        <div class="caseId_label3">
                        <h1>Time Commited: <span class="resultArea_innerText_headers">${crime.zonedDateTime}</span></h1>
                        </div>

                    </div>

                </div>

                 <div class="description__Area">

                    <div class="description_Area__text">
                        <h1>Description</h1>
                    </div>

                    <div class="description__Area___render" id="description_area_render">
                        <p>${crime.description}</p>
                    </div>
                 </div>
            `
            }
            myHtml += ""
            resultArea.innerHTML = myHtml;

        } else {
            resultArea.innerHTML = "No Item";
        }


    }


    async renderTrendingSection() {

        let resultArea = document.getElementById("trending_container_section");

        const trendingCrimes = this.TrendingData.get("trendingCrimes");

        let myHtml = "";

        if (trendingCrimes) {

            for (let crime of trendingCrimes) {
                myHtml += `
                
                <div class="trending_content__container">
                    <div class="trending_content__container_header">
                        <div class="h1_text">
                            <h1>CaseId:&nbsp;&nbsp; ${crime.caseId}</h1>
                        </div>

                        <div class="h2_text">
                            <h1>Date and Time:&nbsp;&nbsp; ${crime.zonedDateTime}</h1>
                        </div>

                        <div class="h3_text">
                            <a href="" class="provideInfo_link">
                                <h1>Provide Info</h1>
                            </a>
                        </div>
                    </div>

                    <div class="trending_content__container_description">
                        <h1 class="description_title">Wanted For:&nbsp;&nbsp; ${crime.crimeType}</h1>
                        <h1>Description</h1>
                        <p class="description_text">${crime.description}</p>
                    </div>
                </div>
                
                
                `
            }
            myHtml += "";
            resultArea.innerHTML += myHtml;
        } else {
            resultArea.innerHTML = "Error loading from Database";
        }

    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetAll(event) {

        event.preventDefault();

        let result = await this.client.getAllCrimes(this.errorHandler);

        this.TrendingData.set("trendingCrimes", result);

        if (result) {
            this.showMessageTrending(`Checkout our New Trending Crime Section!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }

    }


    async onGetByBorough(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let borough = document.getElementById("borough_input").value;

        //This is the data being stored in our current state
        this.dataStore.set("borough_crime_list", null);

        let result = await this.client.getCrimeByBorough(borough, this.errorHandler);

        this.dataStore.set("borough_crime_list", result);


        if (result) {
            this.showMessage(`Checkout what's going on in ${result[0].borough} area!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let borough = document.getElementById("create-borough-field").value;
        let description = document.getElementById("create-description-field").value;
        let crimeType = document.getElementById("create-type-field").value;

        const createdCrime = await this.client.addCrime(borough, description, crimeType, this.errorHandler);

        console.log(createdCrime.caseId)

        if (createdCrime) {
            this.showMessage(`Your new Crime Id: ${createdCrime.caseId} is up for review for the ${createdCrime.borough} area`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const indexPage = new IndexPage();
    indexPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
