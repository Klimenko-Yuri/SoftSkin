import axios from 'axios/dist/axios';
import lodash from 'lodash';
import { getAllComponents, findComponents} from '../requestLogic/wordRequest.js'

let inputField = document.querySelector('.search__input');
let searchButton = document.querySelector('.search__btn');
let hintDiv = document.querySelector('.search__hint');

function checkInputField(field) {
    if (field.value.length >= 3) {
        return true;
    }
    else return false;
}

function hintDivOpener(field) {
    findComponents(inputField.value).then(function(data) {
        console.log(inputField.value)
        let componentsArray =  data;
        console.log(componentsArray)
        hintDiv.innerHTML = '';
            for(let i = 0; i < componentsArray.length; i++) {
                let hintElement = document.createElement('div');
                hintElement.innerText = componentsArray[i].name;  //[i].name
                hintDiv.appendChild(hintElement);
        }
    });
    //alert(field.value)
}

let debounced = _.debounce(function () {
    hintDivOpener(inputField)
}, 1000)

if(inputField && searchButton) {
        inputField.oninput = function() {
        if (checkInputField(inputField)) {
            debounced();
        }
    }
    searchButton.addEventListener('click', function () {
        takeInputLength(inputField);
    })
}
