import axios from 'axios';
import { resolve } from 'path';
import { rejects } from 'assert';

//let inputFieldData = document.querySelector('.search__input');

function getAllComponents() {
    return new Promise(function(resolve, rejects) {
        return axios.get('http://httpbin.org/ip').then(function(data) {
            resolve(data)
        });
    });
    // return [
    //     {"id": 28, 
    //     "name": "Вода деминерализованная", 
    //     "description": "Вода деминерализованная", 
    //     "type": "GOOD", 
    //     "visiable": true }, 
    //     { "id": 32, 
    //     "name": "Вода", 
    //     "description": "Вода", 
    //     "type": "GOOD", 
    //     "visiable": true }
    // ]
}
function findComponents(letters) {
    return new Promise(function(resolve) {
        return axios.get('http://151.80.70.43:8080/skin-expert/find-component/'+letters).then((data) => {
            resolve(data)
        })
    })
}
//console.log(getAllComponents())
export { getAllComponents, findComponents };