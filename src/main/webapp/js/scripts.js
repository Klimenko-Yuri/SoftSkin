
$(document).ready(function () {


    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    let loadFile = document.getElementById("loadImg");
    let btnSend = document.getElementById("send");


    loadFile.addEventListener("change", function (e) {
        readURL(this);
    });

    btnSend.addEventListener('click', function () {
        var formData = new FormData();
        formData.append("image", loadFile.files[0]);

        axios.post('/find-component', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        // var xhr = new XMLHttpRequest();
        // xhr.open("POST", "http://151.80.70.43:8080/skin-expert/find-component/");
        // xhr.send(formData);
    });

});


$(window).load(function(){

});


$(window).resize(function(){

});


$(window).scroll(function(){

});


$(window).on('load', function() {

		$(".preloader-wave").fadeOut();
		$("#preloader").delay(200).fadeOut("slow").remove();

});
