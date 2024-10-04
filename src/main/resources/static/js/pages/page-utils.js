function getElementValueOrNull(selectorId) {
    const selectorValue = document.getElementById(selectorId).value;
    return selectorValue === "" ? null : selectorValue
}

/**
 * 
 * @param {string} objectIdString
 * @param {Array} list 
 * @returns {object} The sum of a and b
 */
function findSelectedObjectFromListById(objectIdString, list) {
    const id = parseInt(objectIdString)
    return list.find(object => object.id === id)
}

function clearInputs() {
    const allFields = document.querySelectorAll('input, select, textarea');

    allFields.forEach(field => {
        // Handle <input> elements
        if (field.tagName.toLowerCase() === 'input') {
            if (field.type === 'checkbox' || field.type === 'radio') {
                // Uncheck checkboxes and radio buttons
                field.checked = false;
            } else if (field.type === 'number') {
                field.value = 0
            } else {
                // Clear text, email, and other input fields
                field.value = '';
            }
        }

        // Handle <select> elements
        if (field.tagName.toLowerCase() === 'select') {
            // Set the select back to the first option (default)
            field.selectedIndex = 0;
        }

        // Handle <textarea> elements
        if (field.tagName.toLowerCase() === 'textarea') {
            field.value = '';
        }
    });

}

