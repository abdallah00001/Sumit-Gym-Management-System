let images = ["https://demolisseursnormands.fr/gym/images/slider/02.jpg", "https://demolisseursnormands.fr/gym/images/slider/04.jpg"];
let mode = 1;

$(document).ready(function() {
setInterval(function() {
if (mode) {
  $(".background").css('background-image', 'url(' + images[0] + ')');
  mode = 0;
} else {
  $(".background").css('background-image', 'url(' + images[1] + ')');
  mode = 1;
}
}, 3000); 
});

const shadowCircle = document.querySelector('.shadow-circle');

document.addEventListener('mousemove', (event) => {
    shadowCircle.style.left = `${event.pageX}px`;
    shadowCircle.style.top = `${event.pageY}px`;
});

document.getElementById('searchButton').addEventListener('click', function() {
    const searchInput = document.getElementById('searchInput').value;
    
    if (isNaN(searchInput)) {
        fetchSubscriptionsByUsername(searchInput);
    } else {
        fetchSubscriptionsByMemberId(searchInput);
    }
});

// Fetch subscriptions by member ID
function fetchSubscriptionsByMemberId(memberId) {
    fetch(`/api/sub/member/${memberId}`)
        .then(response => response.json())
        .then(data => displaySubscriptionInfo(data))
        .catch(error => console.error('Error fetching subscriptions by member ID:', error));
}

// Fetch subscriptions by username
function fetchSubscriptionsByUsername(userName) {
    fetch(`/api/sub/user/${userName}`)
        .then(response => response.json())
        .then(data => displaySubscriptionInfo(data))
        .catch(error => console.error('Error fetching subscriptions by username:', error));
}

// Display subscription info in the subsInfo section
function displaySubscriptionInfo(subscriptions) {
    if (subscriptions && subscriptions.length > 0) {
        const subscription = subscriptions[0]; // Assuming you want to display the first subscription

        // Populate subscription info
        document.getElementById('sessionsInfo').textContent = subscription.attendedDaysCount;
        document.getElementById('trainerInfo').textContent = subscription.subscriptionTypeDto.name;
        document.getElementById('statusInfo').textContent = subscription.status;

        // Set up action buttons with appropriate functionality
        setupActionButtons(subscription);
    } else {
        console.error('No subscriptions found for this user.');
    }
}

// Setup action buttons (Attend, Renew, Freeze, Unfreeze, Refund)
function setupActionButtons(subscription) {
    const attendButton = document.getElementById('attendButton');
    const renewButton = document.getElementById('renewButton');
    const freezeButton = document.getElementById('freezeButton');
    const unfreezeButton = document.getElementById('unfreezeButton');
    const refundButton = document.getElementById('refundButton');

    // Attend button functionality
    attendButton.addEventListener('click', function() {
        const date = new Date().toISOString().split('T')[0]; // Get today's date
        fetch(`/api/sub/member/${subscription.userDto.id}/day/${date}`, { method: 'PUT' })
            .then(response => response.json())
            .then(data => {
                alert('Attendance marked successfully.');
            })
            .catch(error => console.error('Error marking attendance:', error));
    });

    // Renew button functionality
    renewButton.addEventListener('click', function() {
        const renewPrice = document.getElementById('renewPriceInput').value; // Get value from input field
        if (renewPrice) {
            fetch(`/api/sub/renew/${subscription.id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    finalPrice: renewPrice,
                    id: subscription.id,
                    originalPrice: subscription.finalPrice,
                    paymentType: 'Cash', // Assuming Cash, you can modify as needed
                    purpose: 'RENEW'
                })
            })
            .then(response => response.json())
            .then(data => {
                alert('Subscription renewed successfully.');
            })
            .catch(error => console.error('Error renewing subscription:', error));
        } else {
            alert('Please enter the renewal price.');
        }
    });

    // Freeze button functionality
    freezeButton.addEventListener('click', function() {
        const daysToFreeze = document.getElementById('freezeDaysInput').value; // Get value from input field
        if (daysToFreeze) {
            fetch(`/api/sub/freeze/${subscription.id}/${daysToFreeze}`, { method: 'PUT' })
                .then(response => response.json())
                .then(data => {
                    alert('Subscription frozen successfully.');
                })
                .catch(error => console.error('Error freezing subscription:', error));
        } else {
            alert('Please enter the number of days to freeze.');
        }
    });

    // Unfreeze button functionality
    unfreezeButton.addEventListener('click', function() {
        fetch(`/api/sub/unfreeze/${subscription.id}`, { method: 'PUT' })
            .then(response => response.json())
            .then(data => {
                alert('Subscription unfrozen successfully.');
            })
            .catch(error => console.error('Error unfreezing subscription:', error));
    });

    // Refund button functionality
    refundButton.addEventListener('click', function() {
        const moneyRefunded = prompt('Enter the amount to refund:');
        fetch(`/api/sub/refund/${subscription.id}/${moneyRefunded}`, { method: 'PUT' })
            .then(response => response.json())
            .then(data => {
                alert('Refund processed successfully.');
            })
            .catch(error => console.error('Error processing refund:', error));
    });
}

// Display the subscription info dynamically
function displaySubscriptionInfo(subscriptions) {
    const clientInfoDiv = document.getElementById('clientInfo');
    const subsInfoDiv = document.getElementById('subsInfo');

    // Clear previous information
    clientInfoDiv.innerHTML = '';
    subsInfoDiv.innerHTML = '';

    // If no subscriptions found, display a message
    if (subscriptions.length === 0) {
        clientInfoDiv.innerHTML = '<p>No subscriptions found for this member.</p>';
        return;
    }

    // Display client details
    const userDto = subscriptions[0].userDto;
    clientInfoDiv.innerHTML = `
        <p><strong>Client Name:</strong> ${userDto.userName}</p>
        <p><strong>Role:</strong> ${userDto.role}</p>
    `;

    // Display subscription information
    subscriptions.forEach(sub => {
        const subscriptionType = sub.subscriptionTypeDto;

        const subInfo = `
            <h3>Subscription Info</h3>
            <div class="info-row">
                <label>Subscription:</label>
                <span>${subscriptionType.name} (${subscriptionType.periodString})</span>
            </div>
            <div class="info-row">
                <label>Start Date:</label>
                <span>${sub.startDate}</span>
            </div>
            <div class="info-row">
                <label>Expire Date:</label>
                <span>${sub.expireDate}</span>
            </div>
            <div class="info-row">
                <label>Attended Days:</label>
                <span>${sub.attendedDaysCount}</span>
            </div>
            <div class="info-row">
                <label>Status:</label>
                <span>${sub.status}</span>
            </div>
        `;

        subsInfoDiv.innerHTML += subInfo;
    });
}

window.addEventListener('scroll', function() {
    const signinContainer = document.getElementById('signinContainer');
    const rect = signinContainer.getBoundingClientRect();
    const windowHeight = window.innerHeight;

    // Check if the container is in view
    if (rect.top < windowHeight && rect.bottom > 0) {
        signinContainer.classList.add('scrolled');
    } else {
        signinContainer.classList.remove('scrolled');
    }
});
