let loadImg = document.querySelector("#loadImg");
let loadCameraImg = document.querySelector("#camera");
let img = document.querySelector("#img");
let btnSend = document.querySelector("#send");

function readURL(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            img.setAttribute("src", e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

if (loadImg) {
    loadImg.addEventListener("change", function () {
        readURL(this);
        btnSend.className = "";
        btnSend.classList.add('send-file');

    });

}
if(loadCameraImg) {
    loadCameraImg.addEventListener("change", function () {
        readURL(this);
        filename = this.files[0].name;
        btnSend.className = "";
        btnSend.classList.add('send-photo');
    });
}
