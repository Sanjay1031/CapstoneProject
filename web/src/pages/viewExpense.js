import EmployeeMgmtClient from '../api/budgetMeClient';
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
        this.bindClassMethods(['clientLoaded', 'mount', 'displayExpenseDetails' ], this);
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
        document.getElementById('exp_loading').innerHTML = "(Loading expense details...)";

        const expenseDetail = await this.client.getExpense(expenseId);
        this.dataStore.set('expenseDetail', expenseDetail);
//        document.getElementById('update-expense').addEventListener('click', async evt => {
//                            window.location.href = `/update_employee.html?id=${employeeId}`;
//                          });

        document.getElementById('exp_loading').innerHTML = "";
    }

    /**
     * Add the header to the page and load the BudgetMeClient.
     */
    async mount() {
        this.header.addHeaderToPage();
        this.header.loadData();
        this.client = new budgetMeClient();
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
            document.getElementById('amount').innerHTML = expenseDetail.amount;
        }
        if (expenseDetail.tag){
            document.getElementById('tag').innerHTML = expenseDetail.tag;
        }
        if (expenseDetail.date){
            document.getElementById('date').innerHTML = expenseDetail.date;
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