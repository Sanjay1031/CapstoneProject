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
        this.client = new BudgetMeClient();
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new BudgetMeClient();
    }

    async checkIfLoggedIn() {
        const currentUser = await this.client.getIdentity();
        return !!currentUser;
    }
}

const main = async () => {
    const indexPage = new IndexPage();
    const loginPrompt = document.createElement('h3');
    const loggedIn = await indexPage.checkIfLoggedIn();
    loginPrompt.innerHTML = 'Please log in to continue!'
    if (!loggedIn) {
        document.getElementById('please-login').appendChild(loginPrompt)
        document.getElementById('button-container').style.display = "none";
    }
    indexPage.mount();
};



window.addEventListener('DOMContentLoaded', main);