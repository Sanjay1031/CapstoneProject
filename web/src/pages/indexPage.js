import BudgetMeClient from '../api/budgetMeClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class IndexPage extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount'], this);
        this.dataStore = new DataStore();
        this.header = new Header();
        console.log("Index constructor");
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
    }
}

const main = async () => {
    const indexPage = new IndexPage();
    indexPage.mount();
};



window.addEventListener('DOMContentLoaded', main);