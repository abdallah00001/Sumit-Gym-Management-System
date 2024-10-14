const subscriptionTypeSelector = document.getElementById('subscriptionType');
const trainerSelector = document.getElementById('trainer');
const discountInput = document.getElementById('discount')
const cashOutElem = document.getElementById('cashOut')
const paymentTypeInput = document.getElementById('paymentType')
let subTypes = []
let coaches = []
let currentPrice = 0

document.getElementById("register-button").addEventListener('click', register)
subscriptionTypeSelector.addEventListener('change', (event) => updateCashOut(event));
discountInput.addEventListener('change', (event) => addDiscount(event));


async function register() {
    // const member = populateMember()
    // console.log(member);
    
    // const subTypeid = getIdFromSelector(subscriptionTypeSelector)
    const member = populateMember()
    const subType = getCurrentType()
    const subTypeid = subType.id
    const payment = createPayment(subType.price)
    const notes = document.getElementById("notes").value
    // console.log(subscription);
    const prtivateTrainer = getCoach()

    const response = await saveSub(payment, subTypeid,member, notes, prtivateTrainer)
    if (response.status >= 200 && response.status < 300) {
        clearInputs()
        cashOutElem.textContent=""
    }
}

function getIdFromSelector(selectorElem) {
    const value = selectorElem.value
    return value === "" ? 0 : value
}

function getCurrentType(){
    const id = subscriptionTypeSelector.value
    if (!id) {
        return null
    }
    const type = findSelectedObjectFromListById(id, subTypes) 
    return type
}

function updateCashOut(event) {
    const type = getCurrentType()
    currentPrice = type.price
    cashOutElem.textContent = currentPrice - discountInput.value
}


function addDiscount(event) {
    const discoutValue = parseInt(event.target.value)

    //Check if a type is selected first
    if (!isNaN(subscriptionTypeSelector.value)) {
       cashOutElem.textContent = currentPrice - discoutValue
    }
}


function populateMember() {
    // Create a member object
    const member = {
        name: document.getElementById('fullName').value.trim(),
        gender: getElementValueOrNull('gender'),
        birthDate: document.getElementById('birthDate').value,
        phone: document.getElementById('phoneNumber').value.trim()
    };
    return member
}


async function populateSubscriptionTypes() {
    try {
        const response = await getAllSubscriptionTypes();
        subTypes = response.body
        // Clear existing options except the default one
        subscriptionTypeSelector.innerHTML = '<option value="" disabled selected>Select Type</option>';

        // Loop through the fetched types and create options
        subTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type.id;  // Set the option value to the ID of the subscription type
            option.textContent = `${type.name} - ${type.periodString} - $${type.price}`; // Display name, periodString, and price
            subscriptionTypeSelector.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching subscription types:', error);
    }
}


async function populateTrainerNames() {
    try {
        const response = await getAllCoaches();
        coaches = response.body;

        // Clear existing options except the default one
        trainerSelector.innerHTML = '<option value="" selected >None</option>';

        // Loop through the fetched types and create options
        coaches.forEach(coach => {
            const option = document.createElement('option');
            option.value = coach.id;  // Set the option value to the ID of the coach
            option.textContent = coach.name
            trainerSelector.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching subscription types:', error);
    }
}

function createPayment(originalPrice) {
    const paymentType = getElementValueOrNull('paymentType')
    const discount = discountInput.value

    const subscription = {
        // member: member,
        // coach: coach,
        originalPrice: originalPrice,
        paymentType: paymentType,
        discount: discount
    }

    return subscription
}

function getCoach() {
    const coachId = getIdFromSelector(trainerSelector)
    const coach = findSelectedObjectFromListById(coachId, coaches) 
    console.log(coach)
    return coach
}

window.onload = function() {
    populateSubscriptionTypes()
    populateTrainerNames()
}