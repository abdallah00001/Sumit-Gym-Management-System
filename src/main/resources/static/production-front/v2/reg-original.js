const subscriptionTypeSelector = document.getElementById('subscriptionType');
const trainerSelector = document.getElementById('trainer');
const discountInput = document.getElementById('discount');
const cashOutElem = document.getElementById('cashOut');
const paymentTypeInput = document.getElementById('paymentType');

// Second set of elements
const subscriptionTypeSelector2 = document.getElementById('subscriptionType2');
const trainerSelector2 = document.getElementById('trainer2');
const discountInput2 = document.getElementById('discount2');
const cashOutElem2 = document.getElementById('cashOut2');
const paymentTypeInput2 = document.getElementById('paymentType2');

let subTypes = [];
let coaches = [];
let currentPrice = 0;

// Original event listeners
document.getElementById("register-button").addEventListener('click', register);
subscriptionTypeSelector.addEventListener('change', (event) => updateCashOut(event, cashOutElem, discountInput, subscriptionTypeSelector));
discountInput.addEventListener('change', (event) => addDiscount(event, cashOutElem, discountInput, subscriptionTypeSelector));

// New event listeners for the second set of elements
subscriptionTypeSelector2.addEventListener('change', (event) => updateCashOut(event, cashOutElem2, discountInput2, subscriptionTypeSelector2));
discountInput2.addEventListener('change', (event) => addDiscount(event, cashOutElem2, discountInput2, subscriptionTypeSelector2));

async function register() {
    const member = populateMember();
    const subType = getCurrentType(subscriptionTypeSelector);
    const subTypeid = subType.id;
    const payment = createPayment(subType.price, discountInput, paymentTypeInput);
    const notes = document.getElementById("notes").value;
    const privateTrainer = getCoach(trainerSelector);

    const response = await saveSub(payment, subTypeid, member, notes, privateTrainer);
    if (response.status >= 200 && response.status < 300) {
        clearInputs();
        cashOutElem.textContent = "";
    }
    console.log("clicked register");
}

function getIdFromSelector(selectorElem) {
    const value = selectorElem.value;
    return value === "" ? 0 : value;
}

function getCurrentType(selectorElem) {
    const id = selectorElem.value;
    if (!id) {
        return null;
    }
    const type = findSelectedObjectFromListById(id, subTypes);
    return type;
}

function updateCashOut(event, cashOutElem, discountInput, selectorElem) {
    const type = getCurrentType(selectorElem);
    if (type) {
        currentPrice = type.price;
        cashOutElem.textContent = currentPrice - discountInput.value;
    }
}

function addDiscount(event, cashOutElem, discountInput, selectorElem) {
    const discountValue = parseInt(event.target.value);
    if (!isNaN(selectorElem.value)) {
        cashOutElem.textContent = currentPrice - discountValue;
    }
}

function populateMember() {
    const member = {
        name: document.getElementById('fullName').value.trim(),
        gender: getElementValueOrNull('gender'),
        birthDate: document.getElementById('birthDate').value,
        phone: document.getElementById('phoneNumber').value.trim()
    };
    return member;
}

async function populateSubscriptionTypes() {
    try {
        const response = await getAllSubscriptionTypes();
        subTypes = response.body;

        subscriptionTypeSelector.innerHTML = '<option value="" disabled selected>Select Type</option>';
        subscriptionTypeSelector2.innerHTML = '<option value="" disabled selected>Select Type</option>';

        subTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type.id;
            option.textContent = `${type.name} - ${type.periodString} - $${type.price}`;
            subscriptionTypeSelector.appendChild(option);
            subscriptionTypeSelector2.appendChild(option.cloneNode(true));
        });
    } catch (error) {
        console.error('Error fetching subscription types:', error);
    }
}

async function populateTrainerNames() {
    try {
        const response = await getAllCoaches();
        coaches = response.body;

        trainerSelector.innerHTML = '<option value="" selected>None</option>';
        trainerSelector2.innerHTML = '<option value="" selected>None</option>';

        coaches.forEach(coach => {
            const option = document.createElement('option');
            option.value = coach.id;
            option.textContent = coach.name;
            trainerSelector.appendChild(option);
            trainerSelector2.appendChild(option.cloneNode(true));
        });
    } catch (error) {
        console.error('Error fetching trainer names:', error);
    }
}

function createPayment(originalPrice, discountInput, paymentTypeInput) {
    const paymentType = getElementValueOrNull(paymentTypeInput.id);
    const discount = discountInput.value;

    return {
        originalPrice: originalPrice,
        paymentType: paymentType,
        discount: discount
    };
}

function getCoach(selectorElem) {
    const coachId = getIdFromSelector(selectorElem);
    if (coachId) {
        return coaches.find(coach => coach.id === parseInt(coachId));
    }
    return null;
}

window.onload = function() {
    populateSubscriptionTypes();
    populateTrainerNames();
};
