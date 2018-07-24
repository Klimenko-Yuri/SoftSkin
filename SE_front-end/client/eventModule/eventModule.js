import dataModule from '../dataModule/dataModule.js';
import View from '../viewModule/viewModule.js';

const Events = {
    confirmButtonEvent: function () {
        const confirmButton = document.querySelector('.add-button')
        confirmButton.addEventListener('click', dataModule.sendDataFromInput);
    }
}

export default Events