import BudgetMeClient from '../api/budgetMeClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create Budget page of the website.
 */
class CreateBudget extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToHome'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }


    /**
     * Add the header to the page and load the BudgetMeClient.
     */
    async mount() {
        document.getElementById('save-budget').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
    }

    /**
     * Method to run when the create Budget submit button is pressed. Call the BudgetMeClient to create the
     * Budget.
     */
    async submit() {
        const numberRegex = new RegExp('^\d+(\.\d{1,2})?$'); //^(0|[1-9]\d*)(\.\d+)?$

        const targetAmount = document.getElementById('targetAmount').value;
        const status = document.getElementById('status').value;
        const date = document.getElementById('date').value;

        if (!targetAmount || !date) {
            alert("Please fill in all required fields");
            return;
        }
        if (numberRegex.test(targetAmount)) {
            alert ("The target amount you entered has invalid characters");
            return;
        }

        if ((date.substring(0,4) < 1900) || (date.substring(0,4) > 2100) || (date.length != 10)) {
            alert("The date you entered is invalid.");
            return;
        }

        let payload = {targetAmount: targetAmount, status: status, date: date}

        document.getElementById('save-budget').disabled = true;
        document.getElementById('save-budget').innerHTML = 'Saving Budget...';
        document.getElementById('save-budget').style.background='grey';
        const budget = await this.client.createBudget(payload);
        console.log("budget1", budget);
        this.dataStore.set('budget', budget);
        this.redirectToHome();
    }

    /**
     * When the Budget is updated in the datastore, redirect to the Home page.
     */
    redirectToHome() {
        const budget = this.dataStore.get('budget');
        console.log("budget2", budget);
        if (budget) {
            window.location.href = `/index.html`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createBudget = new CreateBudget();
    createBudget.mount();
};

window.addEventListener('DOMContentLoaded', main);
