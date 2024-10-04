
const saveMemberEndPoint = "/members"
let currentMember = null

document.getElementById('memberForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    console.log("submit clicked")
    // Clear previous errors
    document.getElementById('nameError').textContent = '';
    document.getElementById('phoneError').textContent = '';

    const nameElement = document.getElementById('name');
    const phoneElement = document.getElementById('phone');
    const name = nameElement.value.trim();
    const phone = phoneElement.value.trim();
    phoneElement.value = '';
    nameElement.value = '';
    
    const memberData = {
        name: name,
        phone: phone
    }

    saveMember(memberData)

});


// Toggle form visibility
const formContainer = document.getElementById('formContainer');
let isFormVisible = false; // Track form visibility state

// Handle member search
const searchMemberButton = document.getElementById('searchMemberButton');
const memberInfoDiv = document.getElementById('memberInfo');

searchMemberButton.addEventListener('click', async function() {
    const memberId = document.getElementById('memberId').value;

    if (!memberId) {
        alert('Please enter a member ID');
        return;
    }

    try {
        // Replace with the actual API endpoint for fetching a member by ID
        // const response = await apiGet(`/members/${memberId}`);

        const response = await findMemberById(memberId)
        console.log(response)
        // Assuming response contains: { name, phone, attendedDays, remainingDaysCount }
        currentMember = response;

        // Display member details
        memberInfoDiv.innerHTML = `
            <h3>Member Details</h3>
            <p><strong>Name:</strong> ${currentMember.name}</p>
            <p><strong>Phone:</strong> ${currentMember.phone}</p>
            <p><strong>Attended Days:</strong> ${currentMember.attendedDays.join(', ')}</p>
            <p><strong>Remaining Days:</strong> ${currentMember.remainingDaysCount}</p>
        `;
    } catch (error) {
        alert('Failed to fetch member. Please try again.');
        console.error(error);
    }
});

// Handle Add Session (can be extended later)
const addSessionButton = document.getElementById('addSessionButton');

addSessionButton.addEventListener('click', function() {

    if (!currentMember) {
        alert("No member selected")
        return;
    }

    const memberId = currentMember.id

    addAteendanceToSub(memberId)

    // alert('Add session functionality not yet implemented.');
});


async function findMemberById(params) {
    return member = {
        id: params,
        phone: "no phone",
        name: "sba7o"
    }
}