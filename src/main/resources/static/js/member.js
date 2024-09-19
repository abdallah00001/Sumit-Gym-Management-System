// member.js

document.addEventListener("DOMContentLoaded", () => {
    fetchMembers();
});

/**
 * Fetches the members and displays them in the table.
 */
async function fetchMembers() {
    try {
        // Use the apiGet function from api-utils.js
        const members = await apiGet('/members');
        
        if (members.length) {
            displayMembers(members);
        } else {
            showToast('No members found.');
        }
    } catch (error) {
        console.error('Error fetching members:', error);
        showToast('Failed to load members. Please try again later.');
    }
}

/**
 * Displays the list of members in the HTML table.
 * @param {Array} members - List of member objects.
 */
function displayMembers(members) {
    const tableBody = document.querySelector("#membersTable tbody");
    tableBody.innerHTML = ''; // Clear any existing rows
    
    members.forEach(member => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${member.id}</td>
            <td>${member.name}</td>
            <td>${member.email}</td>
        `;
        tableBody.appendChild(row);
    });
}
