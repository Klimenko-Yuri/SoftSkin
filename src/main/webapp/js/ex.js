/**
 * Created by mihail on 11/8/18.
 */

function merge() {
    for (var i = 0; i < 100; i++) {
        setTimeout(function() {setContrast(i)}, 1000);
        //document.body.style.filter = "contrast(" + i + "%)";
    }

function setContrast(percent) {

    document.body.style.filter = "contrast(" + percent + "%)";
}}