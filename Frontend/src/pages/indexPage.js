import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CrimeClient from "../api/CrimeClient";
import TrendingData from "../util/TrendingData";
import RecentlyClosedData from "../util/RecentlyClosedData";


/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGetByBorough', 'onCreate', 'renderExample', 'renderTrendingSection', 'onGetAll',
            'onGetAllClosed',
            'renderRecentlyClosed', 'renderComments'], this);
        this.dataStore = new DataStore();
        this.TrendingData = new TrendingData();
        this.RecentlyClosedData = new RecentlyClosedData();
    }


    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('search_form').addEventListener('submit', this.onGetByBorough);

        document.getElementById('create-form').addEventListener('submit', this.onCreate);

        window.addEventListener("load", this.onGetAll);
        window.addEventListener("load", this.onGetAllClosed);

        this.client = new CrimeClient();

        this.dataStore.addChangeListener(this.renderExample)
        this.dataStore.addChangeListener(this.renderComments)

        this.TrendingData.addChangeListener(this.renderTrendingSection)
        this.RecentlyClosedData.addChangeListener(this.renderRecentlyClosed)

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {

        let resultArea = document.getElementById("left_area__Result");

        const crimes = this.dataStore.get("borough_crime_list");

        if (crimes) {
            let myHtml = "";
            for (let crime of crimes) {
                myHtml += `

                <div class="hover_Gray_container">

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
                
                
                </div>

             
            `
            }
            myHtml += ""
            resultArea.innerHTML = myHtml;

        } else {
            resultArea.innerHTML = "Rendering...";
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


    async renderRecentlyClosed() {

        let resultArea = document.getElementById("RecentlyClosed_Container");
        const recentCrimes = this.RecentlyClosedData.get("recentlyClosed");
        let myHtml = "";
        if (recentCrimes) {
            for (let recentCrime of recentCrimes) {
                myHtml += `
                <div class="trending_content__container">
                    <div class="trending_content__container_header">
                        <div class="h1_text">
                            <h1>CaseId:&nbsp;&nbsp; ${recentCrime.caseId}</h1>
                        </div>

                        <div class="h2_text">
                            <h1>Date and Time:&nbsp;&nbsp; ${recentCrime.dateClosed}</h1>
                        </div>

                        <div class="h3_text">
                            <a href="" class="provideInfo_link">
                                <h1>Provide Info</h1>
                            </a>
                        </div>
                    </div>

                    <div class="trending_content__container_description">
                        <h1 class="description_title">Wanted For:&nbsp;&nbsp; ${recentCrime.crimeType}</h1>
                        <h1>Description</h1>
                        <p class="description_text">${recentCrime.description}</p>
                    </div>
                </div>
                
                `
            }
            myHtml += "";
            resultArea.innerHTML = myHtml;
        } else {
            resultArea.innerHTML = "Error loading from Database";
        }

    }

    async renderComments() {

        let listOfComments = [
            {
                id: 1,
                profileImage: "./css/images/profileImages/chocolateGuyHeadshot.png",
                comment: "I'd like to buy all your chocolates",
                username: "ChocolateEater69",
            },
            {
                id: 2,
                profileImage: "./css/images/profileImages/chocoMomHeadshot.jpg",
                comment: "Oh Sweet Sweet Chocolate, I hate IT! ",
                username: "chocoHater123",
            },
            {
                id: 3,
                profileImage: "./css/images/profileImages/garyHeadshot.jpg",
                comment: "Meowww meow meow meowwww, meow",
                username: "MeowMeow",
            },
            {
                id: 4,
                profileImage: "./css/images/profileImages/HansomeSquidward.jpg",
                comment: "You're almost as handsome as I am ",
                username: "HandsomeMan7",
            },
            {
                id: 5,
                profileImage: "./css/images/profileImages/mrKrabs.jpg",
                comment: "Hello, I like Money",
                username: "MrKrabbyPatty22",
            },
            {
                id: 6,
                profileImage: "./css/images/profileImages/PatrickHeadshot.jpg",
                comment: "I wumbo, You Wumbo, he, she, they Wumbo, The study of Wumbology, it's third grad",
                username: "NotPatrick72",
            },
            {
                id: 7,
                profileImage: "./css/images/profileImages/plankton.jpg",
                comment: "F is Fire, U is for Uranium Bombs, N is for no Survivors... ",
                username: "BestEvilVillain3",
            },
            {
                id: 8,
                profileImage: "./css/images/profileImages/sandyHeadshot.jpg",
                comment: "HIYAH!",
                username: "KarateGirl67",
            },
            {
                id: 9,
                profileImage: "./css/images/profileImages/smitty.jpg",
                comment: "I am number one",
                username: "DeadGuy31",
            },
            {
                id: 10,
                profileImage: "./css/images/profileImages/spongebob headshot.jpg",
                comment: "The best time to wear a striped sweater is all the time",
                username: "BestFryCook25",
            },
        ];

        const result_Area = document.getElementById("comment_render_container");

        let myHtml = `
        <div class="commentTitle">
         <h1>Comments:</h1>
        </div>
        `;

        for (let comment of listOfComments) {
            myHtml += `
            <div class="comment_Container">
            <div class="comment_header">
                <div class="userProfileImage">
                <img class="profileImage" src="${comment.profileImage}"/>
                </div>
                <div class="comment_userName">
                    <h1>@${comment.username}</h1>
                </div>
                <div class="comment_postTime">
                    <h1>Posted: 11:45 AM</h1>
                </div>
            </div>
            <div class="comment_text">
                <p>${comment.comment}</p>
            </div>
            </div>
        </div>
            `
        }

        myHtml += "";
        result_Area.innerHTML += myHtml;
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
    async onGetAllClosed(event) {

        event.preventDefault();

        let result = await this.client.getAllClosedCrimes(this.errorHandler);

        this.RecentlyClosedData.set("recentlyClosed", result);

        if (result) {

        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }

    }


    async onGetByBorough(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let borough = document.getElementById("borough_input").value;

        //This is the data being stored in our current state

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
