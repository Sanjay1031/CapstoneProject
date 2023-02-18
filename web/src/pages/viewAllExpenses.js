import BudgetMeClient from '../api/budgetMeClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";


/**
 * Logic needed for the view Expenses page of the website.
 */

class ViewAllExpenses extends BindingClass {

    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount',  'displayExpensesOnPage', 'generateTable'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayExpensesOnPage);
        this.header = new Header(this.dataStore);
    }

 /**
     * Once the client is loaded, get the employees list.
     */
    async clientLoaded() {
        document.getElementById('expenses').innerText = "(Loading expenditures...)";
        const id = await this.client.getIdentity();

        //Get all employees API
        const expenses = await this.client.getAllExpenses(id.email);
        this.dataStore.set('expenses', expenses);
    }


    /**
     * Add the header to the page and load the EmployeeMgmtClientClient.
     */
    async mount() {
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
        await this.clientLoaded();

    }


    async generateTable(table, data) {

      if (data.length != 0) {
          for (let element of data) {
            let row = table.insertRow();

            row.addEventListener('click', async evt => {
                        window.location.href = `/view_expense.html?id=${element.expenseId}`;
                      });

            let cell = row.insertCell();
            let text = document.createTextNode(element.expenseId);
            cell.appendChild(text);

            cell = row.insertCell();
            text = document.createTextNode(element.expenseName);
            cell.appendChild(text);


            cell = row.insertCell();
            text = document.createTextNode(element.expenseAmount);
            cell.appendChild(text);


            cell = row.insertCell();
            text = document.createTextNode(element.tag);
            cell.appendChild(text);

            cell = row.insertCell();
            text = document.createTextNode(element.date);
            cell.appendChild(text);
          }
      }
    }

 /**
     * When the employees are updated in the datastore, update the list of employees on the page.
     */
    async displayExpensesOnPage() {
        const expenses = this.dataStore.get('expenses');

        if (!expenses) {
            return;
        }

        let table = document.querySelector("table");

        //Flush the table first
        var tableHeaderRowCount = 1;
        var rowCount = table.rows.length;
        for (var i = tableHeaderRowCount; i < rowCount; i++) {
            table.deleteRow(tableHeaderRowCount);
        }
        //Generate table data with the new set of employees
        this.generateTable(table, expenses);
        document.getElementById('expenses').innerText = "";

        if (expenses.length === 0) {
            document.getElementById('expenses').innerText = "(No expenses found...)";
        }
 }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewAllExpenses = new ViewAllExpenses();
    await viewAllExpenses.mount();
};

window.addEventListener('DOMContentLoaded', main);