import BudgetMeClient from '../api/budgetMeClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create Expense page of the website.
 */
class CreateExpense extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewExpenses'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }


    /**
     * Add the header to the page and load the BudgetMeClient.
     */
    async mount() {
        document.getElementById('save-expense').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
    }

    /**
     * Method to run when the create Expense submit button is pressed. Call the BudgetMeClient to create the
     * Expense.
     */
    async submit() {
        const nameRegex = new RegExp('[^a-zA-Z\\s-\'.]');
        const numberRegex = new RegExp('^\d+(\.\d{1,2})?$'); //^(0|[1-9]\d*)(\.\d+)?$
        const phoneRegex = new RegExp('\\D');

        const expenseName = document.getElementById('expenseName').value;
        const expenseAmount = document.getElementById('expenseAmount').value;
        const tag = document.getElementById('tag').value;
        const date = document.getElementById('date').value;

        if (!expenseName || !expenseAmount || !tag || !date) {
            alert("Please fill in all required fields");
            return;
        }
        if (nameRegex.test(expenseName)) {
            alert("The expense name you entered has invalid characters");
            return;
        }
        if (numberRegex.test(expenseAmount)) {
            alert ("The expense amount you entered has invalid characters");
            return;
        }
        if (nameRegex.test(tag)) {
            alert("The tag you entered has invalid characters");
            return;
        }
        if ((date.substring(0,4) < 1900) || (date.substring(0,4) > 2100) || (date.length != 10)) {
            alert("The date you entered is invalid.");
            return;
        }

        let payload = {expenseName: expenseName, expenseAmount: expenseAmount, tag: tag, date: date}

        document.getElementById('save-expense').disabled = true;
        document.getElementById('save-expense').innerHTML = 'Saving Expense...';
        document.getElementById('save-expense').style.background='grey';
        const expense = await this.client.createExpense(payload);
        this.dataStore.set('expense', expense);
        this.redirectToViewExpenses();
    }

    /**
     * When the Expense is updated in the datastore, redirect to the view Expense page.
     */
    redirectToViewExpenses() {
        const expense = this.dataStore.get('expense');
        if (expense) {
            window.location.href = `/index.html`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createExpense = new CreateExpense();
    createExpense.mount();
};

window.addEventListener('DOMContentLoaded', main);
