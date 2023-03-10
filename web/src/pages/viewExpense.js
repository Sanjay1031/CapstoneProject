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
        this.bindClassMethods(['clientLoaded', 'mount', 'displayExpenseDetails', 'deleteExpenseFromTable' ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayExpenseDetails);
        document.getElementById('delete-expense').addEventListener('click', this.deleteExpenseFromTable);
        this.header = new Header(this.dataStore);
    }

 /**
     * Once the client is loaded, get the expense details.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const expenseId = urlParams.get('id');
        document.getElementById('exp_loading').innerHTML = "(Loading expense details...)";

        const expenseDetail = await this.client.getExpense(expenseId);
        this.dataStore.set('expenseDetail', expenseDetail);
        document.getElementById('update-expense').addEventListener('click', async evt => {
                             window.location.href = `/updateExpense.html?id=${expenseId}`;
                           });

        document.getElementById('exp_loading').innerHTML = "";
    }

    /**
     * Add the header to the page and load the BudgetMeClient.
     */
    async mount() {
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
            document.getElementById('ename').innerHTML = expenseDetail.expenseName;
        }
        if (expenseDetail.expenseAmount){
            document.getElementById('amount').innerHTML = expenseDetail.expenseAmount;
        }
        if (expenseDetail.tag){
            document.getElementById('tag').innerHTML = expenseDetail.tag;
        }
        if (expenseDetail.date){
            document.getElementById('date').innerHTML = expenseDetail.date;
        }
 }

    async deleteExpenseFromTable() {

        if(confirm("Are you sure you want to delete this expense?") == true) {
            const urlParams = new URLSearchParams(window.location.search); 
            const expenseId = urlParams.get('id'); 

            document.getElementById('delete-expense').disabled = true; 
            document.getElementById('delete-expense').value = 'Deleting Expense...';
            document.getElementById('delete-expense').style.background = 'grey'; 

            const expense = await this.client.deleteExpense(expenseId); 
            if(expense) {
                window.location.href = '/viewAllExpenses.html'; 
            }
        }
    }


}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewExpense = new ViewExpense();
    await viewExpense.mount();
};

window.addEventListener('DOMContentLoaded', main);