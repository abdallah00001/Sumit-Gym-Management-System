function displayObjectsInDivs(objectsList) {
    // Get the container where you want to display the divs
    const container = document.getElementById('objectsContainer');
    
    // Clear the container in case there is existing content
    container.innerHTML = '';

    // Loop through the list of objects
    objectsList.forEach((obj, index) => {
        // Create a new div for each object
        const div = document.createElement('div');
        
        // Add some content to the div (e.g., display the object as a JSON string)
        div.innerHTML = `<strong>Object ${index + 1}:</strong> ${JSON.stringify(obj)}`;

        // Option 1: Attach the object using data-* attributes (useful for storing small values)
        div.dataset.obj = JSON.stringify(obj);

        // Option 2: Attach the object directly to the DOM element (useful for larger objects)
        div.objectData = obj;

        // Add any CSS styling to the div if needed
        div.style.border = '1px solid black';
        div.style.margin = '10px';
        div.style.padding = '10px';

        // Create a button
        const button = document.createElement('button');
        button.textContent = 'Log Object';

        // Add a click event to the button to log the object
        button.addEventListener('click', () => {
            console.log('Original object:', div.objectData); // Access the bound object
        });

        // Append the button to the div
        div.appendChild(button);

        // Append the div to the container
        container.appendChild(div);
    });
}
