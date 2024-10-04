// Simulating server-side fetched users and trainers
const users = [
    { id: 1, name: "John Doe" },
    { id: 2, name: "Jane Smith" },
    { id: 3, name: "Alice Johnson" }
];

const trainers = [
    { id: 1, name: "Trainer A" },
    { id: 2, name: "Trainer B" },
    { id: 3, name: "Trainer C" }
];

// Populate the user dropdown
const userSelect = document.getElementById('userSelect');
users.forEach(user => {
    const option = document.createElement('option');
    option.value = user.id;
    option.textContent = user.name;
    userSelect.appendChild(option);
});

// Populate the trainer dropdown
const trainerSelect = document.getElementById('trainerSelect');
trainers.forEach(trainer => {
    const option = document.createElement('option');
    option.value = trainer.id;
    option.textContent = trainer.name;
    trainerSelect.appendChild(option);
});

// Function to filter results based on selected filters
function filterResults() {
    const memberId = document.getElementById('memberId').value;
    const paymentType = document.getElementById('paymentType').value;
    const userId = document.getElementById('userSelect').value;
    const trainerId = document.getElementById('trainerSelect').value;
    const isExpired = document.getElementById('expiredCheck').checked;
    const isFrozen = document.getElementById('frozenCheck').checked;

    // Placeholder to display results
    let results = "Results:\n";

    // Adding filters to the results
    if (memberId) {
        results += `Member ID: ${memberId}\n`;
    }
    if (paymentType) {
        results += `Payment Type: ${paymentType}\n`;
    }
    if (userId) {
        const selectedUser = users.find(user => user.id === parseInt(userId));
        results += `User: ${selectedUser ? selectedUser.name : 'Unknown'}\n`;
    }
    if (trainerId) {
        const selectedTrainer = trainers.find(trainer => trainer.id === parseInt(trainerId));
        results += `Trainer: ${selectedTrainer ? selectedTrainer.name : 'Unknown'}\n`;
    }
    if (isExpired) {
        results += "Expired: Yes\n";
    }
    if (isFrozen) {
        results += "Frozen: Yes\n";
    }

    // Display the results
    document.getElementById('results').innerText = results;
}
