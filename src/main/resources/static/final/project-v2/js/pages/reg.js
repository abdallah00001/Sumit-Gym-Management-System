let images = ["https://demolisseursnormands.fr/gym/images/slider/02.jpg", "https://demolisseursnormands.fr/gym/images/slider/04.jpg"];
let mode = 1;
let subscriptionId = null;
let memberId;
let subscriptionPrice;
// let subscriptionType;
let personalTrainer;
let currentMember = null;


$(document).ready(function () {
    setInterval(function () {
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

const memberByPhoneEndpoint = "/members/phone/";
const memberByIdEndpoint = "/members/";

// Function to get member by phone number
async function getMemberByPhone(phoneNumber) {
    const endpoint = `${memberByPhoneEndpoint}${phoneNumber}`;
    const response = await apiGet(endpoint);
    return response;
}

// Function to get member by ID
async function getMemberById(memberId) {
    const endpoint = `${memberByIdEndpoint}${memberId}`;
    const response = await apiGet(endpoint);
    return response;
}

// Event listener for the unfreeze button
document.getElementById('unfreezeButton').addEventListener('click', async () => {
    if (!subscriptionId) {
        alert("Subscription ID not found. Please search for a member first.");
        return;
    }

    try {
        // Send unfreeze request
        const unfreezeResponse = await fetch(`http://localhost:8080/api/sub/unfreeze/${subscriptionId}`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!unfreezeResponse.ok) {
            const errorData = await unfreezeResponse.json();
            alert(`Failed to unfreeze subscription: ${errorData.message || 'Unknown error'}`);
            return;
        }

        // alert("Subscription unfrozen successfully!");

        // Fetch the updated member data after unfreezing
        const memberResponse = await fetch(`http://localhost:8080/api/members/${memberId}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (memberResponse.ok) {
            const updatedMember = await memberResponse.json();
            updateMemberInfo(updatedMember); // Update the UI with new member info
        } else {
            alert("Failed to fetch updated member information.");
        }
    } catch (error) {
        console.error("Error unfreezing subscription:", error);
        alert("An error occurred while unfreezing the subscription. Please try again.");
    }
});




// Event listener for the refund button
document.getElementById('refundButton').addEventListener('click', async () => {
    const refundAmount = document.getElementById('refundAmountInput').value.trim();

    if (!refundAmount || isNaN(refundAmount) || refundAmount <= 0) {
        alert("Please enter a valid refund amount.");
        return;
    }

    if (!subscriptionId) {
        alert("Subscription ID not found. Please search for a member first.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/sub/refund/${subscriptionId}/${refundAmount}`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        document.getElementById('unfreezeButton').addEventListener('click', async () => {
            if (!subscriptionId) {
                alert("Subscription ID not found. Please search for a member first.");
                return;
            }

            try {
                // Send unfreeze request
                const unfreezeResponse = await fetch(`http://localhost:8080/api/sub/unfreeze/${subscriptionId}`, {
                    method: 'PUT',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (!unfreezeResponse.ok) {
                    const errorData = await unfreezeResponse.json();
                    alert(`Failed to unfreeze subscription: ${errorData.message || 'Unknown error'}`);
                    return;
                }

                // alert("Subscription unfrozen successfully!");


                // Fetch the updated member data after unfreezing
                const memberResponse = await fetch(`http://localhost:8080/api/members/${memberId}`, {
                    method: 'GET',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (memberResponse.ok) {
                    const updatedMember = await memberResponse.json();
                    updateMemberInfo(updatedMember); // Update the UI with new member info
                } else {
                    alert("Failed to fetch updated member information.");
                }
            } catch (error) {
                console.error("Error unfreezing subscription:", error);
                alert("An error occurred while unfreezing the subscription. Please try again.");
            }
        });

        if (response.ok) {
            // alert("Refund processed successfully!");
            // Fetch the updated member data after unfreezing
            const memberResponse = await fetch(`http://localhost:8080/api/members/${memberId}`, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (memberResponse.ok) {
                const updatedMember = await memberResponse.json();
                updateMemberInfo(updatedMember); // Update the UI with new member info
            } else {
                alert("Failed to fetch updated member information.");
            }
        } else {
            const errorData = await response.text();
            alert(`Failed to process refund: ${errorData.message || 'Unknown error'}`);
        }
    } catch (error) {
        console.error("Error processing refund:", error);
        alert("An error occurred while processing the refund. Please try again.");
    }
});

// Event listener for the renew button
document.getElementById('renewButton').addEventListener('click', async () => {
    const renewDiscount = document.getElementById('renewPriceInput').value.trim();

    console.log(renewDiscount);

    if (isNaN(renewDiscount) || parseInt(renewDiscount) < 0) {
        alert("Please enter a valid price for renewal.");
        return;
    }
    // if (!renewDiscount || isNaN(renewDiscount) || renewDiscount <= 0) {
    //     alert("Please enter a valid price for renewal.");
    //     return;
    // }
    if (!subscriptionId || !currentMember) {
        alert("Subscription ID or member data not found. Please search for a member first.");
        return;
    }

    try {
        // Extract the required information from the current member's subscription details
        const subscription = currentMember.subscriptionDto || {};
        const paymentDetails = subscription.payments?.[0] || {}; // Assuming the first payment contains relevant data

        // <New code mark>
        const subType = subscription.subscriptionTypeDto;
        const originalPrice = subType.price;

        const paymentObject = {
            payment: {
                discount: renewDiscount || 0, // Use old discount if available
                // originalPrice: paymentDetails.originalPrice || subscriptionPrice, // Use original price from the old payment or subscription
                originalPrice: subscriptionPrice, // Use original price from the old payment or subscription
                finalPrice: originalPrice - parseFloat(renewDiscount), // Final price is the renewed price entered by the user
                paymentType: paymentDetails.paymentType || "Cash", // Defaulting to Cash if paymentType is not available
                purpose: "RENEW", // Purpose for this payment is renewal
                createdAt: new Date().toISOString(), // Current timestamp
                // user: {
                //     id: currentMember.id, // Fetch member ID
                //     userName: currentMember.name || "N/A", // Use member name or "N/A" if not available
                //     role: "ROLE_USER", // Default role, adjust as needed
                //     enabled: true,
                //     credentialsNonExpired: true,
                //     accountNonExpired: true,
                //     accountNonLocked: true
                // }
            },
            notes: "Renewal of subscription", // Additional notes if necessary
            subscriptionType: subscription.subscriptionTypeDto?.name || "Unknown",  // Same subscription type as before
            personalTrainer: subscription.personalTrainer || false // Same personal trainer preference as before
        };
        // </New code mark>

        const response = await fetch(`http://localhost:8080/api/sub/renew/${subscriptionId}`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(paymentObject) // Send the payment object in the request body
        });

        if (response.ok) {
            // alert("Subscription renewed successfully!");
            // Fetch the updated member data after unfreezing
            const memberResponse = await fetch(`http://localhost:8080/api/members/${memberId}`, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (memberResponse.ok) {
                const updatedMember = await memberResponse.json();
                updateMemberInfo(updatedMember); // Update the UI with new member info
            } else {
                alert("Failed to fetch updated member information.");
            }
        } else {
            const errorData = await response.json();
            alert(`Failed to renew subscription: ${errorData.message || 'Unknown error'}`);
        }
    } catch (error) {
        console.error("Error renewing subscription:", error);
        alert("An error occurred while renewing the subscription. Please try again.");
    }
});



// Event listener for the attend button
document.getElementById('attendButton').addEventListener('click', async () => {
    if (!memberId) {
        alert("Member ID not found. Please search for a member first.");
        return;
    }

    // Get the current date in 'YYYY-MM-DD' format
    const currentDate = new Date().toISOString().split('T')[0];

    try {
        const response = await fetch(`http://localhost:8080/api/sub/member/${memberId}/day/${currentDate}`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            // alert("Attendance recorded successfully!");
            // Fetch the updated member data after unfreezing
            const memberResponse = await fetch(`http://localhost:8080/api/members/${memberId}`, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (memberResponse.ok) {
                const updatedMember = await memberResponse.json();
                updateMemberInfo(updatedMember); // Update the UI with new member info
            } else {
                alert("Failed to fetch updated member information.");
            }
        } else {
            const errorData = await response.json();
            alert(`Failed to record attendance: ${errorData.message || 'Unknown error'}`);
        }
    } catch (error) {
        console.error("Error recording attendance:", error);
        alert("An error occurred while recording attendance. Please try again.");
    }
});

// Function to update member info UI
function updateMemberInfo(member) {
    currentMember = member;  // Store member details globally for reuse
    memberId = member.id;

    const clientInfoDiv = document.getElementById('clientInfo');
    const subscriptionTypeInfoSpan = document.getElementById('subscriptionTypeInfo');
    const sessionsInfoSpan = document.getElementById('sessionsInfo');
    const trainerInfoSpan = document.getElementById('trainerInfo');
    const startDateInfoSpan = document.getElementById('startDateInfo');
    const expireDateInfoSpan = document.getElementById('expireDateInfo');
    const remainingFreezeSpan = document.getElementById('remainingFreezeSpanInfo');
    const statusInfoSpan = document.getElementById('statusInfo');
    const discountInfoSpan = document.getElementById('discountInfo');
    const finalPriceInfoSpan = document.getElementById('finalPriceInfo');
    const priceInfoSpan = document.getElementById('priceInfo');

    // Updating client info section
    clientInfoDiv.innerHTML = `
        <h3>Client Info</h3>
        <div class="info-row">
            <label>ID:</label>
            <span>${member.id || 'N/A'}</span>
        </div>
        <div class="info-row">
            <label>Name:</label>
            <span>${member.name || 'N/A'}</span>
        </div>
        <div class="info-row">
            <label>Phone:</label>
            <span>${member.phone || 'N/A'}</span>
        </div>
        <div class="info-row">
            <label>Birth Date:</label>
            <span>${member.birthDate || 'N/A'}</span>
        </div>
        <div class="info-row">
            <label>Gender:</label>
            <span>${member.gender || 'N/A'}</span>
        </div>
    `;

    // Updating subscription info section
    const subscription = member.subscriptionDto || {};
    
    subscriptionTypeInfoSpan.innerText = subscription.subscriptionTypeDto.name || '0';
    sessionsInfoSpan.innerText = subscription.attendedDaysCount || '0';
    // notesInfoSpan.innerText = subscription.notes || 'N/A';
    //Preventing null trainers from causing errors (some subs have no trainer) 
    trainerInfoSpan.innerText = (subscription.privateTrainer ? subscription.privateTrainer.name : 'N/A');
    startDateInfoSpan.innerText = subscription.startDate || 'N/A';
    expireDateInfoSpan.innerText = subscription.expireDate || 'N/A';
    remainingFreezeSpan.innerText = subscription.remainingFreezeLimit || 'N/A';
    statusInfoSpan.innerText = subscription.status || 'N/A';
    discountInfoSpan.innerText = subscription.payments.map(payment => payment.discount) || '0';
    finalPriceInfoSpan.innerText = subscription.payments.map(payment => payment.finalPrice) || '0';
    priceInfoSpan.innerText = subscription.subscriptionTypeDto.price || '0';

    // Store the subscription ID for renew functionality
    subscriptionId = subscription.id || null;
    subscriptionPrice = subscription.subscriptionTypeDto?.price || null;


    // Set refund amount input to the subscription price if available
    if (subscriptionPrice) {
        document.getElementById('refundAmountInput').value = subscriptionPrice;
    }
}



// Event listener for the search button
document.getElementById('searchButton').addEventListener('click', async () => {
    const inputValue = document.getElementById('searchInput').value.trim();

    if (!inputValue) {
        alert("Please enter a phone number or ID.");
        return;
    }

    // const isId = !isNaN(inputValue);
    const isId = !inputValue.startsWith("01");
    // 

    try {
        let result;
        if (isId) {
            result = await getMemberById(inputValue);
        } else {
            result = await getMemberByPhone(inputValue);
        }
        updateMemberInfo(result.body);
    } catch (error) {
        console.error("Error fetching member data:", error);
        alert("Failed to fetch member data. Please try again.");
    }
});

// Function to update the client information on the page
function displayClientInfo(clientData) {
    const clientInfoDiv = document.getElementById('clientInfo');
    const subsInfoDiv = document.getElementById('subsInfo');

    // Update client info
    clientInfoDiv.innerHTML = `
        <h3>Client Information</h3>
        <div class="info-row">
            <label>ID:</label>
            <span>${clientData.id}</span>
        </div>
        <div class="info-row">
            <label>Name:</label>
            <span>${clientData.name}</span>
        </div>
        <div class="info-row">
            <label>Phone:</label>
            <span>${clientData.phone}</span>
        </div>
        <div class="info-row">
            <label>Gender:</label>
            <span>${clientData.gender}</span>
        </div>
        <div class="info-row">
            <label>Birth Date:</label>
            <span>${clientData.birthDate ? clientData.birthDate : "Not Provided"}</span>
        </div>
    `;

    // Update subscription info (if available)
    if (clientData.subscriptionDto) {
        document.getElementById('sessionsInfo').textContent = clientData.subscriptionDto.attendedDaysCount;
        document.getElementById('trainerInfo').textContent = clientData.subscriptionDto.notes || "No Trainer";
        document.getElementById('statusInfo').textContent = clientData.subscriptionDto.status;
    } else {
        // Handle case where no subscription is found
        subsInfoDiv.innerHTML = `
            <h3>Subscription Info</h3>
            <div>No subscription found for this client.</div>
        `;
    }
}

document.getElementById('trainer-form').addEventListener('submit', function (e) {
    e.preventDefault();

    const trainerData = {
        name: document.getElementById('trainerName').value,
        phone: document.getElementById('trainerPhone').value
    };

    fetch('http://localhost:8080/api/coach/admin/save', {
        method: 'POST',
        // mode: 'no-cors',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(trainerData)
    })

        .then(response => response.text())
        .then(data => {
            alert(`Trainer added successfully! ID: ${data.id}`);
            //From reg-original.js
            populateTrainerNames()
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

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

document.getElementById('subscription-form').addEventListener('submit', function (e) {
    e.preventDefault();

    const subscriptionData = {
        name: document.getElementById('subName').value,
        price: parseFloat(document.getElementById('price').value),
        periodLength: parseInt(document.getElementById('periodLength').value),
        allowedFreezeDays: parseInt(document.getElementById('allowedFreezeDays').value),
        periodType: document.getElementById('periodType').value,
        forPrivateTrainer: document.getElementById('forPrivateTrainer').value
    };

    fetch('http://localhost:8080/api/sub-type/admin/save', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(subscriptionData)
    })
        .then(response => response.text())
        .then(data => {
            alert('Subscription added successfully!');
            //From reg-original.js
            populateSubscriptionTypes()
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

document.getElementById('freezeButton').addEventListener('click', function () {
    const daysToFreeze = document.getElementById('freezeDaysInput').value;

    if (!subscriptionId) {
        alert("No subscription found. Please search for a member first.");
        return;
    }

    if (!daysToFreeze || daysToFreeze <= 0) {
        alert("Please enter a valid number of days to freeze.");
        return;
    }

    fetch(`http://localhost:8080/api/sub/freeze/${subscriptionId}/${daysToFreeze}`, {
        method: 'PUT',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Failed to freeze the subscription.');
            }
        })
        .then(data => {
            // alert(`Subscription frozen for ${daysToFreeze} days!`);


            return fetch(`http://localhost:8080/api/members/${memberId}`, {
                method: 'GET',
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        })
        .then(response => response.json())
        .then(updatedMember => {

            updateMemberInfo(updatedMember);
        })
        .catch(error => {
            console.error('Error:', error);
            // alert('An error occurred while freezing the subscription or fetching updated member info.');
        });
});

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

window.addEventListener('scroll', function () {
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


// Event listener for the "Change Subscription" button
document.getElementById('changeSubButton').addEventListener('click', async () => {
    const subTypeId = document.getElementById('subscriptionType2').value;
    const trainerId = document.getElementById('trainer2').value;
    const paymentType = document.getElementById('paymentType2').value;
    const discount = parseFloat(document.getElementById('discount2').value) || 0; // Convert discount to number or default to 0

    // Ensure a subscription type and payment type are selected
    if (!subTypeId || !paymentType) {
        alert("Please select a subscription type and payment type.");
        return;
    }

    const subType = subTypes.find(type => type.id == subTypeId);

    // Check if subType is valid
    if (!subType) {
        alert("Invalid subscription type selected.");
        return;
    }

    // Prepare the request body
    const requestBody = {
        member: {
            id: currentMember.id // Using the globally stored member details
        },
        payment: {
            finalPrice: subType.price - discount, // Adjust the final price based on discount
            originalPrice: subType.price,
            paymentType: paymentType,
            discount: discount,
            // purpose: "RENEW" // Uncomment if needed
        },
        privateTrainer: trainerId ? { id: trainerId } : null, // Optional trainer info
        notes: "Subscription changed via web form"
    };

    try {
        // First fetch to update subscription
        const response = await fetch(`http://localhost:8080/api/sub/sub-type/${subTypeId}`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        });

        if (response.ok) {
            const result = await response.text();
            alert("Subscription updated successfully!");

            // Second fetch to get updated member information
            const memberResponse = await fetch(`http://localhost:8080/api/members/${currentMember.id}`, {
                method: 'GET',
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (memberResponse.ok) {
                const updatedMember = await memberResponse.json();
                updateMemberInfo(updatedMember);
            } else {
                console.error("Failed to fetch updated member info:", memberResponse);
                alert("Failed to fetch updated member information.");
            }

            console.log(result); // Log the result of the subscription update
        } else {
            alert("Failed to update subscription.");
            console.error("Error response:", response);
        }
    } catch (error) {
        console.error("Error updating subscription:", error);
        alert("An error occurred. Please try again.");
    }
});

