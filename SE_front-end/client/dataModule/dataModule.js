import axios from 'axios';
import View from '../viewModule/viewModule.js'

const obj = {  // add for localVersion
    first: true,
    second: false,
    third: true
}

const dataModule = {
    sendDataFromInput: function () {
        const inputData = document.querySelector('.input-field').value.toLowerCase();
        console.log(inputData)
        if(!inputData) {
            return
        }

        let ansver = undefined;
        for (let key in obj) {
            if (inputData == key) {
                ansver = obj[key];
            }
        }
        View.addResultField(ansver)

        // axios.get(`/input?data=${inputData}`).then((result) => {
        //     if (result.data === true) {
        //         View.addResultField(true)
        //     } else if (result.data === false) { 
        //         View.addResultField(false)
        //     } else {
        //         View.addResultField(undefined)
        //     }
        // }).catch((err) => console.log(err))
    }
}

export default dataModule;
