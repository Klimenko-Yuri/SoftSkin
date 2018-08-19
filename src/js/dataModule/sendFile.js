import axios from 'axios/dist/axios'

let btnSend = document.querySelector("#send");
if (btnSend) {
    btnSend.addEventListener('click', function () {

        if (btnSend.classList.contains('send-file')) {

            var formData = new FormData();
            formData.append("image", loadImg.files[0]);
            // axios.post('/parse-photo', formData, {
            //     headers: {
            //         'Content-Type': 'multipart/form-data'
            //     }
            // });

        } else if (btnSend.classList.contains('send-photo')) {

            var formData = new FormData();
            formData.append("image", loadImg.files[0]);
            // axios.post('/parse-photo', formData, {
            //     headers: {
            //         'Content-Type': 'multipart/form-data'
            //     }
            // });
        }
    });
}