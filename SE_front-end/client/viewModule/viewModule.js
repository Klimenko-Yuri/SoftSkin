const View = {
    inputContainer: document.querySelector('#input-container'),
    addResultField: function(type) {
        const mainField = document.querySelector('.field-wrap');
        const fieldWrapRes = document.createElement('div');
        fieldWrapRes.classList.add('field-wrap', 'field-wrap_res');
        const resultField = document.createElement('span');
        resultField.innerText = document.querySelector('.input-field').value;
        document.querySelector('.input-field').value = ''
        resultField.classList.add('result-field')
        fieldWrapRes.appendChild(resultField);
        this.inputContainer.insertBefore(fieldWrapRes, mainField);

        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-button');
        deleteButton.classList.add('confirm-button')
        deleteButton.innerHTML = '<i class="icon-cancel"></i>';
        deleteButton.addEventListener('click', function() {
            deleteButton.parentNode.parentNode.removeChild(deleteButton.parentNode);
        });

        fieldWrapRes.appendChild(deleteButton)

        if (type === true) {
            resultField.style.borderColor = 'green';
        } else if (type === false) {
            resultField.style.borderColor = 'red';
        } else {
            resultField.style.borderColor = 'yellow';
        }
    }
};

export default View;