import BudgetMeClient from '../api/budgetMeClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view expense page of the website.
 */
//Global variable to track the very first expense Id retrieved and the first page.

class ViewExpense extends BindingClass {

    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'displayExpenseDetails', 'update', 'redirectToViewExpenses'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayExpenseDetails);
        this.header = new Header(this.dataStore);
    }

 /**
     * Once the client is loaded, get the expense details.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const expenseId = urlParams.get('id');
        this.dataStore.set('expenseId', expenseId);
        const expenseDetail = await this.client.getExpense(expenseId);
        this.dataStore.set('expenseDetail', expenseDetail);
        this.displayExpenseDetails();

    }

    /**
     * Add the header to the page and load the BudgetMeClient.
     */
    async mount() {
        document.getElementById('save-expense').addEventListener('click', this.update);
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
        await this.clientLoaded();
    }


     /**
     * Display expense details  on the page.
     */
    displayExpenseDetails() {
        const expenseDetail = this.dataStore.get('expenseDetail');

        if (!expenseDetail) {
            return;
        }

        if (expenseDetail.expenseName){
            document.getElementById('expenseName').value = expenseDetail.expenseName;
        }
        if (expenseDetail.expenseAmount){
            document.getElementById('expenseAmount').value = expenseDetail.expenseAmount;
        }
        if (expenseDetail.tag){
            document.getElementById('expenseTag').value = expenseDetail.tag;
        }
        if (expenseDetail.date){
            document.getElementById('expenseDate').value = expenseDetail.date;
        }
 }

    async update() {

         const nameRegex = new RegExp('[^a-zA-Z\\s-\'.]');
         const numberRegex = new RegExp('^\d+(\.\d{1,2})?$');
         const expenseId = this.dataStore.get('expenseId');
         const expenseName = document.getElementById('expenseName').value;
         const expenseAmount = document.getElementById('expenseAmount').value;
         const tag = document.getElementById('expenseTag').value;
         const date = document.getElementById('expenseDate').value;
          if (!expenseName || !expenseAmount || !tag || !date) {
             alert("Please fill in all required fields");
             return;
         }
         if (nameRegex.test(expenseName)) {
             alert("The first name you entered has invalid characters");
             return;
         }
         if (numberRegex.test(expenseAmount)) {
             alert ("The expense amount you entered has invalid characters");
             return;
         }
         if ((date.substring(0,4) < 1900) || (date.substring(0,4) > 2100) || (date.length != 10)) {
             alert("The date you entered is invalid.");
             return;
         }

         let payload = {expenseId: expenseId, expenseName: expenseName, expenseAmount: expenseAmount, tag: tag, date: date}

         document.getElementById('save-expense').disabled = true;
         document.getElementById('save-expense').innerHTML = 'Saving Expense...';
         document.getElementById('save-expense').style.background='grey';
         const expense = await this.client.updateExpense(payload);
         this.dataStore.set('expense', expense);
         this.redirectToViewExpenses();
     }

    redirectToViewExpenses() {
         const expense = this.dataStore.get('expense');
         if (expense) {
             window.location.href = `/viewAllExpenses.html`;
         }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updateExpense = new ViewExpense();
    await updateExpense.mount();
};

window.addEventListener('DOMContentLoaded', main);